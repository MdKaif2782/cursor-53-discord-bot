package ai.kf.interactions.event;

import ai.kf.utils.ImageCreator;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TestWelcomeEvent implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!test-welcome-event")) {

            String username = messageCreateEvent.getMessage().getAuthor().getName();
            String mentionTag = messageCreateEvent.getMessage().getAuthor().asUser().get().getMentionTag();
            // Create a welcome image
            ImageCreator welcomeImageCreator = new ImageCreator();
            try {
                var systemChannel = messageCreateEvent.getServer().get().getChannelsByName("welcome").get(0).asTextChannel().get();
                    try {
                        BufferedImage profilePic = messageCreateEvent.getMessage().getAuthor().getAvatar().asBufferedImage().get();
                        BufferedImage welcomeImage = welcomeImageCreator.createWelcomeImage(profilePic, username);
                        // convert BufferedImage to file without saving it
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(welcomeImage, "png", baos);
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();
                        ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);
                        systemChannel.sendMessage("Welcome " + mentionTag + "!", bais, "welcome.png");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
