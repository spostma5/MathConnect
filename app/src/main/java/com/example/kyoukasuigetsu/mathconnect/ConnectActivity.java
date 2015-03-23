package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.zip.GZIPInputStream;


public class ConnectActivity extends ActionBarActivity {

    public static final String ROOM = "ROOM";
    public static final int TIME = 500;

    private ImageButton drawButton;
    private ImageButton shapeButton;
    private ImageButton mathButton;
    private ImageButton settingsButton;

    private RelativeLayout gridLayout;
    private RelativeLayout relativeLayout;
    private GridLayout gridLayout2;
    private GridLayout gridLayout3;
    private GridLayout gridLayout4;

    public int ind = 0;

    public static ConnectActivity connectActivity;

    public Vector<Drawing> drawingView;

    private Room room;

    private Paint paint;

    String oldPath = "";

    private int colour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectActivity = this;

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_connect);

        room = new Room(this.getIntent().getStringExtra(ROOM).split(";=;")[0],this.getIntent().getStringExtra(ROOM).split(";=;")[1],null);

        drawButton = (ImageButton)findViewById(R.id.drawButton);
        shapeButton = (ImageButton)findViewById(R.id.shapeButton);
        mathButton = (ImageButton)findViewById(R.id.mathButton);
        settingsButton = (ImageButton)findViewById(R.id.settingsButton);

        gridLayout = (RelativeLayout)findViewById(R.id.connectGridLayout);
        relativeLayout = (RelativeLayout)findViewById(R.id.RLConnect);
        gridLayout2 = (GridLayout)findViewById(R.id.connectGridLayout2);
        gridLayout3 = (GridLayout)findViewById(R.id.connectGridLayout3);
        gridLayout4 = (GridLayout)findViewById(R.id.connectGridLayout4);

        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout2.setVisibility(View.INVISIBLE);
        gridLayout3.setVisibility(View.INVISIBLE);
        gridLayout4.setVisibility(View.INVISIBLE);

        drawingView = new Vector<Drawing>();
        Drawing newDraw = (Drawing)findViewById(R.id.drawingView);
        drawingView.add(newDraw);
        drawingView.get(0).setRoom(room);

        colour = Color.BLACK;

        paint = new Paint();
        paint.setColor(colour);
        paint.setStrokeWidth(new Float(.6));
        paint.setStyle(Paint.Style.FILL);

        setButtonSizes();

        Timer t = new Timer();

        t.scheduleAtFixedRate(
                new TimerTask()
                {
                    public void run()
                    {
                        if(!room.getFriend().equals("null"))
                            new EndpointsGetTask().execute(new Pair<Context, String>(ConnectActivity.this, room.getName()));
                        else
                            new EndpointsRoomRefresh().execute(new Pair<Context, String>(ConnectActivity.this, room.getName()));
                    }
                },
                500,      // run first occurrence immediately
                TIME);  // time between runs
    }

    public void setRoom(String result) {
        room = new Room(result.split(";=;")[0],result.split(";=;")[1], this);
        drawingView.get(ind).setRoom(room);
    }

    @Override
    public void onBackPressed() {
        //DO NOTHING
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void returnHome(View view) {
        new EndpointsGoHome().execute(new Pair<Context, String>(ConnectActivity.this, room.getName()));
    }

    public void clearScreen(View view) {
        new EndpointsClearScreen().execute(new Pair<Context, String>(ConnectActivity.this, room.getName() + ";=;" + room.getFriend()));
        drawingView.get(ind).clearScreen();
    }

    private void setButtonSizes() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ViewGroup.LayoutParams lp;
        lp = drawButton.getLayoutParams();
        lp.width = size.x/4;
        lp.height = size.x/4;

        drawButton.setLayoutParams(lp);
        shapeButton.setLayoutParams(lp);
        mathButton.setLayoutParams(lp);
        settingsButton.setLayoutParams(lp);
    }

    public void onDrawButton(View view) {
        gridLayout2.setVisibility(View.INVISIBLE);
        gridLayout3.setVisibility(View.INVISIBLE);
        gridLayout4.setVisibility(View.INVISIBLE);

        if(gridLayout.getVisibility() == View.VISIBLE) {
            gridLayout.setVisibility(View.INVISIBLE);
            showDrawing();
        }
        else {
            gridLayout.setVisibility(View.VISIBLE);
            hideDrawing();
        }
    }

    public void onShapeButton(View view) {
        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout3.setVisibility(View.INVISIBLE);
        gridLayout4.setVisibility(View.INVISIBLE);

        if(gridLayout2.getVisibility() == View.VISIBLE) {
            gridLayout2.setVisibility(View.INVISIBLE);
            showDrawing();
        }
        else {
            gridLayout2.setVisibility(View.VISIBLE);
            hideDrawing();
        }
    }

    public void onMathButton(View view) {
        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout2.setVisibility(View.INVISIBLE);
        gridLayout4.setVisibility(View.INVISIBLE);

        if(gridLayout3.getVisibility() == View.VISIBLE) {
            gridLayout3.setVisibility(View.INVISIBLE);
            showDrawing();
        }
        else {
            gridLayout3.setVisibility(View.VISIBLE);
            hideDrawing();
        }
    }

    public void onSettingsButton(View view) {
        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout2.setVisibility(View.INVISIBLE);
        gridLayout3.setVisibility(View.INVISIBLE);

        if(gridLayout4.getVisibility() == View.VISIBLE) {
            gridLayout4.setVisibility(View.INVISIBLE);
            showDrawing();
        }
        else {
            gridLayout4.setVisibility(View.VISIBLE);
            hideDrawing();
        }
    }

    public void hideDrawing() {
        drawingView.get(ind).setVisibility(View.INVISIBLE);
    }

    public void showDrawing() {
        drawingView.get(ind).setVisibility(View.VISIBLE);
    }

    public void toggleColour(View view) {
        Button button = (Button)view;
        ColorDrawable mdraw = (ColorDrawable)button.getBackground();
        colour = mdraw.getColor();
        drawingView.get(ind).setColour(colour);
    }

    public void toggleSize(View view) {
        Button button = (Button)view;
        String size =  button.getText().toString();
        drawingView.get(ind).setSize(size);
    }

    public void drawFromGet(String data) throws IOException{
        String[] parts = data.split(";=;");
        String paths = parts[1];
        if(oldPath.equals(paths)) {
            //DO NOTHING
        }
        else {
            oldPath = paths;
            if(paths.equals("CLS")) {
                new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getName() + ";=;" + "null" + ";=;"
                        + "null" + ";=;" + "null"));

                drawingView.get(ind).clearScreen();
                drawingView.get(ind).invalidate();
                oldPath = "null";
            }
            else if(paths.equals("ADD")) {
                addPageRep();

                new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getName() + ";=;" + "null" + ";=;"
                        + "null" + ";=;" + "null"));

                oldPath = "null";
            }
            else if(paths.equals("NEXT")) {
                nextPageRep();

                new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getName() + ";=;" + "null" + ";=;"
                        + "null" + ";=;" + "null"));

                oldPath = "null";
            }
            else if(paths.equals("PREV")) {
                prevPageRep();

                new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getName() + ";=;" + "null" + ";=;"
                        + "null" + ";=;" + "null"));

                oldPath = "null";
            }
            else if(paths.startsWith("SQUARE")) {
                drawingView.get(ind).drawSquare(parts[0], paths, parts[2]);
            }
            else {
                paths = decompress(parts[1]);
                drawingView.get(ind).onTouchEventVar(parts[0], paths, parts[2]);
            }
        }
    }


    public static String decompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        System.out.println("Input String length : " + str.length());
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1")));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "ISO-8859-1"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
            outStr += line;
        }
        System.out.println("Output String lenght : " + outStr.length());
        return outStr;
    }

    public void addPage(View view) {
        if(ind == drawingView.size() - 1) {
            Drawing newDrawing = new Drawing(this,drawingView.get(ind).getAttribs());
            relativeLayout.addView(newDrawing, 0, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            drawingView.add(newDrawing);
            newDrawing.setRoom(drawingView.get(ind).getRoom());

            new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getFriend() + ";=;" + "null" + ";=;"
                    + "ADD" + ";=;" + "null"));

            setPage(ind+1);
        }
    }

    public void nextPage(View view) {
        if(ind != drawingView.size()-1) {
            new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getFriend() + ";=;" + "null" + ";=;"
                    + "NEXT" + ";=;" + "null"));

            setPage(ind+1);
        }
    }

    public void prevPage(View view) {
        if(ind != 0) {
            new EndpointsPostRaw().execute(new Pair<Context, String>(null, room.getFriend() + ";=;" + "null" + ";=;"
                    + "PREV" + ";=;" + "null"));

            setPage(ind-1);
        }
    }

    public void addPageRep() {
        Drawing newDrawing = new Drawing(this,drawingView.get(ind).getAttribs());
        relativeLayout.addView(newDrawing, 0, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        drawingView.add(newDrawing);
        newDrawing.setRoom(drawingView.get(ind).getRoom());

        setPageRep(ind+1);
    }

    public void nextPageRep() {
        setPageRep(ind+1);
    }

    public void prevPageRep() {
        setPageRep(ind-1);
    }

    public void setPage(int index) {
        drawingView.get(ind).setVisibility(View.GONE);
        ind = index;
    }

    public void setPageRep(int index) {
        drawingView.get(ind).setVisibility(View.GONE);
        ind = index;
        drawingView.get(ind).setVisibility(View.VISIBLE);
    }

    public void drawSquare(View view) {
        drawingView.get(ind).shapeMode = 1;
    }

    public void drawCircle(View view) {

    }

    public void drawLine(View view) {

    }
}
