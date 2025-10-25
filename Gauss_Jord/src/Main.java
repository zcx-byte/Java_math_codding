import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {

        double[][] Gauss = {

                {-9.41, -8.58, 0.93, -9.09, 0.04, 4.19},
                {6.32, 4.39, 1.32, -7.73, 8.5, 8.14},
                {2.02, -2.23, -6.24, -3.25, 5.58, 9.22},
                {-1.71, 1.02, -3.58, 5.98, 4.58, 2.37},
                {0.63, -6.18, 3.5, 6.49, 1.66, -2.2}
        };

        // для того, чтобы не брать каждый раз текущий элемент делаем цикл, который сам брать элемент по главной диагонали
        // Gauss.length - 1 - потому что в итоге последняя строка получится так, как нам уже нужно
        for (int step = 0; step < Gauss.length - 1; step++){

            // объявляю текущий элемент
            // step = 1 -> Gauss[1][1] - как раз, что нам и нужно
            double divisor = Gauss[step][step];

            // нормализуем строку step
            /*
            как работает:
            допустим, step = 2 (т.е. 3-я строка)
            j=0: Gauss[2][0] = Gauss[2][0] / divisor
            j=1: Gauss[2][1] = Gauss[2][1] / divisor
            j=2: Gauss[2][2] = Gauss[2][2] / divisor
            и т.д.
             */

            // нормализуем строки
            for (int j = step; j < Gauss[step].length; j++) {

                /*
                для step = 0
                берётся элемент Gauss[0][0] (цикл j тоже начинается с 0)
                и делится на divisor, который в этот момент = Gauss[0][0]
                и так далее по циклу для первой строки
                 */
                Gauss[step][j] = Gauss[step][j] / divisor;

            }

            //обнуление под ведущим элементом
            // берётся i = step + 1 -> i = 1
            for (int i = step + 1; i < Gauss.length; i++) {

                // объявляем ведущий элемент
                double coeff = Gauss[i][step];

                for (int j = step; j < Gauss[i].length; j++) {

                    // обнуляем все до ведущего и кроме него
                    Gauss[i][j] = Gauss[i][j] - coeff * Gauss[step][j];
                }
            }

        }

        // нормализуем последнюю строку
        int lastRow = Gauss.length - 1;

        double lastDivisor = Gauss[lastRow][lastRow];

        if (Math.abs(lastDivisor) > 1e-10) {

            for (int j = lastRow; j < Gauss[lastRow].length; j++) {

                Gauss[lastRow][j] = Gauss[lastRow][j] / lastDivisor;
            }
        }

        // обратный ход метода Гаусса
        int n = Gauss.length;

        double[] solution = new double[n];      // массив для хранения решений

        // начинаем с последней строки - последняя переменная равна свободному члену
        solution[n-1] = Gauss[n-1][n];      // последний столбец содержит свободные члены

        // идем снизу вверх, подставляя найденные значения
        for (int i = n - 2; i >= 0; i--) {

            double sum = Gauss[i][n];       // начинаем со свободного члена

            // вычитаем уже найденные переменные, умноженные на коэффициенты
            for (int j = i + 1; j < n; j++) {

                sum = sum - Gauss[i][j] * solution[j];

            }

            solution[i] = sum; // получаем значение текущей переменной
        }

        // выводим результат с округлением через String.format
        System.out.println("Решение системы методом Гаусса:");

        for (int i = 0; i < solution.length; i++) {

            System.out.println("x" + (i + 1) + " = " + String.format("%.3f", solution[i]));

        }

        System.out.println("\nМатрица после метода Гаусса:");
        for (int i = 0; i < Gauss.length; i++) {

            System.out.print("[");

            for (int j = 0; j < Gauss[i].length; j++) {

                System.out.print(String.format("%.3f", Gauss[i][j]));

                if (j < Gauss[i].length - 1) {

                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }

        // =================== МЕТОД ЖОРДАНА-ГАУССА ===================

        // Создаем копию исходной матрицы, чтобы не портить оригинал и не мешать методу Гаусса
        double[][] JordanGauss = new double[Gauss.length][Gauss[0].length];

        for (int i = 0; i < Gauss.length; i++) {
            // Копируем каждую строку
            System.arraycopy(Gauss[i], 0, JordanGauss[i], 0, Gauss[i].length);
        }

        int jordanN = JordanGauss.length;    // количество уравнений (строк)

        int jordanM = JordanGauss[0].length; // количество столбцов (переменные + свободный член)

        // Основной цикл по шагам (по каждому ведущему элементу на главной диагонали)
        for (int step = 0; step < jordanN; step++) {

            // ============ ШАГ 1: ВЫБОР ГЛАВНОГО ЭЛЕМЕНТА (частичный pivoting) ============
            // Ищем строку с максимальным по модулю элементом в текущем столбце step (от step до конца)
            // Это делается для повышения числовой устойчивости — избегаем деления на малые числа
            int maxRow = step;

            for (int i = step + 1; i < jordanN; i++) {

                if (Math.abs(JordanGauss[i][step]) > Math.abs(JordanGauss[maxRow][step])) {

                    maxRow = i; // нашли строку с большим элементом — запоминаем её

                }
            }

            // Если лучшая строка — не текущая, меняем их местами
            if (maxRow != step) {

                double[] temp = JordanGauss[step];

                JordanGauss[step] = JordanGauss[maxRow];

                JordanGauss[maxRow] = temp;

                System.out.println(" -> Меняем местами строки " + (step+1) + " и " + (maxRow+1) + " для устойчивости.");

            }

            // ============ ШАГ 2: ПРОВЕРКА НА ВЫРОЖДЕННОСТЬ ============
            // Если ведущий элемент всё равно близок к нулю — система вырождена (нет единственного решения)
            if (Math.abs(JordanGauss[step][step]) < 1e-10) {

                System.out.println(" -> Предупреждение: матрица вырождена на шаге " + (step + 1) + ". Продолжаем для демонстрации.");

                continue;
            }

            // ============ ШАГ 3: НОРМАЛИЗАЦИЯ ТЕКУЩЕЙ СТРОКИ ============
            // Делим всю строку step на ведущий элемент, чтобы он стал равен 1
            double divisor = JordanGauss[step][step];

            System.out.println(" -> Нормализуем строку " + (step+1) + ": делим на " + String.format("%.3f", divisor));

            for (int j = step; j < jordanM; j++) {

                JordanGauss[step][j] /= divisor;

            }

            // ============ ШАГ 4: ОБНУЛЕНИЕ ВСЕХ ЭЛЕМЕНТОВ В СТОЛБЦЕ step, КРОМЕ ВЕДУЩЕГО ============
            // Проходим по всем строкам, кроме текущей (step)
            for (int i = 0; i < jordanN; i++) {

                if (i != step) { // пропускаем текущую строку — там уже 1

                    // Коэффициент — это элемент, который нужно обнулить
                    double coeff = JordanGauss[i][step];

                    if (Math.abs(coeff) < 1e-10) continue; // если уже ~0 — пропускаем

                    System.out.println(" -> Обнуляем элемент [" + (i+1) + "][" + (step+1) + "] = " + String.format("%.3f", coeff) +
                            " с помощью строки " + (step+1));

                    // Вычитаем из строки i строку step, умноженную на coeff
                    // Цель: сделать JordanGauss[i][step] = 0
                    for (int j = step; j < jordanM; j++) {

                        JordanGauss[i][j] -= coeff * JordanGauss[step][j];

                    }
                }
            }
        }

        // ============ ШАГ 5: ИЗВЛЕЧЕНИЕ РЕШЕНИЯ ============
        // После всех преобразований левая часть (первые n столбцов) должна быть единичной матрицей
        // Тогда в последнем столбце (индекс n) — сразу готовые значения x1, x2, ..., xn
        double[] jordanSolution = new double[jordanN];

        for (int i = 0; i < jordanN; i++) {

            jordanSolution[i] = JordanGauss[i][jordanN]; // решения лежат в последнем столбце

        }

        // ============ ВЫВОД РЕЗУЛЬТАТА ============
        System.out.println("\n\nРешение системы методом Жордана-Гаусса:");

        for (int i = 0; i < jordanSolution.length; i++) {

            System.out.println("x" + (i + 1) + " = " + String.format("%.3f", jordanSolution[i]));

        }

        System.out.println("\nМатрица после метода Жордана-Гаусса:");

        for (int i = 0; i < JordanGauss.length; i++) {

            System.out.print("[");

            for (int j = 0; j < JordanGauss[i].length; j++) {

                System.out.print(String.format("%.3f", JordanGauss[i][j]));

                if (j < JordanGauss[i].length - 1) {

                    System.out.print(", ");

                }
            }
            System.out.println("]");
        }
    }
}