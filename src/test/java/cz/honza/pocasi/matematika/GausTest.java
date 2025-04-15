package cz.honza.pocasi.matematika;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GausTest {

	@Test
	void test() {
		
		double[][] matice = {{2, 0}, {0, 2}};
		double[] vektor = {6, 6}; 
		
		double[] vysledek = Gaus.gaussianElimination(matice, vektor);
		Assertions.assertNotNull(vysledek);
		Assertions.assertEquals(2, vysledek.length);
		Assertions.assertEquals(3, vysledek[0]);
		Assertions.assertEquals(3, vysledek[1]);
	}

}
