package cz.honza.pocasi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {

	private static final double TRESHOLD = 19.5;
	private static final String FILE = "/home/honza/teplota/P1PRUZ01_TMA_N.csv";
	private static final int YEAR = 2025;
	private static final int YEAR_START = 2016;
	private static final int DAY = 1;
	private static final int MONTH = 5;
	private static final int EXTRA_DAYS = 4;
	private static final double GLOBAL_WARMING = 0.06; 
	
	
	private static double linearniRegrese(List<Double> data) {
		if (data == null || data.isEmpty()) {
			throw new IllegalArgumentException();
		}
		final int n = data.size(); 
		
		if (n == 1) {
			return 0.0;
		}
		final double A = n * IntStream.range(0, n)
				.mapToDouble(i -> i * data.get(i))
	            .sum();
		
		double B = IntStream.range(0, n).mapToDouble(i -> i).sum() 
				* IntStream.range(0, n).mapToDouble(i -> data.get(i)).sum();
		double C = n * IntStream.range(0, n).mapToDouble(i -> i * i).sum();
		double D = Math.pow(IntStream.range(0, n).mapToDouble(i -> i).sum(), 2);
		
		
		return (A - B) / (C - D);
	}
	

	public static void main(String[] args) {
		
		
		final File file = new File(FILE);
		try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String s;
		    
		    int lt = 0;
		    int ge = 0;
		    final List<Double> values = new ArrayList<Double>();
		    final Map<Integer, List<Double>> avgData = new HashMap<Integer, List<Double>>();
		    
		    
		    while ((s = br.readLine()) != null) {
		    	if (s.length() == 0 || s.charAt(0) == '#') {
		    		continue;
		    	}
		        final String[] fields = s.split(";");
		        if (fields.length < 4) {
		        	throw new Exception("Field " + s);
		        }
		        final int rok = Integer.parseInt(fields[0]);
		        final int mesic = Integer.parseInt(fields[1]);
		        final int den = Integer.parseInt(fields[2]);
		        double teplota = Double.parseDouble(fields[3]);
		        
		        if (rok < YEAR_START) continue;
		        if (rok == YEAR_START && mesic == 1 && DAY <= 2 * EXTRA_DAYS + 1) continue;
		        		        
		        final int den_start = calculateDayStart(rok);
		        final int den_konec = calculateDayEnd(rok);
		        final int mesic_start = calculateMonthStart();
		        final int mesic_konec = calculateMonthEnd(rok);
		        final int rok_start = calculateYearStart(rok, mesic);
		        final int rok_konec = calculateYearEnd(rok, mesic);
		        
		        teplota += GLOBAL_WARMING * (YEAR - rok_start); 
		        
		        if (rok < rok_start) continue;
		        
		        
		        if (rok == rok_start) {
		        	if (mesic < mesic_start) continue;
		        	if (mesic == mesic_start) {
		        		if (den < den_start) continue;
		        	}
		        }
		        
		        if (rok > rok_konec) continue;
		        
		        if (rok == rok_konec) {
		        	if (mesic > mesic_konec) continue;
		        	if (mesic == mesic_konec) {
		        		if (den > den_konec) continue;
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
		        System.out.println(den + ". " + mesic + ".  " + rok + " " + teplota);
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
		    final double probability = normalCDF(TRESHOLD, E, var);
	        System.out.println("P(X < " + TRESHOLD + ") = " + probability);
	        System.out.println("LT kurs " + 1 / probability);
	        System.out.println("GE kurs " + 1 / (1 - probability));
	        
	        final List<Integer> years = avgData.keySet().stream().sorted().toList();
	        final List<Double> avg = new ArrayList<Double>();
	        for (Integer y : years) {
	        	List<Double> yd = avgData.get(y);
	        	avg.add(yd.stream().mapToDouble(i -> i).average().getAsDouble());
	        }
	        System.out.println("Teploty = " + avg);
	        double globalWarming = linearniRegrese(avg);
	        System.out.println("Globalni oteplovani = " + globalWarming);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static double normalCDF(double x, double mean, double variance) {
		final double stdDev = Math.sqrt(variance);
		final double z = (x - mean) / stdDev;
        return 0.5 * (1 + erf(z / Math.sqrt(2)));
    }

    private static double erf(double z) {
        // Přesná implementace chybové funkce (Gaussova aproximace)
    	final double t = 1.0 / (1.0 + 0.5 * Math.abs(z));
    	final double tau = t * Math.exp(-z * z - 1.26551223 +
                t * (1.00002368 +
                        t * (0.37409196 +
                                t * (0.09678418 +
                                        t * (-0.18628806 +
                                                t * (0.27886807 +
                                                        t * (-1.13520398 +
                                                                t * (1.48851587 +
                                                                        t * (-0.82215223 +
                                                                                t * 0.17087277)))))))));
        return z >= 0 ? (1 - tau) : (tau - 1);
    }

	private static int maxDaysOfMonth(int year, int month) {
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
		if (DAY + EXTRA_DAYS <= maxDaysOfMonth(year, MONTH)) return MONTH;
		int month = MONTH + 1;
		if (month == 13) month = 1;
		return month;
	}
	
	
	private static int calculateDayStart(int year) {
		if (DAY > EXTRA_DAYS) return DAY - EXTRA_DAYS;
		return maxDaysOfMonth(year, MONTH - 1) - EXTRA_DAYS + 1;
	}
	
	private static int calculateDayEnd(int year) {
		int diff = DAY + EXTRA_DAYS - maxDaysOfMonth(year, MONTH) ;
		if (diff <= 0) return DAY + EXTRA_DAYS;
		return diff;
	}


}
