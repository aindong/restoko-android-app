package com.aindong.restoko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get extra values from intent
        Bundle extra = this.getIntent().getExtras();

        if (extra == null) {
            return;
        }

        TextView textWelcome = (TextView) findViewById(R.id.textWelcome);
        textWelcome.setText("Welcome " + extra.getString("username"));
    }
}
