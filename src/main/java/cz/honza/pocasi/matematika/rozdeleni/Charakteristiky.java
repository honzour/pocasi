package cz.honza.pocasi.matematika.rozdeleni;

import java.util.List;

public class Charakteristiky {
	public static double E(List<Double> hodnoty) {
		double sum = 0;
		for (Double d : hodnoty) {
			sum += d;
		}
		return sum / hodnoty.size();
	}
	
	public static double VAR(List<Double> hodnoty) {
		double E = E(hodnoty);
		double sum = 0;
		for (Double d : hodnoty) {
			double diff = d - E;
			sum += diff * diff;
		}
		return sum / hodnoty.size();
	}
	
	public static double moment(List<Double> hodnoty, int moment) {
		double E = E(hodnoty);
		double sum = 0;
		for (Double d : hodnoty) {
			double diff = d - E;
			sum += Math.pow(diff, moment);
		}
		return sum / hodnoty.size();
	}

}
