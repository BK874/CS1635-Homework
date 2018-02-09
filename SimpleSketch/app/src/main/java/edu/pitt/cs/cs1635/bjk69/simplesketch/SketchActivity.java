package edu.pitt.cs.cs1635.bjk69.simplesketch;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SketchActivity extends AppCompatActivity {
    public static Paint mPaint;
    MyView mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mv = new MyView(this);
        mv.setDrawingCacheEnabled(true);
        setContentView(mv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
    }

    public void colorChanged(int color) {
        mPaint.setColor(color);
    }


    private static final int CLEAR_MENU_ID = Menu.FIRST;
    private static final int ERASE_MENU_ID = Menu.FIRST + 1;
    private static final int RED_MENU_ID = Menu.FIRST + 2;
    private static final int BLUE_MENU_ID = Menu.FIRST + 3;
    private static final int GREEN_MENU_ID = Menu.FIRST + 4;
    private static final int YELLOW_MENU_ID = Menu.FIRST + 5;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, CLEAR_MENU_ID, 0, "Clear").setShortcut('5', 'z');
        menu.add(0, ERASE_MENU_ID, 0, "Erase").setShortcut('5', 'z');
        menu.add(0, RED_MENU_ID, 0, "Red").setShortcut('5', 'r');
        menu.add(0, BLUE_MENU_ID, 0, "Blue").setShortcut('5', 'b');
        menu.add(0, GREEN_MENU_ID, 0, "Green").setShortcut('5', 'g');
        menu.add(0, YELLOW_MENU_ID, 0, "Yellow").setShortcut('5', 'y');

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (item.getItemId()) {
            case CLEAR_MENU_ID:
                mv.clearDrawing();
                return true;

            case ERASE_MENU_ID:
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                mPaint.setAlpha(0x80);
                return true;

            case RED_MENU_ID:
                colorChanged(0xFFFF0000);
                return true;

            case BLUE_MENU_ID:
                colorChanged(0xFF0000FF);
                return true;

            case GREEN_MENU_ID:
                colorChanged(0xFF00FF00);
                return true;

            case YELLOW_MENU_ID:
                colorChanged(0xFFFFFF00);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}