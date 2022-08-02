package com.example.carassist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewCarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap photo;
    private Button photoButton;
    private Button backButton;
    private Button finishButton;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText agencyEditText;
    private EditText commentsEditText;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        photoButton = (Button) this.findViewById(R.id.takePhotoBtn);
        backButton =  (Button) this.findViewById(R.id.button4);
        finishButton = (Button) this.findViewById(R.id.finishBtn);
        nameEditText = (EditText) this.findViewById(R.id.carNameEditText);
        priceEditText = (EditText) this.findViewById(R.id.priceEditText);
        agencyEditText = (EditText) this.findViewById(R.id.carAgencyDetailsEditText);
        commentsEditText = (EditText) this.findViewById(R.id.commentsEditText);
        photoButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        photo = null;
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            photoButton.setText("Take New Photo");
        }
    }

    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean saveNewCar() {
        if (nameEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter valid car name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (priceEditText.getText().toString().isEmpty() || !isInteger(priceEditText.getText().toString())) {
            Toast.makeText(this, "Please enter valid car price (integer)", Toast.LENGTH_LONG).show();
            return false;
        }
        if (agencyEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter valid agency", Toast.LENGTH_LONG).show();
            return false;
        }

        Car c = new Car(nameEditText.getText().toString(), Integer.valueOf(priceEditText.getText().toString()), agencyEditText.getText().toString(), commentsEditText.getText().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ArrayList<Integer> data = new ArrayList<>();
        if (photo != null)
        {
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            for (byte b: bytes)
            {
                data.add(Byte.toUnsignedInt(b));
            }
        }

        Map<String, Object> message = new HashMap<>();
        message.put("name", c.getName());
        message.put("price", c.getPrice());
        message.put("agency", c.getAgency());
        message.put("comments", c.getComments());
        message.put("photo", data);
        db.collection("cars").add(message);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        if (view == photoButton) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        else if (view == finishButton) {
            if( saveNewCar()) {
                finish();
            }
        }
        else if (view == backButton) {
            finish();
        }
    }
}

