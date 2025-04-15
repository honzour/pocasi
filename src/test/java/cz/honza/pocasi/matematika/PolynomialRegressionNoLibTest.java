package cz.honza.pocasi.matematika;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PolynomialRegressionNoLibTest {

	@Test
	void test() {
		Polynom p = PolynomialRegressionNoLib.fitPolynomial(Arrays.asList(new Bod2D(0, 0), new Bod2D(2, 4)), 1);
		double middle = p.f(1);
		Assertions.assertTrue(middle > 1.9);
		Assertions.assertTrue(middle < 2.1);
	}

}
