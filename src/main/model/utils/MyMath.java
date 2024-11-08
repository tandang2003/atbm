package main.model.utils;

public class MyMath {
    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static int inverseInZm(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }

    public static void main(String[] args) {
        int a = 53;
        int m = 26;
        System.out.println(inverseInZm(a, m));
    }

    public static double[] multiplyMatrices(double[] a, double[][] b) {
        double[] result = new double[a.length];
        double[][] transpose = transposeMatrix(b);
        for (int i = 0; i < a.length; i++) {
            result[i] = multiplyMatrices(a, transpose[i]);
        }
        return result;
    }

    public static double[][] inverseMatrix(double[][] matrix, int mod) {
        int n = matrix.length;
        double[][] inverse = new double[n][n];
        double determinant = calculateDeterminant(matrix, mod);
        if (determinant == 0) {
            return null;
        }
        double invDeterminant = inverseInZm((int) determinant, mod);
        double[][] adjoint = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjoint[i][j] = (int) (Math.pow(-1, i + j) * calculateDeterminant(getMinor(matrix, i, j), mod));
            }
        }

        double[][] adjointTranspose = transposeMatrix(adjoint);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = (int) ((invDeterminant * adjointTranspose[i][j])% mod + mod) % mod;
            }
        }
        return inverse;
    }

    private static double multiplyMatrices(double[] a, double[] b) {
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    public static double[][] transposeMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] transpose = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transpose[i][j] = matrix[j][i];
            }
        }
        return transpose;
    }

    public static boolean isInverseMatrix(double[][] matrix, int modulus) {
        return calculateDeterminant(matrix, modulus) == 1;
    }

    private static double calculateDeterminant(double[][] matrix, int modulus) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0] % modulus;
        } else if (n == 2) {
            return ((matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % modulus + modulus) % modulus;
        }

        double determinant = 0;
        for (int i = 0; i < n; i++) {
            double minorDeterminant = calculateDeterminant(getMinor(matrix, 0, i), modulus);
            double cofactor = Math.pow(-1, i) * matrix[0][i] * minorDeterminant;
            determinant = (determinant + cofactor) % modulus;
        }

        // Ensure determinant is non-negative
        determinant = (determinant + modulus) % modulus;
        return determinant;
    }

    private static double[][] getMinor(double[][] matrix, int row, int col) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];
        for (int i = 0, minorRow = 0; i < n; i++) {
            if (i == row) continue;
            for (int j = 0, minorCol = 0; j < n; j++) {
                if (j == col) continue;
                minor[minorRow][minorCol++] = matrix[i][j];
            }
            minorRow++;
        }
        return minor;
    }

    public static boolean coprime(int a, int b) {
        return gcd(a, b) == 1;
    }
}
