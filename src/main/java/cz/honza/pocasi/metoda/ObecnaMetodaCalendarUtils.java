package cz.honza.pocasi.metoda;

import cz.honza.pocasi.kalendar.Utils;

public class ObecnaMetodaCalendarUtils {
	public static int calculateYearStart(int yearHistorical, int month, int day, int extraDays) {
		if (month > 1 && month < 12) return yearHistorical;
		if (month == 1 && day > extraDays) return yearHistorical;
		if (month == 12 && day < 32 - extraDays) return yearHistorical;
		if (month == 1) {
			return yearHistorical - 1;
		} else {
			return yearHistorical;
		}
	}
	
	public static int calculateYearEnd(int yearHistorical, int month, int day, int extraDays) {
		if (month > 1 && month < 12) return yearHistorical;
		if (month == 1 && day > extraDays) return yearHistorical;
		if (month == 12 && day < 32 - extraDays) return yearHistorical;
		if (month == 12) {
			return yearHistorical + 1;
		} else {
			return yearHistorical;
		}
	}
	
	public static int calculateMonthStart(int month, int day, int extraDays) {
		if (day > extraDays) return month;
		int month2 = month - 1;
		if (month2 == 0) month2 = 12;
		return month2;
	}
	
	public static int calculateMonthEnd(int year, int month, int day, int extraDays) {
		if (day + extraDays <= Utils.maxDaysOfMonth(year, month)) return month;
		int month2 = month + 1;
		if (month2 == 13) month2 = 1;
		return month2;
	}
	
	
	public static int calculateDayStart(int year, int month, int day, int extraDays) {
		if (day > extraDays) return day - extraDays;
		return Utils.maxDaysOfMonth(year, month - 1) - extraDays + 1;
	}
	
	public static int calculateDayEnd(int year, int month, int day, int extraDays) {
		int diff = day + extraDays - Utils.maxDaysOfMonth(year, month) ;
		if (diff <= 0) return day + extraDays;
		return diff;
	}


}
