package cz.honza.pocasi.metoda;

import java.util.List;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Funkce;

public class Drevacka extends ObecnaMetoda {
	
	public Drevacka(ObecnaMetoda.Settings settings) {
		super(settings);
	}
	
	public Drevacka(List<Radek> historickaData, int extraDays, int trainingYears) {
		super(historickaData, extraDays, trainingYears);
	}
	
	@Override
	public Vysledek spocitejSUpravenymiDaty(Radek zadani, List<Double> historickaData) {
		int lt = 0;
		int ge = 0;
		
		for (Double teplota : historickaData) {
	        	        
	        if (teplota >= zadani.teplota) {
	        	ge++;
	        } else {
	        	lt++;
	        }
	    }
		return new Vysledek("Dřevácká", zadani, (lt + ge) / (double) lt, (lt + ge) / (double) ge, null, histogram(historickaData));
	}
	
	private Funkce histogram(final List<Double> historickaData) {
		final double epsilon = 0.1;
		final double min = historickaData.stream().min(Double::compareTo).get() - epsilon;
		final double max = historickaData.stream().max(Double::compareTo).get() + epsilon;
		final int count = historickaData.size() / 10 + 1;
		
		final double size = (max - min) /  count;
		
		Funkce f = new Funkce() {
			
			@Override
			public double f(double x) {
				if (x < min || x >= max) {
					return 0;
				}
				
				final int box = (int)((x - min) / size);
				final double A = min + box * size;
				final double B = min + (box + 1) * size;
				final long boxCount = historickaData.stream().filter(v -> v >= A && v < B).count();
				return boxCount / (historickaData.size() * size); 
			}
		};
		
		return f;
	}

}
