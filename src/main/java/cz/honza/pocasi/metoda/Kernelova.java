package cz.honza.pocasi.metoda;

import java.util.List;
import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.matematika.analyza.Integral;

public class Kernelova extends ObecnaMetoda {
	
	public Kernelova(ObecnaMetoda.Settings settings) {
		super(settings);
	}

	@Override
	public Vysledek spocitejSUpravenymiDaty(Radek zadani, List<Double> historickaData) {
		final double pasmo = 3.0;
		final Funkce hustota = new Hustota(pasmo, historickaData);
		
		double pLt = Integral.urcityIntegral(hustota, zadani.teplota - 50, zadani.teplota, 1/100.0);
		double pGt = 1 - pLt; 

		return new Vysledek("Kernelová", zadani, 1 / pLt, 1/ pGt, "Šíře pásma = " + pasmo + " F");
	}
	
	private static double jadrovaFunkce(double u) {
		return 1 / Math.sqrt(2 * Math.PI) * Math.exp(-u * u / 2);
	}
	
	private static class Hustota implements Funkce {

		public Hustota(double h, List<Double> hodnoty) {
			this.h = h; 
			this.hodnoty = hodnoty;
		}
		
		private double h;
		private List<Double> hodnoty;

		@Override
		public double f(double x) {
			double suma = hodnoty.stream().map(hodnota -> jadrovaFunkce((x - hodnota) / h)).mapToDouble(Double::doubleValue).sum();
			return 1.0 / (hodnoty.size() * h) * suma;
		}
		
	}
}
