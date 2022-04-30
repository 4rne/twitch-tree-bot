package com.bot.tree;

import java.util.ArrayList;

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
    return Helper.joinArrayList(englishNames, "or", "'", "---");
  }

  public String getGermanNamestoString() {
    return Helper.joinArrayList(germanNames, "or", "'", "---");
  }

  public String getSwedishNamestoString() {
    return Helper.joinArrayList(swedishNames, "or", "'", "---");
  }

  public ArrayList<String> getAllNames() {
    ArrayList<String> names = new ArrayList<String>();
    names.addAll(getEnglishNames());
    names.addAll(getGermanNames());
    names.addAll(getSwedishNames());
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
