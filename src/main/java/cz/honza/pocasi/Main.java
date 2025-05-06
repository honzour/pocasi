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
import cz.honza.pocasi.metoda.ObecnaMetodaDataUtils;
import cz.honza.pocasi.metoda.Vysledek;

public class Main {
	
	private static final double TEPLOTA = 25.8;
	private static final int ROK = 2025;
	private static final int MESIC = 6;
	private static final int DEN = 15;
	
	private static final int EXTRA_DAYS = 4;
	private static final int YEAR_START = 2016;
	private static final int YEAR_END = 3000;
	
	private static final boolean SHOW_GUI = true;
	private static final boolean TEST = true;
	
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
		if (TEST) {
			test(data);
		} else {
			run(data);
		}
	}
	
	private static void test(List<Radek> data) {
		final List<Metoda> metody = new ArrayList<Metoda>();
		metody.add(new Drevacka(data, EXTRA_DAYS, 8));
		metody.add(new Kernelova(data, EXTRA_DAYS, 8));
		metody.add(new Normalni(data, EXTRA_DAYS, 8));
		metody.add(new Momentova(data, EXTRA_DAYS, 8));
		
		final List<String> vysledky = new ArrayList<String>();
		for (Metoda m : metody) {
			double penize = m.otestuj(data);
			vysledky.add(m.getClass().getSimpleName() + " " + penize);
		}
		vysledky.forEach(v -> System.out.println(v));
		
	}
	
	private static void run(List<Radek> data) {
		final ObecnaMetoda.Settings settings = new ObecnaMetoda.Settings(EXTRA_DAYS, YEAR_START, YEAR_END);
		final Radek zadani = new Radek(ROK, MESIC, DEN, TEPLOTA);
		final List<Metoda> metody = new ArrayList<Metoda>();
		metody.add(new Drevacka(settings));
		metody.add(new Normalni(settings));
		metody.add(new Momentova(settings));
		metody.add(new Kernelova(settings));
		
		final List<Vysledek> vysledky = new ArrayList<Vysledek>();
		for (Metoda m : metody) {
			Vysledek v = m.spocitej(zadani, data);
			System.out.println(v);
			vysledky.add(v);
		}
		
		if (SHOW_GUI) {
			final List<Radek> upravenaData = ObecnaMetodaDataUtils.upravData(data, zadani, settings);
			
			final List<Double> upraveneTeploty = upravenaData.stream().map(radek -> radek.teplota).collect(Collectors.toList());
			GuiApplication.start(vysledky, upraveneTeploty, data);
		}

	}
}
