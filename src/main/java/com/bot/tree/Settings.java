package com.bot.tree;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings extends Properties {
  String filename = "config.properties";
  public Settings init() {
    try (InputStream input = new FileInputStream(filename)) {
      System.out.println("Loading config from " + filename + "." );
      load(input);
    } catch (IOException ex) {
      System.out.println("No " + filename + " found. Creating.");
      save();
    }
    return this;
  }

  public void save() {
    try (OutputStream output = new FileOutputStream(filename)) {
      store(output, null);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getTwitchAccessToken() {
    if(getProperty("twitch_access_token") == null) {
      setProperty("twitch_access_token", TwitchChatBot.config.get("TWITCH_ACCESS_TOKEN"));
      save();
    }
    return getProperty("twitch_access_token");
  }

  public String getTwitchRefreshToken() {
    if(getProperty("twitch_refresh_token") == null) {
      setProperty("twitch_refresh_token", TwitchChatBot.config.get("TWITCH_REFRESH_TOKEN"));
      save();
    }
    return getProperty("twitch_refresh_token");
  }
}
