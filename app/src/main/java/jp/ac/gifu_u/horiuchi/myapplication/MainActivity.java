package jp.ac.gifu_u.horiuchi.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(new MyView(this));
//        //EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
////        Button b = (Button) findViewById(R.id.button);
////        b.setOnClickListener(this);
//
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });
//    }
//
////    @Override
////    public void onClick(View v) {
////        showToast("アプリを終了しました");
////        finish();
////    }
////
////    private void showToast(String message) {
////        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
////    }
//
////    public void onResume() {
////        super.onResume();
////        // 明るさセンサ(TYPE_LIGHT)のリストを取得
////        List<Sensor> sensors =
////                manager.getSensorList(Sensor.TYPE_LIGHT);
////        // ひとつ以上見つかったら、最初のセンサを取得してリスナーに登録
////        if (sensors.size() != 0) {
////            Sensor sensor = sensors.get(0);
////            manager.registerListener(
////                    this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
////        }
////    }
////
////    protected void onPause() {
////        super.onPause();
////        // 一時停止の際にリスナー登録を解除
////        manager.unregisterListener(this);
////    }
////
////    public void onAccuracyChanged(Sensor arg0, int arg1) {
////    }
////    public void onSensorChanged(SensorEvent arg0) {
////        // 明るさセンサが変化したとき
////        if (arg0.sensor.getType() == Sensor.TYPE_LIGHT) {
////            // 明るさの値（単位ルクス）を取得
////            float intensity = arg0.values[0];
////            // 結果をテキストとして表示
////            String str = Float.toString(intensity) + "ルクス";
////            TextView textview =
////                    (TextView) findViewById(R.id.status_text);
////            textview.setText(str);
////        }
////    }
//
//}

public class MainActivity extends AppCompatActivity{
    private PreviewView previewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewView = findViewById(R.id.previewView);

        // パーミッションのチェック
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "カメラの許可が必要です", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(this, cameraSelector, preview);
    }

    //@Override
//    public void onClick(View v) {
//        ImageCapture.OutputFileOptions outputFileOptions =
//                new ImageCapture.OutputFileOptions.Builder(new File(...)).build();
//        imageCapture.takePicture(outputFileOptions, cameraExecutor,
//                new ImageCapture.OnImageSavedCallback() {
//                    @Override
//                    public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
//                        // insert your code here.
//                    }
//                    @Override
//                    public void onError(ImageCaptureException error) {
//                        // insert your code here.
//                    }
//                }
//        );
//    }
}