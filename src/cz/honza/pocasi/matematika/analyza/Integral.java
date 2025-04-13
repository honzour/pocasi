package cz.honza.pocasi.matematika.analyza;

import cz.honza.pocasi.matematika.Funkce;

public class Integral {
	public static double urcityIntegral(Funkce f, double odkud, double kam, double step) {
		double suma = 0;
		double oldFx = Double.NaN;
		double oldX = Double.NaN;
		for (double x = odkud; x <= kam; x += step) {
			double fx = f.f(x);
			if (!Double.isNaN(oldX)) {
				suma += step * (fx + oldFx) / 2;
			}
			oldX = x;
			oldFx = fx;
		}
		return suma;
	}
}
