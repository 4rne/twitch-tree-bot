package com.bot.tree;

public class Bot {
    public static void main(String[] args) {
        TwitchChatBot bot = new TwitchChatBot();
        String token = System.getenv().get("TWITCH_API_TOKEN");
        if(token == null) {
            System.err.println("no TWITCH_API_TOKEN provided");
            System.exit(1);
        }
        String botName = System.getenv().get("BOT_NAME");
        if(botName == null) {
            System.err.println("BOT_NAME not provided");
            System.exit(1);
        }
        String channels = System.getenv().get("JOIN_CHANNELS");
        if(channels == null) {
            System.err.println("JOIN_CHANNELS not provided");
            System.exit(1);
        }
        String[] channelList = channels.split(",");
        bot.init(token, botName, channelList);
    }
}
