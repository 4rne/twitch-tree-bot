package com.bot.tree;

public class Tree {
  private String english;
  private String swedish;
  private String german;
  private String latin;

  public Tree(String english, String swedish, String german, String latin) {
    this.english = english;
    this.swedish = swedish;
    this.german = german;
    this.latin = latin;
  }

  public String getEnglishName() {
    return english;
  }

  public String getSwedishName() {
    return swedish;
  }

  public String getGermanName() {
    return german;
  }

  public String getLatinName() {
    return latin;
  }

  public String[] getNames() {
    return new String[] {english, swedish, german, latin};
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
}
