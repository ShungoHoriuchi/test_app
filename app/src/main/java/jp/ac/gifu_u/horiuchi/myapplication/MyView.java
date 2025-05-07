package jp.ac.gifu_u.horiuchi.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {
    // コンストラクタ（ここではこの引数のものを追加）
    public MyView(Context context) {
        super(context);
    }
    // ビューの描画を行うときに呼ばれるメソッド
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // どのように描画するかを指定する Paint オブジェクトを作成
        Paint paint = new Paint();
        paint.setColor(Color.BLUE); // 描画色を青に指定
        // (10,20)から(30,40)へ青線を描画
        canvas.drawLine(10, 20, 30, 40, paint);

        // Bitmap オブジェクトを作成
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.takoyaki_yatai);
        // (50,200) に画像を描画
        canvas.drawBitmap(bitmap, 50, 200, null);

    }
}
