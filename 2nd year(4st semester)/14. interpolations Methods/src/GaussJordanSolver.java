public class GaussJordanSolver {

    // Метод для обмена двух строк в матрице
    public static void swapRows(int i, int j, double[][] matrix) {
        int cols = matrix[0].length;
        for (int k = 0; k < cols; k++) {
            double temp = matrix[i][k];
            matrix[i][k] = matrix[j][k];
            matrix[j][k] = temp;
        }
    }

    // Реализация алгоритма Гаусса-Жордана (аналог подпрограммы JG)
    public static void gaussJordan(double[][] matrix) {
        int minRow = 0;
        int maxRow = matrix.length - 1;
        int minCol = 0;
        int maxCol = matrix[0].length - 1;
        double epsilon = 0.00001;

        // Проходим по столбцам (кроме последнего, так как это столбец свободных членов)
        for (int k = minCol; k < maxCol; k++) {

            // Поиск ведущего элемента (пивотинг)
            if (Math.abs(matrix[k][k]) < epsilon) {
                int o = k + 1;
                while (true) {

                    // Если нашли ненулевой элемент или вышли за границы
                    if (Math.abs(matrix[o][k]) > epsilon || o > maxRow) {
                        break;
                    }
                    o++;
                }

                // Меняем строки местами, только если найденная строка в пределах матрицы
                if (o <= maxRow) {
                    swapRows(k, o, matrix);
                }
            }

            // Исключение переменной из других строк
            for (int i = minRow; i <= maxRow; i++) {
                for (int j = k + 1; j <= maxCol; j++) {
                    if (i != k) {

                        // Формула из кода
                        matrix[i][j] = (matrix[i][j] * matrix[k][k] - matrix[k][j] * matrix[i][k]) / matrix[k][k];
                    }
                }
            }

            // Обнуление столбца под и над ведущим элементом
            for (int i = minRow; i <= maxRow; i++) {
                if (i != k) {
                    matrix[i][k] = 0;
                }
            }

            // Нормализация ведущей строки
            for (int j = maxCol; j >= k; j--) {
                matrix[k][j] = matrix[k][j] / matrix[k][k];
            }
        }
    }

    public static void main(String[] args) {
        final int n = 9;

        // В Java размерность указывается явно: (n + 1) строк, (n + 2) столбцов
        double[][] m = new double[n + 1][n + 2];

        // Здесь мы генерируем случайные данные для демонстрации работы алгоритма
        java.util.Random rand = new java.util.Random();

        for (int i = 0; i <= n; i++) {

            // Генерируем базовое число для строки (аналог Cells(i + 2, 2))
            double baseValue = rand.nextDouble() * 10.0;

            for (int j = 0; j <= n + 1; j++) {
                if (j == n + 1) {

                    // Последний столбец - свободные члены (аналог Cells(i + 2, 3))
                    m[i][j] = rand.nextDouble() * 100.0;
                } else {

                    // Заполнение степенями (аналог Cells(i + 2, 2) ^ j)
                    m[i][j] = Math.pow(baseValue, j);
                }
            }
        }

        // Решение системы
        gaussJordan(m);

        // Вывод результатов (вместо записи в Cells(i + 2, 6))
        System.out.println("Результаты решения (последний столбец матрицы):");
        for (int i = 0; i <= n; i++) {
            System.out.printf("x[%d] = %.6f%n", i, m[i][n + 1]);
        }
    }
}