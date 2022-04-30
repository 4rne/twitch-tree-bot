package com.bot.tree;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jibble.pircbot.*;
import org.yaml.snakeyaml.*;

public class TwitchChatBot extends PircBot {
	private String token;
	private String[] channels;

	private static ArrayList<Tree> trees = new ArrayList<Tree>();
	private LocalDateTime lastMessage = LocalDateTime.now().minusHours(42);

	private Weather weather = new Weather();

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
	}

	private Tree loadTree(Map.Entry<String, Object> treeYaml) {
		String latinName = treeYaml.getKey();
			Object englishObject = ((LinkedHashMap<String, Object>) treeYaml.getValue()).get("en");
			ArrayList<String> englishNames = parseTreeNames(englishObject);
			Object germanObject = ((LinkedHashMap<String, Object>) treeYaml.getValue()).get("de");
			ArrayList<String> germanNames = parseTreeNames(germanObject);
			Object swedishObject = ((LinkedHashMap<String, Object>) treeYaml.getValue()).get("se");
			ArrayList<String> swedishNames = parseTreeNames(swedishObject);
			Tree t = new Tree();
			t.setLatinName(latinName);
			t.setEnglishNames(englishNames);
			t.setGermanNames(germanNames);
			t.setSwedishNames(swedishNames);
			System.out.println(t.getDescription());
			return t;
	}

	private ArrayList<String> parseTreeNames(Object nameObject) {
		ArrayList<String> names;
		if(nameObject.getClass() == (new ArrayList<String>().getClass())) {
			names = (ArrayList<String>) nameObject;
		} else {
			names = new ArrayList<String>();
			names.add((String) nameObject);
		}
		return names;
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
			Tree t = containsTreeName(message);
			if(t != null) {
				sendMessage(channel, "🤖 🌳🌲 I overheard you talking about a tree! " + t.getDescription());
				updateLastMessageTimestamp();
			}
			String msg = message.toLowerCase();
			if(msg.contains("pripps") && !sender.equalsIgnoreCase("Pseud0obot")) {
				sendMessage(channel, "ArbPripps You cannot translate glorious Pripps Blå ArbPripps ");
			}
			if(msg.startsWith("!weather") && !sender.equalsIgnoreCase("Pseud0obot")) {
				sendMessage(channel, "🤖 " + weather.toReadableString());
			}
			if(msg.startsWith("!estimate") && !sender.equalsIgnoreCase("Pseud0obot")) {
				sendMessage(channel, "🤖 " + estimate());
			}
			if(!sender.equalsIgnoreCase("Pseud0obot") && (msg.startsWith("!commands") || msg.startsWith("!help"))) {
				sendMessage(channel, "🤖 You can use the command !estimate to get an estimate on a tree job. Use !weather to get the current weather in Uppsala. Mention any tree name in a chat message and I will tell you how the tree is called in different languages.");
			}
		}
	}

	private Tree containsTreeName(String str) {
		for (Tree tree : trees) {
			for (String name : tree.getAllNames()) {
				Pattern regex = Pattern.compile("(^|\\W)" + name.toLowerCase() + "($|\\W)");
				Matcher regexMatcher = regex.matcher(str.toLowerCase());
				if(regexMatcher.find()) {
					return tree;
				}
			}
		}
		return null;
	}

	private String estimate() {
		try {
			ArrayList<String> fullTeam = new ArrayList<String> (Arrays.asList("Stepan", "Vektor", "JP", "CrazyGroundie", "Bjurn", "Karlous"));
			ArrayList<String> prices = new ArrayList<String> (Arrays.asList("over 9000", "too much", "a kidney and 3", "1", (new Random().nextInt(5999) + 4000) + "", "1337", "69", "3.14159", "666", "42", "420"));
			ArrayList<String> jobTeam = new ArrayList<String>();
			int teamSize = new Random().nextInt(fullTeam.size() - 1) + 1;
			for(int i = 0; i < teamSize; i++) {
				int memberPosition = new Random().nextInt(fullTeam.size());
				jobTeam.add(fullTeam.get(memberPosition));
				fullTeam.remove(memberPosition);
			}
			String price = prices.get(new Random().nextInt(prices.size()));
			return "I calculated all the costs of " + Helper.joinArrayList(jobTeam, "and", "", "") + " plus driving and tools. In total this job will cost " + price + " Kroner. Kappa";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "broken";
		}
	}

	private void updateLastMessageTimestamp() {
		lastMessage = LocalDateTime.now();
	}
}
