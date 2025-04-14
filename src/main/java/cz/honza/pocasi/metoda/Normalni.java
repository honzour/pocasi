package cz.honza.pocasi.metoda;


import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.rozdeleni.Charakteristiky;
import cz.honza.pocasi.matematika.rozdeleni.Normal;

public class Normalni extends ObecnaMetoda {
	
	public Normalni(ObecnaMetoda.Settings settings) {
		super(settings);
	}

	@Override
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		
		historickaData = upravData(historickaData, zadani);
		final List<Double> values = historickaData.stream().map(radek -> radek.teplota).collect(Collectors.toList());

		
		final double E = Charakteristiky.E(values);
		final double VAR = Charakteristiky.VAR(values);

		
		final double probability = Normal.normalCDF(zadani.teplota, E, VAR);

		return new Vysledek("Normální rozdělení", zadani, 1 / probability, 1 / (1 - probability), "EX = " + E + "\n" + "var = " + VAR);
	}
}
