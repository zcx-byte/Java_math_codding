import java.util.Locale;

public class CauchyEquationSolver {

    // вариант 10
    // Уравнение: y' = cos(2x) * cos(2y) в квадрате
    // Начальное условие: y(0) = 0.5
    private static final double X0 = 0.0;
    private static final double Y0 = 0.5;
    private static final double H = 0.1;      // шаг табуляции
    private static final int STEPS = 10;      // количество шагов

    // Точность для метода Милна
    private static final double EPSILON = 0.001;

    public static void main(String[] args) {

        // Чтобы числа выводились с точкой, а не с запятой
        Locale.setDefault(Locale.US);

        System.out.println("Решение уравнения Коши, вариант 10");
        System.out.println("y' = cos(2x) * cos(2y)^2, y(0) = 0.5");
        System.out.println("Шаг h = " + H + ", шагов = " + STEPS);
        System.out.println();

        // Запускаем все методы по очереди
        runEuler();
        System.out.println();
        runModifiedEuler();
        System.out.println();
        runRungeKutta();
        System.out.println();
        runAdams1();
        System.out.println();
        runAdams2();
        System.out.println();
        runAdams3();
    }

    // Правая часть уравнения: f(x, y) = cos(2x) * cos(2y)^2
    private static double f(double x, double y) {
        return Math.cos(2 * x) * Math.pow(Math.cos(2 * y), 2);
    }

    // Метод Эйлера
    // Самый простой метод: следующая точка = текущая + шаг * производная в текущей точке
    // Формула: yk = y(k-1) + h * f(x(k-1), y(k-1))
    private static void runEuler() {
        System.out.println("Метод Эйлера");

        double x = X0;
        double y = Y0;

        for (int k = 0; k <= STEPS; k++) {
            System.out.println("k=" + k + " x=" + x + " y=" + y);

            if (k < STEPS) {
                // Считаем следующую точку по формуле Эйлера
                y = y + H * f(x, y);
                x = x + H;
            }
        }
    }

    // Модифицированный метод Эйлера
    // Более точная версия: берём производную не в начале отрезка, а в его середине
    // Формула: сначала считаем y в середине, потом используем его
    private static void runModifiedEuler() {
        System.out.println("Модифицированный метод Эйлера");

        double x = X0;
        double y = Y0;

        for (int k = 0; k <= STEPS; k++) {
            System.out.println("k=" + k + " x=" + x + " y=" + y);

            if (k < STEPS) {

                // Сначала оцениваем значение функции в середине шага
                double yMid = y + (H / 2) * f(x, y);
                double xMid = x + H / 2;

                // Теперь используем производную в середине для полного шага
                y = y + H * f(xMid, yMid);
                x = x + H;
            }
        }
    }

    // Метод Рунге-Кутта четвёртого порядка
    // Самый точный из одношаговых методов. Использует четыре оценки производной
    // считаем a, b, c, d, потом усредняем с весами
    private static void runRungeKutta() {
        System.out.println("Метод Рунге-Кутта");

        double x = X0;
        double y = Y0;

        for (int k = 0; k <= STEPS; k++) {
            System.out.println("k=" + k + " x=" + x + " y=" + y);

            if (k < STEPS) {

                // Четыре оценки наклона
                double a = H * f(x, y);
                double b = H * f(x + H / 2, y + a / 2);
                double c = H * f(x + H / 2, y + b / 2);
                double d = H * f(x + H, y + c);

                // Усредняем с весами 1, 2, 2, 1 и делим на 6
                y = y + (a + 2 * b + 2 * c + d) / 6;
                x = x + H;
            }
        }
    }

