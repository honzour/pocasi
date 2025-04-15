package cz.honza.pocasi.matematika;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LinearniRegreseTest {
	@Test
	void test() {
		double result = LinearniRegrese.linearniRegrese(Arrays.asList(0.0, 1.0, 2.0, 2.0, 1.0, 0.0));
		Assertions.assertEquals(0.0, result);
	}
}
