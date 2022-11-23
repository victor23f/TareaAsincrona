package com.example.tareaasincrona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int numeros[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rng = new Random();
        numeros = new int[10];
        for (int i=0;i<numeros.length-1;i++){
            numeros[i] = rng.nextInt(50)+1;
            System.out.println(numeros[i]);
        }

    }


    public void ordenamientoBurbuja() {
        int aux;

        for (int i = 0;i<numeros.length-1;i++){
            for (int j = 0; j < numeros.length -1; j++) {
                if (numeros[j] > numeros[j+1])
                {
                    aux          = numeros[j];
                    numeros[j]   = numeros[j+1];
                    numeros[j+1] = aux;
                }
            }
        }
        for (int j = 0; j < numeros.length -1; j++) {
            System.out.println(numeros[j]);
        }
    }
    public void botonOrdenamientoBurbuja(View view){
        ordenamientoBurbuja();
    }
    public void botonOrdenamientoBurbujaConHilos(View view){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ordenamientoBurbuja();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(
                                        getBaseContext(),
                                        "¡Números Ordenados!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
        ).start();


    }

    public void ordenamientoAsincrono(View view){

    }


    public class SimpleTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int aux;

            for (int i = 0; i < numeros.length - 1; i++) {
                for (int j = 0; j < numeros.length -1; j++) {
                    if (numeros[j] > numeros[j+1])
                    {
                        aux          = numeros[j];
                        numeros[j]   = numeros[j+1];
                        numeros[j+1] = aux;
                    }
                }
                // Notifica a onProgressUpdate() del progreso actual
                if(!isCancelled())
                    publishProgress((int)(((i+1)/(float)(numeros.length-1))*100));
                else break;
            }
            return null;
        }
    }
}