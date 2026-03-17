public class quadraticSpline {

    public static void main(String[] args) {

        // final - нельзя изменить значение переменной, после того, как оно было присвоего
        final int n = 6;

        // Данные из варианта №10
        double[] x = {-1.2, -0.6, 0.5, 1.5, 1.9, 2.6};
        double[] y = {3.38688, -4.35456, 11.71875, 29.53125, 26.00169, 6.88896};

        double[] a = new double[n];
        double[] b = new double[n];
        double[] c = new double[n];
        double[] z = new double[n];

        // Начальное значение z[0] (производная в первой точке)
        // Если не задано, можно вычислить или принять за 0
        z[0] = 0; // или другое значение, если известно

        System.out.println("Расчёт коэффициентов интерполяции:");
        System.out.println("=====================================");

        for (int k = 1; k < n; k++) {
            double dx = x[k] - x[k - 1];
            double dy = y[k] - y[k - 1];

            a[k] = dy / (dx * dx) - z[k - 1] / dx;
            b[k] = 2 * dy / dx - z[k - 1];
            c[k] = y[k];
            z[k] = b[k];

            System.out.printf("k = %d:%n", k);
            System.out.printf("  x[%d] = %.1f, x[%d] = %.1f, dx = %.4f%n",
                    k-1, x[k-1], k, x[k], dx);
            System.out.printf("  y[%d] = %.5f, y[%d] = %.5f, dy = %.5f%n",
                    k-1, y[k-1], k, y[k], dy);
            System.out.printf("  a[%d] = %.6f%n", k, a[k]);
            System.out.printf("  b[%d] = %.6f%n", k, b[k]);
            System.out.printf("  c[%d] = %.6f%n", k, c[k]);
            System.out.printf("  z[%d] = %.6f%n", k, z[k]);
            System.out.println();
        }

        // Вывод итоговых коэффициентов
        System.out.println("=====================================");
        System.out.println("Итоговые коэффициенты:");
        System.out.println("=====================================");
        System.out.println("k\ta[k]\t\tb[k]\t\tc[k]");
        for (int k = 1; k < n; k++) {
            System.out.printf("%d\t%.6f\t%.6f\t%.6f%n", k, a[k], b[k], c[k]);
        }
    }

}
