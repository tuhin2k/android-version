package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;


public class Game {

    static int num_player=0,grid_row=0,grid_col=0,turn,rotation,play_flag=0,sound_flag=1,same_rc=0,last_box_x,st,computer_flag=0,box;
    static String in;
    static int grid[][]=new int[9][13];
    static Players players[]=new Players[7];
    static JFrame frame=new JFrame();
    static JButton b1=new JButton();
    static JButton play_again=new JButton();
    static JButton new_game=new JButton();
    static JButton sound=new JButton();
    static JButton q_mark=new JButton();
    
    
    public static void main(String[] args) {
        
        
            File f_in=new File("images/dollar.png");
            Image frame_icon=null;
            try{
                frame_icon=ImageIO.read(f_in);
            }
            catch(Exception ex){
                System.err.println("frame icon not found");
            }
            frame.setIconImage(frame_icon);
       
        
        Color BT=new Color(184,207,229);
        
        Image image;
        image = null;
        File input = new File("src/gray.jpg");
        try {
            image = ImageIO.read(input);
        } catch (Exception ex) {
            System.out.println("file not found");
        }
        
        frame.setSize(1344,840);
        frame.setResizable(false);
        frame.setContentPane(new ImagePanel(image));
        
        
        
        b1.setBounds(620,50,100,36);
        ImageIcon icon =new ImageIcon("Images/playnow.png");
        b1.setIcon(icon);
        
        b1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                rotation=1;
                
                int x=0;
                
                if(play_flag==0)
                {
                    boolean b=true;
                    while(b)
                    {
                        in = JOptionPane.showInputDialog(frame, "enter how many players?(1 - 5)\nyou can also add cpu as a player");
                        if(in==null){
                            int jo=JOptionPane.showConfirmDialog(frame, "Are you sure to cancel?");
                            if(jo==0){
                                Game.reset();
                                return;
                            }
                            else{
                                continue;
                            }
                        }
                        if ("".equals(in)) {
                            continue;
                        }
                        try{
                            x = Integer.parseInt(in);
                        }
                        catch(Exception ex){
                            JOptionPane.showMessageDialog(frame, "enter number please");
                            continue;
                        }
                        if (x < 1 || x > 5) {
                            JOptionPane.showMessageDialog(frame, "number of players should be between 1 - 5");
                            continue;
                        }
                        b = false;
                    }
                    num_player = x;

                }
                
                
                if(same_rc==0)
                {
                    boolean b = true;
                    while (b) {
                        in = JOptionPane.showInputDialog(frame, "enter how many rows?(2 - 8)");
                        if (in == null) {
                            int jo = JOptionPane.showConfirmDialog(frame, "Are you sure to cancel?");
                            if (jo == 0) {
                                Game.reset();
                                return;
                            } else {
                                continue;
                            }
                        }
                        if ("".equals(in)) {
                            continue;
                        }
                        try {
                            x = Integer.parseInt(in);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "enter number please");
                            continue;
                        }
                        if (x < 2 || x > 8) {
                            JOptionPane.showMessageDialog(frame, "number of rows should be between 2 - 8");
                            continue;
                        }
                        grid_row = x;
                        b = false;
                    }

                    b = true;
                    while (b) {
                        in = JOptionPane.showInputDialog(frame, "enter how many columns?(2 - 12)");
                        if (in == null) {
                            int jo = JOptionPane.showConfirmDialog(frame, "Are you sure to cancel?");
                            if (jo == 0) {
                                Game.reset();
                                return;
                            } else {
                                continue;
                            }
                        }
                        if ("".equals(in)) {
                            continue;
                        }
                        try {
                            x = Integer.parseInt(in);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "enter number please");
                            continue;
                        }
                        if (x < 2 || x > 12) {
                            JOptionPane.showMessageDialog(frame, "number of columns should be between 2 - 12");
                            continue;
                        }
                        grid_col = x;
                        b = false;
                    }

                }
                
                box=get_box(grid_row,grid_col);
                st=get_alignment();
                last_box_x=50+st+Game.box*grid_col;
                
                
                for(int i=1;i<=num_player;i++){
                    if(play_flag!=0)in=players[i].name;
                    players[i]=new Players();
                    if(i==1){
                        JButton sb=new JButton();
                        sb.setBounds(last_box_x,105, 180, 60);
                        sb.setIcon(new ImageIcon("images/sb.jpg"));
                        //sb.setEnabled(false);
                        sb.setBorderPainted(false);
                        frame.getContentPane().add(sb);
                        write.text(last_box_x, 190-25, 135, 40, players[1].name+"'s turn");
                    }
                    if(play_flag==0){
                        if(i==num_player){
                            int jo=JOptionPane.showConfirmDialog(frame, "Do you want to add cpu as a player?");
                            if(jo==0){
                                computer_flag=1;
                                num_player++;
                                in="Computer";
                                players[num_player]=new Players();
                                break;
                            }
                        }
                    }
                }
                
                
                int a,b,c,d,f,g,h;
                c=1;
                for(int i=1;i<=grid_row;i++){
                    for(int j=1;j<=grid_col;j++){
                        grid[i][j]=4;
                    }
                }
                
                
                turn=grid_row*(grid_col+1)+grid_col*(grid_row+1);
                
                
                for(int i=0;i<grid_row;i++){
                    for(int j=0;j<grid_col;j++){
                        JLabel label=new JLabel();
                        label.setBounds((j+1)*Game.box +st-Game.box ,(i+1)*Game.box +35 -Game.box ,Game.box,Game.box);
                        ImageIcon icon2,icon3;
                        
                        JButton L=new JButton();
                        JButton R=new JButton();
                        JButton U=new JButton();
                        JButton D=new JButton();
                        
                        
                        
                        L.setBounds(0,5,5,Game.box-10);
                        R.setBounds(Game.box-5,5,5,Game.box-10);
                        U.setBounds(5,0,Game.box-10,5);
                        D.setBounds(5,Game.box-5,Game.box-10,5);
                        
                        L.setBackground(BT);
                        R.setBackground(BT);
                        U.setBackground(BT);
                        D.setBackground(BT);
                        
                        
                        L.setBorderPainted(false);
                        R.setBorderPainted(false);
                        U.setBorderPainted(false);
                        D.setBorderPainted(false);
                        
                        
                        
                        
                        L.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent x){
                                Game.t(L);
                            }
                        });
                        
                        R.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent x){
                                Game.t(R);
                            }
                        });
                        
                        U.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent x){
                                Game.t(U);
                            }
                        });
                        
                        D.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent x){
                                Game.t(D);
                            }
                        });
                        
                        
                        label.add(L);
                        label.add(R);
                        label.add(U);
                        label.add(D);
                        
                        
                        
                        
                        
                        
                        frame.getContentPane().add(label);
                        
                    }
                }
                frame.remove(b1);
                frame.remove(q_mark);
                
                
                frame.add(play_again);
                frame.add(new_game);
                
                
                frame.revalidate();
                frame.repaint();
                
                
                
                
                
            }
        });
        
        frame.add(b1);
        frame.add(sound);
        
        
        
        sound.setBounds(1250, 30, 36, 36);
        sound.setIcon(new ImageIcon("Images/sound_on.png"));
        sound.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
               if(sound_flag==1)sound.setIcon(new ImageIcon("Images/sound_off.png"));
               else sound.setIcon(new ImageIcon("Images/sound_on.png"));
               sound_flag^=1;
               if(sound_flag==1)PlaySound.play("src/click.wav");
            }
        });
        
        
        play_again.setBounds(1150, 600, 135, 40);
        new_game.setBounds(1150, 650, 135, 40);
        play_again.setIcon(new ImageIcon("Images/play_again.png"));
        new_game.setIcon(new ImageIcon("Images/new_game.png"));
        
        play_again.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int jo;
                jo=JOptionPane.showConfirmDialog(frame, "with same rows and columns ?");
                if(jo==0)same_rc=1;
                if(jo==1) same_rc=0;
                if(jo!=0 && jo!=1){
                    return;
                }
                Players.num=0;
                play_flag=1;
                frame.getContentPane().removeAll();
                b1.setVisible(false);
                frame.add(b1);
                frame.add(sound);
                b1.doClick();
            }
        });
        
        new_game.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int jo=JOptionPane.showConfirmDialog(frame, "Are you sure to exit?");
                if(jo!=0)return;
                Game.reset();
            }
        });
        
        input = new File("src/howtoplay.png");
        try {
            image = ImageIO.read(input);
        } catch (Exception ex) {
            System.out.println("file not found");
        }
        JFrame htp=new JFrame();
        htp.setSize(1274, 960);
        htp.setResizable(false);
        htp.setContentPane(new ImagePanel(image));
        q_mark.setIcon(new ImageIcon("Images/q_mark.jpg"));
        q_mark.setBounds(1210, 30, 36, 36);
        q_mark.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                htp.setVisible(true);
            }
        });
        frame.add(q_mark);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setVisible(true);
        
        
    }
    static void t(JButton L){
        
        PlaySound.play("src/click.wav");
        
        int plr_num=rotation%num_player;
        if(plr_num==0)plr_num=num_player;
        rotation++;
        
        Color col=Players.getCol(plr_num);
        
        int a,b,c,d,e,f,g,i,j,p;
        p=0;
        a=L.getLocation().x;
        b=L.getLocation().y;
        c=L.getParent().getLocation().x;
        d=L.getParent().getLocation().y;
        Component label_1=L.getParent();
        Component frame=label_1.getParent();
        Component label_2,Button_2;
        label_2=null;
        Button_2=null;
        j=(c-st+Game.box)/Game.box;
        i=(d-35+Game.box)/Game.box;
        if(a==0&&b==5){
            if(j!=1){
                label_2=frame.getComponentAt(c-Game.box, d);
                Button_2=label_2.getComponentAt(Game.box-5, 5);
                grid[i][j-1]--;
                if(grid[i][j-1]==0){
                    p++;
                    PlaySound.play("src/gotit.wav");
                    players[plr_num].gotPoint(label_2);
                }
            }
        }
        if(a==Game.box-5&&b==5){
            if(j!=grid_col){
                label_2=frame.getComponentAt(c+Game.box, d);
                Button_2=label_2.getComponentAt(0, 5);
                grid[i][j+1]--;
                if(grid[i][j+1]==0){
                    p++;
                    PlaySound.play("src/gotit.wav");
                    players[plr_num].gotPoint(label_2);
                }
            }
        }
        if(a==5&&b==0){
            if(i!=1){
                label_2=frame.getComponentAt(c, d-Game.box);
                Button_2=label_2.getComponentAt(5, Game.box-5);
                grid[i-1][j]--;
                if(grid[i-1][j]==0){
                    p++;
                    PlaySound.play("src/gotit.wav");
                    players[plr_num].gotPoint(label_2);
                }
            }
        }
        if(a==5&&b==Game.box-5){
            if(i!=grid_row){
                label_2=frame.getComponentAt(c, d+Game.box);
                Button_2=label_2.getComponentAt(5, 0);
                grid[i+1][j]--;
                if(grid[i+1][j]==0){
                    p++;
                    PlaySound.play("src/gotit.wav");
                    players[plr_num].gotPoint(label_2);
                }
            }
        }
        L.setEnabled(false);
        //
        L.setBackground(col);
        
        //
        turn--;
        grid[i][j]--;
        if(grid[i][j]==0){
            p++;
            PlaySound.play("src/gotit.wav");
            players[plr_num].gotPoint(label_1);
        }
        
        
        
        if(Button_2!=null){
            Button_2.setEnabled(false);
            //
            Button_2.setBackground(col);
            //
        }
        
        if(p!=0){
            rotation--;
        }
        
        
        
        int player_turn=rotation%num_player;;
        if(player_turn==0)player_turn=num_player;
        
        Players.getTurn(player_turn);
        
        frame.revalidate();
        frame.repaint();
        
        
        if(turn==0){
            Players.won();
            Players.num=0;
        }
        
        
        
        frame.revalidate();
        frame.repaint();
        
        if(turn!=0&&computer_flag==1&&player_turn==num_player){
            new Computer().start();
        }
        
        
        
    }
    static void reset(){
        Players.num=0;
        play_flag=0;
        same_rc=0;
        computer_flag=0;
        frame.getContentPane().removeAll();
        b1.setVisible(true);
        frame.add(b1);
        frame.add(sound);
        frame.add(q_mark);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
    static int get_alignment(){
        int w,f,p;
        w=1344;
        w/=2;
        f=Game.box*grid_col+230;
        f/=2;
        p=w-f;
        return p;
    }
    static int get_box(int a,int b){
        a=640/a;
        b=960/b;
        if(a<=b)return a; else return b;
    }
}




