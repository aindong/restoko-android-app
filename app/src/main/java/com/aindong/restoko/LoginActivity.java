package com.aindong.restoko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get the reference of controls to be able to access their values
        final EditText textUsername = (EditText) findViewById(R.id.textUsername);
        final EditText textPassword = (EditText) findViewById(R.id.textPassword);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(textUsername, textPassword);
            }
        });
    }

    private void doLogin(final EditText username, final EditText password) {
        // Show a toast message for feedback purposes of button click
        Toast toast = Toast.makeText(getApplicationContext(), "Authenticating please wait...", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        // Create a new thread for processing the login
        // This is needed to not disrupt the main thread or ui thread from hanging
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    // Create a new intent for showing main activity
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("username", username.getText().toString());
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Start the thread worker
        thread.start();
    }
}
