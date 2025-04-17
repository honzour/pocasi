package cz.honza.pocasi.metoda;

import cz.honza.pocasi.io.Radek;

public class Vysledek {
	public String jmeno;
	public Radek co;
	public double kurzLt;
	public double kurzGe;
	public String extraInfo;
	
	
	public Vysledek(String jmeno, Radek co, double kurzLt, double kurzGe, String extraInfo) {
		super();
		this.jmeno = jmeno;
		this.co = co;
		this.kurzLt = kurzLt;
		this.kurzGe = kurzGe;
		this.extraInfo = extraInfo;
	}


	public String toString() {
		return "Metoda " + jmeno + " " + co.datum.getDayOfMonth() + "." + co.datum.getMonthValue() + "." + co.datum.getYear() + " teplota " + co.teplota + "\n" +
				"kurzLt " + kurzLt + "\n" +
				"kurzGe " + kurzGe +
				(extraInfo == null ? "" : "\n" + extraInfo) +
				"\n";
	}
}
