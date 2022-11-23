package com.example.tareaasincrona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int numeros[];
    TextView progressLabel;
    Button sort,cancel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressLabel = findViewById(R.id.progressLabel);
        progressBar = (ProgressBar) findViewById(R.id.barraProgreso);
        sort = (Button)findViewById(R.id.ordenarAsyn);
        cancel = (Button)findViewById(R.id.cancelar);
        Random rng = new Random();
        numeros = new int[10];
        for (int i=0;i<numeros.length;i++){
            numeros[i] = rng.nextInt(50)+1;
            System.out.println(numeros[i]);
        }

    }


    public void ordenamientoBurbuja() {
        int aux;

        for (int i = 0;i<numeros.length;i++){
            for (int j = 0; j < numeros.length -1; j++) {
                if (numeros[j] > numeros[j+1])
                {
                    aux          = numeros[j];
                    numeros[j]   = numeros[j+1];
                    numeros[j+1] = aux;
                }
            }
        }
        for (int j = 0; j < numeros.length; j++) {
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
        SimpleTask simpleTask= new SimpleTask();
        simpleTask.execute();
    }


    private class SimpleTask extends AsyncTask<Void, Integer, Void> {

        /*
        Se hace visible el botón "Cancelar" y se desactiva
        el botón "Ordenar"
         */
        @Override
        protected void onPreExecute() {
            cancel.setVisibility(View.VISIBLE);
            sort.setEnabled(false);
        }


        /*
        Ejecución del ordenamiento y transmision de progreso
         */
        @Override
        protected Void doInBackground(Void... params) {
            int aux;
            for (int i = 0;i<numeros.length;i++){
                for (int j = 0; j < numeros.length -1; j++) {
                    if (numeros[j] > numeros[j+1])
                    {
                        aux          = numeros[j];
                        numeros[j]   = numeros[j+1];
                        numeros[j+1] = aux;
                    }
                }

                long tiempo = 0;
                while (tiempo<1000000000){
                tiempo++;
                }
                // Notifica a onProgressUpdate() del progreso actual
                if(!isCancelled())

                    publishProgress((int)(((i+1)/(float)(numeros.length))*100));
                else break;
            }
            for (int j = 0; j < numeros.length; j++) {
                System.out.println(numeros[j]);
            }
            return null;
        }

        /*
         Se informa en progressLabel que se canceló la tarea y
         se hace invisile el botón "Cancelar"
          */
        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressLabel.setText("En la Espera");
            cancel.setVisibility(View.INVISIBLE);
            sort.setEnabled(true);
        }

        /*
        Impresión del progreso en tiempo real
          */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            progressLabel.setText(values[0] + "%");
        }

        /*
        Se notifica que se completó el ordenamiento y se habilita
        de nuevo el botón "Ordenar"
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressLabel.setText("Completado");
            sort.setEnabled(true);
        }

    }
}