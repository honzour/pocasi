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
	
	protected static final int DAY = 15;
	protected static final int MONTH = 5;
	protected static final int EXTRA_DAYS = 4;
	protected static final int YEAR_START = 2016;
	protected static final double GLOBAL_WARMING = 0.06;
	
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
			final int rok_start = calculateYearStart(radek.rok, radek.mesic);
	        radek.teplota = radek.teplota + GLOBAL_WARMING * (zadani.rok - rok_start);
		}
	}

	protected List<Radek> filtrujData(List<Radek> historickaData) {
		
		List<Radek> filtr = new ArrayList<Radek>();
		for (Radek radek : historickaData) {
	        
	        if (radek.rok < YEAR_START) continue;
	        if (radek.rok == YEAR_START && radek.mesic == 1 && DAY <= 2 * EXTRA_DAYS + 1) continue;
		        		        
	        final int den_start = calculateDayStart(radek.rok);
	        final int den_konec = calculateDayEnd(radek.rok);
	        final int mesic_start = calculateMonthStart();
	        final int mesic_konec = calculateMonthEnd(radek.rok);
	        final int rok_start = calculateYearStart(radek.rok, radek.mesic);
	        final int rok_konec = calculateYearEnd(radek.rok, radek.mesic);
		     
		        
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
		historickaData = filtrujData(historickaData);
		otepliData(historickaData, zadani);
		return historickaData;
	}
	
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
