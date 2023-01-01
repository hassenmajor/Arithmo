package com.major.arithmo;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.major.science.*;

public class CurveActivity extends Activity {

    EditText editFonction, editVariable;
    ImageView OKcurve, OKvariable;
    TextView lblExtremums, lblSolutions;
    ImageView imageCurve;
    CheckBox lblImage, lblDerive, lblPrimitive;
    ProgressBar progressBar;
    Spinner spinnerCurve;

    String[] itemsEchelle = { "0.1 × 0.1", "1 × 1", "10 × 10", "100 × 100" } ;

    String Extremums, Solutions;
    Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);;
    Handler CurveHandler = new Handler() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve);

        editFonction = (EditText)findViewById(R.id.editFonction);
        editVariable = (EditText)findViewById(R.id.editVariable);
        OKcurve = (ImageView)findViewById(R.id.OKcurve);
        OKvariable = (ImageView)findViewById(R.id.OKvariable);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        lblImage = (CheckBox)findViewById(R.id.lblImage);
        lblDerive = (CheckBox)findViewById(R.id.lblDerive);
        lblPrimitive = (CheckBox)findViewById(R.id.lblPrimitive);
        lblExtremums = (TextView)findViewById(R.id.lblExtremums);
        lblSolutions = (TextView)findViewById(R.id.lblSolutions);
        spinnerCurve = (Spinner)findViewById(R.id.spinnerCurve);
        imageCurve = (ImageView)findViewById(R.id.imageCurve);
        Repère() ;
        editVariable.setText("0");
        imageCurve.setImageBitmap(bitmap);

        ArrayAdapter<String> a=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsEchelle);
        a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCurve.setAdapter(a);
        spinnerCurve.setSelection(1);

        /*Intent Curve = getIntent() ;
        if (Curve.hasExtra("in") && Curve.getStringExtra("in").indexOf(':')==-1)
            editFonction.setText(Curve.getStringExtra("in").split("=")[0]) ;*/

        OKvariable.setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
            try
            {
                OK() ;
            } catch (Exception e) { }
        } } ) ;

        OKcurve.setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
            try
            {
                if (progressBar.getProgress()==progressBar.getMax())
                {
                    OKcurve.setImageResource(R.drawable.ic_clear);
                    Courbe();
                }
            } catch (Exception e) { }
        } } ) ;
    }

    public void Repère() { final Thread Repère = new Thread(new Runnable() {
        public void run() {
            Canvas canvas = new Canvas(bitmap) ;
            Paint paint = new Paint() ;

            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(1);
            for (int i = 0; i <= 20; i++)
                canvas.drawLine(50*i, 0, 50*i, 1000, paint) ;
            for (int j = 0; j <= 20; j++)
                canvas.drawLine(0, 50*j, 1000, 50*j, paint) ;

            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(2);
            canvas.drawLine(0, 500, 1000, 500, paint) ;
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            canvas.drawLine(500, 0, 500, 1000, paint) ;

            CurveHandler.post(new Runnable() { public void run() {
                imageCurve.setImageBitmap( bitmap );
            } } ) ;
        } } ) ; Repère.start() ;
    }

    public void Courbe() { final Thread Courbe = new Thread(new Runnable() {
        public void run() {
            progressBar.setProgress(0);
            Extremums = "Extremums :";
            Solutions = "Solutions :";
            Canvas canvas = new Canvas(bitmap) ;
            Paint paint = new Paint(), pen = new Paint() ;
            double echelle = Math.pow(10, spinnerCurve.getSelectedItemPosition()) ;
            String f = editFonction.getText().toString();
            String v = editVariable.getText().toString();
            if (Arithmétique.Valeur(f) == 0) return;
            CurveHandler.post(new Runnable() { public void run() { GO(); } } ) ;

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(4);
            pen.setColor(Color.BLACK);
            pen.setStrokeWidth(10);
            double X, X0 = 0;
            double Y, Y0 = 0, Y9 = 0;
            int x, y, x0 = 0, y0 = (int)( 500-1000/(2*echelle)*Analyse.Image( f, (-500)*(2*echelle)/1000 ) ) ;
            if (lblImage.isChecked())
                for (x = 0; x <= 1000; x++)
                {
                    X = (x-500)*(2*echelle)/1000 ;
                    Y = Analyse.Image(f, X) ;
                    y = (int) (500-1000/(2*echelle)*Y);
                    if (x > 1 && Double.isFinite(Y) && Double.isFinite(Y0))
                    {
                        if (Math.abs(y-y0) < 20*echelle)
                            canvas.drawLine(x0, y0, x, y, paint);
                        if (x == 500)
                            canvas.drawPoint(500, y, pen);
                        if (Y == 0 || (Y*Y0 < 0 && Math.abs(Y-Y0) < 10*echelle) )
                        {
                            canvas.drawPoint(x, 500, pen);
                            Solutions = Solutions + "  " + Double.toString(X) ;
                        }
                        if (Y0 > Math.max(Y9, Y) || Y0 < Math.min(Y9, Y))
                            if (Math.abs(Y0-Y9) < 10*echelle)
                            {
                                canvas.drawPoint(x0, y0, pen);
                                Extremums = Extremums + "  " + Double.toString(X0);
                            }
                    }
                    x0 = x; X0 = X;
                    y0 = y; Y9 = Y0; Y0 = Y;
                    progressBar.setProgress(x);
                }

            paint.setColor(Color.GRAY);
            double Z, Z0 = 0;
            int z, z0 = (int)( 500-1000/(2*echelle)*Analyse.Dérivée(f, (-500)*(2*echelle)/1000 ) ) ;
            if (lblDerive.isChecked())
                for (x = 0; x <= 1000; x++)
                {
                    X = (x-500)*(2*echelle)/1000 ;
                    Z = Analyse.Dérivée(f, X )  ;
                    z = (int) (500-1000/(2*echelle)*Z) ;
                    if (x > 0 && Double.isFinite(Z) && Double.isFinite(Z0))
                        canvas.drawLine(x0, z0, x, z, paint);
                    x0 = x; Z0 = Z; z0 = z;
                    progressBar.setProgress(x);
                }

            paint.setStrokeWidth(1);
            if (lblPrimitive.isChecked() && Mathématique.Logique.estNombre(v))
            {
                double Xprim = Double.valueOf(v);
                int xprim = (int) (500+1000/(2*echelle)*Xprim);
                int un = 1; if (Xprim<0) un = -1;
                for (x = 500+1; x != xprim; x=x+un)
                {
                    X = (x-500)*(2*echelle)/1000 ;
                    Y = Analyse.Image(f, X) ;
                    y = (int) (500-1000/(2*echelle)*Y);
                    if (Double.isFinite(Y))
                        canvas.drawLine(x, (float) (500-Y/Math.abs(Y)), x, (float) (y+Y/Math.abs(Y)), paint);
                    else
                        break;
                    progressBar.setProgress(x);
                }
            }

            progressBar.setProgress(progressBar.getMax());
            CurveHandler.post(new Runnable() { public void run() { GO();
                OKcurve.setImageResource(R.drawable.ic_create); } } ) ;
        } } ) ; Courbe.start();
    }

    private void GO()
    {
        imageCurve.setImageBitmap( bitmap );
        lblSolutions.setText( Solutions );
        lblExtremums.setText( Extremums );
    }
    private void OK() { final Thread thread = new Thread(new Runnable() {
        public void run() {
            if (Mathématique.Logique.estNombre(editVariable.getText().toString()))
            {
                final double x = Double.valueOf(editVariable.getText().toString()) ;
                final double Image = Analyse.Image( editFonction.getText().toString(), x );
                final double Dérivée = Analyse.Dérivée( editFonction.getText().toString(), x );
                final double Primitive = Analyse.Intégrale( editFonction.getText().toString(), 0, x );
                CurveHandler.post(new Runnable() { public void run() {
                    lblImage.setText("Image f(" + Mathématique.Arrondi( x , 3 ) + ") = ");
                    lblDerive.setText("Derivation f'(" + Mathématique.Arrondi( x , 3 ) + ") = ");
                    lblPrimitive.setText("Integration \u222B de 0 à " + Mathématique.Arrondi( x , 3 ) + " = ");
                } } ) ;
                CurveHandler.post(new Runnable() { public void run() {
                    lblImage.setText(lblImage.getText() + String.valueOf(
                            Mathématique.Arrondi( Image , 3 ))) ;
                    lblDerive.setText(lblDerive.getText() + String.valueOf(
                            Mathématique.Arrondi( Dérivée, 3 ) )) ;
                    lblPrimitive.setText(lblPrimitive.getText() + String.valueOf(
                            Mathématique.Arrondi( Primitive, 3) )) ;
                } } ) ;
            }
        } } ) ; thread.start() ;
    }
}
