package ai.kf.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageCreator {

    public BufferedImage createWelcomeImage(BufferedImage profilePic, String username) throws IOException {
        // Load the background image
        BufferedImage backgroundImage = ImageIO.read(new URL("https://cdn.discordapp.com/attachments/968916979467571230/1259832271033204816/IMG_20240708_171438_451_2_1.png?ex=668d1dbb&is=668bcc3b&hm=ee8a950b3d71f7e2512f8145d90cc2f18341d6cd6bf07654955544ab55c1caf3&"));

        // Create a new image with the same dimensions
        BufferedImage outputImage = new BufferedImage(backgroundImage.getWidth(), backgroundImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = outputImage.createGraphics();

        // Draw the background image
        g2d.drawImage(backgroundImage, 0, 0, null);

        // Create a semi-transparent black overlay
        g2d.setColor(new Color(0, 0, 0, 127));
        g2d.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());

        // Calculate the diameter of the circular profile picture (20% of the total image width)
        int diameter = (int) (outputImage.getWidth() * 0.2);

        // Load the profile picture
        if (profilePic == null) {
            profilePic = ImageIO.read(new URL("https://cdn.discordapp.com/embed/avatars/0.png"));
        }

        // Resize the profile picture to fit the circle
        BufferedImage resizedProfilePic = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dProfilePic = resizedProfilePic.createGraphics();
        g2dProfilePic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2dProfilePic.drawImage(profilePic, 0, 0, diameter, diameter, null);
        g2dProfilePic.dispose();

        // Draw the profile picture with a circular clip
        int x = (outputImage.getWidth() - diameter) / 2;
        int y = (outputImage.getHeight() - diameter) / 2 - 30;
        g2d.setClip(new Ellipse2D.Float(x, y, diameter, diameter));
        g2d.drawImage(resizedProfilePic, x, y, null);


        // Draw the welcome text at the bottom of the image
        g2d.setClip(null);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 50));
        String welcomeText = "Welcome " + username;
        int textWidth = g2d.getFontMetrics().stringWidth(welcomeText);
        int textX = (outputImage.getWidth() - textWidth) / 2;
        int textY = y + diameter + 70;
        g2d.drawString(welcomeText, textX, textY);

        g2d.dispose();

        return outputImage;
    }
}
