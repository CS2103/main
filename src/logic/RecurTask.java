package logic;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class RecurTask extends Task {
	private static final String tag_weekly = "weekly";
	private static final String tag_monthly = "monthly";
	private static final String tag_daily = "daily";
	private static final String tag_yearly = "yearly";
	
	
	private ArrayList<DateTime> recurDate = new ArrayList<DateTime>();
	private ArrayList<DateTime> recurDone = new ArrayList<DateTime>();
	private DateTime recurStart;
	private DateTime recurEnd;
	private String recurTag; 
	
	public RecurTask(String title, DateTime startingTime, DateTime endingTime, DateTime recurEnding, String recurTag) throws InvalidTimeException{
		super(title,startingTime, endingTime);
		this.recurEnd = recurEnding;
		DateTime begin = new DateTime(startingTime);
		
		this.recurTag = recurTag;
		recurDate.add(begin);
		switch (recurTag){
		case tag_weekly:
			while(begin.plusWeeks(1).isBefore(recurEnding)){
				begin = new DateTime(begin.plusWeeks(1));
				recurDate.add(begin);
			
			}
			break;
		case tag_daily:
			while(begin.plusDays(1).isBefore(recurEnding)){
				begin = new DateTime(begin.plusDays(1));
				recurDate.add(begin);	
			}
			break;
		case tag_monthly:
			while(begin.plusMonths(1).isBefore(recurEnding)){
				begin = new DateTime(begin.plusMonths(1));
				recurDate.add(begin);
			}
			break;
		case tag_yearly:
			while(begin.plusMonths(1).isBefore(recurEnding)){
				begin = new DateTime(begin.plusYears(1));
				recurDate.add(begin);
			}
			break;
		}
		
		
	}
	
	public ArrayList<DateTime> returnTime(){
		return recurDate;
	}
	
	public void mark(DateTime date){
		for(DateTime t:recurDate){
			if (t.getDayOfYear() == date.getDayOfYear()){
				recurDone.add(t);
				recurDate.remove(t);
			}
		}	
	}
	
	public boolean mark(){
		DateTime now = new DateTime();
		for(DateTime t:recurDate){
			if (t.getDayOfYear() == now.getDayOfYear()){
				recurDone.add(t);
				recurDate.remove(t);
				return true;
			}
		}	
		return false;
	}
	
	public void unmark(DateTime date){
		for(DateTime t:recurDone){
			if(t.getDayOfYear() == date.getDayOfYear()){
				recurDate.add(t);
				recurDone.remove(t);
			}
		}
	}
	
	public boolean unmark(){
		DateTime now = new DateTime();
		for(DateTime t:recurDate){
			if (t.getDayOfYear() == now.getDayOfYear()){
				recurDate.add(t);
				recurDone.remove(t);
				return true;
			}
		}	
		return false;
	}
	//if the return year is 0, the date does not exist
	public DateTime delete(DateTime date){
		DateTime del = new DateTime(0,1,1,0,0); 
		for(DateTime t:recurDate){
			if(t.getDayOfYear() == date.getDayOfYear()){
				recurDate.remove(t);
				del = t;
			}
		}
		return del;
	}
	
	public boolean includeDate(DateTime date){
		for(DateTime t: recurDate){
			if(t.equals(date)){
				return true;
			}
		}
		return false;
	}
	
	public void clearRecurDone(){
		recurDone.clear();
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
