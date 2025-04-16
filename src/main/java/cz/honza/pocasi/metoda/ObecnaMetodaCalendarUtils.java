package cz.honza.pocasi.metoda;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;

public class ObecnaMetodaCalendarUtils {
	public static int calculateYearStart(Radek historickeDato, Radek zadani, int extraDays) {
		if (zadani.mesic > 1) return historickeDato.rok;
		if (zadani.mesic == 1 && zadani.den > extraDays) return historickeDato.rok;
		throw new RuntimeException("TODO");
	}
	
	public static int calculateYearEnd(Radek historickeDato, Radek zadani, int extraDays) {
		if (zadani.mesic < 12) return historickeDato.rok;
		if (zadani.mesic == 12 && zadani.den < 32 - extraDays) return historickeDato.rok;
		throw new RuntimeException("TODO");
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
