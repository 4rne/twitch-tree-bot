package com.bot.tree;

import java.util.HashMap;

public class BotLoader {
    public static void main(String[] args) {
        String params[] = new String[] {"TWITCH_API_TOKEN", "BOT_NAME", "JOIN_CHANNELS", "TWITCH_CLIENT_ID", "TWITCH_CLIENT_SECRET", "OPENWEATHERMAP_API_TOKEN", "TWITCH_ACCESS_TOKEN", "EVENT_CHANNEL_ID"};
        HashMap<String, String> config_options = new HashMap<String, String>();

        for (String param : params) {
            String value = System.getenv().get(param);
            if(value == null) {
                System.err.println("no " + param + " provided");
                System.exit(1);
            } else {
                config_options.put(param, value);
            }
        }

        TwitchChatBot bot = new TwitchChatBot(config_options);
        bot.registerEvents();
        bot.start();
    }
}
