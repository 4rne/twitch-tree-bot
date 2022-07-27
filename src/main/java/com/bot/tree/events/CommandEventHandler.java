package com.bot.tree.events;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.bot.tree.Estimate;
import com.bot.tree.Tree;
import com.bot.tree.TreeLogic;
import com.bot.tree.Weather;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

public class CommandEventHandler {
    HashMap<String, String> config;
	private LocalDateTime lastMessage = LocalDateTime.now().minusHours(42);
    private TreeLogic trees;
	private Weather weather = new Weather();

    public CommandEventHandler(SimpleEventHandler eventHandler, HashMap<String, String> config) {
        this.config = config;
        trees = new TreeLogic();
        eventHandler.onEvent(ChannelMessageEvent.class, event -> onChannelMessage(event));
    }

    public void onChannelMessage(ChannelMessageEvent event) {
		if(LocalDateTime.now().minusSeconds(30).isAfter(lastMessage))
		{
            String message = event.getMessage();
			if(event.getUser().getName().equalsIgnoreCase(config.get("BOT_NAME"))) {
				return;
			}
			Tree t = trees.containsTreeName(message);
			if(t != null) {
				event.reply(event.getTwitchChat(), "🤖 🌳🌲 I overheard you talking about a tree! " + t.getDescription());
				updateLastMessageTimestamp();
			}
			String msg = message.toLowerCase();
			if(msg.contains("pripps")) {
				event.getTwitchChat().sendMessage(event.getChannel().getName(), "ArbPripps You cannot translate glorious Pripps Blå ArbPripps ");
			}
			else if(msg.startsWith("!weather")) {
				event.getTwitchChat().sendMessage(event.getChannel().getName(), "🤖 " + weather.toReadableString());
			}
			else if(msg.startsWith("!estimate")) {
				event.getTwitchChat().sendMessage(event.getChannel().getName(), "🤖 " + new Estimate(msg).toString());
			}
			else if(msg.startsWith("!chipper")) {
				event.getTwitchChat().sendMessage(event.getChannel().getName(), "🤖 We have got two chippers. One puny Timberwolf and a big strong Jensen.");
			}
			else if(msg.startsWith("!chainsaw")) {
				event.getTwitchChat().sendMessage(event.getChannel().getName(), "🤖 We have enough chainsaws.");
			}
			else if(msg.startsWith("!commands") || msg.startsWith("!help")) {
				event.getTwitchChat().sendMessage(event.getChannel().getName(), "🤖 You can use the command !estimate to get an estimate on a tree job. Use !weather to get the current weather in Uppsala. Mention any tree name in a chat message and I will tell you how the tree is called in different languages. Use !chipper to get info about the chippers. Use !chainsaw to get info about chainsaws.");
			}
        }
    }

	private void updateLastMessageTimestamp() {
		lastMessage = LocalDateTime.now();
	}
}
