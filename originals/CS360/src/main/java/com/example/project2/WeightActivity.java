package com.example.project2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WeightActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;

    MyDatabaseHelper myDBhelp;
    ArrayList<String> weight_id, weight_date, weight_current, weight_goal;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initiated the elements on the page
        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(WeightActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDBhelp = new MyDatabaseHelper(WeightActivity.this);
        weight_id = new ArrayList<>();
        weight_date = new ArrayList<>();
        weight_current = new ArrayList<>();
        weight_goal = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(WeightActivity.this, weight_id,weight_date, weight_current, weight_goal);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(WeightActivity.this));
    }
    // used to read data from database into arrays for displaying
    void storeDataInArrays(){
        Cursor cursor = myDBhelp.readAllData();
        if(cursor.getCount() == 0) { // informs user if no data is in database
            Toast.makeText(this, "No Data Entered", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {// goes through each line in the database to read items and display them.
                weight_id.add(cursor.getString(0));
                weight_date.add(cursor.getString(1));
                weight_current.add(cursor.getString(2));
                weight_goal.add(cursor.getString(3));
            }
        }
    }
}
