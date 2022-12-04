package com.example.test_pro;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class signup_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.signup_page);

        View.OnClickListener temp1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s0= new String();
                EditText et0= (EditText) findViewById(R.id.reg_user);
                s0=et0.getText().toString();
                if(s0.equals(""))return;
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("user_list").child(s0);
                ValueEventListener btn_lis= new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s= new String();
                        EditText et= (EditText) findViewById(R.id.reg_user);
                        s=et.getText().toString();

                        if(!dataSnapshot.exists()){
                            String pass=new String();
                            EditText etpass=(EditText)findViewById(R.id.reg_password);
                            pass=etpass.getText().toString();
                            FirebaseDatabase.getInstance().getReference().child("user_list").child(s).setValue(pass);
                            Toast.makeText(getApplicationContext(),"you are registered",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{

                            Toast.makeText(getApplicationContext(),s+" already exists",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                ref.addListenerForSingleValueEvent(btn_lis);
            }
        };

        findViewById(R.id.register).setOnClickListener(temp1);
    }
}
