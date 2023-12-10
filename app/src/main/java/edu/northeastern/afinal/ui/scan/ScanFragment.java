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
    private String objectId = null;
    private ArFragment arFragment;
    private ModelRenderable cubeRenderable;
    private TextView dimensionsTextView;
    private Button captureButton, lockButton;
    private TransformableNode cubeNode; // Reference to the cube node
    private boolean dimensionsLocked = false;
    private float lockedWidth, lockedHeight, lockedDepth;
    private boolean returningFromSearch = false;

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
        ModelRenderable.builder()
                .setSource(getContext(), R.raw.cube)
                .build()
                .thenAccept(renderable -> cubeRenderable = renderable)
                .exceptionally(throwable -> {
                    Log.e("ScanFragment", "Error loading cube model", throwable);
                    return null;
                });

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

        lockButton = root.findViewById(R.id.button_lock);
        lockButton.setOnClickListener(v -> lockDimensions());

        Button jumpButton = root.findViewById(R.id.button_jump);
        jumpButton.setOnClickListener(v -> navigateToSearchFragment());

        textureView = binding.cameraPreview;
        textureView.setSurfaceTextureListener(textureListener);

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

    private void lockDimensions() {
        if (cubeNode != null && cubeNode.getParent() != null) {
            Vector3 scale = cubeNode.getWorldScale();
            lockedWidth = scale.x;
            lockedHeight = scale.y;
            lockedDepth = scale.z;
            dimensionsLocked = true;

            String lockedDimensionsText = String.format("Locked Dimensions - Width: %.2f m, Height: %.2f m, Depth: %.2f m", lockedWidth, lockedHeight, lockedDepth);
            dimensionsTextView.setText(lockedDimensionsText);
            Toast.makeText(getContext(), "Dimensions locked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Place the cube first", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDimensionTextView() {
        if (cubeNode != null && !dimensionsLocked) {
            Vector3 scale = cubeNode.getWorldScale();
            String dimensionsText = String.format("Width: %.2f m, Height: %.2f m, Depth: %.2f m", scale.x, scale.y, scale.z);
            dimensionsTextView.setText(dimensionsText);
        }
    }

    private void navigateToSearchFragment() {
        if (!dimensionsLocked) {
            Toast.makeText(getContext(), "Lock dimensions first", Toast.LENGTH_SHORT).show();
            return;
        }
        NavController navController = Navigation.findNavController(requireView());
        Bundle args = new Bundle();
        args.putString(SearchResultFragment.ARG_KEYWORD, "desk");
        args.putString(SearchResultFragment.ARG_MIN_WIDTH, String.valueOf(lockedWidth));
        args.putString(SearchResultFragment.ARG_MAX_WIDTH, String.valueOf(lockedWidth));
        args.putString(SearchResultFragment.ARG_MIN_HEIGHT, String.valueOf(lockedHeight));
        args.putString(SearchResultFragment.ARG_MAX_HEIGHT, String.valueOf(lockedHeight));
        args.putString(SearchResultFragment.ARG_MIN_DEPTH, String.valueOf(lockedDepth));
        args.putString(SearchResultFragment.ARG_MAX_DEPTH, String.valueOf(lockedDepth));
        navController.navigate(R.id.action_scanFragment_to_searchResultFragmentDimensions, args);

        // Set the flag to true as we are navigating to the search screen
        returningFromSearch = true;
    }

    private void takeArScreenshot() {
        ArSceneView arSceneView = arFragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(arSceneView.getWidth(), arSceneView.getHeight(), Bitmap.Config.ARGB_8888);

        // Use PixelCopy to copy the surface texture to the bitmap.
        PixelCopy.request(arSceneView, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                getActivity().runOnUiThread(() -> {
                    // Freeze the screen with the captured bitmap
                    freezeScreenWithBitmap(bitmap);
                });
            } else {
                Toast.makeText(getContext(), "Failed to capture screenshot", Toast.LENGTH_SHORT).show();
            }
        }, new Handler(Looper.getMainLooper()));
    }

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
}
