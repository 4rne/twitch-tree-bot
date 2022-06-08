package com.bot.tree.events;

import com.github.twitch4j.pubsub.events.ChannelSubscribeEvent;

public class SubscriptionEventHandler {
    public static void handle(ChannelSubscribeEvent event) {
        String message = "";

        // New Subscription
        if (event.getData().getCumulativeMonths() <= 1) {
            message = String.format(
                    "%s has subscribed to %s!",
                    event.getData().getDisplayName(),
                    event.getData().getChannelName()
            );
        }
        // Resubscription
        if (event.getData().getCumulativeMonths() > 1) {
            message = String.format(
                    "%s has subscribed to %s in his %s month!",
                    event.getData().getDisplayName(),
                    event.getData().getChannelName(),
                    event.getData().getCumulativeMonths()
            );
        }

        System.out.println(message);
        // event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }
}
