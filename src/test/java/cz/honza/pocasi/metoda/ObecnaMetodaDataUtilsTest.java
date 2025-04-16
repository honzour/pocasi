package cz.honza.pocasi.metoda;


import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cz.honza.pocasi.io.Radek;

class ObecnaMetodaDataUtilsTest {

	@Test
	void test() {
		final List<Radek> vystup = ObecnaMetodaDataUtils.filtrujData(
				Arrays.asList(
						new Radek(2015, 31, 12, 0.1),
						new Radek(2016, 1, 1, 0.1),
						new Radek(2016, 2, 1, 0.1),
						new Radek(2016, 12, 28, 0.1),
						new Radek(2016, 12, 29, 0.1),
						new Radek(2016, 12, 30, 0.1),
						new Radek(2016, 12, 31, 0.1),
						new Radek(2017, 1, 1, 0.1),
						new Radek(2017, 1, 2, 0.1),
						new Radek(2017, 1, 3, 0.1),
						new Radek(2017, 1, 4, 0.1),
						new Radek(2017, 1, 5, 0.1),
						new Radek(2017, 1, 6, 0.1),
						new Radek(2017, 1, 7, 0.1),
						new Radek(2017, 1, 2, 0.1),
						new Radek(2017, 19, 7, 0.1)
						),
				new Radek(2025, 1, 2, 0.1),
				new ObecnaMetoda.Settings(4, 2016, 0));
		Assertions.assertNotNull(vystup);
		Assertions.assertEquals(9, vystup.size());
	}

}
