package com.example.test_pro;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main_lobby extends AppCompatActivity {

    static String my_id;
    static DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("user_status");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.main_lobby);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            my_id=bundle.getString("my_id");
        }

        ref.child(my_id).setValue("offline");



        findViewById(R.id.invite).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Lobby.class);
                i.putExtra("my_id",my_id);
                startActivity(i);
            }
        });

        findViewById(R.id.go_online).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Waiting.class);
                i.putExtra("my_id",my_id);
                startActivity(i);
            }
        });
    }
}
