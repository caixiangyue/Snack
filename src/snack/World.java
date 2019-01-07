package snack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import snack.Snack.Node;

/**
 *
 * @author cxy
 */
public class World extends JPanel implements KeyListener{
    
    public World(){
        this.setFocusable(true);
        this.addKeyListener(this);
        Random random = new Random();
        int seed = random.nextInt(FOOD_WIDTH/10);
        foodX = 10 * seed;
        seed = random.nextInt(FOOD_HEIGHT/10);
        foodY = 10 * seed ;
        new Thread(new WordThread()).start();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(X, Y, WIDTH, HEIGHT);
        g.setColor(Color.red);
        g.drawRect(X, Y, WIDTH, HEIGHT);
        g.setColor(Color.green);
        g.fillRect(foodX, foodY, 10, 10);
        g.setColor(Color.white);
        g.fillRect(Snack.list.getFirst().getX(), Snack.list.getFirst().getY(), 10, 10);
        for(Node node : Snack.list){
            g.drawRect(node.getX(), node.getY(), 10, 10);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                if(snack.getSize() > 1 && snack.currentDirector == snack.down)
                    break;
                snack.currentDirector = snack.up;
                break;
            case KeyEvent.VK_DOWN:
                if(snack.getSize() > 1 && snack.currentDirector == snack.up)
                    break;
                snack.currentDirector = snack.down;
                break;
            case KeyEvent.VK_LEFT:
                if(snack.getSize() > 1 && snack.currentDirector == snack.right)
                    break;
                snack.currentDirector = snack.left;
                break;
            case KeyEvent.VK_RIGHT:
                if(snack.getSize() > 1 && snack.currentDirector == snack.left)
                    break;
                snack.currentDirector = snack.right;
                break;
            default:
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    
    
    class WordThread implements Runnable{
        int speed;

        @Override
        public void run() {
            while(true){
                speed = 200-Snack.speed;
                if(speed < 50){
                    speed = 50;
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                if(snack.move()){
                    JOptionPane.showMessageDialog(World.this, "game over!");
                    System.exit(0);
                }
                repaint();
            }
           
        }
        
    }
    private static final int X = 0;
    private static final int Y = 0;
    public static final int WIDTH = 780;
    public static final int HEIGHT = 550;
    static final int FOOD_WIDTH = WIDTH-10;
    static final int FOOD_HEIGHT = HEIGHT-10;
    static int foodX;
    static int foodY;
    private static final Snack snack = new Snack();
    
}
