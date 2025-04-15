package cz.honza.pocasi.metoda;

import java.util.List;

import cz.honza.pocasi.io.Radek;

public class Drevacka extends ObecnaMetoda {
	
	public Drevacka(ObecnaMetoda.Settings settings) {
		super(settings);
	}
	
	@Override
	protected Vysledek spocitejSUpravenymiDaty(Radek zadani, List<Double> historickaData) {
		int lt = 0;
		int ge = 0;
		
		for (Double teplota : historickaData) {
	        	        
	        if (teplota >= zadani.teplota) {
	        	ge++;
	        } else {
	        	lt++;
	        }
	    }
		return new Vysledek("Dřevácká", zadani, (lt + ge) / (double) lt, (lt + ge) / (double) ge, null);
	}

}
