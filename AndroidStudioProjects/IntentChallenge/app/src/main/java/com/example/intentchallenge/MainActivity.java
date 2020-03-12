package com.example.intentchallenge;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView ivAttitude, ivCall, ivWeb, ivMap;
    Button btnCreate;
    final int CREATE_PAGE = 1;
    String phone = "", website = "", location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivAttitude = findViewById(R.id.ivAttitude);
        ivCall = findViewById(R.id.ivCall);
        ivWeb = findViewById(R.id.ivWeb);
        ivMap = findViewById(R.id.ivMap);
        btnCreate = findViewById(R.id.btnCreate);

        ivAttitude.setVisibility(View.GONE);
        ivCall.setVisibility(View.GONE);
        ivWeb.setVisibility(View.GONE);
        ivMap.setVisibility(View.GONE);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.intentchallenge.CreateNewContact.class);
                startActivityForResult(intent, CREATE_PAGE);
            }
        });

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        ivWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + website));
                startActivity(intent);
            }
        });

        ivMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0.0?q=" + location));
                startActivity(intent);
            }
        });

    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CREATE_PAGE) {
            if (resultCode == RESULT_OK) {
                if (data.getExtras() != null) {
                    int attitude = data.getIntExtra("attitude", 1);
                    int attitudeImage = 0;
                    switch (attitude) {
                        case 1:
                            attitudeImage = R.drawable.good;
                            break;

                        case 0:
                            attitudeImage = R.drawable.neutral;
                            break;

                        default:
                            attitudeImage = R.drawable.bad;
                    }
                    ivAttitude.setImageResource(attitudeImage);
                    phone = data.getStringExtra("phone");
                    website = data.getStringExtra("website");
                    location = data.getStringExtra("location");

                    ivAttitude.setVisibility(View.VISIBLE);
                    ivCall.setVisibility(View.VISIBLE);
                    ivWeb.setVisibility(View.VISIBLE);
                    ivMap.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this,"No data received!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}