package cz.honza.pocasi.metoda;

import java.util.List;

import cz.honza.pocasi.io.Radek;

public class Drevacka extends ObecnaMetoda {
	
	@Override
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		
		int lt = 0;
		int ge = 0;
		
		for (Radek radek : historickaData) {
	        
	        if (radek.rok < YEAR_START) continue;
	        if (radek.rok == YEAR_START && radek.mesic == 1 && zadani.den <= 2 * EXTRA_DAYS + 1) continue;
		        		        
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
		        
	        if (teplota >= zadani.teplota) {
	        	ge++;
	        } else {
	        	lt++;
	        }
	    }
		return new Vysledek("Dřevácká", zadani, (lt + ge) / (double) lt, (lt + ge) / (double) ge, null);
	}

}