class ImagePanel extends JComponent {
    Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        
    }
}

class PlaySound{
    public static void play(String path){
        if(Game.sound_flag==0)return;
        new Thread(new Runnable(){
            @Override
            public void run(){
                Clip clip=null;
                try{
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                   }
                catch(Exception e){
            
                }
                clip.start();
            }
        }).start();
    }
}

class Players{
    String name;
    int point;
    int pos,x,y;
    static int num=0;
    Players(){
        String name = null;
        this.point=0;
        num++;
        pos=num;
        x=Game.last_box_x;
        y=190+50*pos;
        if(Game.play_flag==0&&Game.computer_flag==0){
            boolean b=true;
            while(b){
                name=JOptionPane.showInputDialog(Game.frame,"enter name of player " + num + " :");
                if (name == null) {
                    continue;
                }
                if ("".equals(name)) {
                    continue;
                }
                this.name=name;
                b=false;
            }
        }
        else{
            name=Game.in;
        }
        this.name=name;
        
        write.text(x, y, 100, 40, this.name);
        write.text(x+100, y, 35, 40, ":0" );
        
        //
        JButton plr_col=new JButton();
        plr_col.setBounds(x+140, y, 40, 40);
        plr_col.setEnabled(false);
        plr_col.setBackground(getCol(pos));
        Game.frame.getContentPane().add(plr_col);
        //
        
        Game.frame.revalidate();
        Game.frame.repaint();
    }
    
    
    void gotPoint(Component c){
        this.point++;
        write.text(x+100, y, 35, 40, ":"+Integer.toString(this.point));
        int xx,yy;
        xx=c.getLocation().x;
        yy=c.getLocation().y;
        JButton jl=new JButton();
        jl.setEnabled(false);
        int aaa=Game.box/2 -30;
        jl.setBounds(xx+aaa, yy+aaa, 60, 60);
        jl.setBorderPainted(false);
        jl.setBackground(Players.getCol(this.pos));
        JButton dollar=new JButton();
        //dollar.setEnabled(false);
        int bbb=Game.box/2-15;
        dollar.setBounds(xx+bbb,yy+bbb,30,30);
        dollar.setBorderPainted(false);
        dollar.setIcon(new ImageIcon("Images/dollar.png"));
        
        Game.frame.getContentPane().add(dollar);
        Game.frame.getContentPane().add(jl);
        
    }
    static void getTurn(int r){
        write.text(Game.last_box_x, 190-25, 135, 40, Game.players[r].name+"'s turn");
    }
    
