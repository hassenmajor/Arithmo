package com.major.arithmo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.major.science.*;

public class MatrixActivity extends Activity {

    String[] items ;
    Spinner spinnerMatrix ;
    TabHost tabhost ;
    TabHost.TabSpec spec ;
    ImageView OKmatrix ;
    EditText editA, editB, editC, Scalaire ;
    TextView detABC ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        spinnerMatrix = (Spinner)findViewById(R.id.spinnerMatrix) ;
        tabhost = (TabHost)findViewById(R.id.tabhost);
        OKmatrix = (ImageView)findViewById(R.id.OKmatrix);
        editA = (EditText)findViewById(R.id.editA);
        editB = (EditText)findViewById(R.id.editB);
        editC = (EditText)findViewById(R.id.editC);
        Scalaire = (EditText)findViewById(R.id.Scalaire);
        detABC = (TextView)findViewById(R.id.detABC);
        items = getResources().getStringArray(R.array.matrix_list);

        Scalaire.setEnabled(false);
        tabhost.setup();

        spec = tabhost.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator(getString(R.string.matrix)+" A");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(getString(R.string.matrix)+" B");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("tag2");
        spec.setContent(R.id.tab3);
        spec.setIndicator(getString(R.string.result)+" C");
        tabhost.addTab(spec);

        tabhost.setCurrentTab(0);

        ArrayAdapter<String> a=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                items);
        a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerMatrix.setAdapter(a);
        spinnerMatrix.setSelection(0);

        /*Intent Matrix = getIntent() ;
        if ( Matrix.hasExtra("in") && Mathématique.Logique.estNombre(Matrix.getStringExtra("in")) )
        {
            Scalaire.setText(Matrix.getStringExtra("in")) ;
        }*/

        spinnerMatrix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> arg0) {

            }
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (spinnerMatrix.getSelectedItemPosition() == 3) Scalaire.setEnabled(true);
                else Scalaire.setEnabled(false);
            }
        } ) ;

        OKmatrix.setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
            try
            {
                YES();
                Toast.makeText(MatrixActivity.this, "See result in C tab", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                NO();
            }
            MAYBE();
        } } ) ;
    }

    private void MAYBE()
    {
        double A = Double.NaN, B = A, C = A ;
        try
        {
            A = Algèbre.Déterminant(Matrice(editA.getText().toString())) ;
        } catch (Exception e) { }
        try
        {
            B = Algèbre.Déterminant(Matrice(editB.getText().toString())) ;
        } catch (Exception e) { }
        try
        {
            C = Algèbre.Déterminant(Matrice(editC.getText().toString())) ;
        } catch (Exception e) { }
        detABC.setText("det A : " + Mathématique.Arrondi(A, 3)
                + "     det B : " + Mathématique.Arrondi(B, 3)
                + "     det C : " + Mathématique.Arrondi(C, 3) ) ;
    }
    private void YES()
    {
        switch (spinnerMatrix.getSelectedItemPosition())
        {
            case 0:
                editC.setText( Texte( Algèbre.Somme(Matrice(editA.getText().toString()), Matrice(editB.getText().toString())) ) ) ; break;
            case 1:
                editC.setText( Texte( Algèbre.Somme(Matrice(editA.getText().toString()), Algèbre.Produit(-1, Matrice(editB.getText().toString()))) ) ) ; break;
            case 2:
                editC.setText( Texte( Algèbre.Produit(Matrice(editA.getText().toString()), Matrice(editB.getText().toString())) ).toString() ) ; break;
            case 3:
                editC.setText( Texte( Algèbre.Produit(Double.valueOf(Scalaire.getText().toString()), Matrice(editA.getText().toString())) ) ) ; break;
            case 4:
                editC.setText( Texte( Algèbre.Inverse(Matrice(editA.getText().toString())) ) ) ; break;
            case 5:
                editC.setText( Texte( Algèbre.Comatrice(Matrice(editA.getText().toString())) ) ) ; break;
        }
    }
    private void NO()
    {
        editC.setText( " ? " ) ;
    }

    public static double[][] Matrice(String Texte)
    {
        while (Texte.indexOf("  ") != -1) Texte = Texte.replace("  ", " ") ;
        String [] y, x = Texte.split("\n") ;
        double [][] t = new double [x.length][0] ;
        for (int i = 0; i < x.length; i++)
        {
            y = x[i].split(" ") ;
            t[i] = new double [y.length] ;
            for (int j = 0; j < y.length; j++)
                t[i][j] = Double.valueOf(y[j].replace(",", ".")) ;
        }
        return t ;
    }
    public static double[] Liste(String Texte, char Séparateur)
    {
        String [] x = Texte.split(Character.toString(Séparateur)) ;
        double [] t = new double [x.length] ;
        for (int i = 0; i < x.length; i++)
            t[i] = Double.valueOf(x[i]) ;
        return t ;
    }
    public static String Texte(double[][] Matrice)
    {
        String x = "";
        for (int i = 0; i < Matrice.length; i++)
        {
            for (int j = 0; j < Matrice[i].length; j++)
            {
                if ((int) Matrice[i][j] == Matrice[i][j]) x = x + String.valueOf((int) Matrice[i][j]) ;
                else x = x + String.valueOf( Mathématique.Arrondi(Matrice[i][j], 3) ) ;
                if (j < Matrice[i].length-1) x = x + "  " ;
            }
            if (i < Matrice.length-1) x = x + "\n" ;
        }
        return x ;
    }
}
