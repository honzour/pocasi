package cz.honza.pocasi.matematika;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Regrese {
	// https://cs.wikipedia.org/wiki/Line%C3%A1rn%C3%AD_regrese
	public static double linearniRegrese(List<Double> data) {
		if (data == null || data.isEmpty()) {
			throw new IllegalArgumentException();
		}
		final int n = data.size(); 
		
		if (n == 1) {
			return 0.0;
		}
		final double A = n * IntStream.range(0, n)
				.mapToDouble(i -> i * data.get(i))
	            .sum();
		
		double B = IntStream.range(0, n).mapToDouble(i -> i).sum() 
				* IntStream.range(0, n).mapToDouble(i -> data.get(i)).sum();
		double C = n * IntStream.range(0, n).mapToDouble(i -> i * i).sum();
		double D = Math.pow(IntStream.range(0, n).mapToDouble(i -> i).sum(), 2);
		
		
		return (A - B) / (C - D);
	}
	
	// https://cs.wikipedia.org/wiki/Polynomick%C3%A1_regrese
	public static Polynom polynomialniRegrese(int stupenPolynomu, List<Bod2D> data) {
		if (data.size() == 0) {
			return Polynom.konstantni(0);
		}
		if (data.size() == 1) {
			return Polynom.konstantni(data.get(0).y);
		}
		if (data.size() <= stupenPolynomu) {
			stupenPolynomu = data.size() - 1;
		}
		double[] b = new double[stupenPolynomu + 1];
		for (int i = 0; i < b.length; i++) {
			double suma = 0;
			double exp = b.length - 1 - i;
			for (Bod2D bod : data) {
				suma += bod.y * Math.pow(bod.x, exp);
			}
			b[i] = suma;
		}
		double[][] A = new double[stupenPolynomu + 1][stupenPolynomu + 1];
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[i].length; j++) {
				double suma = 0;
				for (Bod2D bod : data) {
					suma += Math.pow(bod.x, 2 * A.length - i - j);
				}
				A[i][j] = suma;
			}	
		}
		double[] koefs = Gaus.solve(A, b);
		List<Double> l = new ArrayList<Double>();
		for (int i = koefs.length - 1; i >= 0; i--)
			l.add(koefs[i]);
		return new Polynom(l);
	}

}
