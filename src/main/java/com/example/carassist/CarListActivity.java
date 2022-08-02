package com.example.carassist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button backBtn;
    ArrayList<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        list = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        backBtn = (Button)(findViewById(R.id.button3));
        backBtn.setOnClickListener(this);
        ListView carList = findViewById(R.id.carList);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> data = db.collection("cars").get();
        while (!data.isComplete())
        {
        }

        for (QueryDocumentSnapshot document : data.getResult()) {
            Map<String, Object> message = new HashMap<>();
            message.put("name", (String) document.get("name"));
            message.put("price", (long) document.get("price"));
            message.put("agency", (String) document.get("agency"));
            message.put("comments", (String) document.get("comments"));
            if (document.get("photo") != null)
            {
                message.put("photo", (ArrayList<Long>) document.get("photo"));
            }
            else
            {
                message.put("photo", null);
            }

            list.add(message);
        }

        ArrayList<String> carNames = new ArrayList<>();
        for (Map<String, Object> map : list)
        {
            carNames.add((String) map.get("name"));
        }

        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                carNames);
        carList.setAdapter(arr);
        carList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backBtn)
        {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String carname =(String) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(this, CarDetailsActivity.class);
        
        for (Map<String, Object> map : list)
        {
            if (carname.equals((String) map.get("name")))
            {
                intent.putExtra("name",(String) map.get("name"));
                intent.putExtra("price",(long) map.get("price"));
                intent.putExtra("agency",(String) map.get("agency"));
                intent.putExtra("comments",(String) map.get("comments"));

                ArrayList<Long> lis = (ArrayList<Long>) map.get("photo");
                byte[] photoBytes = new byte[lis.size()];
                for (int j = 0; j < lis.size(); j++)
                {
                    photoBytes[j] = lis.get(j).byteValue();
                }

                intent.putExtra("photo",photoBytes);
            }
        }
        
        startActivity(intent);
    }
}