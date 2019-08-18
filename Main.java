 import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.BOLD;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

 class MultipleClasses{
     
   


    public static void main (String []args){
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10,10,800,600);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
        
        
        
    
    }

    void setVisible(boolean b) {
        new MultipleClasses();
    }
    
}
class Gameplay extends JPanel implements KeyListener, ActionListener {
    
    private boolean play = false;
    private int score = 0;
    private int total_bricks = 28;
    
    private Timer timer;
    private int delay = 5;
    private int playerx = 330;
    private int ballposx = 120;
    private int ballposy = 350;
     private int balldirx = -1;
    private int balldiry = -2;
    private Map map;
    public Gameplay (){
    map = new Map(4,7);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false); 
    timer = new Timer(delay, this);
    timer.start();
    }
    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 792, 592);
        // draw map
        map.draw((Graphics2D) g); 
       
        //borders
        g.setColor(Color.black);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,792,3);
        g.fillRect(791,0,3,592);
        
        //scores
        g.setColor(Color.white);
        g.setFont(new Font ("serif", Font.BOLD,25));
        g.drawString(" "+score, 692, 30);
         
        //slider
        g.setColor(Color.green);
        g.fillRect(playerx,550,120,8);
        //ball
        g.setColor(Color.red);
        g.fillOval(ballposx,ballposy,20,20);
        if (total_bricks <= 0){
            play = false ; 
            balldirx = 0;
            balldiry = 0;
            g.setColor(Color.white);
            g.setFont(new Font ("serif", Font.BOLD,25));
            g.drawString("YOU WON "
                    + " NEW HIGHSCORE:"+score, 190, 300);
            
            g.setFont(new Font ("serif", Font.BOLD,25));
            g.drawString("Press Enter to Restart", 290, 350);
        
        }
        
        if(ballposy > 670){
            play = false ; 
            balldirx = 0;
            balldiry = 0;
            g.setColor(Color.white);
            g.setFont(new Font ("serif", Font.BOLD,25));
            g.drawString("Game over  score:"+score, 190, 300);
            
            g.setFont(new Font ("serif", Font.BOLD,25));
            g.drawString("Press Enter to Restart", 290, 350);
              
        
        }
        
        g.dispose();
        
        
        
    }
    
     @Override
    public void actionPerformed(ActionEvent e) { 
       timer.start();
       
       if(play){
           if(new Rectangle (ballposx,ballposy,20,20 ).intersects(new Rectangle (playerx,550,120,8 ))){
            balldiry = -balldiry;
           }
           a: for (int i = 0; i<map.map.length; i ++){
           for(int j = 0; j<map.map[0].length; j++){
           if (map.map[i][j]> 0){
               int brickx = j*map.brickwidth + 90;
               int bricky = i*map.bricklength + 50;
               int brickwidth = map.brickwidth;
               int bricklength =map.bricklength;
               
               Rectangle rect = new Rectangle(brickx, bricky , brickwidth, bricklength);
               Rectangle ballrec = new Rectangle(ballposx , ballposy , 30,30);
               Rectangle brickrect = rect ;
               if (ballrec.intersects(brickrect)){
               map.setBrickvalue(0, i, j);
               total_bricks --;
               score += 10;
               if (ballposx+ 19<= brickrect.x||ballposx +1 >= brickrect.x +brickrect.width ){
               balldirx = - balldirx;
               
               }else {
               balldiry = -balldiry;
               }
               break a;
               }
               
           }}
           }
       ballposx += balldirx;
       ballposy += balldiry;
       if (ballposx<0){
       balldirx = -balldirx;
       
       }
       if (ballposy<0){
       balldiry = -balldiry;
       
       }
       if (ballposx>770){
       balldirx = -balldirx;
       
       }
       }
       repaint();
        
    }
    


    @Override
    public void keyTyped(KeyEvent e) {
       
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_RIGHT ){
        if (playerx >= 680){
        playerx = 680;}
        else {
            moveRight();
        }}
        if(e.getKeyCode()== KeyEvent.VK_LEFT ){
        if (playerx <= 10){
        playerx = 10;}
        else {
            moveLeft();
        }}
        if(e.getKeyCode()== KeyEvent.VK_ENTER){
        if (!play){
        play = true;
        ballposx = 120;
        ballposy = 350;
        balldirx = -1;
        balldiry = -2;
        playerx = 310;
        score = 0;
        total_bricks = 28;
        map = new Map(4,7);
        repaint();
                
                
        }
        }
    }
       public void moveRight(){
       play = true;
       playerx += 30;
       
       }
       public void moveLeft(){
       play = true;
       playerx -= 30;
       
       }
    }
 
 class Map {
    public int map[][];
    public int brickwidth;
    public int bricklength;
    public Map(int row , int col){
        map = new int [row][col];
        for (int i = 0; i<map.length; i++){
            for(int j = 0; j<map[0].length; j++){
            map[i][j] = 1;
            }
        
        }
    brickwidth = 540/col;
    bricklength = 150/row;
    
}
    public void draw (Graphics2D g){
        for (int i = 0; i<map.length; i++){
            for(int j = 0; j<map[0].length; j++){
            if (map[i][j] > 0){
                g.setColor(Color.magenta);
                g.fillRect(j * brickwidth + 80, i*bricklength, brickwidth, bricklength);
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.black);
                g.drawRect(j * brickwidth + 80, i*bricklength, brickwidth, bricklength);
            
            }
            }
        
        }
        
    


    }
public void setBrickvalue(int value,int row , int col){
   map[row][col] = value;
}
    
    }
 
 
 



















































































































































































































































































































































































































































