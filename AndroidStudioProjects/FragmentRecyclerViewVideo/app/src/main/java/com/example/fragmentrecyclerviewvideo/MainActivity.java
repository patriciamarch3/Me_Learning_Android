package com.example.fragmentrecyclerviewvideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {
    TextView tvName, tvTel;
    EditText etName, etTel;
    Button btnAdd;
    ListFrag listFrag;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        tvTel = findViewById(R.id.tvTel);
        etName = findViewById(R.id.etName);
        etTel = findViewById(R.id.etTel);
        btnAdd = findViewById(R.id.btnAdd);

        // get ListFrag
        fragmentManager = this.getSupportFragmentManager();
        listFrag = (ListFrag) fragmentManager.findFragmentById(R.id.listFrag);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phone = etTel.getText().toString();
                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter ALL Fields!", Toast.LENGTH_SHORT).show();
                } else {
                    ApplicationClass.people.add(new Person(name.trim(), phone.trim()));
                    Toast.makeText(MainActivity.this, "Person added SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    etName.setText(null);
                    etTel.setText(null);
                    listFrag.notifyDataChanged();
                }
            }
        });

        if (findViewById(R.id.layout_portrait) != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentById(R.id.detailFrag))
                    .hide(fragmentManager.findFragmentById(R.id.addPersonFrag))
                    .show(fragmentManager.findFragmentById(R.id.listFrag))
                    .commit();
        } else {
            onItemClicked(ApplicationClass.getCurrentIndex());
            fragmentManager.beginTransaction()
                    .show(fragmentManager.findFragmentById(R.id.detailFrag))
                    .show(fragmentManager.findFragmentById(R.id.addPersonFrag))
                    .show(fragmentManager.findFragmentById(R.id.listFrag))
                    .commit();
        }
    }

    @Override
    public void onItemClicked(int index) {
        ApplicationClass.setCurrentIndex(index);

        tvName.setText(ApplicationClass.people.get(index).getName());
        tvTel.setText(ApplicationClass.people.get(index).getPhone());

        if (findViewById(R.id.layout_portrait) != null) {
            fragmentManager.beginTransaction()
                    .show(fragmentManager.findFragmentById(R.id.detailFrag))
                    .show(fragmentManager.findFragmentById(R.id.addPersonFrag))
                    .hide(fragmentManager.findFragmentById(R.id.listFrag))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
