package cz.honza.pocasi.matematika.rozdeleni;

public class Normal {
	
	/**
	 * Distribuční funkce normálního rozdělení
	 * @param x
	 * @param mean střední hodnota
	 * @param variance rozptyl
	 * @return
	 */
	
	public static double normalCDF(double x, double mean, double variance) {
		final double stdDev = Math.sqrt(variance);
		final double z = (x - mean) / stdDev;
        return 0.5 * (1 + erf(z / Math.sqrt(2)));
    }

    private static double erf(double z) {
        // Přesná implementace chybové funkce (Gaussova aproximace)
    	final double t = 1.0 / (1.0 + 0.5 * Math.abs(z));
    	final double tau = t * Math.exp(-z * z - 1.26551223 +
                t * (1.00002368 +
                        t * (0.37409196 +
                                t * (0.09678418 +
                                        t * (-0.18628806 +
                                                t * (0.27886807 +
                                                        t * (-1.13520398 +
                                                                t * (1.48851587 +
                                                                        t * (-0.82215223 +
                                                                                t * 0.17087277)))))))));
        return z >= 0 ? (1 - tau) : (tau - 1);
    }

}
