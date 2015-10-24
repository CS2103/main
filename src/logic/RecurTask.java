package logic;

import org.joda.time.DateTime;

public class RecurTask extends Task {
	private static final String tag_weekly = "weekly";
	private static final String tag_monthly = "monthly";
	private static final String tag_daily = "daily";
	private static final String tag_yearly = "yearly";
	
	
	private DateTime[] recurDate = new DateTime[366];
	private DateTime recurStart;
	private DateTime recurEnd;
	private String recurTag; 
	
	public RecurTask(String title, DateTime startingTime, DateTime endingTime, DateTime recurEnding, String recurTag) throws InvalidTimeException{
		super(title,startingTime, endingTime);
		this.recurEnd = recurEnding;
		DateTime begin = new DateTime(startingTime);
		int index = 1;
		this.recurTag = recurTag;
		recurDate[0] = begin;
		switch (recurTag){
		case tag_weekly:
			while(begin.plusWeeks(1).isBefore(recurEnding)){
				recurDate[index] = begin;
				index++;
		
			}
			break;
		case tag_daily:
			while(begin.plusDays(1).isBefore(recurEnding)){
				recurDate[index] = begin;
				index++;
			}
			break;
		case tag_monthly:
			while(begin.plusMonths(1).isBefore(recurEnding)){
				recurDate[index] = begin;
				index++;
			}
			break;
		case tag_yearly:
			while(begin.plusMonths(1).isBefore(recurEnding)){
				recurDate[index] = begin;
				index++;
			}
			break;
		}
		
		
	}
	
	public boolean isRecur(DateTime today){
	
		for(DateTime d:recurDate){
			if(today.getDayOfYear() == d.getDayOfYear()){
				return true;
			}
		}
		return false;
	}

}
