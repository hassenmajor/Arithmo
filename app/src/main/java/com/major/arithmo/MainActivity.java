package com.major.arithmo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.major.science.*;

public class MainActivity extends Activity {

    EditText textin, textout, textbegin, textfinish;
    TextView textBar;
    Switch swtch;
    SeekBar seekBar;
    ProgressBar mainBar;
    Spinner spinner;
    CheckBox checkBox, checkInter, checkAll;
    ImageView mainCopy, OKmain;

    String[] itemsBase;
    double ans[] = new double[10];

    String toast;
    String string;
    Handler MainHandler = new Handler() { public void handleMessage(Message msg) {
        if (msg.what == 0) NO() ;
        else YES() ;
        mainBar.setVisibility(View.INVISIBLE);
    } } ;

    @Override
    protected void onPause()
    {
        OKmain.setImageResource(R.drawable.ic_send_black);
        mainCopy.setImageResource(R.drawable.ic_copy_black);
        super.onPause();
    }

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        textin = (EditText)findViewById(R.id.textin);
        textout = (EditText)findViewById(R.id.textout);
        textbegin = (EditText)findViewById(R.id.textbegin);
        textfinish = (EditText)findViewById(R.id.textfinish);
        swtch = (Switch)findViewById(R.id.swtch);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        mainBar = (ProgressBar)findViewById(R.id.mainBar);
        spinner = (Spinner)findViewById(R.id.spinner);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkInter = (CheckBox)findViewById(R.id.checkInter);
        checkAll = (CheckBox)findViewById(R.id.checkAll);
        textBar = (TextView)findViewById(R.id.textBar);
        mainCopy = (ImageView)findViewById(R.id.mainCopy);
        OKmain = (ImageView)findViewById(R.id.OKmain);
        itemsBase = getResources().getStringArray(R.array.base_list);

        textbegin.setEnabled(false);
        textfinish.setEnabled(false);

