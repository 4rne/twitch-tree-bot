package com.bot.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Estimate {
  private String msg;
  public Estimate(String msg) {
    this.msg = msg;
  }

  public String random() {
		ArrayList<String> fullTeam = new ArrayList<String> (Arrays.asList("Stepan", "Vektor", "JP", "CrazyGroundie", "Bjurn", "Karlous"));
		ArrayList<String> prices = new ArrayList<String> (Arrays.asList("over 9000", "too much", "a kidney and 3", "1", (new Random().nextInt(5999) + 4000) + "", "1337", "69", "3.14159", "666", "42", "420"));
		ArrayList<String> jobTeam = new ArrayList<String>();
		int teamSize = new Random().nextInt(fullTeam.size() - 2) + 2;
		for(int i = 0; i < teamSize; i++) {
			int memberPosition = new Random().nextInt(fullTeam.size());
			jobTeam.add(fullTeam.get(memberPosition));
			fullTeam.remove(memberPosition);
		}
		String price = prices.get(new Random().nextInt(prices.size()));
		return "I calculated all the costs of " + Helper.joinArrayList(jobTeam, "and", "", "") + " plus driving and tools. In total this job will cost " + price + " Kronor. Kappa";
	}

	private String realEstimate(String people, String hours) {
		String sanitized_hours = hours.replaceAll("[^0-9]", "");
		String sanitized_people = people.replaceAll("[^0-9]", "");
		Map<String, Integer[][]> data = Map.of(
			"4", new Integer[][] {
				new Integer[] {2735, 5015},
				new Integer[] {4970, 7250},
				new Integer[] {7205, 9485},
				new Integer[] {9440, 11720},
				new Integer[] {11675, 13955},
				new Integer[] {13910, 16190}
			},
			"6", new Integer[][] {
				new Integer[] {3695, 5975},
				new Integer[] {6890, 9170},
				new Integer[] {10085, 12365},
				new Integer[] {13280, 15560},
				new Integer[] {16475, 18755},
				new Integer[] {19670, 21950}
			},
			"8", new Integer[][] {
				new Integer[] {4655, 6935},
				new Integer[] {8810, 11090},
				new Integer[] {12965, 15245},
				new Integer[] {17120, 19400},
				new Integer[] {21275, 23555},
				new Integer[] {25430, 27710}
			});

		if(!data.containsKey(sanitized_hours)) {
			return null;
		}

		Integer[][] hours_element = data.get(sanitized_hours);
		Integer[] prices = null;
		try {
			prices = hours_element[Integer.parseInt(sanitized_people) - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}

		return realEstimateString(prices[1], prices[0], sanitized_people, sanitized_hours);
	}

	private String realEstimateString(int realPrice, int discount, String people, String hours) {
		return "This job will cost " + realPrice + " Kronor. But the customer only pays the discount price of " + discount + " Kronor. It will take " + hours + " hours with " + people  + " treeple.";
	}

  public String toString() {
		String[] args = msg.split(" ");
		if(args.length < 3) {
    	return random();
		}
		String realEstimate = realEstimate(args[1], args[2]);
		if(realEstimate == null) {
			return random();
		} else {
			return realEstimate;
		}
  }
}