    static void won(){
        int w=0,c=0,m=-1;
        String winner;
        for(int i=1;i<=Game.num_player;i++){
            if(Game.players[i].point>m){
                w=i;
                m=Game.players[i].point;
                c=0;
            }
            if(Game.players[i].point==m){
                c++;
            }
        }
        if(c==1){
            winner=Game.players[w].name + " won";
        }
        else{
            winner="no winner";
        }
        write.text(Game.last_box_x, 190-25, 135, 40, winner);
    }
    static Color getCol(int r){
        if(r==0)return Color.WHITE;
        if(r==1)return Color.BLACK;
        if(r==2)return Color.BLUE;
        if(r==3)return Color.GREEN;
        if(r==4)return Color.MAGENTA;
        if(r==5)return Color.YELLOW;
        if(r==6)return Color.RED;
        return Color.GRAY;
    }
}
class write{
    static void text(int loc_x,int loc_y,int w,int h,String s){
        JTextField new_text=new JTextField(s);
        new_text.setFocusable(false);
        new_text.setBounds(loc_x, loc_y, w, h);
        Component old_text=Game.frame.getContentPane().getComponentAt(loc_x, loc_y);
        if(!old_text.equals(Game.frame)){
            Game.frame.getContentPane().remove(old_text);
        }
        new_text.setBackground(Color.CYAN);
        new_text.setFont(new Font(Font.SERIF,Font.BOLD,18));
        Game.frame.getContentPane().add(new_text);
    }
}

