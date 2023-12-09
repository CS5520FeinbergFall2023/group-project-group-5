package edu.northeastern.afinal.ui.scan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.northeastern.afinal.R;
import edu.northeastern.afinal.databinding.FragmentScanBinding;
import edu.northeastern.afinal.ui.browse.SearchResultFragment;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.math.Vector3;

import java.util.Collections;

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
    private String objectId = null; // Default to null
    private ArFragment arFragment;
    private AnchorNode firstPointAnchorNode, secondPointAnchorNode;
    private ModelRenderable markerRenderable;
    private TextView distanceTextView;
    private Button captureButton;


    public static ScanFragment newInstance(@Nullable String objectId) {
        ScanFragment fragment = new ScanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OBJECT_ID, objectId); // Put the object ID in the arguments
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ModelRenderable.builder()
                .setSource(getContext(), Uri.parse("file:///android_asset/sphere.glb")) // Replace 'your_model.glb' with your model's filename
                .build()
                .thenAccept(renderable -> markerRenderable = renderable)
                .exceptionally(throwable -> {
                    Log.e("ScanFragment", "Error loading GLB model", throwable);
                    return null;
                });

        if (getArguments() != null) {
            objectId = getArguments().getString(ARG_OBJECT_ID); // Retrieve the object ID
//            Log.d("ScanFragment",objectId);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize AR Fragment
        setupArFragment();

        // Update the IDs to match those in the layout
        distanceTextView = root.findViewById(R.id.distance_text_view);
        captureButton = root.findViewById(R.id.button_capture);
        captureButton.setOnClickListener(v -> takeArScreenshot());

        // Set up jump button (if needed)
        Button jumpButton = root.findViewById(R.id.button_jump);
        jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:from scan page to search result page with keyword and dimensions
                NavController navController = Navigation.findNavController(requireView());
                Bundle args = new Bundle();
                 args.putString(SearchResultFragment.ARG_KEYWORD, "desk");
                 args.putString(SearchResultFragment.ARG_MIN_WIDTH, "0");
                 args.putString(SearchResultFragment.ARG_MAX_WIDTH, "50");
                 args.putString(SearchResultFragment.ARG_MIN_HEIGHT, "5");
                 args.putString(SearchResultFragment.ARG_MAX_HEIGHT, "20");
                 args.putString(SearchResultFragment.ARG_MIN_DEPTH, "5");
                 args.putString(SearchResultFragment.ARG_MAX_DEPTH, "10.5");
                navController.navigate(R.id.action_scanFragment_to_searchResultFragmentDimensions, args);
            }
        });

        textureView = binding.cameraPreview;
        textureView.setSurfaceTextureListener(textureListener);

        return root;
    }

    private void setupArFragment() {
        arFragment = (ArFragment) getChildFragmentManager().findFragmentById(R.id.ar_fragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if (firstPointAnchorNode == null) {
                firstPointAnchorNode = placeMarker(hitResult);
            } else if (secondPointAnchorNode == null) {
                secondPointAnchorNode = placeMarker(hitResult);
                float distance = calculateDistanceBetweenPoints(firstPointAnchorNode, secondPointAnchorNode);
                distanceTextView.setText(String.format("Distance: %.2f meters", distance));
                Log.d("ScanFragment", "Distance between points: " + distance + " meters");
                // Optionally, reset points for new measurement
                // firstPointAnchorNode = null;
                // secondPointAnchorNode = null;
            }
        });
    }

    private AnchorNode placeMarker(HitResult hitResult) {
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        // Attach a node with the marker model to the anchor
        if (markerRenderable != null) {
            Node markerNode = new Node();
            markerNode.setParent(anchorNode);
            markerNode.setRenderable(markerRenderable);
        }
        return anchorNode;
    }

    // New methods for AR functionality
    private AnchorNode placeAnchor(HitResult hitResult) {
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        return anchorNode;
    }

    private float calculateDistanceBetweenPoints(AnchorNode firstPoint, AnchorNode secondPoint) {
        Vector3 point1 = firstPoint.getWorldPosition();
        Vector3 point2 = secondPoint.getWorldPosition();
        return Vector3.subtract(point1, point2).length();
    }

    private void takeArScreenshot() {
        ArSceneView view = arFragment.getArSceneView();
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                // Save bitmap to a file or display it
                // Example: saveBitmapToFile(bitmap);
            } else {
                Toast.makeText(getContext(), "Failed to capture screenshot", Toast.LENGTH_SHORT).show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }

    private void suggestProducts(float distance) {
        // Implement your logic to suggest products based on the distance
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if ARCore is installed and up to date
        switch (ArCoreApk.getInstance().checkAvailability(getContext())) {
            case SUPPORTED_INSTALLED:
                // ARCore is installed and supported on this device
                break;
            case SUPPORTED_NOT_INSTALLED:
                // ARCore is not installed, prompt the user to install it
                break;
            case UNSUPPORTED_DEVICE_NOT_CAPABLE:
                // ARCore is not supported on this device
                break;
            // Handle other cases
        }
        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    public void onPause() {
        stopBackgroundThread();
        closeCamera();
        super.onPause();
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
}
