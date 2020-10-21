package com.greentechnology.firebasechatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.greentechnology.firebasechatapp.Model.UserInfo;

public class RegistrationActivity extends AppCompatActivity {

    TextInputLayout userEmailEt, userPasswordEt, userNameEt, userMobileEt;
    Button createAccountBtn;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mAuth = FirebaseAuth.getInstance();

        userRef = FirebaseDatabase.getInstance().getReference("UsersInformation");
        
        userEmailEt = (TextInputLayout) findViewById(R.id.userEmailET);
        userPasswordEt = (TextInputLayout) findViewById(R.id.userPasswordET);
        userNameEt = (TextInputLayout) findViewById(R.id.userNameET);
        userMobileEt = (TextInputLayout) findViewById(R.id.userMoibleET);





        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait...");
        createAccountBtn = (Button)  findViewById(R.id.createAccountBtn);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if(!TextUtils.isEmpty(userEmailEt.getEditText().getText().toString()) && !TextUtils.isEmpty(userPasswordEt.getEditText().getText().toString()) && !TextUtils.isEmpty(userNameEt.getEditText().getText().toString()) && !TextUtils.isEmpty(userMobileEt.getEditText().getText().toString())){
                    Toast.makeText(RegistrationActivity.this, "Ok", Toast.LENGTH_SHORT).show();


                    mAuth.createUserWithEmailAndPassword(userEmailEt.getEditText().getText().toString(), userPasswordEt.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                UserInfo userInfo = new UserInfo(userNameEt.getEditText().getText().toString(), userEmailEt.getEditText().getText().toString(), userMobileEt.getEditText().getText().toString(), userPasswordEt.getEditText().getText().toString());
                                userRef.child(mAuth.getCurrentUser().getUid()).setValue(userInfo);
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



                }else{
                    Toast.makeText(RegistrationActivity.this, "Kindly Enter Require information!", Toast.LENGTH_SHORT).show();
//
                }
                
            }
        });
    }
}
