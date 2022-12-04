package com.example.test_pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class Login_page extends AppCompatActivity {

    static String s0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.login_page);

        View.OnClickListener temp = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),signup_page.class);
                startActivity(i);
            }
        };

        findViewById(R.id.reg_button).setOnClickListener(temp);

        View.OnClickListener temp1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String s0= new String();
                EditText et0= (EditText) findViewById(R.id.login_user);
                s0=et0.getText().toString();
                if(s0.equals(""))return;
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("user_list").child(s0);
                ValueEventListener btn_lis= new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(),"you are not registered",Toast.LENGTH_LONG).show();
                        }
                        else{
                            String s= new String();
                            s=dataSnapshot.getValue(String.class);
                            String s1= new String();
                            EditText et= (EditText)findViewById(R.id.login_password);
                            s1=et.getText().toString();
                            if(!s.equals(s1)){
                                Toast.makeText(getApplicationContext(),"incorrect password",Toast.LENGTH_LONG).show();
                            }
                            else{
                                //
                                Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),Main_lobby.class);

                                i.putExtra("my_id",s0);
                                startActivity(i);
                                //
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                ref.addListenerForSingleValueEvent(btn_lis);
            }
        };

        findViewById(R.id.login).setOnClickListener(temp1);

    }
}
