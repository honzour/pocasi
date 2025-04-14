package cz.honza.pocasi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.DataReader;
import cz.honza.pocasi.io.Radek;
import cz.honza.pocasi.kalendar.Utils;
import cz.honza.pocasi.matematika.Bod2D;
import cz.honza.pocasi.matematika.Polynom;
import cz.honza.pocasi.matematika.PolynomialRegressionNoLib;
import cz.honza.pocasi.metoda.Drevacka;
import cz.honza.pocasi.metoda.Matfyzacka;
import cz.honza.pocasi.metoda.Metoda;
import cz.honza.pocasi.metoda.Normalni;

public class Main {
	
	private static final double TEPLOTA = 16.5;
	private static final int ROK = 2025;
	private static final int MESIC = 4;
	private static final int DEN = 15;
	
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
		
		List<Metoda> metody = new ArrayList<Metoda>(); 
		metody.add(new Drevacka());
		metody.add(new Normalni());
		metody.add(new Matfyzacka());
		
		metody.forEach(
			m -> System.out.println(m.spocitej(new Radek(ROK, MESIC, DEN, TEPLOTA), data).toString())
		);
	}
}
