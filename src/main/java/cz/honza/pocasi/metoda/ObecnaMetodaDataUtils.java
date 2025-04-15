package cz.honza.pocasi.metoda;

import java.util.ArrayList;
import java.util.List;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;
import cz.honza.pocasi.metoda.ObecnaMetoda.Settings;

public class ObecnaMetodaDataUtils {
	
	public static void otepliData(List<Radek> historickaData, Radek zadani,Settings settings) {
		for (Radek radek : historickaData) {
			final int rok_start = calculateYearStart(radek.rok, radek.mesic, radek.den, settings.extraDays);
	        radek.teplota = radek.teplota + settings.globalWarming * (zadani.rok - rok_start);
		}
	}

	public static List<Radek> filtrujData(List<Radek> historickaData, Radek zadani, Settings settings) {
		
		List<Radek> filtr = new ArrayList<Radek>();
		for (Radek radek : historickaData) {
	        
	        if (radek.rok < settings.yearStart) continue;
	        if (radek.rok == settings.yearStart && radek.mesic == 1 && radek.den <= 2 * settings.yearStart + 1) continue;
		        		        
	        final int den_start = calculateDayStart(zadani.rok, zadani.mesic, zadani.den, settings.extraDays);
	        final int den_konec = calculateDayEnd(zadani.rok, zadani.mesic, zadani.den, settings.extraDays);
	        final int mesic_start = calculateMonthStart(zadani.mesic, zadani.den, settings.extraDays);
	        final int mesic_konec = calculateMonthEnd(zadani.rok, zadani.mesic, zadani.den, settings.extraDays);
	        final int rok_start = calculateYearStart(radek.rok, zadani.mesic, zadani.den, settings.extraDays);
	        final int rok_konec = calculateYearEnd(radek.rok, zadani.mesic, zadani.den, settings.extraDays);
		     
		        
	        if (radek.rok < rok_start) continue;
		        
	        if (radek.rok == rok_start) {
	        	if (radek.mesic < mesic_start) continue;
	        	if (radek.mesic == mesic_start) {
	        		if (radek.den < den_start) continue;
	        	}
	        }
	        
	        if (radek.rok > rok_konec) continue;
		        
	        if (radek.rok == rok_konec) {
	        	if (radek.mesic > mesic_konec) continue;
	        	if (radek.mesic == mesic_konec) {
	        		if (radek.den > den_konec) continue;
	        	}
	        }
	        filtr.add(radek);
		}
		return filtr;
	}
	
	protected static int calculateYearStart(int year, int month, int day, int extraDays) {
		if (month > 1 && month < 12) return year;
		if (month == 1 && day > extraDays) return year;
		if (month == 12 && day < 32 - extraDays) return year;
		if (month == 1) {
			return year - 1;
		} else {
			return year;
		}
	}
	
	protected static int calculateYearEnd(int year, int month, int day, int extraDays) {
		if (month > 1 && month < 12) return year;
		if (month == 1 && day > extraDays) return year;
		if (month == 12 && day < 32 - extraDays) return year;
		if (month == 12) {
			return year + 1;
		} else {
			return year;
		}
	}
	
	protected static int calculateMonthStart(int month, int day, int extraDays) {
		if (day > extraDays) return month;
		int month2 = month - 1;
		if (month2 == 0) month2 = 12;
		return month2;
	}
	
	protected static int calculateMonthEnd(int year, int month, int day, int extraDays) {
		if (day + extraDays <= Utils.maxDaysOfMonth(year, month)) return month;
		int month2 = month + 1;
		if (month2 == 13) month2 = 1;
		return month2;
	}
	
	
	protected static int calculateDayStart(int year, int month, int day, int extraDays) {
		if (day > extraDays) return day - extraDays;
		return Utils.maxDaysOfMonth(year, month - 1) - extraDays + 1;
	}
	
	protected static int calculateDayEnd(int year, int month, int day, int extraDays) {
		int diff = day + extraDays - Utils.maxDaysOfMonth(year, month) ;
		if (diff <= 0) return day + extraDays;
		return diff;
	}



}
