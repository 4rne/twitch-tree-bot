package com.bot.tree;

import java.util.ArrayList;

public class Tree {
  private String latinName;
  private ArrayList<String> englishNames = new ArrayList<String>();
  private ArrayList<String> swedishNames = new ArrayList<String>();
  private ArrayList<String> germanNames = new ArrayList<String>();

  public ArrayList<String> getEnglishNames() {
    return englishNames;
  }

  public ArrayList<String> getSwedishNames() {
    return swedishNames;
  }

  public ArrayList<String> getGermanNames() {
    return germanNames;
  }

  public String getLatinName() {
    return latinName;
  }

  public ArrayList<String> getNames() {
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
      .append(latin)
      .append("' is also known as '")
      .append(swedish)
      .append("' in Swedish. In German it is called '")
      .append(german)
      .append("' and in English it is referred to as '")
      .append(english)
      .append("'.");
    return sb.toString();
  }

  public void setLatinName(String latinName) {
  }

  public void setEnglishNames(ArrayList<String> englishNames) {
  }
}
