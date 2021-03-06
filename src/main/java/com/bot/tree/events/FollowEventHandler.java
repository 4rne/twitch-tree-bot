package com.bot.tree.events;

import com.bot.tree.TwitchChatBot;
import com.bot.tree.UserInfo;
import com.github.twitch4j.pubsub.events.FollowingEvent;

public class FollowEventHandler {
    public static void handle(FollowingEvent event) {
        String message = String.format(
            "Hi, @%s. Thank you for following the Arboris gang. <3 If you have any questions feel free to ask. It might take a while until we get to read the chat. ;)",
            event.getData().getDisplayName()
        );

        System.out.println(message);
        String channel;
        try {
            channel = TwitchChatBot.twitchClient.getChat().getChannelIdToChannelName().get(event.getChannelId());
        } catch (Exception e) {
            channel = UserInfo.fetchChannelName(event.getChannelId());
        }
        TwitchChatBot.twitchClient.getChat().sendMessage(channel, message);
    }
}
