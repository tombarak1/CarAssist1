package com.example.carassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CarDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    Button backListBtn;
    Button backMainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        backListBtn = (Button)findViewById(R.id.backListBtn);
        backMainBtn = (Button)findViewById(R.id.backMainBtn);

        backListBtn.setOnClickListener(this);
        backMainBtn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            long price = extras.getLong("price");
            String agency = extras.getString("agency");
            String comments = extras.getString("comments");
            byte[] photo = extras.getByteArray("photo");

            if (photo.length != 0)
            {
                Bitmap bitmapPhoto = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                ImageView myImage = (ImageView) findViewById(R.id.carImageView);
                myImage.setImageBitmap(Bitmap.createScaledBitmap(bitmapPhoto, 120, 120, false));
            }

            TextView tv = (TextView) findViewById(R.id.textView5);
            tv.setText("Car Model: " + name);
            tv = (TextView) findViewById(R.id.textView6);
            tv.setText("Price: " + price + "$");
            tv = (TextView) findViewById(R.id.textView7);
            tv.setText("Agency: " + agency);
            tv = (TextView) findViewById(R.id.textView8);
            tv.setText("Comments: " + comments);

        }
            }

    @Override
    public void onClick(View view) {
        if(view == backListBtn)
        {
            finish();
        }
        else if(view == backMainBtn)
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
