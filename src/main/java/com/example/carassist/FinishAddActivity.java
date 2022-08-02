package com.example.carassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinishAddActivity extends AppCompatActivity implements View.OnClickListener {
    Button mainMenuBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_add);
        mainMenuBtn = (Button)findViewById(R.id.button);
        mainMenuBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mainMenuBtn)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}