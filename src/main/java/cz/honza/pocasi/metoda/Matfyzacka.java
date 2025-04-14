package cz.honza.pocasi.metoda;


import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.matematika.analyza.Integral;
import cz.honza.pocasi.matematika.rozdeleni.Charakteristiky;
import cz.honza.pocasi.matematika.rozdeleni.Edgeworth;

public class Matfyzacka extends ObecnaMetoda {
	
	public Matfyzacka(ObecnaMetoda.Settings settings) {
		super(settings);
	}

	@Override
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		historickaData = upravData(historickaData, zadani);
		final List<Double> values = historickaData.stream().map(radek -> radek.teplota).collect(Collectors.toList());
		final double E = Charakteristiky.E(values);
		final double VAR = Charakteristiky.VAR(values);
		final double mu3 = Charakteristiky.moment(values, 3);
		final double mu4 = Charakteristiky.moment(values, 4);
		
		Funkce edgeworth = new Edgeworth(E, VAR, mu3, mu4);
		
		double pLt = Integral.urcityIntegral(edgeworth, zadani.teplota - 50, zadani.teplota, 1/100.0);
		double pGt = 1 - pLt; 
		
		return new Vysledek("Matfyzácká - Edgeworth", zadani, 1 / pLt, 1/ pGt, "EX = " + E + "\n" + "var = " + VAR);
	}

}
