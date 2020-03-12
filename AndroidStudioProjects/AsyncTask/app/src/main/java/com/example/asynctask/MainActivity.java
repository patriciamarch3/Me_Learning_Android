package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText etTime;
    Button btnRoll;
    TextView tvResult;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTime = findViewById(R.id.etTime);
        btnRoll = findViewById(R.id.btnRoll);
        tvResult = findViewById(R.id.tvResult);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numnberofRolls = Integer.parseInt(etTime.getText().toString().trim());
                // calls the async doInBackground function
                new RunDiceInBackground().execute(numnberofRolls);
            }
        });
    }

    //First param: type of data to be passed in
    //Second param: type of data to be used for the progress bar
    //Third param: type of data to be returned
    private class RunDiceInBackground extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            progressBar.setMax(Integer.parseInt(etTime.getText().toString().trim()));
        }

        /**
         *
         * @param integers: matches the class's first param, an array
         * @return: matches the class's third param
         */
        @Override
        protected String doInBackground(Integer... integers) {
            int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0;
            Random random = new Random();
            double currentProgress = 0, previusProgress = 0;
            String results;
            for (int i = 0; i < integers[0]; i++) {
                // if show progress each time, it slows down the whole application for displaying the graph each time;
                // thus we choose certain percentage
                currentProgress = (double) i / integers[0];
                if (currentProgress - previusProgress >= 0.05 ) {
                    publishProgress(i); // it will call onProgressUpdate function
                    previusProgress = currentProgress;
                }

                int randomValue = random.nextInt(6) + 1;
                switch (randomValue) {
                    case 1:
                        ones++;
                        break;

                    case 2:
                        twos++;
                        break;

                    case 3:
                        threes++;
                        break;

                    case 4:
                        fours++;
                        break;

                    case 5:
                        fives++;
                        break;

                    default:
                        sixes++;
                }
            }
            results = "Results:\n" +
                    "ones: " + ones + "\ntwos: " + twos +
                    "\nthrees: " + threes + "\nfours: " + fours +
                    "\nfives: " + fives + "\nsixes: " + sixes;

            return results;
        }

        /**
         *
         * @param values: matches the class's second param
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0]);
            tvResult.setText(values[0] + " / " + etTime.getText().toString().trim());
        }

        /**
         *
         * @param s: matches the class's third param,
         *         is the values returned by doInBackground function
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.GONE);
            tvResult.setText(s);
            Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_SHORT).show();
        }
    }
}
