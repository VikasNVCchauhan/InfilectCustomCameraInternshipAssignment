package com.example.infilectcustomcamerainternshipassignment.CustomCamera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.infilectcustomcamerainternshipassignment.FileHandeling.ImagePath;
import com.example.infilectcustomcamerainternshipassignment.MainActivity;
import com.example.infilectcustomcamerainternshipassignment.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import de.hdodenhof.circleimageview.CircleImageView;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CustomCameraClass extends AppCompatActivity implements View.OnClickListener {

    //Widgets
    private CircleImageView circleImageView;
    private ImageView imageViewSwitchCamera, imageViewFlash;
    private LinearLayout linearLayoutClickImage, linearLayoutSwitchCamera;
    private TextureView textureView;
    private TextView textView;

    public static final String CAMERA_FRONT = "1";
    public static final String CAMERA_BACK = "0";
    private String CURRENT_CAMERA = "0";
    private boolean isFlashSupported;
    private boolean isTorchOn;

    //Check Status orientation of o/p image
    private static final SparseArray ORIENTATION = new SparseArray();

    static {
        ORIENTATION.append(Surface.ROTATION_0, 90);
        ORIENTATION.append(Surface.ROTATION_90, 0);
        ORIENTATION.append(Surface.ROTATION_180, 270);
        ORIENTATION.append(Surface.ROTATION_270, 180);
    }

    private String cameraId = CAMERA_BACK;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder caprureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;

    //save to file
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;


    CameraDevice.StateCallback stateCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setIdForAllWidgets();
        Toast.makeText(this, "" + cameraId, Toast.LENGTH_SHORT).show();
        checkAppPermissions();
        setFilePath();
        setImageToCardView(0);          //Here we are getting recent captured image to set on bottom custom navigation

        assert textureView != null;

        textureView.setSurfaceTextureListener(textureListener);
        circleImageView.setOnClickListener(this);
        linearLayoutClickImage.setOnClickListener(this);
        linearLayoutSwitchCamera.setOnClickListener(this);
        imageViewFlash.setOnClickListener(this);
    }

    private void checkAppPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CAMERA_PERMISSION);
            return;
        }
    }

    private void setFilePath() {
        ImagePath imagePath = new ImagePath();
        File directory = new File(imagePath.NVCImages);
        if (!directory.exists()) {
            File wallpaperDirectory = new File("/sdcard/NVCImages/");
            wallpaperDirectory.mkdirs();
        }
        file = directory;
    }

    private void setIdForAllWidgets() {
        imageViewFlash = findViewById(R.id.image_view_flash_main_activity);
        circleImageView = findViewById(R.id.circle_image_view_camera_activity_main);
        linearLayoutClickImage = findViewById(R.id.linear_layout_click_to_capture_images_camera_activity);
        linearLayoutSwitchCamera = findViewById(R.id.linear_layout_rotate_camera_camera_activity);
        textureView = (TextureView) findViewById(R.id.texture_view_camera_activity_main);
        imageViewSwitchCamera = findViewById(R.id.image_view_switch_camera_camera_activity_main);
        textView = findViewById(R.id.text_view_switch_camera);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view == linearLayoutClickImage) {
            captureImage();
        } else if (view == circleImageView) {
            callGalleryClass();
        } else if (view == linearLayoutSwitchCamera) {
            Toast.makeText(this, "Working on This Functionality", Toast.LENGTH_SHORT).show();
//            switchCamera();
        } else if (view == imageViewFlash) {
            switchFlash();
        }
    }

    private void switchCamera() {
        if (cameraId.equals(CAMERA_BACK)) {
            cameraId = CAMERA_FRONT;
            imageViewSwitchCamera.setImageResource(R.drawable.ic_switch_camera_grey_24dp);
            textView.setText("FRONT");

//            stoPreview();
//            startBackgroundThread();
//            startBackgroundThread();
//            openCamera();

        } else {
            cameraId = CAMERA_BACK;
            imageViewSwitchCamera.setImageResource(R.drawable.ic_switch_camera_white_24dp);
            textView.setText("BACK");

//            stoPreview();
//            startBackgroundThread();
//            startBackgroundThread();
//            openCamera();
        }
    }

    private void callGalleryClass() {
        Intent intent = new Intent(CustomCameraClass.this, MainActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void captureImage() {
        if (cameraDevice == null) {
            return;
        } else {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraDevice.getId());
                Size[] jpegSize = null;
                if (cameraCharacteristics != null) {
                    jpegSize = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
                }
                //Capture image with custom size
                int width = 640;
                int height = 480;
                if (jpegSize != null && jpegSize.length > 0) {
                    width = jpegSize[0].getWidth();
                    height = jpegSize[0].getHeight();
                }
                ImageReader imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
                List<Surface> outputSurface = new ArrayList<>(2);
                outputSurface.add(imageReader.getSurface());
                outputSurface.add(new Surface(textureView.getSurfaceTexture()));

                final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureBuilder.addTarget(imageReader.getSurface());
                captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

                //check orientation base on device
                int rotation = getWindowManager().getDefaultDisplay().getRotation();
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, (Integer) ORIENTATION.get(rotation));

                setPathForSavingCapturedImage();            //Creating directory if not exist

                ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() {
                    @Override
                    public void onImageAvailable(ImageReader imageReader) {
                        Image image = null;
                        try {
                            image = imageReader.acquireLatestImage();
                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.capacity()];
                            buffer.get(bytes);
                            saveBytes(bytes);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (image != null) {
                                image.close();
                            }
                        }
                    }

                    private void saveBytes(byte[] bytes) throws IOException {
                        OutputStream outputStream = null;
                        try {
                            outputStream = new FileOutputStream(file);
                            outputStream.write(bytes);
                            setImageToCardView(1);
                        } finally {
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }
                    }
                };
                imageReader.setOnImageAvailableListener(onImageAvailableListener, mBackgroundHandler);
                final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                        super.onCaptureCompleted(session, request, result);
                        //Toast.makeText(MainActivity.this, "Saved " + file, Toast.LENGTH_SHORT).show();
                        createCameraPreview();
                    }
                };

                cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {

                        try {
                            cameraCaptureSession.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                    }
                }, mBackgroundHandler);

            } catch (@SuppressLint("NewApi") CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPathForSavingCapturedImage() {            //Here we are creating new directory if not exists
        String fileName = System.currentTimeMillis() + ".jpg";
        file = new File("/sdcard/NVCImages/", fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void setImageToCardView(int checker) {
        if (checker == 0) {
            if (file.exists()) {
                File[] imgCount;
                imgCount = file.listFiles();
                int position = (imgCount.length) - 1;
                Bitmap bmp = BitmapFactory.decodeFile(imgCount[position].getAbsolutePath());
                circleImageView.setImageBitmap(bmp);
            } else {
                circleImageView.setImageResource(R.drawable.horizontal_recycler_view_women);
                //Set placeholder in image view
            }
        } else {
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            circleImageView.setImageBitmap(bmp);
            Toast.makeText(this, "" + file, Toast.LENGTH_SHORT).show();
        }
        ///Now set this bitmap on imageview
    }

    private void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            caprureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            caprureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCSession) {
                    if (cameraDevice == null) {
                        return;
                    } else {
                        cameraCaptureSession = cameraCSession;
                        updatePreview();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(CustomCameraClass.this, "Changed", Toast.LENGTH_SHORT).show();
                }
            }, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if (cameraDevice == null) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            caprureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
            try {
                cameraCaptureSession.setRepeatingBurst(Collections.singletonList(caprureRequestBuilder.build()), null, mBackgroundHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchFlash() {
        try {
            if (cameraId.equals(CAMERA_BACK)) {
                if (isFlashSupported) {
                    if (isTorchOn) {
                        caprureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
                        cameraCaptureSession.setRepeatingRequest(caprureRequestBuilder.build(), null, null);
                        imageViewFlash.setImageResource(R.drawable.ic_flash_off_black_24dp);
                        isTorchOn = false;
                    } else {
                        caprureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
                        cameraCaptureSession.setRepeatingRequest(caprureRequestBuilder.build(), null, null);
                        imageViewFlash.setImageResource(R.drawable.ic_flash_on_black_24dp);
                        isTorchOn = true;
                    }
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void setupFlashButton() {
        if (cameraId.equals(CAMERA_BACK) && isFlashSupported) {
            imageViewFlash.setVisibility(View.VISIBLE);

            if (isTorchOn) {
                imageViewFlash.setImageResource(R.drawable.ic_flash_on_black_24dp);
            } else {
                imageViewFlash.setImageResource(R.drawable.ic_flash_off_black_24dp);
            }

        } else {
            imageViewFlash.setVisibility(View.GONE);
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {

            CameraCharacteristics chars = manager.getCameraCharacteristics(cameraId);
            Integer facing = chars.get(CameraCharacteristics.LENS_FACING);

            Boolean available = chars.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            isFlashSupported = available == null ? false : available;
            setupFlashButton();

            // Do something with the characteristics
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];

            //checking realtime permissions
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.GET_ACCOUNTS
                }, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallBack, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You can't use camera without permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        startBackgroundThread();
        super.onResume();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    protected void onPause() {
        stopBackgroundThread();
        super.onPause();
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

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

}
