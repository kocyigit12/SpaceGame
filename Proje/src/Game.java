import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
class Ammo {
    private int x;
    private int y;

    public Ammo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Game extends JPanel implements KeyListener, ActionListener {
    File file = new File("scores.txt");

    Timer timer = new Timer(4,this);
    private int PassedTime=0;
    private int ConsumedAmmo=0;
    private BufferedImage image;

    private ArrayList<Ammo> ammos = new ArrayList<Ammo>();

    private int AmmoDirY=1;
    private int ballX=0;
    private int ballDirX=2;
    private int SpaceShipX=0;
    private int dirSpaceX=20;


    public boolean Control(){ // it checks if you hit the ball or not
        for (Ammo ammo : ammos){

            if (new Rectangle(ammo.getX(),ammo.getY(),10,20).intersects(new Rectangle(ballX,0,20,20))){
                return true;
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        PassedTime += 5;
        g.setColor(Color.red);
        g.fillOval(ballX,0,20,20);
        g.drawImage(image,SpaceShipX,490,image.getWidth()/10,image.getHeight()/10,this);


            g.setColor(Color.BLUE);
        for (Ammo ammo : ammos){
            g.fillRect(ammo.getX(),ammo.getY(),10,20);
        }
        for (Ammo ammo : ammos) {
            if (ammo.getY() < 0) {

                ammos.remove(ammo);
            }
        }
        if (Control()){
            timer.stop();
            String message = "You have won !!!\n"+
                             "Consumed Ammo : " + ConsumedAmmo +
                             "\nPassed Time : " + PassedTime/1000.0 + " seconds ";

            JOptionPane.showMessageDialog(this,message);
            System.exit(0);
        }


    }


    @Override
    public void repaint() {// it will cal paint every time
        super.repaint();

    }

    public Game(){

        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(Color.BLACK);

        timer.start();

    }

    @Override
    public void keyTyped(KeyEvent e) {



    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if (c==KeyEvent.VK_LEFT){

            if (SpaceShipX<=0){
                SpaceShipX=0;
            }
            else {
                SpaceShipX-=dirSpaceX;
            }

        }else if (c == KeyEvent.VK_RIGHT){

            if (SpaceShipX>=720){
                SpaceShipX=720;
            }else{
                SpaceShipX +=dirSpaceX;
            }


        }else if (c==KeyEvent.VK_CONTROL){
            ammos.add(new Ammo(SpaceShipX+15,470));

            ConsumedAmmo++;
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));


                writer.append("Consumed_Ammo: "+ ConsumedAmmo);
                writer.newLine();
                writer.append("estimated time: "+PassedTime/1000.0 + " seconds ");
                writer.newLine();


                writer.flush();
                writer.close();


            } catch (IOException e1) {

            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                long leng=0;
                String line;
                while((line =reader.readLine())!=null){

                    if(line.isEmpty()){
                        break;
                    }
                    System.out.println(line);


                    leng+=line.length();}



            } catch (FileNotFoundException e1) {
                System.out.println("No previous information ");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ammo ammo : ammos){ // it moves bullets
            ammo.setY(ammo.getY()-AmmoDirY);

        }

        ballX+=ballDirX;  // and these are moves the ball

        if (ballX>=750){
            ballDirX=-ballDirX;
        }
        if (ballX<=0){
            ballDirX= - ballDirX;
        }repaint();
         // it will call the paint while actionPerformed is active

    }



}
