package cz.honza.pocasi.metoda;

import java.util.List;
import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.rozdeleni.Charakteristiky;
import cz.honza.pocasi.matematika.rozdeleni.Normal;

public class Normalni extends ObecnaMetoda {
	
	public Normalni(ObecnaMetoda.Settings settings) {
		super(settings);
	}

	@Override
	public Vysledek spocitejSUpravenymiDaty(Radek zadani, List<Double> historickaData)  {
		final double E = Charakteristiky.E(historickaData);
		final double VAR = Charakteristiky.VAR(historickaData);
		
		final double probability = Normal.normalCDF(zadani.teplota, E, VAR);

		return new Vysledek("Normální rozdělení", zadani, 1 / probability, 1 / (1 - probability), "EX = " + E + "\n" + "var = " + VAR);
	}
}
