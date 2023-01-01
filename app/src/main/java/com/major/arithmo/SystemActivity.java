package com.major.arithmo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.major.science.Algèbre;
import com.major.science.Mathématique;

public class SystemActivity extends Activity {

    EditText systemin;
    TextView systemout;
    CheckBox checksystem;
    ImageView systemCopy, OKsystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        systemin = (EditText)findViewById(R.id.systemin);
        systemout = (TextView)findViewById(R.id.systemout);
        checksystem = (CheckBox)findViewById(R.id.checksystem);
        systemCopy = (ImageView)findViewById(R.id.systemCopy);
        OKsystem = (ImageView)findViewById(R.id.OKsystem);

        OKsystem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try
                {
                    YES();
                    Toast.makeText(SystemActivity.this, "Linear system solved", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    NO();
                }
            }
        });

        systemCopy.setOnClickListener(new View.OnClickListener() { public void onClick(View v)
        {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("System Solution", systemout.getText());
            clipboard.setPrimaryClip(clip);

            systemout.requestFocus();
        } } ) ;

    }

    private String give(double x)
    {
        String y ;
        int[] z = Mathématique.Fraction(x, (float) Math.pow(10, -8)) ;
        if (checksystem.isChecked() && z!=null && z[1]!=1)
            y = (z[0] + "÷" + z[1]) ;
        else
        if ((int) x == x)
            y = String.valueOf( (int) x ) ;
        else
            y = String.valueOf( x ).replace("E", " × 10 ^ ") ;
        return y ;
    }
    void NO()
    {
        systemout.setText("?");
    }
    void YES()
    {
        String x = systemin.getText().toString().toUpperCase();
        x = x.replace(" ", "").replace("\t", "").replace("\n\n", "\n");
        if (x.charAt(x.length()-1)=='\n') x = x.substring(0, x.length()-1);
        String[] y = Algèbre.Tableau(x, "\n");
        String[] z;
        for (int i=0; i<y.length; i++)
        {
            z = Algèbre.Tableau(y[i], "=");
            y[i] = z[0]+"-("+z[1]+")";
        }
        String c = "";
        for (int i=0; i<Mathématique.LETTRE.length(); i++)
            if (x.indexOf(Mathématique.LETTRE.charAt(i)+"") != -1)
                c = c + Mathématique.LETTRE.charAt(i);
        double[] n = Algèbre.Valeur(y, c.toCharArray());
        String s = "";
        for (int i=0; i<y.length; i++)
            s = s + c.toLowerCase().charAt(i) + " = " + give(n[i]) + "\n";
        systemout.setText(s);
    }
}
