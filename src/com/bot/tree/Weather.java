package com.bot.tree;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Weather {
  private String temp;
  private String weather;
  private String windspeed;
  private String direction;
	private LocalDateTime lastCheck = LocalDateTime.now().minusHours(0);

  public Weather() {
    parseWeather();
  }

  public String toReadableString() {
    queueWeatherCheckIfNeeded();
    return "Current weather in Uppsala region: " + weather + " at " + temp + "°C. Wind speed is " + windspeed + "km/h from " + direction + "°.";
  }

  private void parseWeather() {
    URL url;
    try {
      url = new URL("https://api.openweathermap.org/data/2.5/weather?q=Uppsala,SE&units=metric&appid=" + System.getenv().get("OPENWEATHERMAP_API_TOKEN"));
      InputStreamReader reader = new InputStreamReader(url.openStream());
      Map weatherObject = new Gson().fromJson(reader, Map.class);
      temp = Math.round(((Double) ((Map)weatherObject.get("main")).get("temp"))) + "";
      weather = (String) ((Map) ((ArrayList<Object>)weatherObject.get("weather")).get(0)).get("main");
      windspeed = Math.round(((Double) ((Map)weatherObject.get("wind")).get("speed")) * 3.6) + "";
      direction = ((Integer) ((Map)weatherObject.get("wind")).get("deg")) + "";
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    lastCheck = LocalDateTime.now();
    System.out.println("Parsed weather from openweathermap:");
    System.out.println(toReadableString());
  }

  private void queueWeatherCheckIfNeeded() {
    if(LocalDateTime.now().minusMinutes(10).isAfter(lastCheck)) {
      parseWeather();
    }
  }
}
