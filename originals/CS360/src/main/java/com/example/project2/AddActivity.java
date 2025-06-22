package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    EditText dateInput, weightInput, goalInput;
    Button inputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //sets up input methods
        dateInput = findViewById(R.id.dateInput);
        weightInput = findViewById(R.id.weightInput);
        goalInput = findViewById(R.id.goalInput);
        inputButton = findViewById(R.id.inputButton);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // on clicking add button, data attempts to go into database
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper (AddActivity.this);
                myDB.addWeight(dateInput.getText().toString().trim(), // sends input values to add method
                        Integer.valueOf(weightInput.getText().toString().trim()),
                        Integer.valueOf(goalInput.getText().toString().trim()));

                Intent intent = new Intent(AddActivity.this, WeightActivity.class);
                startActivity(intent); // used to send user back to table display after adding weight
            }
        });
    }
}