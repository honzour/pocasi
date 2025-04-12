package cz.honza.pocasi.metoda;


import java.util.ArrayList;
import java.util.List;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.rozdeleni.Normal;

public class Normalni extends ObecnaMetoda {

	@Override
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		final List<Double> values = new ArrayList<Double>();
		for (Radek radek : historickaData) {
	        
	        if (radek.rok < YEAR_START) continue;
	        if (radek.rok == YEAR_START && radek.mesic == 1 && DAY <= 2 * EXTRA_DAYS + 1) continue;
		        		        
	        final int den_start = calculateDayStart(radek.rok);
	        final int den_konec = calculateDayEnd(radek.rok);
	        final int mesic_start = calculateMonthStart();
	        final int mesic_konec = calculateMonthEnd(radek.rok);
	        final int rok_start = calculateYearStart(radek.rok, radek.mesic);
	        final int rok_konec = calculateYearEnd(radek.rok, radek.mesic);
		        
	        double teplota = radek.teplota + GLOBAL_WARMING * (zadani.rok - rok_start); 
		        
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

	        values.add(teplota);
	    }

		double sum = 0;
		for (Double d : values) {
			sum += d;
		}
		final double E = sum / values.size();
		
		sum = 0;
		for (Double d : values) {
			double diff = E - d;
			sum += diff * diff;
		}
		final double var = sum / values.size();
		
		final double probability = Normal.normalCDF(zadani.teplota, E, var);

		return new Vysledek("Normální", zadani, 1 / probability, 1 / (1 - probability), "EX = " + E + "\n" + "var = " + var);
	}
}
