package cz.honza.pocasi.metoda;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.honza.pocasi.io.Radek;

public abstract class ObecnaMetoda implements Metoda {
	
	public static class Settings {
		public int extraDays;
		public int yearStart;
		public int yearEnd;

		public Settings(int extraDays, int yearStart, int yearEnd) {
			this.extraDays = extraDays;
			this.yearStart = yearStart;
			this.yearEnd = yearEnd;
		}
	}
	
	protected Settings settings;
	
	protected Map<MonthDay, Double> zadavaciTeploty;
	protected Map<MonthDay, Double> vysledneTeploty;
	protected int checkYear;
	
	/**
	 * Pro reálný běh,
	 * @param settings
	 */
	public ObecnaMetoda(Settings settings) {
		this.settings = settings;
	}
	
	/**
	 * Pro test.
	 * @param historickaData
	 */
	public ObecnaMetoda(List<Radek> historickaData, int extraDays, int trainingYears) {
		final int checkYear = historickaData.stream().map(radek -> radek.datum.getYear()).max(Comparator.naturalOrder()).get();
		final int temperatureYear = checkYear - 1;
		final int lastTrainingYear = checkYear - 2;
		this.settings = new Settings(extraDays, lastTrainingYear - trainingYears + 1, lastTrainingYear);
		
		
		vysledneTeploty = historickaData.stream().filter(radek -> radek.datum.getYear() == checkYear).collect(
				Collectors.toMap(radek -> MonthDay.from(radek.datum), radek -> radek.teplota));
		zadavaciTeploty = historickaData.stream().filter(radek -> radek.datum.getYear() == temperatureYear).collect(
				Collectors.toMap(radek -> MonthDay.from(radek.datum), radek -> radek.teplota));
	}
	
	public Vysledek spocitej(Radek zadani, List<Radek> historickaData) {
		historickaData = upravData(historickaData, zadani);
		final List<Double> teploty = historickaData.stream().map(radek -> radek.teplota).collect(Collectors.toList());
		return spocitejSUpravenymiDaty(zadani, teploty);
	}
	
	public double otestuj(List<Radek> historickaData) {
		double penize = 0;
		int day = 0;
		for (MonthDay md : zadavaciTeploty.keySet()) {
			day++;
			if (!vysledneTeploty.containsKey(md)) {
				continue;
			}
			final double zadanaTeplota = zadavaciTeploty.get(md);
			final double skutecnaTeplota = vysledneTeploty.get(md);
			final Radek zadani = new Radek(LocalDate.of(checkYear, md.getMonth(), md.getDayOfMonth()), zadanaTeplota);
			final Vysledek vysledek = spocitej(zadani, historickaData);
			
			if (vysledek.kurzGe < 1.84) {
				if (skutecnaTeplota >= zadanaTeplota) {
					penize += 0.84;
				} else {
					penize -=1;
				}
			}
			if (vysledek.kurzLt < 1.84) {
				if (skutecnaTeplota < zadanaTeplota) {
					penize += 0.84;
				} else {
					penize -=1;
				}
			}
			System.out.println(this.getClass().getSimpleName() + " " + day + " " + penize);
		}
		
		
		return penize;
	}
	
	public abstract Vysledek spocitejSUpravenymiDaty(Radek zadani, List<Double> historickaData);
	

	
	protected List<Radek> upravData(List<Radek> historickaData, Radek zadani) {
		return ObecnaMetodaDataUtils.upravData(historickaData, zadani, settings);
	}
}
