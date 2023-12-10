package edu.northeastern.afinal.ui.scan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.northeastern.afinal.R;
import edu.northeastern.afinal.databinding.FragmentScanBinding;
import edu.northeastern.afinal.ui.browse.SearchResultFragment;
import com.google.ar.core.Anchor;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ScanFragment extends Fragment {

    private FragmentScanBinding binding;
    private TextureView textureView;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private static final String ARG_OBJECT_ID = "object_id";
    private String objectId = null;
    private ArFragment arFragment;
    private ModelRenderable cubeRenderable;
    private TextView dimensionsTextView;
    private Button captureButton, lockButton;
    private TransformableNode cubeNode; // Reference to the cube node
    private boolean dimensionsLocked = false;
    private float lockedWidth, lockedHeight, lockedDepth;
    private boolean returningFromSearch = false;

    private FragmentManager fragmentManager;

    public static ScanFragment newInstance(@Nullable String objectId) {
        ScanFragment fragment = new ScanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OBJECT_ID, objectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            objectId = getArguments().getString(ARG_OBJECT_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupArFragment();

        dimensionsTextView = root.findViewById(R.id.dimensions_text_view);
        captureButton = root.findViewById(R.id.button_capture);
        captureButton.setOnClickListener(v -> takeArScreenshot());

        Button jumpButton = root.findViewById(R.id.button_jump);
        jumpButton.setOnClickListener(v -> {
            // Values in inches
            float minWidthInInches = 0.0f; // Example minimum width
            float maxWidthInInches = 100.0f; // Example maximum width
            float minHeightInInches = 0.0f; // Example minimum height
            float maxHeightInInches = 100.0f; // Example maximum height
            float minDepthInInches = 0.0f;  // Example minimum depth
            float maxDepthInInches = 100.0f;  // Example maximum depth

            // Now, you can navigate to the search fragment with min and max values in inches
            navigateToSearchFragment(
                    minWidthInInches, maxWidthInInches,
                    minHeightInInches, maxHeightInInches,
                    minDepthInInches, maxDepthInInches
            );
        });


        textureView = binding.cameraPreview;
        textureView.setSurfaceTextureListener(textureListener);

        fragmentManager = getChildFragmentManager();
        arFragment = (ArFragment) fragmentManager.findFragmentById(R.id.ar_fragment);

        //todo: load furniture
        //todo: if !objectId==null && !objectId.isEmpty()
        //replace ID with objectID
//        loadFurnitureModel("0");

        //load a cube
        FragmentManager fragmentManager = getChildFragmentManager();
        arFragment = (ArFragment) fragmentManager.findFragmentById(R.id.ar_fragment);
        arFragment.setOnTapPlaneGlbModel("cube.glb",null);



//        ModelRenderable.builder()
//                .setSource(getContext(), Uri.parse("cube.glb"))
//                .build()
//                .thenAccept(renderable -> cubeRenderable = renderable)
//                .exceptionally(throwable -> {
//                    Log.e("ScanFragment", "Error loading cube model", throwable);
//                    return null;
//                });

        return root;
    }

    private void setupArFragment() {
        arFragment = (ArFragment) getChildFragmentManager().findFragmentById(R.id.ar_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> updateDimensionTextView());
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            // Log statement to check if the listener is triggered
            Log.d("ScanFragment", "Tap detected on AR plane");

            if (cubeRenderable == null || dimensionsLocked) return;

            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());

            cubeNode = new TransformableNode(arFragment.getTransformationSystem());
            cubeNode.setParent(anchorNode);
            cubeNode.setRenderable(cubeRenderable);
            cubeNode.select();
        });

    }

    private void updateDimensionTextView() {
        if (cubeNode != null && !dimensionsLocked) {
            Vector3 scale = cubeNode.getWorldScale();
            String dimensionsText = String.format(
                    "Width: %.2f m, Height: %.2f m, Depth: %.2f m",
                    scale.x, scale.y, scale.z
            );
            dimensionsTextView.setText(dimensionsText);
        } else if (dimensionsLocked) {
            String lockedDimensionsText = String.format(
                    "Locked Dimensions - Width: %.2f m, Height: %.2f m, Depth: %.2f m",
                    lockedWidth, lockedHeight, lockedDepth
            );
            dimensionsTextView.setText(lockedDimensionsText);
        }
    }

    private void navigateToSearchFragment(
            float minWidthInInches, float maxWidthInInches,
            float minHeightInInches, float maxHeightInInches,
            float minDepthInInches, float maxDepthInInches
    ) {
        NavController navController = Navigation.findNavController(requireView());
        Bundle args = new Bundle();
        args.putString(SearchResultFragment.ARG_KEYWORD, "desk");
        args.putString(SearchResultFragment.ARG_MIN_WIDTH, String.valueOf(minWidthInInches));
        args.putString(SearchResultFragment.ARG_MAX_WIDTH, String.valueOf(maxWidthInInches));
        args.putString(SearchResultFragment.ARG_MIN_HEIGHT, String.valueOf(minHeightInInches));
        args.putString(SearchResultFragment.ARG_MAX_HEIGHT, String.valueOf(maxHeightInInches));
        args.putString(SearchResultFragment.ARG_MIN_DEPTH, String.valueOf(minDepthInInches));
        args.putString(SearchResultFragment.ARG_MAX_DEPTH, String.valueOf(maxDepthInInches));
        navController.navigate(R.id.action_scanFragment_to_searchResultFragmentDimensions, args);

        // Set the flag to true as we are navigating to the search screen
        returningFromSearch = true;
    }

    private int screenshotCount = 0; // A counter for the screenshots

    private void takeArScreenshot() {
        ArSceneView arSceneView = arFragment.getArSceneView();
        final Bitmap bitmap = Bitmap.createBitmap(arSceneView.getWidth(), arSceneView.getHeight(), Bitmap.Config.ARGB_8888);

        PixelCopy.request(arSceneView, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String filename = userId + "_screenshot_" + (screenshotCount++) + ".jpg";
                saveBitmapToFileAndUpload(bitmap, filename);
                Toast.makeText(getContext(), "Screenshot successfully captured", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to capture screenshot", Toast.LENGTH_SHORT).show();
            }
        }, new Handler(Looper.getMainLooper()));
    }

    private void saveBitmapToFileAndUpload(Bitmap bitmap, String filename) {
        File file = new File(getContext().getExternalFilesDir(null), filename);
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            uploadToFirebase(Uri.fromFile(file), filename);
        } catch (IOException e) {
            Log.e("ScanFragment", "Error saving bitmap", e);
        }
    }


    private void uploadToFirebase(Uri fileUri, String filename) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("plans/" + filename);

        storageRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                        String image_url = String.valueOf(storage.getReference().child("plans/" + filename));

                        // Create a new plan object
                        Map<String, Object> newPlan = new HashMap<>();
                        newPlan.put("furniture-id", 0);
                        newPlan.put("image", image_url);
                        newPlan.put("name", "myplan" + Math.random());
                        newPlan.put("user-id", FirebaseAuth.getInstance().getCurrentUser().getUid());

                        // Write the new plan to the Realtime Database
                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("decor-sense/plans");
                        databaseRef.push().setValue(newPlan)
                                .addOnSuccessListener(aVoid -> Log.d("ScanFragment", "Plan added successfully"))
                                .addOnFailureListener(e -> Log.e("ScanFragment", "Failed to add plan", e));

                    }).addOnFailureListener(e -> {
                        Log.e("ScanFragment", "Failed to get download URL", e);
                    });

                    Log.d("ScanFragment", "Screenshot uploaded successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("ScanFragment", "Upload failed", e);
                });
    }


