package com.example.test_pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import android.os.Bundle;

public class Waiting extends AppCompatActivity {

    static String my_id;
    static DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("user_status");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.waiting);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            my_id=bundle.getString("my_id");
        }

        ref.child(my_id).setValue("online");



        ref.child(my_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().equals("offline"))return;
                if(dataSnapshot.getValue().equals("online"))return;
                Intent i=new Intent(getApplicationContext(),Board.class);
                i.putExtra("my_id",my_id);
                i.putExtra("client_no",1);
                i.putExtra("game_path",dataSnapshot.getValue(String.class));
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        ref.child(my_id).setValue("offline");
        super.onBackPressed();
    }
}
