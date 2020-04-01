package aido.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;

/**
 * Created by sumeendranath on 22/09/16.
 */
public class DrawView extends View {

    private final Paint drawPaint;
    private       float size;


    int _x = 0;
    int _y = 0;
    int _radius = 0;


    Canvas _canvas;

    public DrawView(Context context, int color, int x, int y, int radius) {
        super(context);
        drawPaint = new Paint();
        drawPaint.setColor(color);
        drawPaint.setStyle(Paint.Style.STROKE);

        drawPaint.setAntiAlias(true);

        _x = x;
        _y = y;
        _radius = radius;

        /*SetDelay sd = new SetDelay(10000);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                _canvas.drawColor(0, PorterDuff.Mode.CLEAR);
               // _canvas.drawCircle(500, 500, 500, drawPaint);

            }
        });*/
    }


    void set(int x, int y, int rad)
    {
        _x = x; _y = y; _radius = rad;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(_x, _y, _radius, drawPaint);

        _canvas = canvas;
    }



}
