package cz.honza.pocasi.metoda;


import java.util.List;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.matematika.analyza.Integral;
import cz.honza.pocasi.matematika.rozdeleni.Charakteristiky;
import cz.honza.pocasi.matematika.rozdeleni.Edgeworth;

public class Momentova extends ObecnaMetoda {
	
	public Momentova(ObecnaMetoda.Settings settings) {
		super(settings);
	}

	@Override
	public Vysledek spocitejSUpravenymiDaty(Radek zadani, List<Double> historickaData) {
		final double E = Charakteristiky.E(historickaData);
		final double VAR = Charakteristiky.VAR(historickaData);
		final double mu3 = Charakteristiky.moment(historickaData, 3);
		final double mu4 = Charakteristiky.moment(historickaData, 4);
		
		Funkce edgeworth = new Edgeworth(E, VAR, mu3, mu4);
		
		double pLt = Integral.urcityIntegral(edgeworth, zadani.teplota - 50, zadani.teplota, 1/100.0);
		double pGt = 1 - pLt; 
		
		return new Vysledek("Momentová - Edgeworth", zadani, 1 / pLt, 1/ pGt, "EX = " + E + "\nvar = " + VAR + 
				"\nmu3 =" + mu3 + "\nmu4 = " + mu4);
	}

}
