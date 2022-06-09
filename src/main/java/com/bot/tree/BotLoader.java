package com.bot.tree;

import java.util.ArrayList;
import java.util.HashMap;

public class BotLoader {
    public static void main(String[] args) {
        String params[] = new String[] {"TWITCH_API_TOKEN", "BOT_NAME", "JOIN_CHANNELS", "TWITCH_CLIENT_ID", "TWITCH_CLIENT_SECRET", "OPENWEATHERMAP_API_TOKEN", "TWITCH_ACCESS_TOKEN", "EVENT_CHANNEL_ID", "TWITCH_REFRESH_TOKEN"};
        HashMap<String, String> config_options = new HashMap<String, String>();
        ArrayList<String> missingParams = new ArrayList<String>();

        for (String param : params) {
            String value = System.getenv().get(param);
            if(value == null) {
                missingParams.add(param);
            } else {
                config_options.put(param, value);
            }
        }

        if(missingParams.size() > 0) {
            for (String param : missingParams) {
                System.err.println("no " + param + " provided");
            }
            System.exit(1);
        }

        TwitchChatBot bot = new TwitchChatBot(config_options);
        bot.registerEvents();
        bot.start();
        Thread tokenRefreshThread = new Thread(new Runnable(){
            @Override
            public void run() {
                while(true) {
                    TokenRefresher.refreshToken();
                    try {
                        Thread.sleep(60 * 1000 * 60);;
                    } catch (Exception e) {
                    }
                }
            }
        });
        tokenRefreshThread.start();

    }
}
