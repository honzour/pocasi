package cz.honza.pocasi.metoda;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;
import cz.honza.pocasi.matematika.Bod2D;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.matematika.FunkceSDerivaci;
import cz.honza.pocasi.matematika.Polynom;
import cz.honza.pocasi.matematika.PolynomialRegressionNoLib;
import cz.honza.pocasi.metoda.ObecnaMetoda.Settings;

public class ObecnaMetodaDataUtils {
	
	public static class RokTeplota {
		
		public RokTeplota(int rok, double teplota) {
			super();
			this.rok = rok;
			this.teplota = teplota;
		}
		public int rok;
		public double teplota;
	}
	
	public static void otepliData(List<Radek> historickaData, Funkce oteplovani) {
		for (Radek radek : historickaData) {
	        radek.teplota = radek.teplota + oteplovani.f(radek.datum.getYear() + radek.datum.getDayOfYear() / 365.15);
		}
	}

	public static List<Radek> filtrujData(List<Radek> historickaData, Radek zadani, Settings settings) {
		return historickaData.stream().filter(radek -> acceptRadek(radek, zadani, settings)).collect(Collectors.toList());
	}
	
	public static List<Radek> kopiruj(List<Radek> historickaData) {
		return historickaData.stream().map(r -> r.copy()).collect(Collectors.toList());
	}
	
	public static List<RokTeplota> spocitejRocniPrumery(List<Radek> historickaData) {
		final Map<Integer, List<Radek>> roky = historickaData.stream().collect(Collectors.groupingBy(r -> r.datum.getYear()));
		return roky.keySet().stream().sorted().map(
				rok ->
					new RokTeplota(
						rok,
						roky.get(rok).stream().mapToDouble(radek -> radek.teplota).average().orElse(0)
					)
				).collect(Collectors.toList());
	}
	
	public static Polynom spocitejPolynomMaxim(List<RokTeplota> teploty, int stupen) {
		return PolynomialRegressionNoLib.fitPolynomial(
				teploty.stream().map(rt -> new Bod2D(rt.rok, rt.teplota)).collect(Collectors.toList()),
				stupen
			);
	}
	
	public static Funkce spocitejLokaliOteplovani(List<Radek> historickaData, Radek zadani) {
		final List<RokTeplota> prumery = spocitejRocniPrumery(historickaData);
		final FunkceSDerivaci polynomMaxim = spocitejPolynomMaxim(prumery, 20);
		final int posledniRok = historickaData.stream().map(radek -> radek.datum.getYear()).max(Comparator.naturalOrder()).orElse(0);
		final double linearniOteplovani = polynomMaxim.derivace(posledniRok);
		final double rokZadadani = zadani.datum.getYear() + zadani.datum.getDayOfYear() / 365.15 - 0.5; 
		
		final Funkce polynomialniOteplovani = new Funkce() {
			
			@Override
			public double f(double rok) {
				if (rokZadadani < posledniRok) {
					return polynomMaxim.f(posledniRok) - polynomMaxim.f(rok);
				}
				return polynomMaxim.f(posledniRok) - polynomMaxim.f(rok) + linearniOteplovani * (rokZadadani - posledniRok); 

			}
		}; 
		
		return polynomialniOteplovani;
	}
	
	public static void prepoctiDataNaDen(List<Radek> historickaData, Radek zadani) {
		final Polynom p = polynomRoku(historickaData);
		double denZadani = Utils.dayIndexInYear(zadani.datum);
		double teplotaDne = p.f(denZadani);
		for (Radek radek : historickaData) {
			double denCoMenim = Utils.dayIndexInYear(radek.datum);	
	        radek.teplota = radek.teplota + teplotaDne - p.f(denCoMenim);
		}
	}
	
	private static List<Bod2D> regresniBody(List<Radek> teploty) {
		return teploty.stream()
				.map(rad -> 
					new Bod2D(Utils.dayIndexInYear(rad.datum), rad.teplota))
				.collect(Collectors.toList());
		
	}
	
	public static Polynom polynomRoku(List<Radek> teploty) {
		return PolynomialRegressionNoLib.fitPolynomial(regresniBody(teploty), 36);
		
	}
	
	public static List<Radek> upravData(List<Radek> historickaData, Radek zadani, Settings settings) {
		historickaData = kopiruj(historickaData);
		final Funkce oteplovani = spocitejLokaliOteplovani(historickaData, zadani);
		prepoctiDataNaDen(historickaData, zadani);
		historickaData = filtrujData(historickaData, zadani, settings);
		otepliData(historickaData, oteplovani);
		return historickaData;
	}
	
	private static boolean isBetweenDaysMonths(LocalDate from, LocalDate to, LocalDate datum) {
		final int fromDay = from.getDayOfYear();
		final int toDay = to.getDayOfYear();
		final int datumDay = datum.getDayOfYear();
		
		if (fromDay <= toDay) {
			return fromDay <= datumDay && datumDay <= toDay;
		} else {
			// Kolem silvestra
			return datumDay >= fromDay || datumDay <= toDay;
		}
	}
	
	public static boolean acceptRadek(Radek historickeDato, Radek zadani, Settings settings) {
		// Úplně ignorujeme řádky před nakonfigurovaným rokem kvůli globálnímu oteplování 
        if (historickeDato.datum.getYear() < settings.yearStart) return false;
     // Úplně ignorujeme řádky před nakonfigurovaným rokem kvůli globálnímu oteplování 
        if (historickeDato.datum.getYear() > settings.yearEnd) return false;
        final LocalDate from = zadani.datum.minusDays(settings.extraDays);
        final LocalDate to = zadani.datum.plusDays(settings.extraDays);
        return isBetweenDaysMonths(from, to, historickeDato.datum);
	}
}
