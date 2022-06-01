package com.bot.tree;

import java.util.HashMap;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.bot.tree.events.*;

public class TwitchChatBot {
	private HashMap<String, String> config = new HashMap<String, String>();
	private TwitchClient twitchClient;

	public TwitchChatBot(HashMap<String, String> config_options) {
		this.config = config_options;

		TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

		OAuth2Credential credential = new OAuth2Credential("twitch", config.get("TWITCH_API_TOKEN"));

		twitchClient = clientBuilder
			.withClientId(config.get("TWITCH_CLIENT_ID"))
			.withClientSecret(config.get("TWITCH_CLIENT_SECRET"))
			.withEnableHelix(true)
			.withChatAccount(credential)
			.withEnableChat(true)
			.withEnableGraphQL(false)
			.withEnableKraken(false)
			.build();
  }

  public void start() {
		for (String channel : config.get("JOIN_CHANNELS").split(",")) {
			twitchClient.getChat().joinChannel(channel);
		}
	}

	public void registerEvents() {
		SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

		new DonationEventHandler(eventHandler, config);
		new FollowEventHandler(twitchClient, eventHandler, config);
		new SubscriptionEventHandler(eventHandler, config);
		new CommandEventHandler(twitchClient, eventHandler, config);
	}
}
