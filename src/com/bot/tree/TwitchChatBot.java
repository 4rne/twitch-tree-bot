package com.bot.tree;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jibble.pircbot.*;
import org.yaml.snakeyaml.*;

public class TwitchChatBot extends PircBot {
	private String token;
	private String[] channels;

	private static ArrayList<Tree> trees = new ArrayList<Tree>();
	private LocalDateTime lastMessage = LocalDateTime.now().minusHours(42);

	public boolean init(String token, String[] channels) {
		this.token = token;
		this.channels = channels.clone();

		loadTrees();

		return connect();
	}

	private void loadTrees() {
		Yaml yaml = new Yaml();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("trees.yaml");
		Map<String, Object> treesYaml = yaml.load(inputStream);
		for (Map.Entry<String, Object> entry : treesYaml.entrySet()) {
			trees.add(loadTree(entry));
		}
		System.exit(0);
	}

	private Tree loadTree(Map.Entry<String, Object> treeYaml) {
		String latinName = treeYaml.getKey();
			ArrayList<String> englishNames;
			Object englishObject = ((LinkedHashMap<String, Object>) treeYaml.getValue()).get("en");
			if(englishObject.getClass() == (new ArrayList<String>().getClass())) {
				englishNames = (ArrayList<String>) englishObject;
			} else {
				englishNames = new ArrayList<String>();
				englishNames.add((String) englishObject);
			}
			String swedishName = (String) ((LinkedHashMap<String, Object>) treeYaml.getValue()).get("se");
			String germanName = (String) ((LinkedHashMap<String, Object>) treeYaml.getValue()).get("de");
			Tree t = new Tree();
			t.setLatinName(latinName);
			t.setEnglishNames(englishNames);
			return t;
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
		if(LocalDateTime.now().minusSeconds(15).isAfter(lastMessage))
		{
			Tree t = containsTreeName(message);
			if(t != null) {
				sendMessage(channel, "🤖 🌳🌲 I overheard you talking about a tree! " + t.getDescription());
				updateLastMessageTimestamp();
			}
		}
	}

	private Tree containsTreeName(String str) {
		for (Tree tree : trees) {
			for (String name : tree.getNames()) {
				Pattern regex = Pattern.compile("(^|\\W)" + name.toLowerCase() + "($|\\W|\\w\\W)");
				Matcher regexMatcher = regex.matcher(str.toLowerCase());
				if(regexMatcher.find()) {
					return tree;
				}
			}
		}
		return null;
	}

	private void updateLastMessageTimestamp() {
		lastMessage = LocalDateTime.now();
	}
}
