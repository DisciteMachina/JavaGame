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
    int playerSpeed = 5;

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
        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta --;
                drawCount ++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
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
