package com.kulzdev.bubblesproject.Activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.kulzdev.bubblesproject.R;

public class ClientProfileActivity extends AppCompatActivity {

    private TextInputEditText mFullName, mEmail, mPhoneNumbers;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        mFullName = (TextInputEditText)findViewById(R.id.txtProfileFullName);
        mEmail = (TextInputEditText)findViewById(R.id.txtProfileEmail);
        mPhoneNumbers = (TextInputEditText)findViewById(R.id.txtProfileEmail);

        mAuth = FirebaseAuth.getInstance();

        mFullName.setText("Katlego Milton Molala");
        mEmail.setText("k.katlego@gmail.com");
        mPhoneNumbers.setText("0657896545");

    }
}
