package com.kulzdev.bubblesproject.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText mFullName, mEmail, mPhoneNumbers;
    private TextView mDisplayName;
    private FirebaseAuth mAuth;
    private CircleImageView mProfilePhoto;
    private User mUser;
    private final String TAG = "profile";
    private static int PReCode = 1;
    private static int REQUESTCODE = 1;
    private Uri pickedImg;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        mFullName = (TextInputEditText)findViewById(R.id.txtProfileFullName);
        mEmail = (TextInputEditText)findViewById(R.id.txtProfileEmail);
        mPhoneNumbers = (TextInputEditText)findViewById(R.id.txtProfilePhoneNumber);
        mDisplayName = findViewById(R.id.profile_displayName);


        mAuth = FirebaseAuth.getInstance();

        searchUser(mAuth.getCurrentUser());
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        mProfilePhoto = findViewById(R.id.profile_user_photo);


        findViewById(R.id.btnUpdateProfile).setOnClickListener(this);
        findViewById(R.id.profile_back_arrow).setOnClickListener(this);
        mProfilePhoto.setOnClickListener(this);
    }


    private void searchUser(FirebaseUser currentUser) {

        //find out who locked in
        Query findUser = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("id")
                .equalTo(currentUser.getUid());

        findUser.addValueEventListener(findUsersByID);
    }

    ValueEventListener findUsersByID = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    mUser = dataSnap.getValue(User.class);
                }

                //fill text field with db data
                mFullName.setText(mUser.getFullName());
                mEmail.setText(mUser.getEmail());
                mDisplayName.setText(mUser.getFullName());


                if(mUser.getPhoneNumber() != null){
                    mPhoneNumbers.setText(mUser.getPhoneNumber());
                }

                Glide.with(ClientProfileActivity.this).load(mAuth.getCurrentUser().getPhotoUrl()).into(mProfilePhoto);

                Log.d(TAG,"dataSnapshot does  exist");

            }else{
                Log.d(TAG,"dataSnapshot does not exist");

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.btnUpdateProfile:

                if(mUser != null){
                    //update the user with the given valudes
                    updateUser();

                }
                break;


            case R.id.profile_user_photo:
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestPermission();
                } else {
                    openGallery();
                }
                break;
            case R.id.profile_back_arrow:
                Intent i =  new Intent(this, ClientHomeActivity.class);
                startActivity(i);
                break;
        }

    }

    private void updateUser() {

        if(!validateForm()){
            return;
        }

        mUser.setEmail(mEmail.getText().toString());
        mUser.setFullName(mFullName.getText().toString());


        //phone number is not required
        String password = mPhoneNumbers.getText().toString();
        if (TextUtils.isEmpty(password)) {

        } else {
            mUser.setPhoneNumber(mPhoneNumbers.getText().toString());
        }

        mUser.setId(mAuth.getCurrentUser().getUid());

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mUser.getFullName())
                .build();




        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

        //update a user data
        mDatabase.child(mAuth.getCurrentUser().getUid())
                .setValue(mUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            showMessage("Update successful, Try Again");

                        }else{
                            showMessage("Update failed, Try Again");

                        }

                    }
                });


        //Update the Email
        user.updateEmail(mUser.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });


        if(pickedImg != null){
            //updating firebase Authentication user-profile
            StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_photos");
            final StorageReference imageFilePath = mStorage.child(pickedImg.getLastPathSegment());
            imageFilePath.putFile(pickedImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //image uploaded successfully
                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mUser.getFullName())
                                    .setPhotoUri(uri)
                                    .build();

                            user.updateProfile(profileUpdate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                showMessage("Update Successfull");
                                            }
                                        }
                                    });
                        }


                    });
                }
            });
        }

    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    private boolean validateForm() {

        /*TODO: Validate the Text Field Data
         *
         * */
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }



        String fullname = mFullName.getText().toString();
        if (TextUtils.isEmpty(fullname)) {
            mFullName.setError("Required.");
            valid = false;
        } else {
            mFullName.setError(null);
        }

        return valid;
    }



    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(ClientProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ClientProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(ClientProfileActivity.this, "Please accept for permission", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(ClientProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReCode);
                Log.d("Img", "checkAndRequestPermission permission accepted");
                openGallery();
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image

        Log.d("Img", "Gallery Image");

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null) {
            //the user has successfully picked an image
            //need to save it's reference to the Uri variable

            pickedImg = data.getData();
            mProfilePhoto.setImageURI(pickedImg);

        }
    }





}
