package com.bot.tree;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Tree {
  private String latinName;
  private ArrayList<String> englishNames = new ArrayList<String>();
  private ArrayList<String> swedishNames = new ArrayList<String>();
  private ArrayList<String> germanNames = new ArrayList<String>();

  public void setLatinName(String latinName) {
    this.latinName = latinName;
  }

  public void setEnglishNames(ArrayList<String> englishNames) {
    this.englishNames = new ArrayList<String>(englishNames);
  }

  public void setGermanNames(ArrayList<String> germanNames) {
    this.germanNames = new ArrayList<String>(germanNames);
  }

  public void setSwedishNames(ArrayList<String> swedishNames) {
    this.swedishNames = new ArrayList<String>(swedishNames);
  }

  public String getLatinName() {
    return latinName;
  }

  public ArrayList<String> getEnglishNames() {
    return englishNames;
  }

  public ArrayList<String> getGermanNames() {
    return germanNames;
  }

  public ArrayList<String> getSwedishNames() {
    return swedishNames;
  }

  public String getEnglishNamestoString() {
    return Helper.joinList(englishNames.stream().map(str -> str.replace("#", "")).collect(Collectors.toList()), "or", "'", "---");
  }

  public String getGermanNamestoString() {
    return Helper.joinList(germanNames.stream().map(str -> str.replace("#", "")).collect(Collectors.toList()), "or", "'", "---");
  }

  public String getSwedishNamestoString() {
    return Helper.joinList(swedishNames.stream().map(str -> str.replace("#", "")).collect(Collectors.toList()), "or", "'", "---");
  }

  public ArrayList<String> getAllNames() {
    ArrayList<String> names = new ArrayList<String>();
    names.addAll(getEnglishNames().stream().filter(str -> str.indexOf('#') == -1).collect(Collectors.toList()));
    names.addAll(getGermanNames().stream().filter(str -> str.indexOf('#') == -1).collect(Collectors.toList()));
    names.addAll(getSwedishNames().stream().filter(str -> str.indexOf('#') == -1).collect(Collectors.toList()));
    names.add(getLatinName());
    return names;
  }

  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append("The tree with the latin name '")
      .append(getLatinName())
      .append("' is also known as ")
      .append(getSwedishNamestoString())
      .append(" in Swedish. In German it is called ")
      .append(getGermanNamestoString())
      .append(" and in English it is referred to as ")
      .append(getEnglishNamestoString())
      .append(".");
    return sb.toString();
  }
}
