package cz.honza.pocasi.metoda;

import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;
import cz.honza.pocasi.matematika.Bod2D;
import cz.honza.pocasi.matematika.Polynom;
import cz.honza.pocasi.matematika.PolynomialRegressionNoLib;


public abstract class ObecnaMetoda implements Metoda {
	
	public static class Settings {
		public int extraDays;
		public int yearStart;
		public double globalWarming;

		public Settings(int extraDays, int yearStart, double globalWarming) {
			this.extraDays = extraDays;
			this.yearStart = yearStart;
			this.globalWarming = globalWarming;
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
	
	private static List<Bod2D> regresniBody(List<Radek> teploty) {
		return teploty.stream()
				.map(rad -> 
					new Bod2D(Utils.dayIndexInYear(rad.datum), rad.teplota))
				.collect(Collectors.toList());
		
	}
	
	private static Polynom polynomRoku(List<Radek> teploty) {
		return PolynomialRegressionNoLib.fitPolynomial(regresniBody(teploty), 36);
		
	}
	
	protected List<Radek> kopiruj(List<Radek> historickaData) {
		return historickaData.stream().map(r -> r.copy()).collect(Collectors.toList());
	}
	
	protected void prepoctiDataNaDen(List<Radek> historickaData, Radek zadani) {
		Polynom p = polynomRoku(historickaData);
		double denZadani = Utils.dayIndexInYear(zadani.datum);
		double teplotaDne = p.f(denZadani);
		for (Radek radek : historickaData) {
			double denCoMenim = Utils.dayIndexInYear(radek.datum);	
	        radek.teplota = radek.teplota + teplotaDne - p.f(denCoMenim);
		}
	}
	
	protected void otepliData(List<Radek> historickaData, Radek zadani) {
		ObecnaMetodaDataUtils.otepliData(historickaData, zadani, settings);
	}

	public List<Radek> filtrujData(List<Radek> historickaData, Radek zadani) {
		return ObecnaMetodaDataUtils.filtrujData(historickaData, zadani, settings);
	}
	
	protected List<Radek> upravData(List<Radek> historickaData, Radek zadani) {
		historickaData = kopiruj(historickaData);
		prepoctiDataNaDen(historickaData, zadani);
		historickaData = filtrujData(historickaData, zadani);
		otepliData(historickaData, zadani);
		return historickaData;
	}
}
