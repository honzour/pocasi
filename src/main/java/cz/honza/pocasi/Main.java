package cz.honza.pocasi;

import java.util.ArrayList;
import java.util.List;
import cz.honza.pocasi.io.DataReader;
import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.metoda.Drevacka;
import cz.honza.pocasi.metoda.Matfyzacka;
import cz.honza.pocasi.metoda.Metoda;
import cz.honza.pocasi.metoda.Normalni;
import cz.honza.pocasi.metoda.ObecnaMetoda;

public class Main {
	
	private static final double TEPLOTA = 16.5;
	private static final int ROK = 2025;
	private static final int MESIC = 4;
	private static final int DEN = 15;
	
	private static final int EXTRA_DAYS = 4;
	private static final int YEAR_START = 2016;
	private static final double GLOBAL_WARMING = 0.06;
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("java pocasi soubor.csv");
			return;
		}
		
		final List<Radek> data = DataReader.read(args[0]);
		
		if (data == null) {
			System.out.println("Nedaří se naparsovat soubor");
			return;
		}
		
		ObecnaMetoda.Settings settings = new ObecnaMetoda.Settings(EXTRA_DAYS, YEAR_START, GLOBAL_WARMING);
		
		List<Metoda> metody = new ArrayList<Metoda>();
		metody.add(new Drevacka(settings));
		metody.add(new Normalni(settings));
		metody.add(new Matfyzacka(settings));
		
		metody.forEach(
			m -> System.out.println(m.spocitej(new Radek(ROK, MESIC, DEN, TEPLOTA), data).toString())
		);
	}
}
