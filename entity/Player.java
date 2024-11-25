package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    BufferedImage[] upImages, downImages, leftImages, rightImages;
    BufferedImage currentImage;

    int animationFrame = 0;
    int animationCounter = 0;


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
        try {
            upImages = new BufferedImage[] {
                    loadImage("/idle.png"),
                    loadImage("/walk1.png"),
                    loadImage("/walk2.png")
            };

            downImages = new BufferedImage[] {
                    loadImage("/back-idle.png"),
                    loadImage("/back-walk1.png"),
                    loadImage("/back-walk2.png")
            };

            leftImages = new BufferedImage[] {
                    loadImage("/left-idle.png"),
                    loadImage("/left-walk1.png"),
                    loadImage("/left-walk2.png")
            };

            rightImages = new BufferedImage[] {
                    loadImage("/right-idle.png"),
                    loadImage("/right-walk1.png"),
                    loadImage("/right-walk2.png")
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private BufferedImage loadImage(String path) throws IOException {
        BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        if (img == null) {
            System.out.println("Failed to load image: " + path);
        }
        return img;
    }

    public void update() {
        boolean isMoving = false;

        int dx = 0, dy = 0;

        if (keyH.upPressed) {
            dy = -1;
            updateAnimation(downImages);
            isMoving = true;
        }
        if (keyH.downPressed) {
            dy = 1;
            updateAnimation(upImages);
            isMoving = true;
        }
        if (keyH.leftPressed) {
            dx = -1;
            updateAnimation(leftImages);
            isMoving = true;
        }
        if (keyH.rightPressed) {
            dx = 1;
            updateAnimation(rightImages);
            isMoving = true;
        }

        // fancy vector
        if (dx != 0 || dy != 0) {
            double magnitude = Math.sqrt(dx * dx + dy * dy);
            dx = (int) Math.round(dx / magnitude * speed);
            dy = (int) Math.round(dy / magnitude * speed);
        }

        // Apply movement
        x += dx;
        y += dy;

        // Reset to idle if not moving
        if (!isMoving) {
            resetToIdle();
        }

        // Prevent player from moving out of bounds
        x = Math.max(0, Math.min(x, gp.screenWidth - gp.tileSize));
        y = Math.max(0, Math.min(y, gp.screenHeight - gp.tileSize));
    }

    public void updateAnimation(BufferedImage[] directionImages) {
        animationCounter++;
        if (animationCounter >= 15) {  // Adjust animation speed here
            animationFrame = (animationFrame + 1) % 3;  // Cycle through the 3 frames
            animationCounter = 0;
        }
        currentImage = directionImages[animationFrame];
    }

    public void resetToIdle() {
        currentImage = upImages[0];
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(currentImage, x, y, gp.tileSize, gp.tileSize, null);
    }
}
