package cz.honza.pocasi;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.DataReader;
import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;
import cz.honza.pocasi.matematika.Polynom;
import cz.honza.pocasi.matematika.Regrese;
import cz.honza.pocasi.matematika.rozdeleni.Normal;

public class Main {

	private static final double TRESHOLD = 19.5;
	private static final int YEAR = 2025;
	private static final int YEAR_START = 2016;
	private static final int DAY = 1;
	private static final int MONTH = 5;
	private static final int EXTRA_DAYS = 4;
	private static final double GLOBAL_WARMING = 0.06; 
	
	
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("java pocasi soubor.csv");
			return;
		}
		
		final List<Radek> data = DataReader.read(args[0]);
		
		if (data == null) {
			System.out.println("Nedaří se naparsovat soubor");
			return;
		}
		
		    
	    int lt = 0;
	    int ge = 0;
	    final List<Double> values = new ArrayList<Double>();
	    final Map<Integer, List<Double>> avgData = new HashMap<Integer, List<Double>>();
		    
		     
		for (Radek radek : data) {
		        
	        if (radek.rok < YEAR_START) continue;
	        if (radek.rok == YEAR_START && radek.mesic == 1 && DAY <= 2 * EXTRA_DAYS + 1) continue;
		        		        
	        final int den_start = calculateDayStart(radek.rok);
	        final int den_konec = calculateDayEnd(radek.rok);
	        final int mesic_start = calculateMonthStart();
	        final int mesic_konec = calculateMonthEnd(radek.rok);
	        final int rok_start = calculateYearStart(radek.rok, radek.mesic);
	        final int rok_konec = calculateYearEnd(radek.rok, radek.mesic);
		        
	        double teplota = radek.teplota + GLOBAL_WARMING * (YEAR - rok_start); 
		        
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
		        
	        List<Double> usek = avgData.get(rok_start);
	        if (usek == null) {
	        	usek = new ArrayList<Double>();
	        	avgData.put(rok_start, usek);
	        }
	        usek.add(teplota);
		        
	        if (teplota >= TRESHOLD) {
	        	ge++;
	        } else {
	        	lt++;
	        }
	        values.add(teplota);
	        System.out.println(radek.den + ". " + radek.mesic + ".  " + radek.rok + " " + teplota);
	    }
	    System.out.println();
	    System.out.println("Dřevácká metoda:");
	    System.out.println("LT " + lt + ", kurs " + (lt + ge) / (double) lt);
	    System.out.println("GE " + ge + ", kurs " + (lt + ge) / (double) ge);
	    System.out.println();
	    double sum = 0;
	    for (Double d : values) {
	    	sum += d;
	    }
	    final double E = sum / values.size();
	    System.out.println("Matfyzácká metoda:");
	    System.out.println("EX = " + E);
	    sum = 0;
	    for (Double d : values) {
	    	double diff = E - d;
	    	sum += diff * diff;
	    }
		final double var = sum / values.size();
		System.out.println("var = " + var);
		final double probability = Normal.normalCDF(TRESHOLD, E, var);
	    System.out.println("P(X < " + TRESHOLD + ") = " + probability);
	    System.out.println("LT kurs " + 1 / probability);
	    System.out.println("GE kurs " + 1 / (1 - probability));
	        
	    final List<Integer> years = avgData.keySet().stream().sorted().collect(Collectors.toList());
	    final List<Double> avg = new ArrayList<Double>();
	    for (Integer y : years) {
	      	List<Double> yd = avgData.get(y);
	       	avg.add(yd.stream().mapToDouble(i -> i).average().getAsDouble());
	    }
	    System.out.println("Teploty = " + avg);
	    double globalWarming = Regrese.linearniRegrese(avg);
	    System.out.println("Globalni oteplovani = " + globalWarming);
	    
	    System.out.println(Utils.dayIndexInYear(2025, 1, 1));
	    System.out.println(Polynom.kvadraticky(1, 2, -3).f(1));
	}
	
	
	private static int calculateYearStart(int year, int month) {
		if (MONTH > 1 && MONTH < 12) return year;
		if (MONTH == 1 && DAY > EXTRA_DAYS) return year;
		if (MONTH == 12 && DAY < 32 - EXTRA_DAYS) return year;
		if (month == 1) {
			return year - 1;
		} else {
			return year;
		}
	}
	
	private static int calculateYearEnd(int year, int month) {
		if (MONTH > 1 && MONTH < 12) return year;
		if (MONTH == 1 && DAY > EXTRA_DAYS) return year;
		if (MONTH == 12 && DAY < 32 - EXTRA_DAYS) return year;
		if (month == 12) {
			return year + 1;
		} else {
			return year;
		}
	}
	
	private static int calculateMonthStart() {
		if (DAY > EXTRA_DAYS) return MONTH;
		int month = MONTH - 1;
		if (month == 0) month = 12;
		return month;
	}
	
	private static int calculateMonthEnd(int year) {
		if (DAY + EXTRA_DAYS <= Utils.maxDaysOfMonth(year, MONTH)) return MONTH;
		int month = MONTH + 1;
		if (month == 13) month = 1;
		return month;
	}
	
	
	private static int calculateDayStart(int year) {
		if (DAY > EXTRA_DAYS) return DAY - EXTRA_DAYS;
		return Utils.maxDaysOfMonth(year, MONTH - 1) - EXTRA_DAYS + 1;
	}
	
	private static int calculateDayEnd(int year) {
		int diff = DAY + EXTRA_DAYS - Utils.maxDaysOfMonth(year, MONTH) ;
		if (diff <= 0) return DAY + EXTRA_DAYS;
		return diff;
	}

}
