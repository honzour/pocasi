package cz.honza.pocasi.metoda;

import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.metoda.ObecnaMetoda.Settings;

public class ObecnaMetodaDataUtils {
	
	public static void otepliData(List<Radek> historickaData, Radek zadani, Settings settings) {
		for (Radek radek : historickaData) {
	        radek.teplota = radek.teplota + settings.globalWarming * (zadani.datum.getYear() - radek.datum.getYear());
		}
	}

	public static List<Radek> filtrujData(List<Radek> historickaData, Radek zadani, Settings settings) {
		return historickaData.stream().filter(radek -> acceptRadek(radek, zadani, settings)).collect(Collectors.toList());
	}
	
	public static boolean acceptRadek(Radek historickeDato, Radek zadani, Settings settings) {
		// Úplně ignorujeme řádky před nakonfigurovaným rokem kvůli globálnímu oteplování 
        if (historickeDato.datum.getYear() < settings.yearStart) return false;
        
        final int den_start = ObecnaMetodaCalendarUtils.calculateDayStart(historickeDato.datum.getYear(), zadani.datum.getMonthValue(), zadani.datum.getDayOfMonth(), settings.extraDays);
        final int den_konec = ObecnaMetodaCalendarUtils.calculateDayEnd(historickeDato.datum.getYear(), zadani.datum.getMonthValue(), zadani.datum.getDayOfMonth(), settings.extraDays);
        final int mesic_start = ObecnaMetodaCalendarUtils.calculateMonthStart(zadani.datum.getMonthValue(), zadani.datum.getDayOfMonth(), settings.extraDays);
        final int mesic_konec = ObecnaMetodaCalendarUtils.calculateMonthEnd(historickeDato.datum.getYear(), zadani.datum.getMonthValue(), zadani.datum.getDayOfMonth(), settings.extraDays);
        final int rok_start = ObecnaMetodaCalendarUtils.calculateYearStart(historickeDato, zadani, settings.extraDays);
        final int rok_konec = ObecnaMetodaCalendarUtils.calculateYearEnd(historickeDato, zadani, settings.extraDays);
	        
        if (historickeDato.datum.getYear() < rok_start) return false;
	        
        if (historickeDato.datum.getYear() == rok_start) {
        	if (historickeDato.datum.getMonthValue() < mesic_start) return false;
        	if (historickeDato.datum.getMonthValue() == mesic_start) {
        		if (historickeDato.datum.getDayOfMonth() < den_start) return false;
        	}
        }
        
        if (historickeDato.datum.getYear() > rok_konec) return false;
	        
        if (historickeDato.datum.getYear() == rok_konec) {
        	if (historickeDato.datum.getMonthValue() > mesic_konec) return false;
        	if (historickeDato.datum.getMonthValue() == mesic_konec) {
        		if (historickeDato.datum.getDayOfMonth() > den_konec) return false;
        	}
        }
        return true;
	}
}
