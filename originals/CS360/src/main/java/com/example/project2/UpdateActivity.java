package com.example.project2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {

    EditText date_input, weight_input, goal_input;
    Button update_button, delete_button;

    String id, date, weight, goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        date_input = findViewById(R.id.dateInput2);
        weight_input = findViewById(R.id.weightInput2);
        goal_input = findViewById(R.id.goalInput2);
        update_button = findViewById(R.id.updateButton);
        delete_button = findViewById(R.id.deleteButton);

        // reads input data if user clicks to update
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                date = date_input.getText().toString().trim();
                weight = weight_input.getText().toString().trim();
                goal = goal_input.getText().toString().trim();
                myDB.updateData(id, date, weight, goal);

                Intent intent = new Intent(UpdateActivity.this, WeightActivity.class);
                startActivity(intent); // sends user back to table
            }
        });
         // sends confirmation dialog if user clicks delete button
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        getAndSetIntentData();
    }

    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("date")
                && getIntent().hasExtra("weight") && getIntent().hasExtra("goal")) {
            //getting data
            id = getIntent().getStringExtra("id");
            date = getIntent().getStringExtra("date");
            weight = getIntent().getStringExtra("weight");
            goal = getIntent().getStringExtra("goal");

            //setting data
            date_input.setText(date);
            weight_input.setText(weight);
            goal_input.setText(goal);
        }
        else {
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();
        }
    }

    // creates a dialog box for user to confirm intention to delete data
    void confirmDialog() {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Delete " + id + " ?");
        builder.setMessage("Are you sure you want to delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { //initiates positve button as yes
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);

                Intent intent = new Intent(UpdateActivity.this, WeightActivity.class);
                startActivity(intent);// sends user back to table if they accept to delete data
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {//initiates negative button as no
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}