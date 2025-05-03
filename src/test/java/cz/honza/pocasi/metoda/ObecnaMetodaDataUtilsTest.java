package cz.honza.pocasi.metoda;


import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cz.honza.pocasi.io.Radek;

class ObecnaMetodaDataUtilsTest {

	@Test
	void testLeden() {
		final List<Radek> vystup = ObecnaMetodaDataUtils.filtrujData(
				Arrays.asList(
						new Radek(2015, 12, 31, 0.1),
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
						new Radek(2017, 7, 19, 0.1)
						),
				new Radek(2025, 1, 2, 0.1),
				new ObecnaMetoda.Settings(4, 2016));
		Assertions.assertNotNull(vystup);
		Assertions.assertEquals(10, vystup.size());
	}
	
	@Test
	void testProsinec() {
		boolean accepted = ObecnaMetodaDataUtils.acceptRadek(
				new Radek(2016, 12, 31, 0.1),
				new Radek(2025, 1, 2, 0.1),
				new ObecnaMetoda.Settings(4, 2016)
			);
		Assert.assertTrue(accepted);
	}
	
	@Test
	void testSrpen() {
		final List<Radek> vystup = ObecnaMetodaDataUtils.filtrujData(
				Arrays.asList(
						new Radek(2015, 8, 15, 0.1),
						new Radek(2016, 8, 10, 0.1),
						new Radek(2016, 8, 11, 0.1),
						new Radek(2016, 8, 12, 0.1),
						new Radek(2016, 8, 13, 0.1),
						new Radek(2016, 8, 14, 0.1),
						new Radek(2016, 8, 15, 0.1),
						new Radek(2016, 8, 16, 0.1),
						new Radek(2016, 8, 17, 0.1),
						new Radek(2016, 8, 18, 0.1),
						new Radek(2016, 8, 19, 0.1),
						new Radek(2016, 8, 20, 0.1)
						),
				new Radek(2025, 8, 15, 28.1),
				new ObecnaMetoda.Settings(4, 2016));
		Assertions.assertNotNull(vystup);
		Assertions.assertEquals(9, vystup.size());
	}
	
	@Test
	void testUnorPestupny() {
		final List<Radek> vystup = ObecnaMetodaDataUtils.filtrujData(
				Arrays.asList(
						new Radek(2016, 2, 24, 0.1),
						new Radek(2016, 2, 25, 0.1),
						new Radek(2016, 2, 26, 0.1),
						new Radek(2016, 2, 27, 0.1),
						new Radek(2016, 2, 28, 0.1),
						new Radek(2016, 2, 29, 0.1),
						new Radek(2016, 3, 1, 0.1),
						new Radek(2016, 3, 2, 0.1),
						new Radek(2016, 3, 3, 0.1),
						new Radek(2016, 3, 4, 0.1),
						new Radek(2016, 3, 5, 0.1),
						new Radek(2017, 2, 24, 0.1),
						new Radek(2017, 2, 25, 0.1),
						new Radek(2017, 2, 26, 0.1),
						new Radek(2017, 2, 27, 0.1),
						new Radek(2017, 2, 28, 0.1),
						new Radek(2017, 3, 1, 0.1),
						new Radek(2017, 3, 2, 0.1),
						new Radek(2017, 3, 3, 0.1),
						new Radek(2017, 3, 4, 0.1),
						new Radek(2017, 3, 5, 0.1),
						new Radek(2017, 3, 6, 0.1),
						new Radek(2017, 3, 7, 0.1)
						),
				new Radek(2024, 2, 29, 0.1),
				new ObecnaMetoda.Settings(4, 2016));
		Assertions.assertNotNull(vystup);
		Assertions.assertEquals(18, vystup.size());
	}
	
	@Test
	void testUnorNePestupny() {
		final List<Radek> vystup = ObecnaMetodaDataUtils.filtrujData(
				Arrays.asList(
						new Radek(2016, 2, 24, 0.1),
						new Radek(2016, 2, 25, 0.1),
						new Radek(2016, 2, 26, 0.1),
						new Radek(2016, 2, 27, 0.1),
						new Radek(2016, 2, 28, 0.1),
						new Radek(2016, 2, 29, 0.1),
						new Radek(2016, 3, 1, 0.1),
						new Radek(2016, 3, 2, 0.1),
						new Radek(2016, 3, 3, 0.1),
						new Radek(2016, 3, 4, 0.1),
						new Radek(2016, 3, 5, 0.1),
						new Radek(2017, 2, 24, 0.1),
						new Radek(2017, 2, 25, 0.1),
						new Radek(2017, 2, 26, 0.1),
						new Radek(2017, 2, 27, 0.1),
						new Radek(2017, 2, 28, 0.1),
						new Radek(2017, 3, 1, 0.1),
						new Radek(2017, 3, 2, 0.1),
						new Radek(2017, 3, 3, 0.1),
						new Radek(2017, 3, 4, 0.1),
						new Radek(2017, 3, 5, 0.1),
						new Radek(2017, 3, 6, 0.1),
						new Radek(2017, 3, 7, 0.1)
						),
				new Radek(2025, 2, 28, 0.1),
				new ObecnaMetoda.Settings(4, 2016));
		Assertions.assertNotNull(vystup);
		Assertions.assertEquals(18, vystup.size());
	}


}
