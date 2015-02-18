package com.example.kyoukasuigetsu.mathconnect;

import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ToggleButton;


public class ConnectActivity extends ActionBarActivity {

    private ImageButton drawButton;
    private ImageButton shapeButton;
    private ImageButton mathButton;
    private ImageButton settingsButton;

    private GridLayout gridLayout;
    private GridLayout gridLayout2;
    private GridLayout gridLayout3;
    private GridLayout gridLayout4;

    private GestureOverlayView gestureOverlayView;

    private ToggleButton[] colourToggles;

    private int colour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_connect);

        drawButton = (ImageButton)findViewById(R.id.drawButton);
        shapeButton = (ImageButton)findViewById(R.id.shapeButton);
        mathButton = (ImageButton)findViewById(R.id.mathButton);
        settingsButton = (ImageButton)findViewById(R.id.settingsButton);

        gridLayout = (GridLayout)findViewById(R.id.connectGridLayout);
        gridLayout2 = (GridLayout)findViewById(R.id.connectGridLayout2);
        gridLayout3 = (GridLayout)findViewById(R.id.connectGridLayout3);
        gridLayout4 = (GridLayout)findViewById(R.id.connectGridLayout4);

        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout2.setVisibility(View.INVISIBLE);
        gridLayout3.setVisibility(View.INVISIBLE);
        gridLayout4.setVisibility(View.INVISIBLE);

        gestureOverlayView = (GestureOverlayView)findViewById(R.id.gestureOverlayView);

        colour = Color.BLACK;

        colourToggles = new ToggleButton[9];
        colourToggles[0] = (ToggleButton)findViewById(R.id.toggleButton);
        colourToggles[1] = (ToggleButton)findViewById(R.id.toggleButton2);
        colourToggles[2] = (ToggleButton)findViewById(R.id.toggleButton3);
        colourToggles[3] = (ToggleButton)findViewById(R.id.toggleButton4);
        colourToggles[4] = (ToggleButton)findViewById(R.id.toggleButton5);
        colourToggles[5] = (ToggleButton)findViewById(R.id.toggleButton6);
        colourToggles[6] = (ToggleButton)findViewById(R.id.toggleButton7);
        colourToggles[7] = (ToggleButton)findViewById(R.id.toggleButton8);
        colourToggles[8] = (ToggleButton)findViewById(R.id.toggleButton9);

        setGestureOn();

       setButtonSizes();
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

        gestureOverlayView.setEnabled(false);

        if(gridLayout.getVisibility() == View.VISIBLE) {
            gridLayout.setVisibility(View.INVISIBLE);
           setGestureOn();
        }
        else
            gridLayout.setVisibility(View.VISIBLE);
    }

    public void onShapeButton(View view) {
        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout3.setVisibility(View.INVISIBLE);
        gridLayout4.setVisibility(View.INVISIBLE);

        gestureOverlayView.setEnabled(false);

        if(gridLayout2.getVisibility() == View.VISIBLE) {
            gridLayout2.setVisibility(View.INVISIBLE);
            setGestureOn();
        }
        else
            gridLayout2.setVisibility(View.VISIBLE);
    }

    public void onMathButton(View view) {
        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout2.setVisibility(View.INVISIBLE);
        gridLayout4.setVisibility(View.INVISIBLE);

        gestureOverlayView.setEnabled(false);

        if(gridLayout3.getVisibility() == View.VISIBLE) {
            gridLayout3.setVisibility(View.INVISIBLE);
            setGestureOn();
        }
        else
            gridLayout3.setVisibility(View.VISIBLE);
    }

    public void onSettingsButton(View view) {
        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout2.setVisibility(View.INVISIBLE);
        gridLayout3.setVisibility(View.INVISIBLE);

        gestureOverlayView.setEnabled(false);

        if(gridLayout4.getVisibility() == View.VISIBLE) {
            gridLayout4.setVisibility(View.INVISIBLE);
            setGestureOn();
        }
        else
            gridLayout4.setVisibility(View.VISIBLE);
    }

    public void setGestureOn() {
        gestureOverlayView.setEnabled(true);
        gestureOverlayView.setGestureVisible(true);
        gestureOverlayView.setUncertainGestureColor(colour);
    }

    public void toggleColour(View view) {
        Button selButton = (Button)view;
        if(selButton.getId() == R.id.toggleButton) {
            colour = colourToggles[0].getDrawingCacheBackgroundColor();
            gestureOverlayView.setGestureColor(colour);
            for(int i = 1; i < 9; i++) {
                colourToggles[i].setChecked(false);
            }
        }
    }
}
