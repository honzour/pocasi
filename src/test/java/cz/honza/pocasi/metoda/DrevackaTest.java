package cz.honza.pocasi.metoda;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cz.honza.pocasi.io.Radek;

class DrevackaTest {

	@Test
	void test() {
		final Drevacka drevacka = new Drevacka(new ObecnaMetoda.Settings(2, 2016));
		final Vysledek vysledek = drevacka.spocitejSUpravenymiDaty(new Radek(2025, 4, 15, 16.5), Arrays.asList(14.0, 15.0, 17.0));
		Assertions.assertEquals(vysledek.kurzGe, 3);
		Assertions.assertEquals(vysledek.kurzLt, 1.5);
		
	}

}
