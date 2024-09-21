package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

import javax.imageio.ImageIO;
import java.io.IOException;


public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }

    public void setDefaultValues () {
        x = 100;
        y = 100;
        speed = 4;

        getPlayerImage();
    }

    public void getPlayerImage() {
    }


    public void update() {
        if (keyH.upPressed) {
            y -= speed;
        }
        if (keyH.downPressed) {
            y += speed;
        }
        if (keyH.leftPressed) {
            x -= speed;
        }
        if (keyH.rightPressed) {
            x += speed;
        }

        // Prevent player from moving out of bounds
        x = Math.max(0, Math.min(x, gp.screenWidth - gp.tileSize));
        y = Math.max(0, Math.min(y, gp.screenHeight - gp.tileSize));

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}
