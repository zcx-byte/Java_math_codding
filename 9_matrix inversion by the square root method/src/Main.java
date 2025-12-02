public class Main {

    // Вспомогательная функция для красивого вывода матрицы
    // С циклами for-each (enhanced for loops)
    // Предназначены для удобного перебора элементов массивов и без необходимости явно управлять индексами
    private static void printMatrix(String name, double[][] mat) {
        System.out.println("Матрица " + name + ":");
        for (double[] row : mat) {      // row — это просто ссылка на mat[0], затем mat[1], и т.д.
            for (double value : row) {
                System.out.printf("%10.4f ", value);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        double[][] A = {
                {5, 1, 1, 3, 3},
                {1, 6, 2, 6, 2},
                {1, 2, 9, 1, 5},
                {3, 6, 1, 9, 2},
                {3, 2, 5, 2, 9}
        };

        final int n = A.length;

        // Матрица L (нижняя треугольная)
        double[][] L = new double[n][n];

        // === Этап 1: Разложение Холецкого ===

        // Первый элемент
        L[0][0] = Math.sqrt(A[0][0]);

        // Первая колонка
        for (int i = 1; i < n; i++) {
            L[i][0] = A[i][0] / L[0][0];
        }

        printMatrix("А", A);

        // Остальные элементы
        for (int k = 1; k < n; k++) {
            double sqSum = 0;
            for (int m = 0; m < k; m++) {
                sqSum += L[k][m] * L[k][m];
            }
            L[k][k] = Math.sqrt(A[k][k] - sqSum);

            if (k < n - 1) {
                for (int i = k + 1; i < n; i++) {
                    double pairSum = 0;
                    for (int m = 0; m < k; m++) {
                        pairSum += L[i][m] * L[k][m];
                    }
                    L[i][k] = (A[i][k] - pairSum) / L[k][k];
                }
            }
        }

        printMatrix("L", L);

        // === Этап 2: Вычисление Y = L^(-1) ===

        double[][] Y = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j > i) {
                    Y[i][j] = 0.0;
                } else if (j == i) {
                    Y[i][j] = 1.0 / L[i][i];
                } else { // j < i
                    Y[i][j] = 0.0;
                    for (int m = j; m < i; m++) {
                        Y[i][j] += L[i][m] * Y[m][j];
                    }
                    Y[i][j] = -Y[i][j] / L[i][i];
                }
            }
        }

        printMatrix("Y", Y);

        // === Этап 3: Вычисление A^(-1) = Y^T * Y ===

        double[][] AInv = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                AInv[i][j] = 0.0;
                for (int m = 0; m < n; m++) {
                    AInv[i][j] += Y[m][i] * Y[m][j]; // Y^T[i][m] * Y[m][j]
                }
            }
        }

        printMatrix("AInv", AInv);
    }

}