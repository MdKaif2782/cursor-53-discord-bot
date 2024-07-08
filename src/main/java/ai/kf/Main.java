package ai.kf;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {
    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder().setToken(System.getenv("TOKEN")).login().join();
        System.out.println(api.createBotInvite());
    }
}