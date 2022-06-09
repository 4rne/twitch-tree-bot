package com.bot.tree;

import java.util.Map;

import com.google.gson.Gson;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TokenRefresher {
  public static boolean refreshToken() {
    HttpUrl url = HttpUrl.parse("https://id.twitch.tv/oauth2/token").newBuilder()
      .addQueryParameter("refresh_token", TwitchChatBot.settings.getTwitchRefreshToken())
      .addQueryParameter("grant_type", "refresh_token")
      .addQueryParameter("client_secret", TwitchChatBot.config.get("TWITCH_CLIENT_SECRET"))
      .addQueryParameter("client_id", TwitchChatBot.config.get("TWITCH_CLIENT_ID"))
      .build();

    Request request = new Request.Builder()
      .url(url)
      .post(RequestBody.create("", null))
      .build();

    try {
      String responseBody = null;
      Response response = new OkHttpClient().newCall(request).execute();
      if (response.isSuccessful()) {
        Gson gson = new Gson(); 
        responseBody = response.body().string();
        Map jsonResponse = gson.fromJson(responseBody, Map.class);

        TwitchChatBot.settings.setProperty("twitch_access_token", (String) jsonResponse.get("access_token"));
        TwitchChatBot.settings.setProperty("twitch_refresh_token", (String) jsonResponse.get("refresh_token"));
        TwitchChatBot.settings.save();
        System.out.println("Refreshed token! HTTP response: " + response.code() + " - " + responseBody);
        return true;
      } else {
        System.out.println("Unable to refresh access token! Code: " + response.code() + " - " + responseBody);
      }
    } catch (Exception e) {
      System.out.println("Got exception while trying to refresh token: ");
      e.printStackTrace();
    }

    return false;
  }
}
