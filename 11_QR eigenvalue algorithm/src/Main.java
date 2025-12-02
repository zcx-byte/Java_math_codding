import java.util.Arrays;

public class Main {

    private static void printMatrix(String name, double[][] mat) {
        System.out.println("Матрица " + name + ":");
        for (double[] row : mat) {
            for (double value : row) {
                System.out.printf("%10.4f ", value);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        final int n = 5;

        // Исходная матрица A₀
        double[][] A = {
                {1, 1, 6, 2, 5},
                {6, 9, 2, 8, 3},
                {8, 9, 3, 3, 9},
                {6, 9, 3, 6, 5},
                {6, 5, 3, 5, 6}
        };

        double[][] Q = new double[n][n];
        double[][] R = new double[n][n];

        // Вспомогательный массив для умножения, чтобы не портить originalA
        double[][] tempA = new double[n][n];

        for (int t = 0; t <= 300; t++) {
            // 1. Q = A, R = 0
            copy(A, Q);
            zero(R);

            // 2. QR-разложение A = Q * R (классический Грам–Шмидт)
            for (int j = 0; j < n; j++) {

                // Норма j-го столбца
                double norm = 0.0;

                for (int m = 0; m < n; m++) {
                    norm += Q[m][j] * Q[m][j];
                }
                R[j][j] = Math.sqrt(norm);

                // Нормируем Q[:,j]
                for (int i = 0; i < n; i++) {
                    Q[i][j] /= R[j][j];
                }

                // Ортогонализация остальных столбцов
                for (int k = j + 1; k < n; k++) {
                    // R[j][k] = Q[:,j] ⋅ Q[:,k]
                    double dot = 0.0;
                    for (int m = 0; m < n; m++) {
                        dot += Q[m][j] * Q[m][k];
                    }
                    R[j][k] = dot;

                    // Вычитаем проекцию
                    for (int i = 0; i < n; i++) {
                        Q[i][k] -= R[j][k] * Q[i][j];
                    }
                }
            }

            // 3. Обновляем A := R * Q
            multiply(R, Q, tempA);  // tempA = R × Q
            copy(tempA, A);         // A = tempA

            // Выводим только последнюю итерацию (t = 300)
            if (t == 300) {
                System.out.println("=== Итерация t = " + t + " (последняя) ===");
                printMatrix("Q", Q);
                printMatrix("R", R);
                printMatrix("A = R * Q", A);
            }
        }
    }


    private static void copy(double[][] src, double[][] dst) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dst[i], 0, src[i].length);
        }
    }

    private static void zero(double[][] m) {
        for (double[] row : m) {
            Arrays.fill(row, 0.0);
        }
    }

    // dst = A × B (квадратные n×n)
    private static void multiply(double[][] A, double[][] B, double[][] dst) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0.0;
                for (int k = 0; k < n; k++) {
                    sum += A[i][k] * B[k][j];
                }
                dst[i][j] = sum;
            }
        }
    }
}