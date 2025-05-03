package cz.honza.pocasi.metoda;

import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;

public abstract class ObecnaMetoda implements Metoda {
	
	public static class Settings {
		public int extraDays;
		public int yearStart;

		public Settings(int extraDays, int yearStart) {
			this.extraDays = extraDays;
			this.yearStart = yearStart;
		}
	}
	
	protected Settings settings;
	
	public ObecnaMetoda(Settings settings) {
		this.settings = settings;
	}
	
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		historickaData = upravData(historickaData, zadani);
		final List<Double> teploty = historickaData.stream().map(radek -> radek.teplota).collect(Collectors.toList());
		return spocitejSUpravenymiDaty(zadani, teploty);
	}
	
	public abstract Vysledek spocitejSUpravenymiDaty(Radek zadani, List<Double> historickaData);
	

	
	protected List<Radek> upravData(List<Radek> historickaData, Radek zadani) {
		return ObecnaMetodaDataUtils.upravData(historickaData, zadani, settings);
	}
}
