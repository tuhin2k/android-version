package com.example.test_pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class Board extends AppCompatActivity {

    static int background_rotation=1,background_number=12;
    static int r=-1,c=-1;
    static int grid[][];
    static int turn;
    static int rotation=0;
    static boolean v_click=false;
    static int client;
    static int point1=0,point2=0;
    static String s1="1st Player's turn",s2="2nd Player's turn",s11="1st Player won",s22="2nd Player won";
    static String path_name;
    static DatabaseReference path_ref;
    static MediaPlayer mp;
    static boolean is_sound=false;
    static Button sound_button,wallpaper;
    static EditText chat;
    static DatabaseReference msg_ref;
    static String my_id,oppo_id=null;
    static  int g=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.board);

        Bundle bundle=getIntent().getExtras();

        client=bundle.getInt("client_no");

        my_id=bundle.getString("my_id");

        path_name=bundle.getString("game_path");


        if(client==0){
            path_ref=FirebaseDatabase.getInstance().getReference().child("running_games").child(path_name).child("game_data");
            msg_ref=FirebaseDatabase.getInstance().getReference().child("running_games").child(path_name);

            oppo_id=bundle.getString("oppo_id");

            r=bundle.getInt("r");
            c=bundle.getInt("c");

            path_ref.setValue(0);
            msg_ref.child("client_0").setValue("no_data");
            msg_ref.child("client_1").setValue("no_data");

            FirebaseDatabase.getInstance().getReference().child("user_status").child(oppo_id).setValue(path_name);

            s1=my_id+"'s turn";
            s2=oppo_id+"'s turn";
            s11=my_id+" won";
            s22=oppo_id+"won";
        }

        if(client==1){
            path_ref=FirebaseDatabase.getInstance().getReference().child("running_games").child(path_name).child("game_data");
            msg_ref=FirebaseDatabase.getInstance().getReference().child("running_games").child(path_name);

            int dimention=path_name.charAt(path_name.length()-1)-'0';
            r=dimention;
            c=dimention;
            oppo_id=path_name.toString();
            oppo_id=oppo_id.substring(0,oppo_id.length()-1);
            oppo_id=oppo_id.substring(0,oppo_id.length()-1);

            s1=oppo_id+"'s turn";
            s2=my_id+"'s turn";
            s11=oppo_id+" won";
            s22=my_id+"won";
        }

        final Drawable drawable = getResources().getDrawable(R.drawable.a1);


        AbsoluteLayout mylayout = (AbsoluteLayout) findViewById(R.id.pnl);

        mylayout.setBackground(drawable);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Point start_point = new Point();
        start_point.y = ((height) / 3);
        start_point.x = ((width) / 20);
        int box_len = (width - 2 * start_point.x) / c;
        int bdr = (int)(((double)25/1080)*width);
        turn = r * (c + 1) + c * (r + 1);

        grid=new int[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                grid[i][j] = 4;
            }
        }

        TextView pl1 = new TextView(this);
        TextView pl2 = new TextView(this);
        pl1.setBackgroundColor(Color.YELLOW);
        pl2.setBackgroundColor(Color.YELLOW);
        pl1.setId((int) 1001);
        pl2.setId((int) 1002);

        if(client==1){
            pl2.setText(" "+my_id+" : ");
            pl1.setText(" "+oppo_id+" : ");
        }
        else{
            pl2.setText(" "+oppo_id+" : ");
            pl1.setText(" "+my_id+" : ");
        }

        pl1.setTextColor(Color.BLUE);
        pl2.setTextColor(Color.BLUE);
        AbsoluteLayout.LayoutParams ot1 = new AbsoluteLayout.LayoutParams(400, 40, 100, 100);
        AbsoluteLayout.LayoutParams ot2 = new AbsoluteLayout.LayoutParams(400, 40, 100, 150);
        pl1.setTextSize(12);
        pl2.setTextSize(12);
        mylayout.addView(pl1, ot1);
        mylayout.addView(pl2, ot2);

        TextView pt1 = new TextView(this);
        TextView pt2 = new TextView(this);
        pt1.setBackgroundColor(Color.YELLOW);
        pt2.setBackgroundColor(Color.YELLOW);
        pt1.setId((int) 1003);
        pt2.setId((int) 1004);
        pt1.setText("0");
        pt2.setText("0");
        pt1.setTextColor(Color.BLUE);
        pt2.setTextColor(Color.BLUE);
        AbsoluteLayout.LayoutParams ott1 = new AbsoluteLayout.LayoutParams(100, 40, 500, 100);
        AbsoluteLayout.LayoutParams ott2 = new AbsoluteLayout.LayoutParams(100, 40, 500, 150);
        pt1.setTextSize(12);
        pt2.setTextSize(12);
        mylayout.addView(pt1, ott1);
        mylayout.addView(pt2, ott2);

        TextView bd = new TextView(this);
        bd.setText(s1);
        bd.setBackgroundColor(Color.YELLOW);
        bd.setId((int) 1005);
        bd.setTextColor(Color.BLUE);
        AbsoluteLayout.LayoutParams bdot = new AbsoluteLayout.LayoutParams(500, 40, 100, 200);
        bd.setTextSize(12);
        mylayout.addView(bd, bdot);

        path_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int button_id=dataSnapshot.getValue(Integer.class);
                if(button_id==0)return;
                if(findViewById(button_id).isClickable()==false)return;
                v_click=true;
                findViewById(button_id).callOnClick();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mp=MediaPlayer.create(this,R.raw.click_sound);

        View.OnClickListener T = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean pre=v_click;

                if(v_click==true||rotation%2==client){
                    if(is_sound==true)
                    mp.start();
                }
                else{
                    return;
                }
                v_click=false;


                turn--;
                int p = 0, r, c, h, x = view.getId();
                Button b1 = (Button) findViewById(x);
                h = x % 10;
                x /= 10;
                c = x % 10;
                x /= 10;
                r = x % 10;
                grid[r][c]--;
                if (grid[r][c] == 0) p++;
                Button b2 = null;
                if (h == 1) {
                    if (c != 0) {
                        b2 = (Button) findViewById(r * 100 + (c - 1) * 10 + 2);
                        grid[r][c - 1]--;
                        if (grid[r][c - 1] == 0) p++;
                    }
                }
                if (h == 2) {
                    if (c != Board.c - 1) {
                        b2 = (Button) findViewById(r * 100 + (c + 1) * 10 + 1);
                        grid[r][c + 1]--;
                        if (grid[r][c + 1] == 0) p++;
                    }
                }
                if (h == 3) {
                    if (r != 0) {
                        b2 = (Button) findViewById((r - 1) * 100 + c * 10 + 4);
                        grid[r - 1][c]--;
                        if (grid[r - 1][c] == 0) p++;
                    }
                }
                if (h == 4) {
                    if (r != Board.r - 1) {
                        b2 = (Button) findViewById((r + 1) * 100 + c * 10 + 3);
                        grid[r + 1][c]--;
                        if (grid[r + 1][c] == 0) p++;
                    }
                }
                b1.setClickable(false);
                if(pre==true)
                b1.setBackgroundColor(Color.RED);
                else b1.setBackgroundColor(Color.GREEN);
                if (b2 != null) {
                    b2.setClickable(false);
                    if(pre==true)
                    b2.setBackgroundColor(Color.RED);
                    else b2.setBackgroundColor(Color.GREEN);
                }

                if (rotation % 2 == 0) point1 += p;
                else point2 += p;


                if (p != 0) {
                    if (rotation % 2 == 0) {
                        TextView tv = findViewById((int) 1003);
                        tv.setText(String.valueOf(point1));
                    } else {
                        TextView tv = findViewById((int) 1004);
                        tv.setText(String.valueOf(point2));
                    }
                }

                rotation++;

                if (p != 0) rotation--;

                if(pre==false)
                path_ref.setValue(view.getId());

                if (turn == 0) {
                    TextView tv = findViewById((int) 1005);
                    if (point1 > point2) {
                        tv.setText(s11);
                    } else if (point1 < point2) {
                        tv.setText(s22);
                    } else {
                        tv.setText("Draw");
                    }
                    return;
                }



                if (rotation % 2 == 0) {
                    TextView tv = findViewById((int) 1005);
                    tv.setText(s1);
                } else {
                    TextView tv = findViewById((int) 1005);
                    tv.setText(s2);
                }
            }
        };

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Button b1 = new Button(this);
                Button b2 = new Button(this);
                Button b3 = new Button(this);
                Button b4 = new Button(this);
                b1.setId(i * 100 + j * 10 + 1);
                b2.setId(i * 100 + j * 10 + 2);
                b3.setId(i * 100 + j * 10 + 3);
                b4.setId(i * 100 + j * 10 + 4);
                b1.setBackgroundColor(Color.BLACK);
                b2.setBackgroundColor(Color.BLACK);
                b3.setBackgroundColor(Color.BLACK);
                b4.setBackgroundColor(Color.BLACK);
                AbsoluteLayout.LayoutParams obj1 = new AbsoluteLayout.LayoutParams(bdr + 0, box_len - bdr - bdr, start_point.x + (j * box_len), start_point.y + (i * box_len) + bdr);
                AbsoluteLayout.LayoutParams obj2 = new AbsoluteLayout.LayoutParams(bdr + 0, box_len - bdr - bdr, start_point.x + (j * box_len) + box_len - bdr, start_point.y + (i * box_len) + bdr);
                AbsoluteLayout.LayoutParams obj3 = new AbsoluteLayout.LayoutParams(box_len - bdr - bdr, bdr + 0, start_point.x + (j * box_len) + bdr, start_point.y + (i * box_len));
                AbsoluteLayout.LayoutParams obj4 = new AbsoluteLayout.LayoutParams(box_len - bdr - bdr, bdr + 0, start_point.x + (j * box_len) + bdr, start_point.y + (i * box_len) + box_len - bdr);
                b1.setOnClickListener(T);
                b2.setOnClickListener(T);
                b3.setOnClickListener(T);
                b4.setOnClickListener(T);
                mylayout.addView(b1, obj1);
                mylayout.addView(b2, obj2);
                mylayout.addView(b3, obj3);
                mylayout.addView(b4, obj4);


            }
        }


        wallpaper = new Button(this);
        AbsoluteLayout.LayoutParams obj1 = new AbsoluteLayout.LayoutParams(80, 80, 900, 100);
        mylayout.addView(wallpaper,obj1);
        wallpaper.setBackground(getResources().getDrawable(R.drawable.wallpaper_icon));
        View.OnClickListener Cng=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background_rotation++;
                int x=background_rotation%background_number;
                if(x==0)x=background_number;
                String wall="a"+Integer.toString(x);
                int id=getResources().getIdentifier(wall,"drawable", getPackageName());
                Drawable drawable=getResources().getDrawable(id);
                AbsoluteLayout mylayout = (AbsoluteLayout) findViewById(R.id.pnl);
                mylayout.setBackground(drawable);

            }
        };
        wallpaper.setOnClickListener(Cng);


        sound_button =new Button(this);
        AbsoluteLayout.LayoutParams obj2 = new AbsoluteLayout.LayoutParams(80, 80, 800, 100);
        mylayout.addView(sound_button,obj2);
        sound_button.setBackground(getResources().getDrawable(R.drawable.sound_off));
        View.OnClickListener snd=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(is_sound==true){
                    sound_button.setBackground(getResources().getDrawable(R.drawable.sound_off));
                    is_sound=false;
                }
                else{
                    sound_button.setBackground(getResources().getDrawable(R.drawable.sound_on));
                    is_sound=true;
                }
            }
        };
        sound_button.setOnClickListener(snd);


        chat=new EditText(this);
        //chat.setBackgroundColor(0);
        chat.setHint("send text");
        chat.setTextSize(15);
        chat.setEms(10);
        chat.setId(0);
        chat.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        AbsoluteLayout.LayoutParams obj3 = new AbsoluteLayout.LayoutParams(800-20, 100, 100, 260);
        mylayout.addView(chat,obj3);


        Button send_button=new Button(this);
        AbsoluteLayout.LayoutParams obj4 = new AbsoluteLayout.LayoutParams(80, 80, 900, 260);
        mylayout.addView(send_button,obj4);
        send_button.setBackground(getResources().getDrawable(R.drawable.send_button));
        View.OnClickListener send=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText et=findViewById(0);
                String s=et.getText().toString();
                if(client==0){
                    msg_ref.child("client_1").setValue(s);
                }
                else{
                    msg_ref.child("client_0").setValue(s);
                }
                et.getText().clear();
            }
        };
        send_button.setOnClickListener(send);

        String read;
        if(client==0)read="client_0"; else read="client_1";
        msg_ref.child(read).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()=="no_data")return;
                String inbox=dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(),inbox,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("user_status").child(oppo_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.getValue().equals("offline")){
                        Toast.makeText(getApplicationContext(),oppo_id+" left",Toast.LENGTH_LONG).show();
                        FirebaseDatabase.getInstance().getReference().child("user_status").child(my_id).setValue("offline");
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        FirebaseDatabase.getInstance().getReference().child("user_status").child(my_id).setValue("offline");
        super.onBackPressed();
    }


}


