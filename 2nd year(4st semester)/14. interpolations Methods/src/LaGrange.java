public class LaGrange {

    /**
     * Метод вычисления интерполяционного полинома Лагранжа
     *
     * @param x массив узлов интерполяции
     * @param y массив значений функции в узлах
     * @param xPoint точка, в которой вычисляется полином
     * @return значение полинома Лагранжа в точке xPoint
     */
    public static double lagrange(double[] x, double[] y, double xPoint) {
        int n = x.length;
        double result = 0;

        // Внешний цикл: суммирование по всем узлам (k от 0 до n-1)
        for (int k = 0; k < n; k++) {

            double numer = 1;  // Числитель базисного полинома
            double denom = 1;  // Знаменатель базисного полинома

            // Внутренний цикл: вычисление базисного полинома Lk(x)
            for (int m = 0; m < n; m++) {
                if (m != k) {

                    // Формула: Lk(x) = Произведение по m!=k [(x - xm) / (xk - xm)]
                    numer = numer * (xPoint - x[m]);
                    denom = denom * (x[k] - x[m]);
                }
            }

            // Добавляем k-е слагаемое: yk * Lk(x)
            result = result + y[k] * numer / denom;
        }

        return result;
    }

    public static void main(String[] args) {

        // Данные из варианта
        double[] x = {-6, -5, -4, -3, -2, -1};
        double[] y = {-6, 1, -8, -10, 1, -9};

        // выбираем точку
        double xPoint = -3.0;

        double result = lagrange(x, y, xPoint);

        System.out.println("Результат интерполяции Лагранжа:");
        System.out.println("x = " + xPoint);
        System.out.println("y = " + result);
    }

}