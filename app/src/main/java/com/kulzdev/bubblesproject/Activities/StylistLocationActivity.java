package com.kulzdev.bubblesproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kulzdev.bubblesproject.R;

public class StylistLocationActivity extends AppCompatActivity {

    Button btnLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stylist_location);

        btnLocation = (Button)findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),ServicesRegistration.class);
                startActivity(intent);
            }
        });

    }
}