//    private void uploadToFirebase(Uri fileUri, String filename) {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference().child("plans/" + filename);
////        Log.d("Storage Link", String.valueOf(storageRef));
//        storageRef.putFile(fileUri)
//                .addOnSuccessListener(taskSnapshot -> {
//                    Log.d("ScanFragment", "Screenshot uploaded successfully");
//                })
//                .addOnFailureListener(e -> {
//                    Log.e("ScanFragment", "Upload failed", e);
//                });
//        int furniture_id = 0;
//        String image_url = String.valueOf(storageRef);
//        String name = "myplan"+Math.random();
//        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//    }


// The uploadToFirebase method remains the same as provided previously


    private void freezeScreenWithBitmap(Bitmap bitmap) {
        if (textureView.isAvailable()) {
            final Canvas canvas = textureView.lockCanvas();
            if (canvas != null) {
                canvas.drawBitmap(bitmap, 0, 0, null);
                textureView.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void suggestProducts(float distance) {
        // Implement your logic to suggest products based on the distance
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            switch (ArCoreApk.getInstance().requestInstall(getActivity(), true)) {
                case INSTALLED:
                    // ARCore is installed and supported on this device
                    startBackgroundThread();
                    if (textureView.isAvailable()) {
                        openCamera();
                    } else {
                        textureView.setSurfaceTextureListener(textureListener);
                    }
                    break;
                case INSTALL_REQUESTED:
                    // ARCore installation requested. Return to onResume after installation.
                    return;
            }
        } catch (UnavailableUserDeclinedInstallationException e) {
            // User declined ARCore installation.
            Toast.makeText(getContext(), "ARCore is required for this feature", Toast.LENGTH_LONG).show();
        } catch (UnavailableDeviceNotCompatibleException e) {
            // Current device is not compatible with ARCore.
            Toast.makeText(getContext(), "ARCore is not supported on this device", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // Generic exception handling for other types of exceptions.
            Toast.makeText(getContext(), "ARCore encountered an error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (returningFromSearch) {
            captureButton.setEnabled(true);
            dimensionsLocked = false;
            returningFromSearch = false; // Reset the flag
        } else {
            // If not returning from search, deactivate the capture button
            captureButton.setEnabled(false);
        }
    }


    @Override
    public void onPause() {
        stopBackgroundThread();
        closeCamera();
        super.onPause();
        captureButton.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            imageDimension = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(SurfaceTexture.class)[0];
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Consider requesting the permission here
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            if (cameraDevice != null) {
                cameraDevice.close();
                cameraDevice = null;
            }
        }
    };

    private void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if (null == cameraDevice) {
                        return;
                    }
                    ScanFragment.this.cameraCaptureSession = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    // Handle configuration failure
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if (cameraDevice == null) {
            return;
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Handle size changes if necessary
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            // Update your view if required
        }


    };


    private void loadFurnitureModel(String furnitureID)
    {
        String furnitureModelPath=String.format("gs://numadfa23-group5.appspot.com/furniture/%s/model/model.glb",furnitureID);
        //download model from firebase storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(furnitureModelPath);
        File localFile = new File(requireContext().getFilesDir(), furnitureID + ".glb");

        storageRef.getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> {
                    // Model downloaded successfully
                    // Use the local path with Sceneform
                    String localPath = localFile.getAbsolutePath();
                    arFragment.setOnTapPlaneGlbModel(localPath, null);
                })
                .addOnFailureListener(exception -> {
                    // Handle failed download
                    exception.printStackTrace();
                });
    }
}
