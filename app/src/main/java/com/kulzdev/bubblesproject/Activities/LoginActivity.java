package com.kulzdev.bubblesproject.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

public class LoginActivity extends AppCompatActivity implements
    View.OnClickListener{

    private DatabaseReference mDatabase;
    private EditText mEmailField, mPasswordField;
    private Button mBtnLogin;
    private TextView signUp;
    private FirebaseAuth mAuth;
    private User findUser;
    private ProgressBar progressBar;
    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //FireBase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        //Text Views
        mEmailField = (EditText)findViewById(R.id.Email);
        mPasswordField = (EditText)findViewById(R.id.Password);

        //clickable Views
        findViewById(R.id.txtSignUp).setOnClickListener(this);
        findViewById(R.id.facebookLayout).setOnClickListener(this);
        findViewById(R.id.gmailLayout).setOnClickListener(this);
        findViewById(R.id.txtForgotPassword).setOnClickListener(this);

        mBtnLogin = (Button)findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


    }

    private void signIn(String email, String password) {

       // Log.d(TAG, "signIn:" + email + " Pass: " + password);

        //Text Fields Validation
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //find out who locked in
                    Query findUser = FirebaseDatabase.getInstance().getReference("users")
                            .orderByChild("id")
                            .equalTo(mAuth.getCurrentUser().getUid());

                    findUser.addValueEventListener(findUsersByID);
                }else{

                    mBtnLogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void updateUI(){

        if(findUser.getUserType().toString().equals("Client")){
            Intent i = new Intent(LoginActivity.this, ClientHomeActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(LoginActivity.this, ServicesRegistration.class);
            startActivity(i);
            //Toast.makeText(getApplicationContext(),"Stylist Activity Doesn't exist YET",Toast.LENGTH_LONG).show();

            //Intent i = new Intent(LoginActivity.this, StylistActivity.class);
           // startActivity(i);
        }

    }


    ValueEventListener findUsersByID = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    findUser = dataSnap.getValue(User.class);
                }

                Log.d(TAG,"dataSnapshot does  exist");

                updateUI();
            }else{
                Log.d(TAG,"dataSnapshot does not exist");

                showMessage("Email / Password Not Found");

                mBtnLogin.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View view){

        /*TODO: All OnClick functionality
         *
         * */

        int id = view.getId();

        switch (id){
            case R.id.facebookLayout:
                Toast.makeText(getApplicationContext(),"Log in with facebook", Toast.LENGTH_LONG).show();
                break;

            case R.id.gmailLayout:
                Toast.makeText(getApplicationContext(),"Log in with Gmail", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnLogin:
                mBtnLogin.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                break;

            case R.id.txtSignUp:
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                break;
            case R.id.txtForgotPassword:
                Toast.makeText(getApplicationContext(),"Forgot Password to be implemented", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private boolean validateForm() {

        /*TODO: Validate the Text Field Data
        *
        * */
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


}
