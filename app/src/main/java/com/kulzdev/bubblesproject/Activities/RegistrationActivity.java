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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

public class RegistrationActivity extends AppCompatActivity implements
View.OnClickListener{



    private EditText mUserFullName, mUserEmail, mUserPassword, mUserConfirmPassword;
    private ProgressBar loadingProgress;
    private Button registerBtn;
    private TextView userLogin;



    private String TAG = "TAG";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private RadioGroup radioGroup;

    private RadioButton userTypeRbtn;
    private RadioButton clientRadioButton;
    private RadioButton stylistRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //FireBase instances
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        //Text Fields
        mUserConfirmPassword = (EditText) findViewById(R.id.txtViewConfirmPassword);
        mUserFullName = (EditText) findViewById(R.id.txtViewfullName);
        mUserEmail = (EditText) findViewById(R.id.txtViewRegEmail);
        mUserPassword = (EditText) findViewById(R.id.txtViewRegPassword);



        //radio group
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        stylistRadioButton = (RadioButton) findViewById(R.id.stylist_radioBtn);
        clientRadioButton = (RadioButton) findViewById(R.id.client_radioBtn);

        userLogin = (TextView) findViewById(R.id.txtLogin);
        userLogin.setOnClickListener(this);

        registerBtn = (Button) findViewById(R.id.btnRegister);
        registerBtn.setOnClickListener(this);

        loadingProgress = (ProgressBar) findViewById(R.id.registerProgressBar);
        loadingProgress.setVisibility(View.INVISIBLE);
    }



    private void CreateUserAccount(String password, final User user) {
        /*TODO: Create a new user user email and password - Authentication Database*/

        //Text Fields Validation
        if (!validateForm()) {

            registerBtn.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.INVISIBLE);

            return;
        }else{

            Log.d(TAG, user.getEmail() + " ; " + user.getFullName()+ " ; " + password);
           mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isComplete() && mAuth.getCurrentUser() != null) {
                                showMessage("Account Created");
                               // Log.d(TAG,"mAuth Id : " + mAuth);

                                user.setId(mAuth.getCurrentUser().getUid()); //this will match the mAuth and Database userID
                                updateUserInfo(user, mAuth.getCurrentUser());
                            } else {
                                showMessage("Account creation failed" + task.getException().getMessage());
                                registerBtn.setVisibility(View.VISIBLE);
                                loadingProgress.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }


    }


    private void updateUserInfo(final User user,final FirebaseUser currentUser) {
        /*TODO: Create a new user - Bubbles Database*/
       // user.setId(currentUser.getUid());
        if(user.getId() != null) {
            mDatabase.child(user.getId())
                    .setValue(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                showMessage(user.getUserType());
                                if (user.getUserType().toString().equals("Stylist")) {
                                    Log.d(TAG, "We are here: " + user.getUserType());
                                    Intent i = new Intent(RegistrationActivity.this, StylistLocationActivity.class);
                                    startActivity(i);
                                } else {
                                    //showMessage("Not a stylist");
                                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(i);
                                }

                            } else {
                                showMessage("Registration failed, Try Again");
                                registerBtn.setVisibility(View.VISIBLE);
                                loadingProgress.setVisibility(View.INVISIBLE);
                            }

                        }
                    });
        }else{
            showMessage("UserID is : null  " + currentUser );
        }

    }

    private void showMessage(String message) {
        /*TODO: Generating Toast Messages*/
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private boolean validateForm() {
        /*TODO: Validate Text Fields*/

        boolean valid = true;


        String fullName = mUserFullName.getText().toString();
        if (TextUtils.isEmpty(fullName)) {
            mUserFullName.setError("Required.");
            valid = false;
        } else {
            mUserFullName.setError(null);
        }

        String email = mUserEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUserEmail.setError("Required.");
            valid = false;
        } else {
            mUserEmail.setError(null);
        }

        String password = mUserPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mUserPassword.setError("Required.");
            valid = false;
        } else {
            mUserPassword.setError(null);
        }

        String confirmPassword = mUserConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            mUserConfirmPassword.setError("Required.");
            valid = false;
        } else {
            mUserConfirmPassword.setError(null);
        }



        return valid;
    }


    @Override
    public void onClick(View v) {
        /*TODO: All onClick functionality*/

        int id = v.getId();

        switch (id){

            case R.id.btnRegister:
                int radioId = radioGroup.getCheckedRadioButtonId();
                userTypeRbtn = findViewById(radioId);
                String userType = userTypeRbtn.getText().toString();
                String fullName = mUserFullName.getText().toString();
                String email = mUserEmail.getText().toString();
                String password = mUserPassword.getText().toString();


                User user = new User(fullName,email, userType);

                registerBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);


                CreateUserAccount(password, user);


                break;

            case R.id.txtLogin:
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
                break;

        }
    }
}
