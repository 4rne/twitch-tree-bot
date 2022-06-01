package com.bot.tree.events;

import java.util.HashMap;

import com.bot.tree.UserInfo;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.FollowEvent;

public class FollowEventHandler {
    HashMap<String, String> config;
    TwitchClient twitchClient;

    public FollowEventHandler(TwitchClient tc, SimpleEventHandler eventHandler, HashMap<String, String> config) {
        this.config = config;
        this.twitchClient = tc;
        eventHandler.onEvent(FollowEvent.class, event -> onFollow(event));
    }

    public void onFollow(FollowEvent event) {
        String message = String.format(
            "Hi, %s. Thank you for following the Arboris gang. <3 If you have any questions feel free to ask. It might take a while until we get to read the chat. ;)",
            UserInfo.fetchDisplayName(twitchClient, event.getUser().getName())
        );

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }
}
