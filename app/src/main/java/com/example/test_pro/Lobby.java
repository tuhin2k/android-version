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

public class Lobby extends AppCompatActivity {

    static String my_id;
    static  DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("user_status");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.lobby);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            my_id=bundle.getString("my_id");
        }

        findViewById(R.id.b33).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et=findViewById(R.id.oppo);
                final String oppo=et.getText().toString();
                if(oppo.equals(""))return;
                ref.child(oppo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(),"player not available",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            String s=dataSnapshot.getValue(String.class);
                            if(s.equals("offline")){
                                Toast.makeText(getApplicationContext(),"player not available or no player in this name",Toast.LENGTH_LONG).show();
                                return;
                            }
                            else if(s.equals("online")){
                                Intent i=new Intent(getApplicationContext(),Board.class);
                                i.putExtra("my_id",my_id);
                                i.putExtra("client_no",0);
                                i.putExtra("game_path",(String)(my_id+"_3"));
                                i.putExtra("r",3);
                                i.putExtra("c",3);
                                i.putExtra("oppo_id",oppo);
                                FirebaseDatabase.getInstance().getReference().child("user_status").child(my_id).setValue(my_id+"_3");

                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"player not available",Toast.LENGTH_LONG).show();
                                return;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        findViewById(R.id.b44).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et=findViewById(R.id.oppo);
                final String oppo=et.getText().toString();
                if(oppo.equals(""))return;
                ref.child(oppo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(),"player not available",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            String s=dataSnapshot.getValue(String.class);
                            if(s.equals("offline")){
                                Toast.makeText(getApplicationContext(),"player not available or no player in this name",Toast.LENGTH_LONG).show();
                                return;
                            }
                            else if(s.equals("online")){
                                Intent i=new Intent(getApplicationContext(),Board.class);
                                i.putExtra("my_id",my_id);
                                i.putExtra("client_no",0);
                                i.putExtra("game_path",(String)(my_id+"_4"));
                                i.putExtra("r",4);
                                i.putExtra("c",4);
                                i.putExtra("oppo_id",oppo);
                                FirebaseDatabase.getInstance().getReference().child("user_status").child(my_id).setValue(my_id+"_4");
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"player not available",Toast.LENGTH_LONG).show();
                                return;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        findViewById(R.id.b55).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et=findViewById(R.id.oppo);
                final String oppo=et.getText().toString();
                if(oppo.equals(""))return;
                ref.child(oppo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(),"player not available",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            String s=dataSnapshot.getValue(String.class);
                            if(s.equals("offline")){
                                Toast.makeText(getApplicationContext(),"player not available or no player in this name",Toast.LENGTH_LONG).show();
                                return;
                            }
                            else if(s.equals("online")){
                                Intent i=new Intent(getApplicationContext(),Board.class);
                                i.putExtra("my_id",my_id);
                                i.putExtra("client_no",0);
                                i.putExtra("game_path",(String)(my_id+"_5"));
                                i.putExtra("r",5);
                                i.putExtra("c",5);
                                i.putExtra("oppo_id",oppo);
                                FirebaseDatabase.getInstance().getReference().child("user_status").child(my_id).setValue(my_id+"_5");
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"player not available",Toast.LENGTH_LONG).show();
                                return;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        findViewById(R.id.b66).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et=findViewById(R.id.oppo);
                final String oppo=et.getText().toString();
                if(oppo.equals(""))return;
                ref.child(oppo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(),"player not available",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            String s=dataSnapshot.getValue(String.class);
                            if(s.equals("offline")){
                                Toast.makeText(getApplicationContext(),"player not available or no player in this name",Toast.LENGTH_LONG).show();
                                return;
                            }
                            else if(s.equals("online")){
                                Intent i=new Intent(getApplicationContext(),Board.class);
                                i.putExtra("my_id",my_id);
                                i.putExtra("client_no",0);
                                i.putExtra("game_path",(String)(my_id+"_6"));
                                i.putExtra("r",6);
                                i.putExtra("c",6);
                                i.putExtra("oppo_id",oppo);
                                FirebaseDatabase.getInstance().getReference().child("user_status").child(my_id).setValue(my_id+"_6");
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"player not available",Toast.LENGTH_LONG).show();
                                return;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
