package cz.honza.pocasi.matematika;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Polynom extends FunkceSDerivaci {
	
	private List<Double> koefs;
	
	public Polynom(List<Double> koefs) {
		this.koefs = koefs.stream().collect(Collectors.toList());
	}
	
	public static Polynom kvadraticky(double A, double B, double C) {
		List<Double> koefs = new ArrayList<Double>();
		koefs.add(C);
		koefs.add(B);
		koefs.add(A);
		return new Polynom(koefs);
	}
	
	public static Polynom linearni(double A, double B) {
		List<Double> koefs = new ArrayList<Double>();
		koefs.add(B);
		koefs.add(A);
		return new Polynom(koefs);
	}
	
	public static Polynom konstantni(double A) {
		List<Double> koefs = new ArrayList<Double>();
		koefs.add(A);
		return new Polynom(koefs);
	}

	@Override
	public double f(double x) {
		
		return IntStream.range(0, koefs.size())
                .mapToDouble(i -> koefs.get(i) * Math.pow(x, i))
                .sum();
	}

}
