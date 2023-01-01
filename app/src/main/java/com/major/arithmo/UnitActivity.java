package com.major.arithmo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

public class UnitActivity extends Activity {

    Spinner hautin, hautout,
            basin, basout,
            highin, highout,
            lowin, lowout;
    EditText timein, timeout,
            spacein, spaceout,
            speedin, speedout,
            massin, massout;
    ImageView OKdeep;

    String[] itemstime = { "Années", "Mois", "Jours", "Heures", "Minutes", "Secondes", "Millisecondes" } ;
    double[] valuestime = { 31536000, 2592000, 86400, 3600, 60, 1, 1E-3 } ;

    String[] itemsspace = { "Parsec (pc)", "Années-lumière (al)", "Unité astronomique (ua)", "Téramètres 10\u00B9\u00B2", "Gigamètres 10\u2079", "Mégamètres 10\u2076",
            "Kilomètres 10\u00B3", "Hectomètres 10\u00B2", "Décamètres 10",
            "Mètres", "Pouces", "Pieds", "Miles",
            "Décimètres 10¯\u00B9", "Centimètres 10¯\u00B2", "Millimètres 10¯\u00B3",
            "Micromètres 10¯\u2076", "Nanomètres 10¯\u2079", "Picomètres 10¯\u00B9\u00B2", "Ångström 10¯\u00B9\u2070" } ;
    double[] valuesspace = { 648000.0*149597870700.0/Math.PI, 9460730472580800.0, 149597870700.0, 1E12, 1E9, 1E6, 1E3, 1E2, 1E1,
            1, 0.0254, 0.305, 1609.344,
            1E-1, 1E-2, 1E-3, 1E-6, 1E-9, 1E-12, 1E-10 } ;

    String[] itemsspeed = { "Mètres par seconde (m·s¯\u00B9)", "Kilomètres par heure (km·h¯\u00B9)", "Nœuds", "Célérité de la lumière"} ;
    double[] valuesspeed = { 1, 5.0/18.0, 0.514, 299792458.0 } ;

    String[] itemsmass = { "Tonnes 10\u2076", "Quintaux 10\u2075", "Kilogrammes 10\u00B3", "Hectogrammes 10\u00B2", "Décagrammes 10",
            "Grammes",
            "Décigrammes 10¯\u00B9", "Centigrammes 10¯\u00B2", "Milligrammes 10¯\u00B3", "Microgrammes 10¯\u2076", "Unité de masse atomique" } ;
    double[] valuesmass = { 1E6, 1E5, 1E3, 1E2, 1E1,
            1,
            1E-1, 1E-2, 1E-3, 1E-6, 1/602214076E15 } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);

        OKdeep = (ImageView)findViewById(R.id.OKdeep);

        timein = (EditText)findViewById(R.id.timein);
        timeout = (EditText)findViewById(R.id.timeout);
        spacein = (EditText)findViewById(R.id.spacein);
        spaceout = (EditText)findViewById(R.id.spaceout);
        speedin = (EditText)findViewById(R.id.speedin);
        speedout = (EditText)findViewById(R.id.speedout);
        massin = (EditText)findViewById(R.id.massin);
        massout = (EditText)findViewById(R.id.massout);

        hautin = (Spinner)findViewById(R.id.hautin);
        hautout = (Spinner)findViewById(R.id.hautout);
        basin = (Spinner)findViewById(R.id.basin);
        basout = (Spinner)findViewById(R.id.basout);
        highin = (Spinner)findViewById(R.id.highin);
        highout = (Spinner)findViewById(R.id.highout);
        lowin = (Spinner)findViewById(R.id.lowin);
        lowout = (Spinner)findViewById(R.id.lowout);

        timein.setOnKeyListener(new View.OnKeyListener() { public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
            if (arg2.getAction()==KeyEvent.ACTION_UP && arg1==KeyEvent.KEYCODE_ENTER)
            {
                GO1();
            }
            return false;
        } } ) ;
        spacein.setOnKeyListener(new View.OnKeyListener() { public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
            if (arg2.getAction()==KeyEvent.ACTION_UP && arg1==KeyEvent.KEYCODE_ENTER)
            {
                GO2();
            }
            return false;
        } } ) ;
        speedin.setOnKeyListener(new View.OnKeyListener() { public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
            if (arg2.getAction()==KeyEvent.ACTION_UP && arg1==KeyEvent.KEYCODE_ENTER)
            {
                GO3();
            }
            return false;
        } } ) ;
        massin.setOnKeyListener(new View.OnKeyListener() { public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
            if (arg2.getAction()==KeyEvent.ACTION_UP && arg1==KeyEvent.KEYCODE_ENTER)
            {
                GO4();
            }
            return false;
        } } ) ;

        ArrayAdapter<String> a=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                itemstime);
        a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        hautin.setAdapter(a);
        hautin.setSelection(5);
        hautout.setAdapter(a);
        hautout.setSelection(5);

        ArrayAdapter<String> b=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsspace);
        b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        basin.setAdapter(b);
        basin.setSelection(9);
        basout.setAdapter(b);
        basout.setSelection(9);

        ArrayAdapter<String> c=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsspeed);
        c.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        highin.setAdapter(c);
        highin.setSelection(0);
        highout.setAdapter(c);
        highout.setSelection(0);

        ArrayAdapter<String> d=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsmass);
        d.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        lowin.setAdapter(d);
        lowin.setSelection(2);
        lowout.setAdapter(d);
        lowout.setSelection(2);

        /*Intent Unit = getIntent() ;
        if ( Unit.hasExtra("in") && Mathématique.Logique.estNombre(Unit.getStringExtra("in")) )
        {
            timein.setText(Unit.getStringExtra("in")) ;
            spacein.setText(Unit.getStringExtra("in")) ;
            speedin.setText(Unit.getStringExtra("in")) ;
            massin.setText(Unit.getStringExtra("in")) ;
        }*/

        OKdeep.setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
            YES();
        } } ) ;
    }

    private void GO1()
    {
        try {
            timeout.setText(
                    String.valueOf(Double.valueOf(timein.getText().toString())
                            * valuestime[hautin.getSelectedItemPosition()]
                            / valuestime[hautout.getSelectedItemPosition()]).replace("E", " × 10 ^ ")
            );
        } catch (Exception e) {
            timeout.setText("?");
        }
    }
    private void GO2()
    {
        try {
            spaceout.setText(
                    String.valueOf(Double.valueOf(spacein.getText().toString())
                            * valuesspace[basin.getSelectedItemPosition()]
                            / valuesspace[basout.getSelectedItemPosition()]).replace("E", " × 10 ^ ")
            );
        } catch (Exception e) {
            spaceout.setText("?");
        }
    }
    private void GO3()
    {
        try {
            speedout.setText(
                    String.valueOf(Double.valueOf(speedin.getText().toString())
                            * valuesspeed[highin.getSelectedItemPosition()]
                            / valuesspeed[highout.getSelectedItemPosition()]).replace("E", " × 10 ^ ")
            );
        } catch (Exception e) {
            speedout.setText("?");
        }
    }
    private void GO4()
    {
        try {
            massout.setText(
                    String.valueOf(Double.valueOf(massin.getText().toString())
                            * valuesmass[lowin.getSelectedItemPosition()]
                            / valuesmass[lowout.getSelectedItemPosition()]).replace("E", " × 10 ^ ")
            );
        } catch (Exception e) {
            massout.setText("?");
        }
    }
    private void YES()
    {
        GO1();
        GO2();
        GO3();
        GO4();
    }
}
