package cz.honza.pocasi.matematika;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PolynomTest {

	@Test
	void test() {
		Polynom p = Polynom.kvadraticky(3, -2, 2);
		Assertions.assertEquals(10, p.f(2));
	}

}
