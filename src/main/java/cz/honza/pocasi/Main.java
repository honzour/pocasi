package cz.honza.pocasi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.gui.GuiApplication;
import cz.honza.pocasi.io.DataReader;
import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.metoda.Drevacka;
import cz.honza.pocasi.metoda.Kernelova;
import cz.honza.pocasi.metoda.Momentova;
import cz.honza.pocasi.metoda.Metoda;
import cz.honza.pocasi.metoda.Normalni;
import cz.honza.pocasi.metoda.ObecnaMetoda;
import cz.honza.pocasi.metoda.Vysledek;

public class Main {
	
	private static final double TEPLOTA = 1;
	private static final int ROK = 2026;
	private static final int MESIC = 1;
	private static final int DEN = 1;
	
	private static final int EXTRA_DAYS = 4;
	private static final int YEAR_START = 2016;
	private static final double GLOBAL_WARMING = 0.06;
	
	private static final boolean SHOW_GUI = true;
	
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
		
		final ObecnaMetoda.Settings settings = new ObecnaMetoda.Settings(EXTRA_DAYS, YEAR_START, GLOBAL_WARMING);
		
		final List<Metoda> metody = new ArrayList<Metoda>();
		metody.add(new Drevacka(settings));
		metody.add(new Normalni(settings));
		metody.add(new Momentova(settings));
		metody.add(new Kernelova(settings));
		
		final List<Vysledek> vysledky = new ArrayList<Vysledek>();
		for (Metoda m : metody) {
			Vysledek v = m.spocitej(new Radek(ROK, MESIC, DEN, TEPLOTA), data);
			System.out.println(v);
			vysledky.add(v);
		}
		
		if (SHOW_GUI) {
			List<Double> hd = data.stream().map(radek -> radek.teplota).collect(Collectors.toList());
			GuiApplication.start(vysledky, hd);
		}
	}
}
