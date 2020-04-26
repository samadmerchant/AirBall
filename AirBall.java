import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class AirBall extends JComponent implements ActionListener, MouseMotionListener, KeyListener {
    private final int PADDLE_MIN_Y = 523;
    private final int PADDLE_MAX_Y = 543;
    // instance variable declarations
    // ball position
    private int xBall = 150;
    private int yBall = 30;
    // ball movement speed
    private int yBallSpeed = 7;
    private int xBallSpeed = 5;
    // bounce brick
    private int paddle = 0;
    // scoring
    public int score = 0;
    public int highScore = 0;
    // game ending + attempts
    public boolean gameOver = false;
    public int attempts = 3;

    public static void main(String[] args) {
        // setting up the window
        JFrame window = new JFrame("AirBall");
        AirBall g = new AirBall();

        // won't run without these
        window.add(g);
        window.pack(); // sizes window

        // configurations
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // stops program when you close the window
        window.setLocationRelativeTo(null); // places window at center of the screen
        window.setVisible(true); // makes window visible
        window.addMouseMotionListener(g); // for mouse input

        // sets ball speed
        Timer ballMove = new Timer(13, g);
        ballMove.start();
    }

    // window size
    public Dimension getPreferredSize() {
        return new Dimension(1000, 650);
    }

    public void paintComponent(Graphics g) {
        // draw and color for all

        // top
        g.setColor(Color.white);
        g.fillRect(0, 0, 1000, 650);

        // bottom
        g.setColor(Color.black);
        g.fillRect(0, 600, 1000, 50);

        // paddle
        g.setColor(Color.black);
        g.fillRect(paddle, 550, 100, 3);

        // ball
        g.setColor(Color.red);
        g.fillOval(xBall, yBall, 30, 30);

        // game end
        g.setColor(Color.black);
        g.setFont(new Font("Arial", 8, 24));

        // broadcasts score + attempts
        if (gameOver == false) {
            g.drawString(String.valueOf("Score : " + score), 50, 50);
            g.drawString(String.valueOf("Attempt(s) : " + attempts), 50, 74);
        }

        // broadcasts score + attempts + highscore
        else {
            g.drawString(String.valueOf("Score : " + score), 50, 50);
            g.drawString(String.valueOf("Attempt(s) : " + attempts), 50, 74);
            g.drawString(String.valueOf("HighScore : " + highScore), 50, 98);
        }

        // delete ball (really)
        if (attempts == 0) {
            yBallSpeed = 0;
            xBallSpeed = 0;
            xBall = 300;
            yBall = 300;
            g.setColor(Color.white);
            g.fillOval(xBall, yBall, 30, 30);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // moves the ball from where it is
        xBall = xBall + xBallSpeed;
        yBall = yBall + yBallSpeed;

        // adds to score based on y value / bounces ball back up
        if (xBall >= paddle && xBall <= paddle + 75 && yBall >= PADDLE_MIN_Y && yBall <= PADDLE_MAX_Y) {
            yBallSpeed = -7;
            score++;
            repaint();
        }

        // reset score and ball if too far down
        // -1 life point
        if (yBall >= 575) {
            // adjusts highscore
            if (score > highScore) {
                highScore = score;
            }

            score = 0;
            yBall = 30; // gameOver = true;

            // adds and removes attempts
            if (attempts > 1) {
                attempts--;
            }

            // placeholder
            else if (attempts == 0) {
            }
            // ends game if not more attempts
            else {
                attempts--;
                gameOver = true;
            }
        }

        // upSide forces ball to go down if at top
        if (yBall <= 0) {
            yBallSpeed = 7;
        }

        // rightSide forces ball to bounce if hit right side
        if (xBall >= 1000) {
            xBallSpeed = -5;
        }

        // leftSide forces ball to bounce if hit left side
        if (xBall <= 0) {
            xBallSpeed = 5;
        }
        // moves the ball sprite based on xBall and yBall
        repaint();
    }

    // moves paddle based on mouse
    public void mouseMoved(MouseEvent e) {
        paddle = e.getX() - 50; // it's -50 so mouse is centered onto paddle
        repaint();
    }

    // wrote these in order to avoid errors
    // they seem like try-catches that don't output into console
    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}

// Samad Merchant Computer Science 1 PAP 5th Period