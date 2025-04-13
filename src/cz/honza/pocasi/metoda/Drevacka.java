package cz.honza.pocasi.metoda;

import java.util.List;

import cz.honza.pocasi.io.Radek;


public class Drevacka extends ObecnaMetoda {
	
	@Override
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		
		historickaData = upravData(historickaData, zadani);
		
		int lt = 0;
		int ge = 0;
		
		for (Radek radek : historickaData) {
	        	        
	        if (radek.teplota >= zadani.teplota) {
	        	ge++;
	        } else {
	        	lt++;
	        }
	    }
		return new Vysledek("Dřevácká", zadani, (lt + ge) / (double) lt, (lt + ge) / (double) ge, null);
	}

}
