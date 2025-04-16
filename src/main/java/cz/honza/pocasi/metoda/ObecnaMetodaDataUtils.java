package cz.honza.pocasi.metoda;

import java.util.ArrayList;
import java.util.List;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.metoda.ObecnaMetoda.Settings;

public class ObecnaMetodaDataUtils {
	
	public static void otepliData(List<Radek> historickaData, Radek zadani,Settings settings) {
		for (Radek radek : historickaData) {
			final int rok_start = ObecnaMetodaCalendarUtils.calculateYearStart(radek.rok, radek.mesic, radek.den, settings.extraDays);
	        radek.teplota = radek.teplota + settings.globalWarming * (zadani.rok - rok_start);
		}
	}

	public static List<Radek> filtrujData(List<Radek> historickaData, Radek zadani, Settings settings) {
		
		List<Radek> filtr = new ArrayList<Radek>();
		for (Radek radek : historickaData) {
	        
	        if (radek.rok < settings.yearStart) continue;
	        if (radek.rok == settings.yearStart && radek.mesic == 1 && radek.den <= 2 * settings.yearStart + 1) continue;
		        		        
	        final int den_start = ObecnaMetodaCalendarUtils.calculateDayStart(zadani.rok, zadani.mesic, zadani.den, settings.extraDays);
	        final int den_konec = ObecnaMetodaCalendarUtils.calculateDayEnd(zadani.rok, zadani.mesic, zadani.den, settings.extraDays);
	        final int mesic_start = ObecnaMetodaCalendarUtils.calculateMonthStart(zadani.mesic, zadani.den, settings.extraDays);
	        final int mesic_konec = ObecnaMetodaCalendarUtils.calculateMonthEnd(zadani.rok, zadani.mesic, zadani.den, settings.extraDays);
	        final int rok_start = ObecnaMetodaCalendarUtils.calculateYearStart(radek.rok, zadani.mesic, zadani.den, settings.extraDays);
	        final int rok_konec = ObecnaMetodaCalendarUtils.calculateYearEnd(radek.rok, zadani.mesic, zadani.den, settings.extraDays);
		     
		        
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
	
	


}
