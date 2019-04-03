package com.kulzdev.bubblesproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kulzdev.bubblesproject.Models.ServicesList;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StylistLocationActivity extends AppCompatActivity {

    Button btnLocation;
    TextInputEditText mBusinessAddress;
    AppCompatSpinner mSpinner;
    String mServiceSeleted;
    private FirebaseAuth mAuth;
    private static final String TAG = "TAG";
    ServicesList mServices;
    ArrayList<String> mServicesList;

    User mUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stylist_location);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.service_category));

        mBusinessAddress = (TextInputEditText) findViewById(R.id.txtBusinesAddress);
        mSpinner = (AppCompatSpinner)findViewById(R.id.spinnerCategoryList);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        mSpinner.setAdapter(arrayAdapter);

        /*TODO: find the current user*/
        Query findUser = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("id")
                .equalTo(mAuth.getCurrentUser().getUid());

        findUser.addValueEventListener(findUsersByID);


     mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             Log.d("TAG","Entering ItemLister");

            if(parent.getItemAtPosition(position).toString().equals(parent.getItemAtPosition(0).toString())){
                //this is just a hint entry
            }else {
                mServiceSeleted = parent.getItemAtPosition(position).toString();

                Toast.makeText(getApplicationContext(), "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }

         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });




        btnLocation = (Button)findViewById(R.id.btnLocationNext);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*TODO: first find the user */


                /*TODO: Then update user's address */
                mUser.setUserAddress(mBusinessAddress.getText().toString());

                /*TODO: Then update user's service */
                if(mServiceSeleted.toString().equals("Hair Services")){
                    mServicesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.hair_services)));
                    mServices = new ServicesList("HairServices",mServicesList);
                    List<ServicesList> tmpList = new ArrayList<ServicesList>();
                    tmpList.add(mServices);
                    mUser.setServices(tmpList);  //hard coded,to be changed later

                    showMessage("Hair service selected");
                    updateUserInfo();
                    updateUI();
                }

                mBusinessAddress.getText();
                Toast.makeText(getApplicationContext(), " " + mBusinessAddress.getText() +" and " + mServiceSeleted.toString(), Toast.LENGTH_LONG).show();

               /* Intent intent =new Intent(getApplicationContext(),StylistHomeActivity.class);
                startActivity(intent);*/
            }
        });



    }

    private void updateUI() {
        Intent i = new Intent(StylistLocationActivity.this, StylistHomeActivity.class);
        startActivity(i);
    }

    private void updateUserInfo() {

        mDatabase.child(mAuth.getCurrentUser().getUid())
                .setValue(mUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            showMessage("Update successful");

                        }else{
                            showMessage("Update failed, Try Again");

                        }

                    }
                });
    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    ValueEventListener findUsersByID = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    mUser = dataSnap.getValue(User.class);

                    showMessage("User found");
                }

                Log.d(TAG,"dataSnapshot does  exist");



            }else{
                Log.d(TAG,"dataSnapshot does not exist");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
