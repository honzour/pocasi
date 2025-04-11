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
        double[] koefs = gaussianElimination(A, B);
        List<Double> k = new ArrayList<Double>();
        for (double d : koefs) {
        	k.add(d);
        }
        return new Polynom(k);
    }

    // Gaussova eliminace
    private static double[] gaussianElimination(double[][] A, double[] B) {
        int n = B.length;

        for (int i = 0; i < n; i++) {
            // Najdeme pivot
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[max][i])) {
                    max = j;
                }
            }

            // Prohodíme řádky
            double[] temp = A[i];
            A[i] = A[max];
            A[max] = temp;

            double t = B[i];
            B[i] = B[max];
            B[max] = t;

            // Eliminace
            for (int j = i + 1; j < n; j++) {
                double factor = A[j][i] / A[i][i];
                B[j] -= factor * B[i];
                for (int k = i; k < n; k++) {
                    A[j][k] -= factor * A[i][k];
                }
            }
        }

        // Zpětná substituce
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = B[i];
            for (int j = i + 1; j < n; j++) {
                x[i] -= A[i][j] * x[j];
            }
            x[i] /= A[i][i];
        }

        return x;
    }


}
