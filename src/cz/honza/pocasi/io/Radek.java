package cz.honza.pocasi.io;

public class Radek {
    public int rok;
    public int mesic;
    public int den;
    public double teplota;
	public Radek(int rok, int mesic, int den, double teplota) {
		this.rok = rok;
		this.mesic = mesic;
		this.den = den;
		this.teplota = teplota;
	}
	
	public Radek copy() {
		return new Radek(rok, mesic, den, teplota);
	}
}
