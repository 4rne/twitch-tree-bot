package com.bot.tree.events;

import java.util.HashMap;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.DonationEvent;

public class DonationEventHandler {
    HashMap<String, String> config;

    public DonationEventHandler(SimpleEventHandler eventHandler, HashMap<String, String> config) {
        this.config = config;
        eventHandler.onEvent(DonationEvent.class, event -> onDonation(event));
    }

    public void onDonation(DonationEvent event) {
        String message = String.format(
                "%s just donated %s using %s!",
                event.getUser().getName(),
                event.getAmount(),
                event.getSource()
        );

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }
}
