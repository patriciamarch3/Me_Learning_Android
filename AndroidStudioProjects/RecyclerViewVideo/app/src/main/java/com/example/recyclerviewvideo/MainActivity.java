package com.example.recyclerviewvideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {
    Button btnAdd;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people.add(new Person("Jaehyun", "Jeong", "Bus"));
                myAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

//        layoutManager = new LinearLayoutManager(this);
//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        people = new ArrayList<Person>();
        people.add(new Person("Minyi", "Hu", "Bus"));
        people.add(new Person("Renjun", "Huang", "Plane"));
        people.add(new Person("Jeno", "Lee", "Plane"));
        people.add(new Person("Mark", "Lee", "Bus"));
        people.add(new Person("Haechan", "Lee", "Plane"));
        people.add(new Person("Jaemin", "Na", "Plane"));
        people.add(new Person("Chenle", "Zhong", "Bus"));
        people.add(new Person("Jisung", "Park", "Bus"));
        people.add(new Person("Winwin", "Dong", "Plane"));
        people.add(new Person("Kun", "Qian", "Bus"));
        people.add(new Person("Ten", "Lee", "Plane"));
        people.add(new Person("Lucas", "Huang", "Plane"));
        people.add(new Person("Yuta", "Natamoto", "Plane"));
        people.add(new Person("Taeyong", "Lee", "Plane"));
        people.add(new Person("Johnny", "Sue", "Bus"));

        myAdapter = new PersonAdapter(this, people);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this,
                "Surname: " + people.get(index).getSurname() + "\nName: " + people.get(index).getName(),
                Toast.LENGTH_SHORT).show();
    }
}
