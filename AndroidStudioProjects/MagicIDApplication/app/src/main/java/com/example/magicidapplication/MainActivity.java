package com.example.magicidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etID;
    Button btnSubmit;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //resource -> layout folder

        // connect the corresponding data to the components of the android view
        etID = findViewById(R.id.etID);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvResult = findViewById(R.id.tvResult);

        tvResult.setVisibility(View.GONE);

        // set the action that should be taken after the button is clicked
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // retrieve the text entered by the user
                String userID = etID.getText().toString().trim();
                String text = "";

                if (userID.length() != 13) {
                    text = getString(R.string.id_length_error);
                } else {
                    // extract the date of birth (first 6 letters)
                    String monthOfBirth = userID.substring(2, 4);
                    int monthValue = Integer.parseInt(monthOfBirth);

                    boolean validMonth = monthValue > 0 && monthValue < 13;
                    if (!validMonth) {
                        text = getString(R.string.id_month_error);
                    } else {
                        String yearOfBirth = userID.substring(0, 2);
                        int yearValue = Integer.parseInt(yearOfBirth);
                        String yearFull = (yearValue >= 0 && yearValue <= 20) ? "20" + yearOfBirth : "19" + yearOfBirth;
                        int yearFullValue = Integer.parseInt(yearFull);
                        String dayOfBirth = userID.substring(4, 6);
                        int dayValue = Integer.parseInt(dayOfBirth);

                        int maxDays = 0;
                        switch (monthValue) {
                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                maxDays = 30;
                                break;

                            case 2:
                                maxDays = (yearFullValue % 4 == 0) ? 29 : 28;
                                break;

                            default:
                                maxDays = 31;
                        }

                        boolean validDay = dayValue > 0 && dayValue <= maxDays;
                        if (!validDay) {
                            text = getString(R.string.id_day_error);
                        } else {
                            String dateOfBirth = yearFull + "/" + monthOfBirth + "/" + dayOfBirth;
                            // extract gender (next 4 letters); 0-4999: female, >=5000: male
                            int genderValue = Integer.parseInt(userID.substring(6, 7));
                            String gender = (genderValue < 5) ? getString(R.string.female) : getString(R.string.male);
                            // extract citizenship (next 1 letter)
                            int nationalityValue = Integer.parseInt(userID.substring(10, 11));
                            String nationality = (nationalityValue == 0) ? getString(R.string.sa_citizen) : getString(R.string.perm_res);

                            // display extracted data
                            text = getString(R.string.date_of_birth) + dateOfBirth + getString(R.string.new_line) +
                                    getString(R.string.gender) + gender + getString(R.string.new_line) +
                                    getString(R.string.nationality) + nationality;
                        }
                    }
                }
                tvResult.setText(text);
                tvResult.setVisibility(View.VISIBLE);
            }
        });

    }
}
