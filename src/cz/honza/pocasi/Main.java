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
	
	private static final double TEPLOTA = 20.0;
	private static final int ROK = 2025;
	private static final int MESIC = 5;
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
		
		
		// final List<Bod2D> body = regresniBody(data);
		
	    //testRegrese();
	    
	   // System.out.println(Utils.dayIndexInYear(2025, 1, 1));
	    //polynomRoku(body);
	    //testRegrese();
	}
	
	private static List<Bod2D> regresniBody(List<Radek> teploty) {
		List<Bod2D> r = new ArrayList<Bod2D>();
		return teploty.stream()
				.map(rad -> 
					new Bod2D(Utils.dayIndexInYear(rad.rok, rad.mesic, rad.den), rad.teplota))
				.collect(Collectors.toList());
		
	}
	
	private static void polynomRoku(List<Bod2D> body) {
		Polynom rocniPolynom = PolynomialRegressionNoLib.fitPolynomial(body, 36);
		for (int i = 0; i < 366; i++) {
			System.out.println(i + " " + rocniPolynom.f(i));
		}
	}

	
	private static void testRegrese() {
		Polynom p = PolynomialRegressionNoLib.fitPolynomial(Arrays.asList(new Bod2D(0, 0), new Bod2D(1, 1), new Bod2D(1, 2), new Bod2D(2, 2)), 1);
		System.out.println(p.f(1));
	}
	

}
