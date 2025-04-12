package cz.honza.pocasi.metoda;

import cz.honza.pocasi.kalendar.Utils;


public abstract class ObecnaMetoda implements Metoda {
	
	protected static final int DAY = 15;
	protected static final int MONTH = 5;
	protected static final int EXTRA_DAYS = 4;
	protected static final int YEAR_START = 2016;
	protected static final double GLOBAL_WARMING = 0.06;

	
	protected int calculateYearStart(int year, int month) {
		if (MONTH > 1 && MONTH < 12) return year;
		if (MONTH == 1 && DAY > EXTRA_DAYS) return year;
		if (MONTH == 12 && DAY < 32 - EXTRA_DAYS) return year;
		if (month == 1) {
			return year - 1;
		} else {
			return year;
		}
	}
	
	protected int calculateYearEnd(int year, int month) {
		if (MONTH > 1 && MONTH < 12) return year;
		if (MONTH == 1 && DAY > EXTRA_DAYS) return year;
		if (MONTH == 12 && DAY < 32 - EXTRA_DAYS) return year;
		if (month == 12) {
			return year + 1;
		} else {
			return year;
		}
	}
	
	protected int calculateMonthStart() {
		if (DAY > EXTRA_DAYS) return MONTH;
		int month = MONTH - 1;
		if (month == 0) month = 12;
		return month;
	}
	
	protected int calculateMonthEnd(int year) {
		if (DAY + EXTRA_DAYS <= Utils.maxDaysOfMonth(year, MONTH)) return MONTH;
		int month = MONTH + 1;
		if (month == 13) month = 1;
		return month;
	}
	
	
	protected int calculateDayStart(int year) {
		if (DAY > EXTRA_DAYS) return DAY - EXTRA_DAYS;
		return Utils.maxDaysOfMonth(year, MONTH - 1) - EXTRA_DAYS + 1;
	}
	
	protected int calculateDayEnd(int year) {
		int diff = DAY + EXTRA_DAYS - Utils.maxDaysOfMonth(year, MONTH) ;
		if (diff <= 0) return DAY + EXTRA_DAYS;
		return diff;
	}

}
