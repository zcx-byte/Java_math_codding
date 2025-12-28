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
        double[][] a = {
                {1, 1, 6, 2, 5},
                {6, 9, 2, 8, 3},
                {8, 9, 3, 3, 9},
                {6, 9, 3, 6, 5},
                {6, 5, 3, 5, 6}
        };

        final int n = a.length; // n = 5

        // Объявление матриц
        double[][] l = new double[n][n];
        double[][] u = new double[n][n];
        double[][] y = new double[n][n]; // L^-1
        double[][] x = new double[n][n]; // U^-1
        double[][] ainv = new double[n][n]; // A^-1

        //   Шаг 1: Первичная инициализация L и U  
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                l[i][j] = 0.0;
                u[i][j] = (i == j) ? 1.0 : 0.0; // единичная матрица
            }
        }

        //   Шаг 2: LU-разложение  
        // Первая колонка L
        for (int i = 0; i < n; i++) {
            l[i][0] = a[i][0];
        }

        // Первая строка U (кроме u[0][0] = 1)
        for (int j = 1; j < n; j++) {
            u[0][j] = a[0][j] / l[0][0];
        }

        // Основной цикл по k = 1 .. n-1
        for (int k = 1; k < n; k++) {
            // Вычисление k-го столбца L
            for (int i = k; i < n; i++) {
                l[i][k] = a[i][k];
                for (int m = 0; m < k; m++) {
                    l[i][k] -= l[i][m] * u[m][k];
                }
            }

            // Вычисление k-й строки U (если k <= n-2)
            if (k <= n - 2) {
                for (int j = k + 1; j < n; j++) {
                    u[k][j] = a[k][j];
                    for (int m = 0; m < k; m++) {
                        u[k][j] -= l[k][m] * u[m][j];
                    }
                    u[k][j] /= l[k][k];
                }
            }
        }

        //   Шаг 3: Вычисление Y = L^-1 (обратная к нижней треугольной)  
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j > i) {
                    y[i][j] = 0.0;
                } else if (j == i) {
                    y[i][j] = 1.0 / l[i][i];
                } else { // j < i
                    y[i][j] = 0.0;
                    for (int m = j; m < i; m++) { // m от j до i-1
                        y[i][j] -= l[i][m] * y[m][j];
                    }
                    y[i][j] /= l[i][i];
                }
            }
        }

        //   Шаг 4: Вычисление X = U^-1 (обратная к верхней треугольной)  
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (j < i) {
                    x[i][j] = 0.0;
                } else if (j == i) {
                    x[i][j] = 1.0;
                } else { // j > i
                    x[i][j] = 0.0;
                    for (int m = i + 1; m <= j; m++) { // m от i+1 до j
                        x[i][j] -= u[i][m] * x[m][j];
                    }
                }
            }
        }

        //   Шаг 5: A^-1 = X * Y  
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ainv[i][j] = 0.0;
                for (int m = 0; m < n; m++) {
                    ainv[i][j] += x[i][m] * y[m][j];
                }
            }
        }

        //   Вывод результатов  
        printMatrix("A", a);
        printMatrix("L", l);
        printMatrix("U", u);
        printMatrix("Y = L^-1", y);
        printMatrix("X = U^-1", x);
        printMatrix("A^-1", ainv);

    }
}