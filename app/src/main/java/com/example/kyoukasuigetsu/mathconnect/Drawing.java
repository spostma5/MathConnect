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

    private Path drawPath, gPath;
    private Paint drawPaint, canvasPaint, gPaint;
    private int colour;
    private Canvas drawCanvas, mainCanvas;
    private Bitmap canvasBitmap;
    private Room room;
    private String points, oldPoints;

    int width,height;

    private final int THIN = 10, MEDIUM = 20, THICK = 30;

    public Drawing(Context context,AttributeSet attrs) {
        super(context,attrs);

        oldPoints = "";
        points = "";
        setupDrawing();
    }

    public void setRoom(Room newRoom) {
        room = newRoom;
    }

    private void setupDrawing() {
        drawPaint = new Paint();
        gPaint = new Paint();
        drawPath = new Path();

        colour = Color.BLACK;

        drawPaint.setColor(colour);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(MEDIUM);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        gPaint.setColor(colour);
        gPaint.setAntiAlias(true);
        gPaint.setStrokeWidth(MEDIUM);
        gPaint.setStyle(Paint.Style.STROKE);
        gPaint.setStrokeJoin(Paint.Join.ROUND);
        gPaint.setStrokeCap(Paint.Cap.ROUND);

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

        try {
            canvas.drawPath(gPath,gPaint);
        } catch(Exception e) {
            //DO NOTHING
        }
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event) {
        Float touchX = event.getX();
        Float touchY = event.getY();

        if(points.length() > 500) {
            new EndpointsPostTask().execute(new Pair<Context, String>(null, room.getName() + ";=;" + drawPaint.getColor() + ";=;"
                    + points + ";=;" + drawPaint.getStrokeWidth()));
            points = touchX.toString() + "&&&" + touchY.toString() + "===";
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                points += touchX.toString() + "&&&" + touchY.toString() + "===";
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                points += touchX.toString() + "&&&" + touchY.toString() + "===";
                break;
            case MotionEvent.ACTION_UP:
                new EndpointsPostTask().execute(new Pair<Context, String>(null, room.getName() + ";=;" + drawPaint.getColor() + ";=;"
                        + points + ";=;" + drawPaint.getStrokeWidth()));
                points = "";

                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public  boolean onTouchEventVar(String newColour, String newPath, String newSize) {
        try {
            if(oldPoints.equals(newPath))
                return true;
            gPaint.setColor(Integer.parseInt(newColour));
            gPaint.setStrokeWidth(Float.parseFloat(newSize));
            gPath = new Path();

            String[] pairs = newPath.split("===");
            gPath.moveTo(Float.parseFloat(pairs[0].split("&&&")[0]),Float.parseFloat(pairs[0].split("&&&")[1]));
            for(int i = 1; i < pairs.length; i++) {
                 gPath.lineTo(Float.parseFloat(pairs[i].split("&&&")[0]),Float.parseFloat(pairs[i].split("&&&")[1]));
            }

            drawCanvas.drawPath(gPath, gPaint);
            gPath.reset();

            oldPoints = points;
            invalidate();
        } catch(Exception e) {
            //DO NOTHING
        }
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
}
