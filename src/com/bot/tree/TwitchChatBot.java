package com.bot.tree;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jibble.pircbot.*;

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
        trees.add(new Tree("oak", "Ek", "Eiche", "Quercus"));
        trees.add(new Tree("fir", "Gran", "Tanne", "Abies"));
        trees.add(new Tree("pine", "Tall", "Kiefer", "Pinus"));
        trees.add(new Tree("birch", "Björk", "Birke", "Betula"));
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
                sendMessage(channel, "I heard you talking about a specific tree! " + t.getDescription());
                updateLastMessageTimestamp();
            }
        }
    }

    private Tree containsTreeName(String str) {
        for (Tree tree : trees) {
            for (String name : tree.getNames()) {
                String treeName = name.toLowerCase();
                Pattern regex = Pattern.compile("\\W" + treeName + "\\W");
                Pattern regexBeginning = Pattern.compile("(\\W)?" + treeName + "\\W");
                Pattern regexEnd = Pattern.compile("\\W" + treeName + "(\\W)?");
                Matcher regexMatcher = regex.matcher(str);
                Matcher regexMatcherBeginning = regexBeginning.matcher(str);
                Matcher regexMatcherEnd = regexEnd.matcher(str);
                if(regexMatcher.find() || regexMatcherBeginning.find() || regexMatcherEnd.find() || str.equalsIgnoreCase(treeName)) {
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
