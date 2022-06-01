package com.bot.tree;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedHashMap;

import org.yaml.snakeyaml.Yaml;

public class TreeLogic {

  private static ArrayList<Tree> trees = new ArrayList<Tree>();

  public TreeLogic() {
    loadTrees();
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

	public Tree containsTreeName(String msg) {
		for (Tree tree : trees) {
			for (String name : tree.getAllNames()) {
				Pattern regex = Pattern.compile("(^|\\W)" + name.toLowerCase() + "($|\\W)");
				Matcher regexMatcher = regex.matcher(msg.toLowerCase());
				if(regexMatcher.find()) {
					return tree;
				}
			}
		}
		return null;
	}
}
