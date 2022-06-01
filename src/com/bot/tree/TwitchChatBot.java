package com.bot.tree;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.jibble.pircbot.*;

public class TwitchChatBot extends PircBot {
	private HashMap<String, String> config = new HashMap<String, String>();
	private String token;
	private String botName;
	private String[] channels;
	private TreeLogic trees = new TreeLogic();

	private LocalDateTime lastMessage = LocalDateTime.now().minusHours(42);

	private Weather weather = new Weather();

	public TwitchChatBot(HashMap<String, String> config_options) {
		this.config = config_options;
  }

  public boolean init() {
		botName = config.get("BOT_NAME");
		token = config.get("TWITCH_API_TOKEN");
		channels = config.get("JOIN_CHANNELS").split(",");

		return connect();
	}

	public boolean connect() {
		try {
			connect("irc.twitch.tv", 6667, token);
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (IrcException e) {
			e.printStackTrace();
			return false;
		}
		for (String channel : channels) {
			joinChannel("#" + channel);
			System.out.println("Connected to: " + channel);
		}

		return true;
	}

	@Override
	protected void onDisconnect() {
		super.onDisconnect();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connect();
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		if(LocalDateTime.now().minusSeconds(30).isAfter(lastMessage))
		{
			if(sender.equalsIgnoreCase(botName)) {
				return;
			}
			Tree t = trees.containsTreeName(message);
			if(t != null) {
				sendMessage(channel, "🤖 🌳🌲 I overheard you talking about a tree! " + t.getDescription());
				updateLastMessageTimestamp();
			}
			String msg = message.toLowerCase();
			if(msg.contains("pripps")) {
				sendMessage(channel, "ArbPripps You cannot translate glorious Pripps Blå ArbPripps ");
			}
			else if(msg.startsWith("!weather")) {
				sendMessage(channel, "🤖 " + weather.toReadableString());
			}
			else if(msg.startsWith("!estimate")) {
				sendMessage(channel, "🤖 " + new Estimate(msg).toString());
			}
			else if(msg.startsWith("!chipper")) {
				sendMessage(channel, "🤖 We have got two chippers. One trusty Timberwolf and an always broken Jensen.");
			}
			else if(msg.startsWith("!chainsaw")) {
				sendMessage(channel, "🤖 We have enough chainsaws.");
			}
			else if(msg.startsWith("!commands") || msg.startsWith("!help")) {
				sendMessage(channel, "🤖 You can use the command !estimate to get an estimate on a tree job. Use !weather to get the current weather in Uppsala. Mention any tree name in a chat message and I will tell you how the tree is called in different languages. Use !chipper to get info about the chippers. Use !chainsaw to get info about chainsaws.");
			}
		}
	}

	private void updateLastMessageTimestamp() {
		lastMessage = LocalDateTime.now();
	}
}
