package model.utils;

public class MyMath {
    /**
     * Tính ước chung lớn nhất (GCD) của hai số nguyên a và b.
     */
    public static int greatestCommonDivisor(int a, int b) {
        if (b == 0) return a;
        return greatestCommonDivisor(b, a % b);
    }

    /**
     * Tìm nghịch đảo của số a trong modulo m.
     * Nếu không tìm thấy, trả về 1 (không phải giá trị chính xác).
     */
    public static int findModularInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1)
                return x;
        }
        return 1; // Không tìm thấy nghịch đảo
    }

    /**
     * Nhân một mảng đơn chiều (vector) với một ma trận và trả về vector kết quả.
     */
    public static double[] multiplyVectorWithMatrix(double[] vector, double[][] matrix) {
        double[] result = new double[vector.length];
        double[][] transpose = transposeMatrix(matrix);
        for (int i = 0; i < vector.length; i++) {
            result[i] = multiplyVectors(vector, transpose[i]);
        }
        return result;
    }

    /**
     * Tính ma trận nghịch đảo trong modulo mod.
     * Trả về null nếu ma trận không khả nghịch.
     */
    public static double[][] calculateInverseMatrix(double[][] matrix, int mod) {
        int n = matrix.length;
        double[][] inverse = new double[n][n];
        double determinant = calculateDeterminant(matrix, mod);
        if (determinant == 0) {
            return null; // Ma trận không khả nghịch
        }
        double invDeterminant = findModularInverse((int) determinant, mod);
        double[][] adjoint = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjoint[i][j] = (int) (Math.pow(-1, i + j) * calculateDeterminant(getMatrixMinor(matrix, i, j), mod));
            }
        }

        double[][] adjointTranspose = transposeMatrix(adjoint);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = (int) ((invDeterminant * adjointTranspose[i][j]) % mod + mod) % mod;
            }
        }
        return inverse;
    }

    /**
     * Nhân hai vector đơn chiều và trả về kết quả (tích vô hướng).
     */
    private static double multiplyVectors(double[] vector1, double[] vector2) {
        double result = 0;
        for (int i = 0; i < vector1.length; i++) {
            result += vector1[i] * vector2[i];
        }
        return result;
    }

    /**
     * Tạo ma trận chuyển vị từ một ma trận ban đầu.
     */
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

    /**
     * Kiểm tra xem một ma trận có phải ma trận khả nghịch trong modulo mod không.
     */
    public static boolean isInvertibleMatrix(double[][] matrix, int mod) {
        return calculateDeterminant(matrix, mod) == 1;
    }

    /**
     * Tính định thức của một ma trận trong modulo mod.
     */
    private static double calculateDeterminant(double[][] matrix, int mod) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0] % mod;
        } else if (n == 2) {
            return ((matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % mod + mod) % mod;
        }

        double determinant = 0;
        for (int i = 0; i < n; i++) {
            double minorDeterminant = calculateDeterminant(getMatrixMinor(matrix, 0, i), mod);
            double cofactor = Math.pow(-1, i) * matrix[0][i] * minorDeterminant;
            determinant = (determinant + cofactor) % mod;
        }

        // Đảm bảo định thức là số không âm
        determinant = (determinant + mod) % mod;
        return determinant;
    }

    /**
     * Lấy ma trận con (minor) khi loại bỏ hàng và cột chỉ định.
     */
    private static double[][] getMatrixMinor(double[][] matrix, int row, int col) {
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

}
