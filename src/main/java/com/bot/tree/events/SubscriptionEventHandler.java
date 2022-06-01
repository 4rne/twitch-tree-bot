package com.bot.tree.events;

import java.util.HashMap;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;

public class SubscriptionEventHandler {
    HashMap<String, String> config;

    public SubscriptionEventHandler(SimpleEventHandler eventHandler, HashMap<String, String> config) {
        this.config = config;
        eventHandler.onEvent(SubscriptionEvent.class, event -> onSubscription(event));
    }

    public void onSubscription(SubscriptionEvent event) {
        String message = "";

        // New Subscription
        if (event.getMonths() <= 1) {
            message = String.format(
                    "%s has subscribed to %s!",
                    event.getUser().getName(),
                    event.getChannel().getName()
            );
        }
        // Resubscription
        if (event.getMonths() > 1) {
            message = String.format(
                    "%s has subscribed to %s in his %s month!",
                    event.getUser().getName(),
                    event.getChannel().getName(),
                    event.getMonths()
            );
        }

        // event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }
}
