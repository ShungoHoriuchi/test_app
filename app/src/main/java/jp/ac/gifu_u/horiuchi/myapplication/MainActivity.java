package jp.ac.gifu_u.horiuchi.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , SensorEventListener {

    // センサを管理するマネージャ
    private SensorManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new MyView(this));
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        Button b = (Button) findViewById(R.id.button);
//        b.setOnClickListener(this);

        // マネージャを取得
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    @Override
    public void onClick(View v) {
        showToast("アプリを終了しました");
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onResume() {
        super.onResume();
// 明るさセンサ(TYPE_LIGHT)のリストを取得
        List<Sensor> sensors =
                manager.getSensorList(Sensor.TYPE_LIGHT);
// ひとつ以上見つかったら、最初のセンサを取得してリスナーに登録
        if (sensors.size() != 0) {
            Sensor sensor = sensors.get(0);
            manager.registerListener(
                    this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected void onPause() {
        super.onPause();
// 一時停止の際にリスナー登録を解除
        manager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }
    public void onSensorChanged(SensorEvent arg0) {
// 明るさセンサが変化したとき
        if (arg0.sensor.getType() == Sensor.TYPE_LIGHT) {
// 明るさの値（単位ルクス）を取得
            float intensity = arg0.values[0];
// 結果をテキストとして表示
            String str = Float.toString(intensity) + "ルクス";
            TextView textview =
                    (TextView) findViewById(R.id.status_text);
            textview.setText(str);
        }
    }

}