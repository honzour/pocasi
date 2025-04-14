package cz.honza.pocasi.matematika.rozdeleni;

import cz.honza.pocasi.matematika.Funkce;

public class Edgeworth implements Funkce {
	
	double mean;
	double variance;
	double mu3;
	double mu4;
	
	public Edgeworth(double mean, double variance, double mu3, double mu4) {
		this.mean = mean;
		this.variance = variance;
		this.mu3 = mu3;
		this.mu4 = mu4;
	}
	
	@Override
    public double f(double x) {
        double sigma = Math.sqrt(variance);
        double z = (x - mean) / sigma;

        // Standardizované momenty
        double gamma1 = mu3 / Math.pow(sigma, 3); // šikmost
        double gamma2 = mu4 / Math.pow(sigma, 4) - 3; // špičatost (nad excess kurtosis)

        // Hermitovy polynomy
        double H3 = z * z * z - 3 * z;
        double H4 = z * z * z * z - 6 * z * z + 3;
        double H6 = Math.pow(z, 6) - 15 * Math.pow(z, 4) + 45 * z * z - 15;

        // Normální hustota
        double phi = (1 / Math.sqrt(2 * Math.PI)) * Math.exp(-0.5 * z * z);

        // Korekce Edgeworthova rozvoje
        double correction = 1
                + (gamma1 / 6.0) * H3
                + (gamma2 / 24.0) * H4
                + (gamma1 * gamma1 / 72.0) * H6;

        return (1 / sigma) * phi * correction;
    }

}
