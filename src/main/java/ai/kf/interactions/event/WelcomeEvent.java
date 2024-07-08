package ai.kf.interactions.event;

import ai.kf.utils.ImageCreator;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class WelcomeEvent implements ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {
        try {
            // Create a welcome image
            ImageCreator welcomeImageCreator = new ImageCreator();
            String username = serverMemberJoinEvent.getUser().getName();
            String mentionTag = serverMemberJoinEvent.getUser().getMentionTag();
            var systemChannel = serverMemberJoinEvent.getServer().getChannelsByName("welcome").get(0).asTextChannel().get();
            BufferedImage profilePic = serverMemberJoinEvent.getUser().getAvatar().asBufferedImage().get();
            BufferedImage welcomeImage = welcomeImageCreator.createWelcomeImage(profilePic, username);
            // convert BufferedImage to file without saving it
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(welcomeImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);
            systemChannel.sendMessage("Welcome " + mentionTag + "!", bais, "welcome.png");

            //Give role
            serverMemberJoinEvent.getServer().getRolesByName("member").get(0).addUser(serverMemberJoinEvent.getUser());

        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
