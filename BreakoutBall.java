
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.JButton;

public class BreakoutBall extends JFrame{
    private Ellipse2D ball;
    final int WIDTH= 900, HEIGHT= 650;
    double xOfBall=480,yOfBall=250;
    Rectangle base = new Rectangle(WIDTH/2-40,HEIGHT-40,80,16);
    Rectangle left= new Rectangle(0,0,5,HEIGHT-20);
    Rectangle right= new Rectangle(WIDTH-5,0,5,HEIGHT-20);
    Rectangle top= new Rectangle(0,20,WIDTH,20);
    Rectangle bottom= new Rectangle(0,HEIGHT-20,WIDTH,20);
    Rectangle[] wall=new Rectangle[100];
    boolean[] wallVisable = new boolean[100];
    double ballspeed = 15;
    double veriticalSpeed=0;
    double horizontalSpeed=0;
    final int UP=0,RIGHT=0,DOWN = 1,LEFT=1;
    int ballDirection1 = (int)(Math.round(Math.random()*1.4));
    int ballDirection2 = (int)(Math.round(Math.random()*1.4));
    double sinOfAngle = (Math.random()*0.3)+0.6;
    int linesOfRectangle = 0;
    int wallhited=0;
    boolean gameOver=false;
    boolean gamePause=false;
    
    int numbersOfRectangleEachLine= 0;
    
    
    public BreakoutBall() { // constructor builds GUI
        
        this.setResizable(false);
        //... Build the content pane.
        ball = new Ellipse2D.Double ();
        
        ball.setFrame (xOfBall,yOfBall, 20, 20);
        this.setSize(WIDTH ,HEIGHT);
        this.setTitle("Breakout Ball");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new Move1().start();
        
        
        
    }//end constructor
    
