package com.bot.tree.events;

import com.bot.tree.TwitchChatBot;
import com.bot.tree.UserInfo;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;

public class RewardRedeemedEventHandler {
    public static void handle(RewardRedeemedEvent event) {
        
        String message = String.format(
            "%s just redeemed ā%sā for %s ArbPripps ",
            event.getRedemption().getUser().getDisplayName(),
            event.getRedemption().getReward().getTitle(),
            event.getRedemption().getReward().getCost()
        );
        if(event.getRedemption().getReward().getIsUserInputRequired()) {
            message += "with the following text: ā" + event.getRedemption().getUserInput() + "ā.";
        } else {
            message += ".";
        }

        System.out.println(message);
        String channel = UserInfo.fetchChannelName(event.getRedemption().getChannelId());
        TwitchChatBot.twitchClient.getChat().sendMessage(channel, message);
    }
}
