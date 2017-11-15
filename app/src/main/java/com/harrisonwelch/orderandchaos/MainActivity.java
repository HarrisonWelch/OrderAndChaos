/*
* Name: Harrison Welch
* Class: CSCI 4010 Mobile Development
* Date: 11-6-2017 8:43 PM
* */
package com.harrisonwelch.orderandchaos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_play);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // what happens when user presses play
                Intent i = new Intent(getApplicationContext(),GameActivity.class);
                startActivity(i);
            }
        });

        button = (Button) findViewById(R.id.btn_more);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // what happens when user presses More
                final String URL = "https://en.wikipedia.org/wiki/Order_and_Chaos";
                Uri uri = Uri.parse(URL);
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        });

        button = (Button) findViewById(R.id.btn_about);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // what happens when user presses About
                Intent i = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(i);
            }
        });
    }
}
