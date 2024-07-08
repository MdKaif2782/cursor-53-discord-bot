package ai.kf;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.user.UserStatus;

public class Main {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        System.setProperty("sun.java2d.xrender", "true");

        DiscordApi api = new DiscordApiBuilder()
                .setToken(System.getenv("TOKEN"))
                .addIntents(Intent.MESSAGE_CONTENT,Intent.GUILD_MEMBERS,Intent.GUILDS)
                .login()
                .join();
        //Set status and activity
        api.updateActivity(ActivityType.PLAYING, "with your feelings");
        api.updateStatus(UserStatus.ONLINE);

        //Listeners
        api.addListener(new ai.kf.interactions.event.WelcomeEvent());
        api.addListener(new ai.kf.interactions.event.TestWelcomeEvent());
        System.out.println(api.createBotInvite());
    }
}