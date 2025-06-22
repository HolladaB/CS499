package com.example.project2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //displays for permission information
    private static final int SMS_CODE = 1;
    private TextView permission;
    private TextView notification;
    private Button permissionButton;

    // displays for user login
    private TextView userNameText;
    private TextView passWordText;
    Button loginButton;
    Button createAccountButton;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userNameText = findViewById(R.id.userNameText);
        passWordText = findViewById(R.id.passWordText);

        db = new DatabaseHelper(this);

        // creates login button
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> loginUser());

        //create account button for new users
        createAccountButton = findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(view -> registerUser());

        //creates permissions buttons and displays
        permission = findViewById(R.id.permission);
        notification = findViewById(R.id.notification);
        permissionButton = findViewById(R.id.permissionButton);

        checkPermission();// sends to check for permissions
        // button to allow user to request permissions
        permissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();

            }
        });
    }

    //method for checking users login information
    private void loginUser() {
        String userName = userNameText.getText().toString();
        String passWord = passWordText.getText().toString();

        if(userName.isEmpty() || passWord.isEmpty()){ // verifies the input fields are filled im
            Toast.makeText(this, "Fields are Blank!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            if(db.checkUserPassWord(userName, passWord)){ // sends to method to check username and password combo
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, WeightActivity.class);
                startActivity(intent); //sends user to next screen if login successful
            }
            else { //informs user of login failure
                Toast.makeText(this, "Login Failed!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // method for registering the user
    private void registerUser() {
        String userName = userNameText.getText().toString();
        String passWord = passWordText.getText().toString();

        if(userName.isEmpty() || passWord.isEmpty()){ // verifies the input fields are filled out
            Toast.makeText(this, "Fields are Blank!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (db.checkUserName(userName)) { // checks to see if username exists, would change to not inform user for security reasons
                Toast.makeText(this, "Username Exists!", Toast.LENGTH_SHORT).show();
            } else {
                if (db.insertData(userName, passWord)) { // verifies the account was created
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                } else { // catches any errors and informs the user
                    Toast.makeText(this, "Registration Error!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //checks if allowed to send SMS and informs the user of permissions.
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            permission.setText("Permission Granted");
            notification.setVisibility(View.VISIBLE);
            notification.setText("You will receive notifications");
        } else {
            permission.setText("Permission Denied");
            notification.setVisibility(View.GONE);
        }
    }


    //requests permissions
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.SEND_SMS)) {
            // Show an explanation to the user why the permission is needed
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, SMS_CODE);
        }
    }
    //returns the users SMS permissions
    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_CODE) {
            //if permissions are granted it notifies user and sends SMS
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permission.setText("Permission Granted");
                notification.setVisibility(View.VISIBLE);
                notification.setText("You will receive notifications");
                sendSMS();
            } else {
                //lets user know if permissions are denied
                permission.setText("Permission Denied");
                notification.setVisibility(View.GONE);
            }

        }
    }
    //method for sending sms notifications, this would be expanded in a functioning app and on a phone thats testable
    private void sendSMS() {
        String phoneNumber = "999-999-9999"; // Replace with users number
        String message = "Thank you for accepting notifications!"; // send message when accepting SMS notifications

        SmsManager smsManager = SmsManager.getDefault(); // not sure of a workaround for this
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}