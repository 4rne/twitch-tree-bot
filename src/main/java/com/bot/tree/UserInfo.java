package com.bot.tree;

import java.util.Arrays;
import java.util.List;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.User;

public class UserInfo {
  public static String fetchDisplayName(TwitchClient tc, String userLogin) {
    List<User> resultList = tc.getHelix().getUsers(null, null, Arrays.asList(userLogin)).execute().getUsers();
        if(resultList.size() > 0) {
            return resultList.get(0).getDisplayName();
        } else {
            return userLogin;
        }
    }
}
