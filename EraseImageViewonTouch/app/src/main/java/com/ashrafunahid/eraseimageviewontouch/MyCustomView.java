package com.ashrafunahid.eraseimageviewontouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.View;

public class MyCustomView extends View {

    private Bitmap destBitmap;
    private Canvas destCanvas = new Canvas();
    private Paint destPaint = new Paint();
    private Path destPath = new Path();

    public MyCustomView(Context context) {
        super(context);

        Bitmap rawBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_image);

        destBitmap = Bitmap.createBitmap(rawBitmap.getWidth(), rawBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        destCanvas.setBitmap(destBitmap);
        destCanvas.drawBitmap(rawBitmap, 0, 0, null);

        destPaint.setAlpha(0);
        destPaint.setAntiAlias(true);
        destPaint.setStyle(Paint.Style.STROKE);
        destPaint.setStrokeJoin(Paint.Join.ROUND);
        destPaint.setStrokeCap(Paint.Cap.ROUND);
        destPaint.setStrokeWidth(50);
        destPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        destCanvas.drawPath(destPath, destPaint);
        canvas.drawBitmap(destBitmap, 0, 0, null);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                destPath.moveTo(xPos, yPos);
                break;
            case MotionEvent.ACTION_MOVE:
                destPath.lineTo(xPos, yPos);
                break;
            default:
                return false;

        }

        invalidate();
        return true;
    }
}
