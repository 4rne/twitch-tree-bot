package com.bot.tree;

import java.util.List;
import java.util.Objects;

public class Helper {
  public static String joinArrayList(List<String> namesList, String joinLastElement, String quotes, String defaultReturn) {
    String strippedQuotes = Objects.toString(quotes, "");
    if(namesList.size() == 1) {
      return strippedQuotes + namesList.get(0) + strippedQuotes;
    } else if (namesList.size() >= 2) {
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < namesList.size() - 2; i++) {
        sb.append(strippedQuotes);
        sb.append(namesList.get(i));
        sb.append(strippedQuotes);
        sb.append(", ");
      }
      sb.append(strippedQuotes);
      sb.append(namesList.get(namesList.size() - 2));
      sb.append(strippedQuotes);
      sb.append(" ");
      sb.append(joinLastElement);
      sb.append(" ");
      sb.append(strippedQuotes);
      sb.append(namesList.get(namesList.size() - 1));
      sb.append(strippedQuotes);
      return sb.toString();
    } else {
      return defaultReturn;
    }
  }
}