    // Метод Адамса первого порядка (m = 1)
    // Это многошаговый метод: для расчёта новой точки использует две предыдущие
    // Формула: yk = y(k-1) + h/2 * (3*f(k-1) - f(k-2))
    // Первые точки считаем методом Рунге-Кутта, потом переключаемся на Адамса
    private static void runAdams1() {
        System.out.println("Метод Адамса, порядок 1");

        // Массивы для хранения всех точек, чтобы обращаться к предыдущим
        double[] x = new double[STEPS + 1];
        double[] y = new double[STEPS + 1];

        x[0] = X0;
        y[0] = Y0;

        for (int k = 0; k <= STEPS; k++) {
            System.out.println("k=" + k + " x=" + x[k] + " y=" + y[k]);

            if (k < STEPS) {
                x[k + 1] = x[k] + H;

                // Для первых точек (пока не набралось достаточно истории)
                // используем метод Рунге-Кутта, как рекомендует методичка
                if (k < 1) {
                    double a = H * f(x[k], y[k]);
                    double b = H * f(x[k] + H / 2, y[k] + a / 2);
                    double c = H * f(x[k] + H / 2, y[k] + b / 2);
                    double d = H * f(x[k] + H, y[k] + c);
                    y[k + 1] = y[k] + (a + 2 * b + 2 * c + d) / 6;
                } else {

                    // Основная формула Адамса первого порядка
                    double fk = f(x[k], y[k]);
                    double fk_1 = f(x[k - 1], y[k - 1]);
                    y[k + 1] = y[k] + (H / 2) * (3 * fk - fk_1);
                }
            }
        }
    }

    // Метод Адамса второго порядка (m = 2)
    // Использует три предыдущие точки для расчёта следующей
    // Формула: yk = y(k-1) + h/12 * (23*f(k-1) - 16*f(k-2) + 5*f(k-3))
    private static void runAdams2() {
        System.out.println("Метод Адамса, порядок 2");

        double[] x = new double[STEPS + 1];
        double[] y = new double[STEPS + 1];

        x[0] = X0;
        y[0] = Y0;

        for (int k = 0; k <= STEPS; k++) {
            System.out.println("k=" + k + " x=" + x[k] + " y=" + y[k]);

            if (k < STEPS) {
                x[k + 1] = x[k] + H;

                // Первые две точки считаем Рунге-Кутта, потом переходим на Адамса
                if (k < 2) {
                    double a = H * f(x[k], y[k]);
                    double b = H * f(x[k] + H / 2, y[k] + a / 2);
                    double c = H * f(x[k] + H / 2, y[k] + b / 2);
                    double d = H * f(x[k] + H, y[k] + c);
                    y[k + 1] = y[k] + (a + 2 * b + 2 * c + d) / 6;
                } else {
                    // Формула Адамса второго порядка
                    double fk = f(x[k], y[k]);
                    double fk_1 = f(x[k - 1], y[k - 1]);
                    double fk_2 = f(x[k - 2], y[k - 2]);
                    y[k + 1] = y[k] + (H / 12) * (23 * fk - 16 * fk_1 + 5 * fk_2);
                }
            }
        }
    }

    // Метод Адамса третьего порядка (m = 3)
    // Использует четыре предыдущие точки
    // Формула: yk = y(k-1) + h/24 * (55*f(k-1) - 59*f(k-2) + 37*f(k-3) - 9*f(k-4))
    private static void runAdams3() {
        System.out.println("Метод Адамса, порядок 3");

        double[] x = new double[STEPS + 1];
        double[] y = new double[STEPS + 1];

        x[0] = X0;
        y[0] = Y0;

        for (int k = 0; k <= STEPS; k++) {
            System.out.println("k=" + k + " x=" + x[k] + " y=" + y[k]);

            if (k < STEPS) {
                x[k + 1] = x[k] + H;

                // Первые три точки считаем Рунге-Кутта
                if (k < 3) {
                    double a = H * f(x[k], y[k]);
                    double b = H * f(x[k] + H / 2, y[k] + a / 2);
                    double c = H * f(x[k] + H / 2, y[k] + b / 2);
                    double d = H * f(x[k] + H, y[k] + c);
                    y[k + 1] = y[k] + (a + 2 * b + 2 * c + d) / 6;
                } else {
                    // Формула Адамса третьего порядка
                    double fk = f(x[k], y[k]);
                    double fk_1 = f(x[k - 1], y[k - 1]);
                    double fk_2 = f(x[k - 2], y[k - 2]);
                    double fk_3 = f(x[k - 3], y[k - 3]);
                    y[k + 1] = y[k] + (H / 24) * (55 * fk - 59 * fk_1 + 37 * fk_2 - 9 * fk_3);
                }
            }
        }
    }
}