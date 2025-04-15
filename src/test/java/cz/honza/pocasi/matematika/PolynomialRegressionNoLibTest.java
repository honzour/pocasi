package cz.honza.pocasi.matematika;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PolynomialRegressionNoLibTest {

	@Test
	void testLinearni() {
		Polynom p = PolynomialRegressionNoLib.fitPolynomial(Arrays.asList(new Bod2D(0, 0), new Bod2D(2, 4)), 1);
		double middle = p.f(1);
		Assertions.assertTrue(middle > 1.9);
		Assertions.assertTrue(middle < 2.1);
	}
	
	@Test
	void testKvadraticky() {
		Polynom p = PolynomialRegressionNoLib.fitPolynomial(
				Arrays.asList(new Bod2D(0, 1), new Bod2D(1, 1), new Bod2D(1, 3), new Bod2D(2, 5)), 2);
		double middle = p.f(1);
		Assertions.assertTrue(middle > 1.9);
		Assertions.assertTrue(middle < 2.1);
	}

}
