package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kyoukasuigetsu on 20/02/2015.
 */

public class Drawing extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int colour;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private Room room;

    int width,height;

    private final int THIN = 10, MEDIUM = 20, THICK = 30;

    public Drawing(Context context,AttributeSet attrs,Room newRoom) {
        super(context,attrs);
        room = newRoom;
        setupDrawing();
    }

    private void setupDrawing() {
        drawPaint = new Paint();
        drawPath = new Path();

        colour = Color.BLACK;

        drawPaint.setColor(colour);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(MEDIUM);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

        super.onSizeChanged(w,h,oldw,oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();

                new EndpointsPostTask().execute(new Pair<Context, String>(null, room.getName() + ";=;" + drawPaint.toString() + ";=;"
                                                                                + drawPath.toString() + ";=;" + drawCanvas.toString()));
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public  boolean onTouchEventVar(Path newPath, Canvas newCanvas, Paint newPaint) {
        newCanvas.drawPath(newPath, newPaint);
        newPath.reset();

        invalidate();
        return true;
    }

    public void setColour(int newColour) {
        colour = newColour;
        drawPaint.setColor(colour);
    }

    public void setSize(String size) {
        if(size.equalsIgnoreCase("   Thin   "))
            drawPaint.setStrokeWidth(THIN);
        else if(size.equalsIgnoreCase("Medium"))
            drawPaint.setStrokeWidth(MEDIUM);
        else if(size.equalsIgnoreCase("  Thick  "))
            drawPaint.setStrokeWidth(THICK);
    }

    public void clearScreen() {
        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    public void drawFromVars(Path newPath, Canvas newCanvas, Paint newPaint) {
        onTouchEventVar(newPath,newCanvas,newPaint);
    }
}
