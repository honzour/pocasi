package cz.honza.pocasi.matematika.analyza;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cz.honza.pocasi.matematika.Funkce;
import cz.honza.pocasi.matematika.Polynom;

class IntegralTest {

	@Test
	void test() {
		Funkce f = Polynom.linearni(2, 0);
		double integral = Integral.urcityIntegral(f, 0, 2, 0.01);
		Assertions.assertTrue(integral > 3.9 && integral < 4.1);
	}

}