class Computer extends Thread{
    static Packet pk_arr[]=null;
    synchronized static void turn(){
        int x=Game.grid_row,y=Game.grid_col,a=-1,b=-1,w,h,c=-1;
        boolean bool=false;
        for(int i=1;i<=x;i++){
            for(int j=1;j<=y;j++){
                if(Game.grid[i][j]==1){
                    a=i;
                    b=j;
                    bool=true;
                    break;
                }
            }
            if(bool==true)break;
        }
        if(a==-1&&b==-1){
            for(int i=1;i<=x;i++){
                for(int j=1;j<=y;j++){
                    if(Game.grid[i][j]!=2&&Game.grid[i][j]!=0){
                        int d=j*Game.box+Game.st-Game.box,e=i*Game.box+35-Game.box;
                        JLabel label=(JLabel)Game.frame.getContentPane().getComponentAt(d,e);
                        Component button_arr[]=label.getComponents(); 
                        if(button_arr[0].isEnabled()==true){
                            if(j==1){
                                a=i;b=j;c=0;
                                bool=true;
                                break;
                            }
                            else{
                                if(Game.grid[i][j-1]!=2){
                                    a=i;b=j;c=0;
                                    bool=true;
                                    break;
                                }
                            }
                        }
                        if(button_arr[1].isEnabled()==true){
                            if(j==Game.grid_col){
                                a=i;b=j;c=1;
                                bool=true;
                                break;
                            }
                            else{
                                if(Game.grid[i][j+1]!=2){
                                    a=i;b=j;c=1;
                                    bool=true;
                                    break;
                                }
                            }
                        }
                        if(button_arr[2].isEnabled()==true){
                            if(i==1){
                                a=i;b=j;c=2;
                                bool=true;
                                break;
                            }
                            else{
                                if(Game.grid[i-1][j]!=2){
                                    a=i;b=j;c=2;
                                    bool=true;
                                    break;
                                }
                            }
                        }
                        if(button_arr[3].isEnabled()==true){
                            if(i==Game.grid_row){
                                a=i;b=j;c=3;
                                bool=true;
                                break;
                            }
                            else{
                                if(Game.grid[i+1][j]!=2){
                                    a=i;b=j;c=3;
                                    bool=true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if(bool==true){
                    break;
                }
            }
        }
        if(a==-1&&b==-1){
            //
            pk_arr=getTurnChain();
            int temp=-1,xx=500;
            for(int i=0;i<pk_arr.length;i++){
                System.err.println(""+pk_arr[i].c+" "+pk_arr[i].x+" "+pk_arr[i].y);
                if(pk_arr[i].c<=xx){
                    xx=pk_arr[i].c;
                    temp=i;
                    
                }
            }
            a=pk_arr[temp].x;
            b=pk_arr[temp].y;
            //
        }
            
        
        w=b*Game.box+Game.st-Game.box;
        h=a*Game.box+35-Game.box;
        
        JLabel jl=(JLabel)Game.frame.getContentPane().getComponentAt(w,h);
        
        Component arr[]=jl.getComponents(); 
        
        for(int i=0;i<arr.length;i++){
            if(c!=-1){
                break;
            }
            if(arr[i].isEnabled()==true){
                c=i; break;
            }
        }
        
        JButton jb=(JButton)arr[c];
        
        
        
        jb.doClick();
    }
    
    
    @Override
    public void run(){
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Computer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        turn();
    }
    
    static int vertualTurn(Util util){
        int a,b,p=0,c=-1;
        boolean f=false;
        for(int i=1;i<=Game.grid_row;i++){
            for(int j=1;j<=Game.grid_col;j++){
                if(util.dup_grid[i][j]==1){
                    p++;
                    util.dup_grid[i][j]--;
                    for(int k=0;k<4;k++){
                        if(util.game_map[i][j][k]==true){
                            util.game_map[i][j][k]=false;
                            c=k; break;
                        }
                    }
                    if(c==0){
                        if(j!=1){
                            util.game_map[i][j-1][1]=false;
                            util.dup_grid[i][j-1]--;
                            if(util.dup_grid[i][j-1]==0){
                                p++;
                            }
                        }
                    }
                    if(c==1){
                        if(j!=Game.grid_col){
                            util.game_map[i][j+1][0]=false;
                            util.dup_grid[i][j+1]--;
                            if(util.dup_grid[i][j+1]==0){
                                p++;
                            }
                        }
                    }
                    if(c==2){
                        if(i!=1){
                            util.game_map[i-1][j][3]=false;
                            util.dup_grid[i-1][j]--;
                            if(util.dup_grid[i-1][j]==0){
                                p++;
                            }
                        }
                    }
                    if(c==3){
                        if(i!=Game.grid_row){
                            util.game_map[i+1][j][2]=false;
                            util.dup_grid[i+1][j]--;
                            if(util.dup_grid[i+1][j]==0){
                                p++;
                            }
                        }
                    }
                    f=true;
                    break;
                }
            }
            if(f)break;
        }
        
        return p;
    }
     
    static Packet getOneChain(Util util){
        int a=0,b=0,c=0,x,y,point=0;
        boolean f=false;
        Packet pk=new Packet();
        for(int i=1;i<=Game.grid_row;i++){
            for(int j=1;j<=Game.grid_col;j++){
                if(util.dup_grid[i][j]==2){
                    a=i; b=j; f=true; break;
                }
            }
            if(f)break;
        }
        
        if(a==0&&b==0){
            pk.x=0;
            pk.y=0;
            pk.c=0;
            return pk;
        }
        
        int m=-1;
        
        for(int i=0;i<4;i++){
            if(util.game_map[a][b][i]==true){
                m=i;
            }
        }
        
        util.dup_grid[a][b]--;
        util.game_map[a][b][m]=false;
        ////
        if(m==0){
            if(b!=1){
                util.game_map[a][b-1][1]=false;
                util.dup_grid[a][b-1]--;
            }
        }
        if(m==1){
            if(b!=Game.grid_col){
                util.game_map[a][b+1][0]=false;
                util.dup_grid[a][b+1]--;
            }
        }
        if(m==2){
            if(a!=1){
                util.game_map[a-1][b][3]=false;
                util.dup_grid[a-1][b]--;
            }
        }
        if(m==3){
            if(a!=Game.grid_row){
                util.game_map[a+1][b][2]=false;
                util.dup_grid[a+1][b]--;
            }
        }
        ////
        
        
        while(true){
            point=vertualTurn(util);
            if(point==0)break;
            c+=point;
        }
        
        
        pk.x=a;
        pk.y=b;
        pk.c=c;
        
        return pk;
    }
    static Packet[] getTurnChain(){
        Util util=new Util();
        Packet arr[]=new Packet[150];
        Packet pk;
        int k=0;
        while(true){
            pk=getOneChain(util);
            if(pk.x==0&&pk.y==0)break;
            arr[k++]=pk;
        }
        Packet[] result=new Packet[k];
        for(int i=0;i<k;i++)result[i]=arr[i];
        
        return result;
    }
}
class Util{
    boolean game_map[][][]=new boolean[Game.grid_row+1][Game.grid_col+1][4];
    int dup_grid[][]=new int[Game.grid_row+1][Game.grid_col+1];
    Util(){
        gameScan();
    }
    private void gameScan(){
        for(int i=1;i<=Game.grid_row;i++){
            for(int j=1;j<=Game.grid_col;j++){
                dup_grid[i][j]=Game.grid[i][j];
            }
        }
        
        for(int i=1;i<=Game.grid_row;i++){
            for(int j=1;j<=Game.grid_col;j++){
                int x,y;
                x=j*Game.box+Game.st-Game.box;
                y=i*Game.box+35-Game.box;
                JLabel l=(JLabel)Game.frame.getContentPane().getComponentAt(x, y);
                Component arr[]=l.getComponents();
                for(int k=0;k<arr.length;k++){
                    if(((JButton)arr[k]).isEnabled()){
                        game_map[i][j][k]=true;
                    }
                    else {
                        game_map[i][j][k]=false;
                    }
                }
            }
        }
    }
    
}

class Packet{
    int x,y,c;
}