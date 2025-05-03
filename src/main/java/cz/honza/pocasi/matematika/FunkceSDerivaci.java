package cz.honza.pocasi.matematika;

public abstract class FunkceSDerivaci implements Funkce {
	public double derivace(double x) {
		final double A = f(x + 0.1);
		final double B = f(x - 0.1);
		return (A - B) / 0.2;
	}
}
