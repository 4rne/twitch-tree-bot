package com.bot.tree;

import java.util.HashMap;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
import com.github.twitch4j.pubsub.events.ChannelSubGiftEvent;
import com.github.twitch4j.pubsub.events.ChannelSubscribeEvent;
import com.github.twitch4j.pubsub.events.FollowingEvent;
import com.github.twitch4j.pubsub.events.HypeTrainStartEvent;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import com.bot.tree.events.*;

public class TwitchChatBot {
	public static HashMap<String, String> config = new HashMap<String, String>();
	public static TwitchClient twitchClient;
	public static Settings settings = new Settings().init();
	private OAuth2Credential twitchCredentials;
	private String userid;

	public TwitchChatBot(HashMap<String, String> config_options) {
		TwitchChatBot.config = config_options;
		twitchCredentials = new OAuth2Credential("twitch", settings.getTwitchAccessToken());
		userid = config.get("EVENT_CHANNEL_ID");

		OAuth2Credential credential = new OAuth2Credential("twitch", config.get("TWITCH_API_TOKEN"));

		twitchClient = TwitchClientBuilder.builder()
			.withClientId(config.get("TWITCH_CLIENT_ID"))
			.withClientSecret(config.get("TWITCH_CLIENT_SECRET"))
			.withEnableHelix(true)
			.withChatAccount(credential)
			.withEnableChat(true)
			.withEnablePubSub(true)
			.withEnableGraphQL(false)
			.build();
  }

  public void start() {
		for (String channel : config.get("JOIN_CHANNELS").split(",")) {
			twitchClient.getChat().joinChannel(channel);
		}
	}

	public void registerEvents() {
		twitchClient.getPubSub().listenForChannelPointsRedemptionEvents(twitchCredentials, userid);
		twitchClient.getEventManager().onEvent(RewardRedeemedEvent.class, RewardRedeemedEventHandler::handle);
		twitchClient.getPubSub().listenForFollowingEvents(twitchCredentials, userid);
		twitchClient.getEventManager().onEvent(FollowingEvent.class, FollowEventHandler::handle);
		twitchClient.getPubSub().listenForHypeTrainEvents(twitchCredentials, userid);
		twitchClient.getEventManager().onEvent(HypeTrainStartEvent.class, System.out::println);
		twitchClient.getPubSub().listenForChannelSubGiftsEvents(twitchCredentials, userid);
		twitchClient.getEventManager().onEvent(ChannelSubGiftEvent.class, System.out::println);
		twitchClient.getEventManager().onEvent(ChannelBitsEvent.class, System.out::println);
		twitchClient.getEventManager().onEvent(ChannelSubscribeEvent.class, SubscriptionEventHandler::handle);
		// twitchClient.getPubSub().listenForWhisperEvents(twitchCredentials, userid);
		// twitchClient.getEventManager().onEvent(PrivateMessageEvent.class, System.out::println);
		// twitchClient.getPubSub().listenForSubscriptionEvents(twitchCredentials, userid);
		// twitchClient.getEventManager().onEvent(ChannelSubscribeEvent.class, System.out::println);
		// twitchClient.getPubSub().listenForCheerEvents(twitchCredentials, userid);
		// twitchClient.getEventManager().onEvent(ChannelBitsEvent.class, System.out::println);

		SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);
		new CommandEventHandler(eventHandler, config);
	}
}
