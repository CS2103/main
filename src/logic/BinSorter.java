//@@author A0129708
package logic;

import java.util.ArrayList;

import application.Constants;

public class BinSorter {

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
	
	
	public ArrayList<Task> sortArrayByTime(ArrayList<Task> inboxArr){
		System.out.println("Is sorting");
		ArrayList<Task> result = new ArrayList<Task>();
		ArrayList<Task> normalTask = new ArrayList<Task>();
		ArrayList<Task> timeUndefined = new ArrayList<Task>();
		ArrayList<Task> recurList = new ArrayList<Task>();
		if(inboxArr.size() <= 1){
			return inboxArr;
		}
		for(int m = 0; m < inboxArr.size(); m++){
			switch(inboxArr.get(m).getType()){
			case Constants.TYPE_RECUR:
				recurList.add(inboxArr.get(m));
				break;
			case Constants.TYPE_FLOATING:
				timeUndefined.add(inboxArr.get(m));
				break;
			default:
				normalTask.add(inboxArr.get(m));
			}
		}
		timeUndefined = sortArrayByAlpha(timeUndefined);

		for(int i = 1; i < normalTask.size() - 1; i++){
			boolean isSorted = true;
			for (int m = 1; m < inboxArr.size() - i; m++) {
				if(normalTask.get(i).getEndingTime().isAfter(normalTask.get(i+1).getEndingTime())){
					Task buffer = normalTask.get(i);
					normalTask.set(i, normalTask.get(i+1));
					normalTask.set(i+1, buffer);
					isSorted = false;
					break;
				}	
			}
			if(isSorted){
				break;
			}
		}
		
		for(int i = 1; i < recurList.size() - 1; i++){
			boolean isSorted = true;
			for (int m = 1; m < recurList.size() - i; m++) {
				
				if(recurList.get(i).getEndingTime().getMinuteOfDay() > normalTask.get(i+1).getEndingTime().getMinuteOfDay()){
					Task buffer = recurList.get(i);
					recurList.set(i, recurList.get(i+1));
					recurList.set(i+1, buffer);
					isSorted = false;
					break;
				}	
			}
			if(isSorted){
				break;
			}
		}

		result.addAll(normalTask);
		result.addAll(recurList);
		result.addAll(timeUndefined);
		return result;
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
