package logic;

import java.util.ArrayList;

import application.Constants;

public class SortTasks {

	public boolean includeAllWords(String[] keywords, String[] title) {
		boolean isFound;
		for (String key : keywords) {
			isFound = false;
			for (String til : title) {
				if (key.equalsIgnoreCase(til)) {
					isFound = true;
				}
			}
			if (isFound == false) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Task> sortArrayByTime(ArrayList<Task> inboxArr) {
		ArrayList<Task> timeUndefined = new ArrayList<Task>();
		ArrayList<Task> recurArray = new ArrayList<Task>();
		if (inboxArr.size() <= 1) {
			return inboxArr;
		}

		for (int m = 1; m < inboxArr.size() - 1; m++) {
			boolean isSorted = true;
			for (int i = 0, len = inboxArr.size() - m; (i < len); i++) {
				if (inboxArr.get(i).getType().equals(Constants.TYPE_FLOATING)) {
					timeUndefined.add(inboxArr.get(i));
					inboxArr.remove(inboxArr.get(i));
					i--;// TODO
					len--;
					isSorted = false;
					break;
				}
				if (inboxArr.get(i).getType().equals(Constants.TYPE_RECUR)) {
					recurArray.add(inboxArr.get(i));
					inboxArr.remove(inboxArr.get(i));
					i--;// TODO
					len--;
					isSorted = false;
					break;
				}
				if (inboxArr.get(i).getEndingTime().compareTo(inboxArr.get(i + 1).getEndingTime()) > 0) {
					Task buffer = inboxArr.get(i);
					inboxArr.set(i, inboxArr.get(i + 1));
					inboxArr.set(i + 1, buffer);
					isSorted = false;
					break;
				}
			}
			if (isSorted) {
				timeUndefined = sortArrayByAlpha(timeUndefined);
				recurArray = sortArrayByAlpha(recurArray);
				inboxArr.addAll(timeUndefined);
				return inboxArr;
			}
		}

		timeUndefined = sortArrayByAlpha(timeUndefined);
		recurArray = sortArrayByAlpha(recurArray);
		inboxArr.addAll(timeUndefined);
		return inboxArr;
	}

	public ArrayList<Task> sortArrayByAlpha(ArrayList<Task> inboxArr) {
		for (int m = 1; m < inboxArr.size(); m++) {
			boolean isSorted = true;
			for (int i = 0; (i < inboxArr.size() - m); i++) {
				boolean isCompared = false;
				for (int j = 0; (j < inboxArr.get(i).getTitle().length() - 1) && (isCompared != true); j++) {
					if (inboxArr.get(i).getTitle().charAt(j) > inboxArr.get(i + 1).getTitle().charAt(j)) {
						Task buffer = inboxArr.get(i);
						inboxArr.set(i, inboxArr.get(i + 1));
						inboxArr.set(i + 1, buffer);
						isSorted = false;
						isCompared = true;
						break;
					} else if (inboxArr.get(i).getTitle().charAt(j) < inboxArr.get(i + 1).getTitle().charAt(j)) {
						isCompared = true;
						break;
					}
				}

			}
			if (isSorted == true) {
				return inboxArr;
			}
		}
		return inboxArr;
	}

}
