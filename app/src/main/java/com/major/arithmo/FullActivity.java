package com.major.arithmo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class FullActivity extends Activity implements View.OnTouchListener {

    RelativeLayout relativeView1, relativeView2, relativeView3, relativeView4, relativeView5;
    Intent intent;
    //String in, out;
    AlertDialog.Builder helpBox;

    @Override
    protected void onResume() {
        relativeView1.setBackground(getDrawable(R.drawable.relativeview1));
        relativeView2.setBackground(getDrawable(R.drawable.relativeview2));
        relativeView3.setBackground(getDrawable(R.drawable.relativeview3));
        relativeView4.setBackground(getDrawable(R.drawable.relativeview4));
        relativeView5.setBackground(getDrawable(R.drawable.relativeview5));
        super.onResume();
    }

    public boolean onTouch(View v, MotionEvent event)
    {
        if (v.getClass()==RelativeLayout.class)
        {
            if (event.getAction()==MotionEvent.ACTION_DOWN)
            {
                v.setBackground(getDrawable(R.drawable.relativeview));
            }
            else if (event.getAction()==MotionEvent.ACTION_UP)
            {
                onResume();
                switch (v.getId())
                {
                    case R.id.relativeView1:
                        intent = new Intent(FullActivity.this, MainActivity.class) ;
                        startActivity(intent);
                        break;
                    case R.id.relativeView2:
                        intent = new Intent(FullActivity.this, SystemActivity.class) ;
                        startActivity(intent);
                        break;
                    case R.id.relativeView3:
                        intent = new Intent(FullActivity.this, CurveActivity.class) ;
                        startActivity(intent);
                        break;
                    case R.id.relativeView4:
                        intent = new Intent(FullActivity.this, MatrixActivity.class) ;
                        startActivity(intent);
                        break;
                    case R.id.relativeView5:
                        intent = new Intent(FullActivity.this, UnitActivity.class) ;
                        startActivity(intent);
                        break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        helpBox = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_activity_help))
                .setView(R.layout.activity_help)
                .setPositiveButton(R.string.positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin)
                            {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:hassenmajor@gmail.com")));
                            } } )
                .setNeutralButton(R.string.neutral,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin)
                            {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.major.arithmo")));
                            } } )
                .setNegativeButton(R.string.negative,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin)
                            {

                            } } );

        relativeView1 = (RelativeLayout)findViewById(R.id.relativeView1);
        relativeView2 = (RelativeLayout)findViewById(R.id.relativeView2);
        relativeView3 = (RelativeLayout)findViewById(R.id.relativeView3);
        relativeView4 = (RelativeLayout)findViewById(R.id.relativeView4);
        relativeView5 = (RelativeLayout)findViewById(R.id.relativeView5);
        relativeView1.setOnTouchListener(this);
        relativeView2.setOnTouchListener(this);
        relativeView3.setOnTouchListener(this);
        relativeView4.setOnTouchListener(this);
        relativeView5.setOnTouchListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_help)
        {
            helpBox.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
