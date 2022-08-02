package com.example.carassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button aboutBtn;
Button reviewBtn;
Button addNewCarBtn;
String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutBtn = (Button)findViewById(R.id.aboutBtn);
        reviewBtn = (Button)findViewById(R.id.reviewBtn);
        addNewCarBtn = (Button)findViewById(R.id.addBtn);

        aboutBtn.setOnClickListener(this);
        reviewBtn.setOnClickListener(this);
        addNewCarBtn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            currentUserId = (String)extras.get("userId");
        }
    }

    @Override
    public void onClick(View view) {
        if(view == aboutBtn)
        {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        else if(view == reviewBtn)
        {
            Intent intent = new Intent(this, CarListActivity.class);
            startActivity(intent);
        }
        else if(view == addNewCarBtn)
        {
            Intent intent = new Intent(this, AddNewCarActivity.class);
            startActivity(intent);
        }
    }
}
