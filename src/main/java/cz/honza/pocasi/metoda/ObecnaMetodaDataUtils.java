package cz.honza.pocasi.metoda;

import java.time.LocalDate;
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
	
	private static boolean isBetweenDaysMonths(LocalDate from, LocalDate to, LocalDate datum) {
		final int fromDay = from.getDayOfYear();
		final int toDay = to.getDayOfYear();
		final int datumDay = datum.getDayOfYear();
		
		if (fromDay <= toDay) {
			return fromDay <= datumDay && datumDay <= toDay;
		} else {
			// Kolem silvestra
			return !(toDay < datumDay && fromDay > toDay);
		}
	}
	
	public static boolean acceptRadek(Radek historickeDato, Radek zadani, Settings settings) {
		// Úplně ignorujeme řádky před nakonfigurovaným rokem kvůli globálnímu oteplování 
        if (historickeDato.datum.getYear() < settings.yearStart) return false;
        final LocalDate from = zadani.datum.minusDays(settings.extraDays);
        final LocalDate to = zadani.datum.plusDays(settings.extraDays);
        return isBetweenDaysMonths(from, to, historickeDato.datum);
	}
}