    private class Move1 extends Thread implements KeyListener {
        public void run(){
            
            
            addKeyListener(this);
            veriticalSpeed=sinOfAngle*ballspeed;
            horizontalSpeed=Math.sqrt(ballspeed*ballspeed+veriticalSpeed*veriticalSpeed);
            for(int i=0;i<100;i++){
                wallVisable[i]=true;
            }
            JOptionPane.showMessageDialog(null, "Welcome to Breakout Ball!\n\nYou can hit Space button to pause the game.\nHit it again to continue.\n\nUse the arrow keys to control the base.", "Welcome!", JOptionPane.WARNING_MESSAGE);
            
            while(true){
                try{
                    repaint();
                    if(!gameOver){
                        switch(ballDirection1){
                            case (LEFT):xOfBall-=horizontalSpeed;
                                
                                switch(ballDirection2){
                                    case(UP):yOfBall-=veriticalSpeed;
                                        break;
                                    case(DOWN):yOfBall+=veriticalSpeed;
                                        break;
                                }
                                break;
                            case (RIGHT):xOfBall+=horizontalSpeed;
                                
                                switch(ballDirection2){
                                    case(UP):yOfBall-=veriticalSpeed;                                    break;
                                    case(DOWN):yOfBall+=veriticalSpeed;                                    break;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    if(!gameOver){
                        ball.setFrame(xOfBall,yOfBall,20,20);
                    }
                    if(ball.intersects(base)){
                        ballDirection2=UP;
                    }
                    
                    if(ball.intersects(top)){
                        ballDirection2=DOWN;
                    }
                    Object[] options = { "Yes", "No" };
                    int confirm=0;
                    String messageforlost = "You lose the game. \nYou hitted "+wallhited+" bricks."+"\nDo you want to play again.";
                    String messageforwon = "You won. \nYou hitted "+wallhited+" bricks."+"\nDo you want to play again.";
                    if(wallhited==100){
                        confirm = JOptionPane.showOptionDialog(null, messageforwon, "You won!",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options, options[0]);
                        if(confirm==JOptionPane.YES_OPTION){
                            gameOver=false;
                            xOfBall=480;
                            yOfBall=250;
                            ballDirection1 = (int)(Math.round(Math.random()*1.4));
                            ballDirection2 = (int)(Math.round(Math.random()*1.4));
                        }else{
                            System.exit(1);
                        }
                    }
                    if(ball.intersects(bottom)){
                        gameOver=true;
                        ball.setFrame(0,0,0,0);
                        
                        confirm = JOptionPane.showOptionDialog(null, messageforlost, "You lost!",
                                                               JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                                                               null, options, options[0]);
                        if(confirm==JOptionPane.YES_OPTION){
                            gameOver=false;
                            xOfBall=480;
                            yOfBall=250;
                            ballDirection1 = (int)(Math.round(Math.random()*1.4));
                            ballDirection2 = (int)(Math.round(Math.random()*1.4));
                        }else{
                            System.exit(1);
                        }
                        for(int i=0;i<100;i++){
                            
                            
                            
                            wallVisable[i]=true;
                            
                            
                            
                        }
                        
                    }
                    if(ball.intersects(left)){
                        ballDirection1=RIGHT;
                    }
                    if(ball.intersects(right)){
                        ballDirection1=LEFT;
                    }
                    
                    Thread.sleep(75);
                }catch(Exception e){
                }
                
            }
        }
        public void keyPressed(KeyEvent e) {
            int code=e.getKeyCode();
            if(!gameOver){
                if(code==KeyEvent.VK_RIGHT){
                    if(base.intersects(right)==false){
                        base.x+=40;
                    }else{
                        base.x-=40;
                    }
                }
                if(code==KeyEvent.VK_LEFT){
                    if(base.intersects(left)==false){
                        base.x-=40;
                    }else{
                        base.x+=40;
                    }
                }
            }
            if(code==KeyEvent.VK_SPACE){
                if(gameOver){
                    gameOver=false;
                }else{
                    gameOver=true;
                }
            }
            
        }
        
        public void keyReleased(KeyEvent e) {
        }
        
        public void keyTyped(KeyEvent e) {
            int code = e.getKeyCode();
            
            
        }
    }
    
    
    
    public static void main(String[] args) {
        new BreakoutBall().setVisible(true);
        
        
        
        
        
    }
    
    public void paint(Graphics g){
        Graphics2D g2D;
        g.setColor(Color.GREEN);
        super.paint(g);
        g.fill3DRect(base.x,base.y,base.width,base.height,true);
        g2D = (Graphics2D) g;
        g.setColor(Color.BLUE);
        
        g2D.fill(ball);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(left.x,left.y,left.width,left.height);
        g.fillRect(right.x,right.y,right.width,right.height);
        g.fillRect(top.x,top.y,top.width,top.height);
        g.fillRect(bottom.x,bottom.y,bottom.width,bottom.height);
        g.setColor(Color.GREEN);
        
        
        
        while(numbersOfRectangleEachLine < 20)
        {
            if(wallVisable[numbersOfRectangleEachLine]){
                Rectangle r = new Rectangle(10+numbersOfRectangleEachLine*44,43,40,16);
                wall[numbersOfRectangleEachLine] = r;
                g.fill3DRect(wall[numbersOfRectangleEachLine].x,wall[numbersOfRectangleEachLine].y,wall[numbersOfRectangleEachLine].width,wall[numbersOfRectangleEachLine].height,true);
            }
            numbersOfRectangleEachLine+=1;
            
        }
        numbersOfRectangleEachLine=0;
        linesOfRectangle=1;
        while(numbersOfRectangleEachLine < 20)
        {
            if(wallVisable[numbersOfRectangleEachLine+linesOfRectangle*20]){
                
                Rectangle r = new Rectangle(10+numbersOfRectangleEachLine*44,63,40,16);
                wall[numbersOfRectangleEachLine+linesOfRectangle*20] = r;
                g.fill3DRect(wall[numbersOfRectangleEachLine+linesOfRectangle*20].x,wall[numbersOfRectangleEachLine+linesOfRectangle*20].y,wall[numbersOfRectangleEachLine+linesOfRectangle*20].width,wall[numbersOfRectangleEachLine+linesOfRectangle*20].height,true);
                
            }
            numbersOfRectangleEachLine+=1;
        }
        numbersOfRectangleEachLine=0;
        linesOfRectangle=2;
        g.setColor(Color.BLUE);
        while(numbersOfRectangleEachLine < 20)
        {
            if(wallVisable[numbersOfRectangleEachLine+linesOfRectangle*20]){
                
                Rectangle r = new Rectangle(10+numbersOfRectangleEachLine*44,83,40,16);
                wall[numbersOfRectangleEachLine+linesOfRectangle*20] = r;
                g.fill3DRect(wall[numbersOfRectangleEachLine+linesOfRectangle*20].x,wall[numbersOfRectangleEachLine+linesOfRectangle*20].y,wall[numbersOfRectangleEachLine+linesOfRectangle*20].width,wall[numbersOfRectangleEachLine+linesOfRectangle*20].height,true);
                
            }
            numbersOfRectangleEachLine+=1;
        }
        numbersOfRectangleEachLine=0;
        linesOfRectangle=3;
        g.setColor(Color.GREEN);
        while(numbersOfRectangleEachLine < 20)
        {
            if(wallVisable[numbersOfRectangleEachLine+linesOfRectangle*20]){
                
                Rectangle r = new Rectangle(10+numbersOfRectangleEachLine*44,103,40,16);
                wall[numbersOfRectangleEachLine+linesOfRectangle*20] = r;
                g.fill3DRect(wall[numbersOfRectangleEachLine+linesOfRectangle*20].x,wall[numbersOfRectangleEachLine+linesOfRectangle*20].y,wall[numbersOfRectangleEachLine+linesOfRectangle*20].width,wall[numbersOfRectangleEachLine+linesOfRectangle*20].height,true);
                
            }
            numbersOfRectangleEachLine+=1;
        }
        numbersOfRectangleEachLine=0;
        linesOfRectangle=4;
        while(numbersOfRectangleEachLine < 20)
        {
            if(wallVisable[numbersOfRectangleEachLine+linesOfRectangle*20]){
                
                Rectangle r = new Rectangle(10+numbersOfRectangleEachLine*44,123,40,16);
                wall[numbersOfRectangleEachLine+linesOfRectangle*20] = r;
                g.fill3DRect(wall[numbersOfRectangleEachLine+linesOfRectangle*20].x,wall[numbersOfRectangleEachLine+linesOfRectangle*20].y,wall[numbersOfRectangleEachLine+linesOfRectangle*20].width,wall[numbersOfRectangleEachLine+linesOfRectangle*20].height,true);
            }
            numbersOfRectangleEachLine+=1;
        }
        numbersOfRectangleEachLine=0;
        for(int i=0;i<100;i++){
            if(ball.intersects(wall[i])){
                g.clearRect(wall[i].x,wall[i].y,wall[i].width,wall[i].height);
                ballDirection2=DOWN;
                wallVisable[i]=false;
                wall[i].x=-100;
                wall[i].y=-100;
                wallhited+=1;
            }
        }
        
        
        
    }
    
    
}



