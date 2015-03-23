package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
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
    private AttributeSet attributes;

    public int shapeMode = 0;
    private PointF start,finish;

    int width,height;

    private final int THIN = 10, MEDIUM = 20, THICK = 30;

    public Drawing(Context context,AttributeSet attrs) {
        super(context,attrs);

        attributes = attrs;

        oldPoints = "";
        points = "";
        setupDrawing();
    }

    public AttributeSet getAttribs() { return attributes; }

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

    public Room getRoom() { return room; }

    @Override
    public  boolean onTouchEvent(MotionEvent event) {
        Float touchX = event.getX();
        Float touchY = event.getY();

        if(shapeMode == 0) {
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
                    new EndpointsPostTask().execute(new Pair<Context, String>(null, room.getFriend() + ";=;" + drawPaint.getColor() + ";=;"
                            + points + ";=;" + drawPaint.getStrokeWidth()));
                    points = "";

                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    break;
                default:
                    return false;
            }
        }
        else if (shapeMode == 1) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    start = new PointF(touchX,touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //DO NOTHING
                    break;
                case MotionEvent.ACTION_UP:
                    finish = new PointF(touchX,touchY);

                    drawCanvas.drawRect(start.x,start.y,finish.x,finish.y,drawPaint);
                    new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getFriend() + ";=;" + drawPaint.getColor() + ";=;"
                            + "SQUARE:" + start.x + ":" + start.y + ":" + finish.x + ":" + finish.y + ";=;" + drawPaint.getStrokeWidth()));
                    invalidate();
                    shapeMode = 0;
                    break;
                default:
                    return false;
            }

        }
        else if (shapeMode == 2) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    start = new PointF(touchX,touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //DO NOTHING
                    break;
                case MotionEvent.ACTION_UP:
                    finish = new PointF(touchX,touchY);
                    double x = finish.x - start.x;
                    double y = finish.y - start.y;
                    double rad = Math.pow(x,2) + Math.pow(y,2);
                    rad = Math.sqrt(rad);
                    float radF = (float)rad;

                    drawCanvas.drawCircle(start.x,start.y,radF,drawPaint);
                    new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getFriend() + ";=;" + drawPaint.getColor() + ";=;"
                            + "CIRCLE:" + start.x + ":" + start.y + ":" + radF + ";=;" + drawPaint.getStrokeWidth()));
                    invalidate();
                    shapeMode = 0;
                    break;
                default:
                    return false;
            }
        }
        else if (shapeMode == 3) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    start = new PointF(touchX,touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //DO NOTHING
                    break;
                case MotionEvent.ACTION_UP:
                    finish = new PointF(touchX,touchY);

                    drawCanvas.drawLine(start.x,start.y,finish.x,finish.y,drawPaint);
                    new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getFriend() + ";=;" + drawPaint.getColor() + ";=;"
                            + "LINE:" + start.x + ":" + start.y + ":" + finish.x + ":" + finish.y + ";=;" + drawPaint.getStrokeWidth()));
                    invalidate();
                    shapeMode = 0;
                    break;
                default:
                    return false;
            }

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

            oldPoints = newPath;
            invalidate();
        } catch(Exception e) {
            //DO NOTHING
        }
        return true;
    }

    public boolean drawSquare(String newColour, String points, String newSize) {
        try {
            if(oldPoints.equals(points))
                return true;
            gPaint.setColor(Integer.parseInt(newColour));
            gPaint.setStrokeWidth(Float.parseFloat(newSize));
            gPath = new Path();

            String[] pairs = points.split(":");
            PointF gStart = new PointF(Float.parseFloat(pairs[1]),Float.parseFloat(pairs[2]));
            PointF gFinish = new PointF(Float.parseFloat(pairs[3]),Float.parseFloat(pairs[4]));

            drawCanvas.drawRect(gStart.x,gStart.y,gFinish.x,gFinish.y,gPaint);

            oldPoints = points;
            invalidate();
        } catch(Exception e) {
            //DO NOTHING
        }
        return true;
    }

    public boolean drawCircle(String newColour, String points, String newSize) {
        try {
            if(oldPoints.equals(points))
                return true;
            gPaint.setColor(Integer.parseInt(newColour));
            gPaint.setStrokeWidth(Float.parseFloat(newSize));
            gPath = new Path();

            String[] pairs = points.split(":");
            PointF gStart = new PointF(Float.parseFloat(pairs[1]),Float.parseFloat(pairs[2]));
            float gFinish = Float.parseFloat(pairs[3]);

            drawCanvas.drawCircle(gStart.x,gStart.y,gFinish,gPaint);

            oldPoints = points;
            invalidate();
        } catch(Exception e) {
            //DO NOTHING
        }
        return true;
    }

    public boolean drawLine(String newColour, String points, String newSize) {
        try {
            if(oldPoints.equals(points))
                return true;
            gPaint.setColor(Integer.parseInt(newColour));
            gPaint.setStrokeWidth(Float.parseFloat(newSize));
            gPath = new Path();

            String[] pairs = points.split(":");
            PointF gStart = new PointF(Float.parseFloat(pairs[1]),Float.parseFloat(pairs[2]));
            PointF gFinish = new PointF(Float.parseFloat(pairs[3]),Float.parseFloat(pairs[4]));

            drawCanvas.drawLine(gStart.x,gStart.y,gFinish.x,gFinish.y,gPaint);

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
