package com.example.eleaveloginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerUserProfile extends AppCompatActivity {

    private TextView tvName, tvEmail, tvNIC, tvPhoneNumber, tvDepartment,tvPosition;
    private Button toUpdateUserProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference users;
    private FirebaseDatabase db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        user=mAuth.getCurrentUser();

        tvName = (TextView) findViewById(R.id.tvName2);
        tvEmail = (TextView) findViewById(R.id.tvEmail2);
        tvNIC = (TextView) findViewById(R.id.tvNic2);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber2);
        tvPosition = (TextView) findViewById(R.id.tvPosition2);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment2);

        toUpdateUserProfile = (Button) findViewById(R.id.btnUpdate2);
        toUpdateUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUpdateUserProfile();
            }


        });

    }

    private void toUpdateUserProfile() {
        Intent intent = new Intent(this, ManagerUpdateUserProfile.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataProfile();
    }

    public void DataProfile(){
        users.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvName.setText(snapshot.child("name").getValue().toString());
                tvEmail.setText(snapshot.child("email").getValue().toString());
                tvNIC.setText(snapshot.child("nic").getValue().toString());
                tvPhoneNumber.setText(snapshot.child("phoneNum").getValue().toString());
                tvDepartment.setText(snapshot.child("department").getValue().toString());
                tvPosition.setText(snapshot.child("position").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}