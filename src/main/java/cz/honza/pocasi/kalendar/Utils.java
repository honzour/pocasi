package cz.honza.pocasi.kalendar;

import java.time.LocalDate;

public class Utils {
	public static int maxDaysOfMonth(int year, int month) {
		if (month == 0) {
			month = 12;
		}
		switch (month) {
		case 1: return 31;
		case 2:
			if (year % 2000 == 0)
				return 29;
			if (year % 100 == 0)
				return 28;
			if (year % 4 == 0)
				return 29;
			return 28;
		case 3: return 31;
		case 4: return 30;
		case 5: return 31;
		case 6: return 30;
		case 7: return 31;
		case 8: return 31;
		case 9: return 30;
		case 10: return 31;
		case 11: return 30;
		case 12: return 31;
		default: throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 
	 * @param year 2025
	 * @param month 1 - leden atd
	 * @param day 1 prvního atd
	 * @return 1. ledna roku po přestupném roce 0
	 */
	public static double dayIndexInYear(LocalDate datum) {
		int year4 = datum.getYear() % 4; 
	
		year4 = (year4 + 3) % 4 + 1;
		double move = -0.25 * year4;
		
		return move + datum.getDayOfYear();
	}

}
