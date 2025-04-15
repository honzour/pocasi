package cz.honza.pocasi.matematika;

import java.util.List;
import java.util.stream.IntStream;

public class LinearniRegrese {
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
}
