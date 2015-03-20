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

import java.util.Timer;
import java.util.TimerTask;


public class ConnectActivity extends ActionBarActivity {

    public static final String ROOM = "ROOM";
    public static final int TIME = 500;

    private ImageButton drawButton;
    private ImageButton shapeButton;
    private ImageButton mathButton;
    private ImageButton settingsButton;

    private RelativeLayout gridLayout;
    private GridLayout gridLayout2;
    private GridLayout gridLayout3;
    private GridLayout gridLayout4;

    public static ConnectActivity connectActivity;

    public Drawing drawingView;

    private Room room;

    private Paint paint;

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
        gridLayout2 = (GridLayout)findViewById(R.id.connectGridLayout2);
        gridLayout3 = (GridLayout)findViewById(R.id.connectGridLayout3);
        gridLayout4 = (GridLayout)findViewById(R.id.connectGridLayout4);

        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout2.setVisibility(View.INVISIBLE);
        gridLayout3.setVisibility(View.INVISIBLE);
        gridLayout4.setVisibility(View.INVISIBLE);


        drawingView = (Drawing)findViewById(R.id.drawingView);
        drawingView.setRoom(room);

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
        drawingView.setRoom(room);
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
        /*
        Intent intent = new Intent(this,HomePage.class);
        intent.putExtra(LoginActivity.USER,user);
        startActivity(intent);
        */
    }

    public void clearScreen(View view) {
        drawingView.clearScreen();
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
        drawingView.setVisibility(View.INVISIBLE);
    }

    public void showDrawing() {
        drawingView.setVisibility(View.VISIBLE);
    }

    public void toggleColour(View view) {
        Button button = (Button)view;
        ColorDrawable mdraw = (ColorDrawable)button.getBackground();
        colour = mdraw.getColor();
        drawingView.setColour(colour);
    }

    public void toggleSize(View view) {
        Button button = (Button)view;
        String size =  button.getText().toString();
        drawingView.setSize(size);
    }

    public void drawFromGet(String data) {
        String[] parts = data.split(";=;");
        drawingView.onTouchEventVar(parts[0],parts[1],parts[2]);
    }
}
