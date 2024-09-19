import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 48x48

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenCol; // 768
    final int screenHeight = tileSize * maxScreenRow; // 576

    double fps = 60; //fps

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Character default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 1;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 100000000/fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            // Update character positions and draw the screen with updated information
            update();
            repaint(); // paintComponent method

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {
        if (keyH.upPressed) {
            playerY -= playerSpeed;
        }
        if (keyH.downPressed) {
            playerY += playerSpeed;
        }
        if (keyH.leftPressed) {
            playerX -= playerSpeed;
        }
        if (keyH.rightPressed) {
            playerX += playerSpeed;
        }

        // Prevent player from moving out of bounds
        playerX = Math.max(0, Math.min(playerX, screenWidth - tileSize));
        playerY = Math.max(0, Math.min(playerY, screenHeight - tileSize));
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