        ArrayAdapter<String> a=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsBase);
        a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter(a);
        spinner.setSelection(0);

        /*Intent Main = getIntent();
        if ( Main.hasExtra("in" ) )
            textin.setText(Main.getStringExtra("in")) ;
        if ( Main.hasExtra("out" ) )
            textout.setText(Main.getStringExtra("out")) ;*/

        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            if (arg0.isChecked())
            {
                textbegin.setHint("-50");
                textfinish.setHint("50");
            }
            else
            {
                textbegin.setHint("-1110");
                textfinish.setHint("1110");
            }
        } } ) ;

        checkInter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            if (arg0.isChecked())
            {
                textbegin.setEnabled(true);
                textfinish.setEnabled(true);
            }
            else
            {
                textbegin.setEnabled(false);
                textfinish.setEnabled(false);
            }
        } } ) ;

        textBar.setText("10¯"+Mathématique.CHIFFRE_EXPOSANT.charAt(seekBar.getProgress()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar arg0) { }
            public void onStartTrackingTouch(SeekBar arg0) { }
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                if (arg1 < 10) textBar.setText("10¯"+Mathématique.CHIFFRE_EXPOSANT.charAt(arg1));
                else textBar.setText("10¯¹\u2070");
            }
        });

        textin.setOnKeyListener(new View.OnKeyListener() { public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
            if (arg2.getAction()==KeyEvent.ACTION_UP && arg1==KeyEvent.KEYCODE_ENTER)
            {
                mainBar.setVisibility(View.VISIBLE);
                string = textin.getText().toString() ;
                try
                {
                    MAYBE();
                }
                catch (Exception e) { }
                return true;
            }
            return false;
        } } ) ;

        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            if (arg0.isChecked()) {
                Arithmétique.Unité_angle(180);
                Toast.makeText(MainActivity.this, R.string.degree, Toast.LENGTH_SHORT).show();
            }
            else {
                Arithmétique.Unité_angle(Math.PI);
                Toast.makeText(MainActivity.this, R.string.radian, Toast.LENGTH_SHORT).show();
            }
        } } ) ;

        mainCopy.setOnTouchListener(new View.OnTouchListener() { public boolean onTouch(View arg0, MotionEvent arg1)
        {
            if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                mainCopy.setImageResource(R.drawable.ic_copy_white);
            else if (arg1.getAction()==MotionEvent.ACTION_UP)
            {
                mainCopy.setImageResource(R.drawable.ic_copy_black);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Text copied", textout.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, R.string.text_copied, Toast.LENGTH_SHORT).show();
            }
            return true;
        } } ) ;

        OKmain.setOnTouchListener(new View.OnTouchListener() { public boolean onTouch(View arg0, MotionEvent arg1)
        {
            if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                OKmain.setImageResource(R.drawable.ic_send_white);
            else if (arg1.getAction()==MotionEvent.ACTION_UP)
            {
                OKmain.setImageResource(R.drawable.ic_send_black);
                try
                {
                    mainBar.setVisibility(View.VISIBLE);
                    string = textin.getText().toString() ;
                    MAYBE();
                }
                catch (Exception e) { }
            }
            return true;
        } } ) ;

    }

    private void MAYBE() { final Thread thread = new Thread(new Runnable() {
        private String give(double x)
        {
            String y ;
            int n = (int) Math.pow(2, spinner.getSelectedItemPosition()) ;
            for (int i = 9; i >= 2; i--) ans[i] = ans[i-1] ;
            ans[1] = x ;
            if (n > 1)
                y = Mathématique.Valeur( String.valueOf((int) x) , 10 , n ) + " (base " + n + ")" ;
            else
            {
                int[] z = Mathématique.Fraction(x, (float) Math.pow(10, -seekBar.getProgress())) ;
                if (checkBox.isChecked() && z!=null && z[1]!=1)
                    y = (z[0] + "÷" + z[1]) ;
                else
                if ((int) x == x)
                    y = String.valueOf( (int) x ) ;
                else
                    y = String.valueOf( x ).replace("E", " × 10 ^ ") ;
            }
            return y ;
        }
        public void run()
        {
            String x, x0 ;
            x = textin.getText().toString() ; x0 = x ;
            ans[0] = 0;
            int m  = x.indexOf(':'), n = x.indexOf('=') ;
            if (m != -1) // C'est un nombre à convertir en base décimale
            {
                String g = x.substring(0, m) ;
                String d = x.substring(m + 1) ;
                if (!Mathématique.Logique.estNombre(g))
                {
                    toast = "Write correct base";
                    MainHandler.sendEmptyMessage(0);
                    return ;
                }
                double y = Double.valueOf(Mathématique.Valeur( d , (int)(double) Double.valueOf(g) , 10 )) ;
                if (Double.isFinite(y))
                {
                    toast = "";
                    string = "\u2022 " + x0 + "\n= " + give(y) + "\n\n" + textout.getText() ;
                    MainHandler.sendEmptyMessage(1);
                }
                else
                {
                    toast = "Correct the base or number";
                    MainHandler.sendEmptyMessage(0);
                }
                return ;
            }
            for (int i = 1; i <= 9; i++) x = x.replace("&" + i, "(" + ans[i] + ")") ;
            if (n != -1 && checkAll.isChecked()) // C'est une équation à résoudre totalement
            {
                double y[] ;
                x = x.replace("=", "-(") + ")" ;
                if (checkInter.isChecked()
                        && Mathématique.Logique.estNombre(textbegin.getText().toString())
                        && Mathématique.Logique.estNombre(textfinish.getText().toString()) )
                    y = Algèbre.Valeur(x, Double.valueOf(textbegin.getText().toString()),
                            Double.valueOf(textfinish.getText().toString()),
                            (float) Math.pow(10, -seekBar.getProgress()), true) ;
                else y = Algèbre.Valeur(x, 0, 0, (float) Math.pow(10, -seekBar.getProgress()),
                        true) ;
                if (y.length > 0)
                {
                    toast = y.length + " solutions found";
                    ans[0] = y[0];
                    string = "\u2022 " + x0;
                    for (int i = 0; i < y.length; i++)
                        string = string + "\n" + Algèbre.Inconnue() + Mathématique.versIndice(i) + " = " + give(y[i]) ;
                    string = string + "\n\n" + textout.getText() ;
                    MainHandler.sendEmptyMessage(1);
                }
                else
                {
                    toast = "0 solution found";
                    MainHandler.sendEmptyMessage(0);
                }
            }
            else if (n != -1) // C'est une équation à résoudre
            {
                double y ;
                x = x.replace("=", "-(") + ")" ;
                if (checkInter.isChecked()
                        && Mathématique.Logique.estNombre(textbegin.getText().toString())
                        && Mathématique.Logique.estNombre(textfinish.getText().toString()) )
                    y = Algèbre.Valeur(x, Double.valueOf(textbegin.getText().toString()),
                            Double.valueOf(textfinish.getText().toString()),
                            (float) Math.pow(10, -seekBar.getProgress())) ;
                else y = Algèbre.Valeur( x, 0, 0, (float) Math.pow(10, -seekBar.getProgress()) ) ;
                if (Double.isFinite(y))
                {
                    toast = "";
                    ans[0] = y;
                    string = "\u2022 " + x0 + "\n" + Algèbre.Inconnue() + " = " + give(y) + "\n\n" + textout.getText() ;
                    MainHandler.sendEmptyMessage(1);
                }
                else
                {
                    toast = "Press ? for help";
                    MainHandler.sendEmptyMessage(0);
                }
            }
            else // C'est une expression à calculer
            {
                double y = Arithmétique.Valeur( x ) ;
                if (Double.isFinite(y))
                {
                    toast = "";
                    string = "\u2022 " + x0 + "\n= " + give(y) + "\n\n" + textout.getText() ;
                    MainHandler.sendEmptyMessage(1);
                }
                else
                {
                    toast = "Invalid expression";
                    MainHandler.sendEmptyMessage(0);
                }
            }
        } } ) ; thread.start() ;
    }
    private void YES()
    {
        if (!toast.isEmpty())
            Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
        textout.setText(string) ;
    }
    private void NO()
    {
        if (!toast.isEmpty())
            Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
        textout.setText( "\u2022 " + string
                + "\n?"
                + "\n\n" + textout.getText() ) ;
    }
}
