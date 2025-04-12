package cz.honza.pocasi.metoda;


import java.util.ArrayList;
import java.util.List;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.rozdeleni.Normal;

public class Normalni extends ObecnaMetoda {

	@Override
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		final List<Double> values = new ArrayList<Double>();
		
		historickaData = kopiruj(historickaData);
		prepoctiDataNaDen(historickaData, zadani);
		historickaData = filtrujData(historickaData);
		otepliData(historickaData, zadani);
		
		for (Radek radek : historickaData) {
	        values.add(radek.teplota);
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

		return new Vysledek("Normální rozdělení", zadani, 1 / probability, 1 / (1 - probability), "EX = " + E + "\n" + "var = " + var);
	}
}
