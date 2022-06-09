package com.bot.tree;

import java.util.Arrays;
import java.util.List;

import com.github.twitch4j.helix.domain.User;

public class UserInfo {
    public static String fetchDisplayName(String userLogin) {
        List<User> resultList = TwitchChatBot.twitchClient.getHelix().getUsers(TwitchChatBot.settings.getTwitchAccessToken(), null, Arrays.asList(userLogin)).execute().getUsers();
        if(resultList.size() > 0) {
            return resultList.get(0).getDisplayName();
        } else {
            return userLogin;
        }
    }

    public static String fetchChannelName(String channelId) {
        List<User> resultList = TwitchChatBot.twitchClient.getHelix().getUsers(TwitchChatBot.settings.getTwitchAccessToken(), Arrays.asList(channelId), null).execute().getUsers();
        if(resultList.size() > 0) {
            return resultList.get(0).getLogin();
        } else {
            return null;
        }
    }
}
