public class cubicSpline {

    public static void main(String[] args) {

        final int n = 5; // 6 точек данных (индексы 0-5)

        // Данные из варианта №10
        double[] x = {-1.2, -0.6, 0.5, 1.5, 1.9, 2.6};
        double[] y = {3.38688, -4.35456, 11.71875, 29.53125, 26.00169, 6.88896};

        double[] a = new double[n + 1];
        double[] b = new double[n + 1];
        double[] c = new double[n + 1];
        double[] d = new double[n + 1];
        double[] z = new double[n + 1];
        double[] w = new double[n + 1];

        /*

        // Первая производная - наклон первого отрезка
        // Приближаем производную через разность соседних точек
                 y₁ - y₀
            y'≈ -----------
                x₁ - x₀
         */
        z[0] = (y[1] - y[0]) / (x[1] - x[0]);

        // Вторая производная = 0 (естественное условие)
        w[0] = 0;

        System.out.println("Расчёт коэффициентов кубической интерполяции:");
        System.out.println("===============================================");
        System.out.printf("z[0] = %.6f%n", z[0]);
        System.out.printf("w[0] = %.6f%n", w[0]);
        System.out.println();

        for (int k = 1; k <= n; k++) {
            double dx = x[k] - x[k - 1];
            double dy = y[k] - y[k - 1];

            a[k] = dy / Math.pow(dx, 3) - z[k - 1] / Math.pow(dx, 2) - w[k - 1] / (2 * dx);
            b[k] = 3 * dy / Math.pow(dx, 2) - 3 * z[k - 1] / dx - w[k - 1];
            c[k] = 3 * dy / dx - 2 * z[k - 1] - w[k - 1] * dx / 2;
            d[k] = y[k];

            z[k] = c[k];
            w[k] = 2 * b[k];

            System.out.printf("k = %d:%n", k);
            System.out.printf("  x[%d] = %.1f, x[%d] = %.1f, dx = %.6f%n",
                    k-1, x[k-1], k, x[k], dx);
            System.out.printf("  y[%d] = %.5f, y[%d] = %.5f, dy = %.5f%n",
                    k-1, y[k-1], k, y[k], dy);
            System.out.printf("  a[%d] = %.6f%n", k, a[k]);
            System.out.printf("  b[%d] = %.6f%n", k, b[k]);
            System.out.printf("  c[%d] = %.6f%n", k, c[k]);
            System.out.printf("  d[%d] = %.6f%n", k, d[k]);
            System.out.printf("  z[%d] = %.6f%n", k, z[k]);
            System.out.printf("  w[%d] = %.6f%n", k, w[k]);
            System.out.println();
        }

        // Вывод итоговых коэффициентов
        System.out.println("===============================================");
        System.out.println("Итоговые коэффициенты для всех отрезков:");
        System.out.println("===============================================");
        System.out.println("k\ta[k]\t\tb[k]\t\tc[k]\t\td[k]");
        for (int k = 1; k <= n; k++) {
            System.out.printf("%d\t%.6f\t%.6f\t%.6f\t%.6f%n",
                    k, a[k], b[k], c[k], d[k]);
        }

        // Пример вычисления значения интерполяционного многочлена
        System.out.println("\n===============================================");
        System.out.println("Вычисления значения многочлена:");
        System.out.println("===============================================");
        double xTest = 0.0; // точка для вычисления
        int segment = findSegment(x, xTest);
        if (segment >= 1) {
            double result = evaluatePolynomial(a[segment], b[segment], c[segment],
                    d[segment], x[segment-1], xTest);
            System.out.printf("S(%.2f) = %.6f (отрезок %d)%n", xTest, result, segment);
        }
    }

    // Поиск отрезка, на котором лежит точка x
    private static int findSegment(double[] x, double xVal) {
        for (int i = 1; i < x.length; i++) {
            if (xVal >= x[i-1] && xVal <= x[i]) {
                return i;
            }
        }
        return -1; // точка вне диапазона
    }

    // Вычисление значения кубического многочлена на отрезке
    private static double evaluatePolynomial(double a, double b, double c,
                                             double d, double x0, double x) {
        double h = x - x0;
        return a * Math.pow(h, 3) + b * Math.pow(h, 2) + c * h + d;
    }


}

