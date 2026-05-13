import java.util.Random;

public class Annealing {

    public static double f(double[] x) {
        double v1 = 0.4 * Math.pow(x[0] - 0.6, 2);
        double v2 = 0.4 * Math.pow(x[1] - 0.5, 2);
        double v3 = 0.8 * Math.pow(x[2] - 0.3, 2);
        return Math.sqrt(v1 + v2 + v3 + 8);
    }

    public static void main(String[] args) {

        Random random = new Random();

        // Начальная точка (3 координаты)
        double[] xOld = {-1.3, 2.7, 0.0};

        double[] xNew = new double[3];

        // Параметры алгоритма
        double t = 100;          // начальная "температура"
        double tMin = 0.001;      // порог остановки
        double maxStep = 1.1;     // максимальный шаг поиска
        int k = 0;                // счётчик итераций

        // Заголовок таблицы результатов
        // вероястность, что алгоритм согласится на ухудшение функции
        System.out.println("k\tt\t     x1\t     x2\t     x3\t     f(x)\t   df\tвероятность\tдействие");
        System.out.printf("%d\t%.4f\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f\t%s%n",
                k, t, xOld[0], xOld[1], xOld[2], f(xOld), 0.0, 0.0, "старт");

        // Основной цикл: пока не "остыли"
        while (t > tMin) {
            k++;
            t *= 0.99;  // плавно снижаем температуру

            // Случайный сдвиг по каждой координате
            double a = maxStep * (random.nextDouble() - 0.5);
            double b = maxStep * (random.nextDouble() - 0.5);
            double c = maxStep * (random.nextDouble() - 0.5);

            xNew[0] = xOld[0] + a;
            xNew[1] = xOld[1] + b;
            xNew[2] = xOld[2] + c;

            double df = f(xNew) - f(xOld);  // изменение функции
            double prob = 0.0;
            String action = "";

            if (df <= 0) {
                
                // Новое значение лучше - принимаем без вопросов
                xOld = xNew.clone();  
                action = "улучшение";
            } else {

                // Новое значение хуже - принимаем с вероятностью
                prob = Math.exp(-df / t);
                if (random.nextDouble() < prob) {
                    xOld = xNew.clone();
                    action = "прыжок вверх";
                } else {
                    action = "отклонили";
                }
            }

            // Вывод текущей итерации
            System.out.printf("%d\t%.4f\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f\t%s%n",
                    k, t, xOld[0], xOld[1], xOld[2], f(xOld), df, prob, action);
        }

        // Финальный результат
        System.out.println("\n Найдена точка: (" +
                String.format("%.4f", xOld[0]) + ", " +
                String.format("%.4f", xOld[1]) + ", " +
                String.format("%.4f", xOld[2]) + ")");
        System.out.println(" Минимум функции: " + String.format("%.6f", f(xOld)));
    }
}