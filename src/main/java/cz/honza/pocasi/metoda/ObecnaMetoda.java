package cz.honza.pocasi.metoda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;
import cz.honza.pocasi.matematika.Bod2D;
import cz.honza.pocasi.matematika.Polynom;
import cz.honza.pocasi.matematika.PolynomialRegressionNoLib;


public abstract class ObecnaMetoda implements Metoda {
	
	public static class Settings {
		public int extraDays;
		public int yearStart;
		public double globalWarming;

		public Settings(int extraDays, int yearStart, double globalWarming) {
			this.extraDays = extraDays;
			this.yearStart = yearStart;
			this.globalWarming = globalWarming;
		}
	}
	
	protected Settings settings;
	
	public ObecnaMetoda(Settings settings) {
		this.settings = settings;
	}
	
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		historickaData = upravData(historickaData, zadani);
		final List<Double> teploty = historickaData.stream().map(radek -> radek.teplota).collect(Collectors.toList());
		return spocitejSUpravenymiDaty(zadani, teploty);
	}
	
	public abstract Vysledek spocitejSUpravenymiDaty(Radek zadani, List<Double> historickaData);
	
	private static List<Bod2D> regresniBody(List<Radek> teploty) {
		return teploty.stream()
				.map(rad -> 
					new Bod2D(Utils.dayIndexInYear(rad.rok, rad.mesic, rad.den), rad.teplota))
				.collect(Collectors.toList());
		
	}
	
	private static Polynom polynomRoku(List<Radek> teploty) {
		return PolynomialRegressionNoLib.fitPolynomial(regresniBody(teploty), 36);
		
	}
	
	protected List<Radek> kopiruj(List<Radek> historickaData) {
		return historickaData.stream().map(r -> r.copy()).collect(Collectors.toList());
	}
	
	protected void prepoctiDataNaDen(List<Radek> historickaData, Radek zadani) {
		Polynom p = polynomRoku(historickaData);
		double denZadani = Utils.dayIndexInYear(zadani.rok, zadani.mesic, zadani.den);
		double teplotaDne = p.f(denZadani);
		for (Radek radek : historickaData) {
			double denCoMenim = Utils.dayIndexInYear(radek.rok, radek.mesic, radek.den);	
	        radek.teplota = radek.teplota + teplotaDne - p.f(denCoMenim);
		}
	}

	
	protected void otepliData(List<Radek> historickaData, Radek zadani) {
		for (Radek radek : historickaData) {
			final int rok_start = calculateYearStart(radek.rok, radek.mesic, radek.den);
	        radek.teplota = radek.teplota + settings.globalWarming * (zadani.rok - rok_start);
		}
	}

	protected List<Radek> filtrujData(List<Radek> historickaData, Radek zadani) {
		
		List<Radek> filtr = new ArrayList<Radek>();
		for (Radek radek : historickaData) {
	        
	        if (radek.rok < settings.yearStart) continue;
	        if (radek.rok == settings.yearStart && radek.mesic == 1 && radek.den <= 2 * settings.yearStart + 1) continue;
		        		        
	        final int den_start = calculateDayStart(zadani.rok, zadani.mesic, zadani.den);
	        final int den_konec = calculateDayEnd(zadani.rok, zadani.mesic, zadani.den);
	        final int mesic_start = calculateMonthStart(zadani.mesic, zadani.den);
	        final int mesic_konec = calculateMonthEnd(zadani.rok, zadani.mesic, zadani.den);
	        final int rok_start = calculateYearStart(radek.rok, zadani.mesic, zadani.den);
	        final int rok_konec = calculateYearEnd(radek.rok, zadani.mesic, zadani.den);
		     
		        
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
	
	protected List<Radek> upravData(List<Radek> historickaData, Radek zadani) {
		historickaData = kopiruj(historickaData);
		prepoctiDataNaDen(historickaData, zadani);
		historickaData = filtrujData(historickaData, zadani);
		otepliData(historickaData, zadani);
		return historickaData;
	}
	
	protected int calculateYearStart(int year, int month, int day) {
		if (month > 1 && month < 12) return year;
		if (month == 1 && day > settings.extraDays) return year;
		if (month == 12 && day < 32 - settings.extraDays) return year;
		if (month == 1) {
			return year - 1;
		} else {
			return year;
		}
	}
	
	protected int calculateYearEnd(int year, int month, int day) {
		if (month > 1 && month < 12) return year;
		if (month == 1 && day > settings.extraDays) return year;
		if (month == 12 && day < 32 - settings.extraDays) return year;
		if (month == 12) {
			return year + 1;
		} else {
			return year;
		}
	}
	
	protected int calculateMonthStart(int month, int day) {
		if (day > settings.extraDays) return month;
		int month2 = month - 1;
		if (month2 == 0) month2 = 12;
		return month2;
	}
	
	protected int calculateMonthEnd(int year, int month, int day) {
		if (day + settings.extraDays <= Utils.maxDaysOfMonth(year, month)) return month;
		int month2 = month + 1;
		if (month2 == 13) month2 = 1;
		return month2;
	}
	
	
	protected int calculateDayStart(int year, int month, int day) {
		if (day > settings.extraDays) return day - settings.extraDays;
		return Utils.maxDaysOfMonth(year, month - 1) - settings.extraDays + 1;
	}
	
	protected int calculateDayEnd(int year, int month, int day) {
		int diff = day + settings.extraDays - Utils.maxDaysOfMonth(year, month) ;
		if (diff <= 0) return day + settings.extraDays;
		return diff;
	}

}
