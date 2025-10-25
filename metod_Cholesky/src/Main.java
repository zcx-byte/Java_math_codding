import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размер матрицы: ");
        int n = scanner.nextInt();

        // Матрица A[n][n+1] — последний столбец — вектор b
        double[][] A = new double[n][n + 1];
        double[][] L = new double[n][n]; // нижняя треугольная
        double[][] U = new double[n][n]; // верхняя, с единичной диагональю
        double[] Y = new double[n];      // промежуточный вектор
        double[] X = new double[n];      // решение

        // Заполняем A случайными числами
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = Math.random() * 10;
                L[i][j] = 0.0;

                if (i == j) U[i][j] = 1.0; // диагональ U = 1

                else U[i][j] = 0.0;
            }
            A[i][n] = Math.random() * 10; // вектор b
        }

        // Вывод исходной матрицы A
        System.out.println("\nИсходная матрица A:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%.4f ", A[i][j]);
            }
            System.out.println();
        }

        // Вывод вектора b
        System.out.println("\nВектор свободных членов B:");
        for (int i = 0; i < n; i++) {
            System.out.printf("%.4f ", A[i][n]);
        }
        System.out.println();

        // Первый слой: L[i][0] = A[i][0], U[0][j] = A[0][j] / L[0][0]
        for (int i = 0; i < n; i++) {
            L[i][0] = A[i][0];
        }
        if (n > 1) {
            for (int j = 1; j < n; j++) {
                U[0][j] = A[0][j] / L[0][0];
            }
        }

        // Основной цикл LU-разложения
        for (int k = 1; k < n; k++) {
            // Считаем столбец L[k..n-1][k]
            for (int i = k; i < n; i++) {
                L[i][k] = A[i][k];
                for (int m = 0; m < k; m++) {
                    L[i][k] -= L[i][m] * U[m][k];
                }
            }

            // Считаем строку U[k][k+1..n-1]
            if (k < n - 1) {
                for (int j = k + 1; j < n; j++) {
                    U[k][j] = A[k][j];
                    for (int m = 0; m < k; m++) {
                        U[k][j] -= L[k][m] * U[m][j];
                    }
                    U[k][j] /= L[k][k]; // деление на диагональ L
                }
            }
        }

        // Решаем L*Y = B (прямая подстановка)
        Y[0] = A[0][n] / L[0][0];
        for (int k = 1; k < n; k++) {
            Y[k] = A[k][n];
            for (int m = 0; m < k; m++) {
                Y[k] -= L[k][m] * Y[m];
            }
            Y[k] /= L[k][k];
        }

        // Решаем U*X = Y (обратная подстановка)
        X[n - 1] = Y[n - 1];
        for (int k = n - 2; k >= 0; k--) {
            X[k] = Y[k];
            for (int m = k + 1; m < n; m++) {
                X[k] -= U[k][m] * X[m];
            }
        }

        // Вывод результатов
        System.out.println("\nМатрица L:");
        for (double[] row : L) {
            for (double val : row) {
                System.out.printf("%.4f ", val);
            }
            System.out.println();
        }

        System.out.println("\nМатрица U:");
        for (double[] row : U) {
            for (double val : row) {
                System.out.printf("%.4f ", val);
            }
            System.out.println();
        }

        System.out.println("\nВектор Y:");
        for (double val : Y) {
            System.out.printf("%.4f ", val);
        }
        System.out.println();

        System.out.println("\nВектор X:");
        for (double val : X) {
            System.out.printf("%.4f ", val);
        }
        System.out.println();
    }
}