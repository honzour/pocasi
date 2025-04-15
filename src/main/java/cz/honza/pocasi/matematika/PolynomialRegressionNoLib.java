package cz.honza.pocasi.matematika;

import java.util.ArrayList;
import java.util.List;

public class PolynomialRegressionNoLib {
	 // Vrátí koeficienty polynomu f(x) = a0 + a1*x + a2*x^2 + ... + an*x^n
    public static Polynom fitPolynomial(List<Bod2D> points, int degree) {
        int n = degree + 1;
        int m = points.size();

        double[][] A = new double[n][n];
        double[] B = new double[n];

        // Vypočítáme matice A a B pro normální rovnici: A * coeffs = B
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                A[row][col] = 0.0;
                for (int i = 0; i < m; i++) {
                    A[row][col] += Math.pow(points.get(i).x, row + col);
                }
            }

            B[row] = 0.0;
            for (int i = 0; i < m; i++) {
                B[row] += points.get(i).y * Math.pow(points.get(i).x, row);
            }
        }

        // Řešíme soustavu rovnic A * x = B pomocí Gaussovy eliminace
        double[] koefs = Gaus.gaussianElimination(A, B);
        List<Double> k = new ArrayList<Double>();
        for (double d : koefs) {
        	k.add(d);
        }
        return new Polynom(k);
    }
}
