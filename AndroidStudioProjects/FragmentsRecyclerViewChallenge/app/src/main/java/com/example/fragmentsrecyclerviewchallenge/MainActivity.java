package com.example.fragmentsrecyclerviewchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CarAdapter.ItemClicked {
    Button btnCar, btnOwner;
    ImageView ivMake;
    TextView tvModel, tvName, tvNumber;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCar = findViewById(R.id.btnCar);
        btnOwner = findViewById(R.id.btnOwner);
        ivMake = findViewById(R.id.ivMake);
        tvModel = findViewById(R.id.tvModel);
        tvName = findViewById(R.id.tvName);
        tvNumber = findViewById(R.id.tvNumber);

        fragmentManager = this.getSupportFragmentManager();

        if (findViewById(R.id.layout_portrait) != null) {
            fragmentManager.beginTransaction()
                    .show(fragmentManager.findFragmentById(R.id.listFrag))
                    .hide(fragmentManager.findFragmentById(R.id.buttonFrag))
                    .hide(fragmentManager.findFragmentById(R.id.carFrag))
                    .hide(fragmentManager.findFragmentById(R.id.ownerFrag))
                    .commit();
        }

        if (findViewById(R.id.layout_land) != null) {
            fragmentManager.beginTransaction()
                    .show(fragmentManager.findFragmentById(R.id.listFrag))
                    .show(fragmentManager.findFragmentById(R.id.buttonFrag))
                    .commit();

            onItemClicked(ApplicationClass.getCurrentIndex());
            showCarOrOwner();
        }

        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationClass.setShowCar(true);
                showCarInfo();
            }
        });

        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationClass.setShowCar(false);
                showOwnerInfo();
            }
        });
    }

    @Override
    public void onItemClicked(int index) {
        ApplicationClass.setCurrentIndex(index);
        Car car = ApplicationClass.carList.get(index);
        if (car.getMake().equals("mercedes")) {
            ivMake.setImageResource(R.drawable.mercedes);
        } else if (car.getMake().equals("nissan")) {
            ivMake.setImageResource(R.drawable.nissan);
        } else {
            ivMake.setImageResource(R.drawable.volkswagen);
        }
        tvModel.setText(car.getModel());
        tvName.setText(car.getOwnerName());
        tvNumber.setText(car.getOwnerNumber());

        if (findViewById(R.id.layout_portrait) != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentById(R.id.listFrag))
                    .show(fragmentManager.findFragmentById(R.id.buttonFrag))
                    .show(fragmentManager.findFragmentById(R.id.carFrag))
                    .hide(fragmentManager.findFragmentById(R.id.ownerFrag))
                    .addToBackStack(null)
                    .commit();

            showCarOrOwner();
        }
    }

    public void showCarInfo() {
        fragmentManager.beginTransaction()
                .show(fragmentManager.findFragmentById(R.id.carFrag))
                .hide(fragmentManager.findFragmentById(R.id.ownerFrag))
                .commit();
    }

    public void showOwnerInfo() {
        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentById(R.id.carFrag))
                .show(fragmentManager.findFragmentById(R.id.ownerFrag))
                .commit();
    }

    public void showCarOrOwner() {
        if (ApplicationClass.isShowCar()) {
            showCarInfo();
        } else {
            showOwnerInfo();
        }
    }
}
