public class Main {
    public static void main(String[] args) {
        int n = 5; // размерность системы

        // Векторы для метода прогонки
        double[] f = new double[n]; // свободные члены
        double[] c = new double[n]; // главная диагональ
        double[] l = new double[n]; // нижняя диагональ (под главной)
        double[] r = new double[n]; // верхняя диагональ (над главной)
        double[] p = new double[n]; // промежуточный вектор p
        double[] q = new double[n]; // промежуточный вектор q
        double[] x = new double[n]; // вектор решения

        // --- Заполнение векторов матрице ---
        f[0] = 9;
        f[1] = 21;
        f[2] = 14;
        f[3] = 20;
        f[4] = 10;

        c[0] = 4;
        c[1] = 6;
        c[2] = 3;
        c[3] = 5;
        c[4] = 6;

        l[0] = 0;
        l[1] = 9;
        l[2] = 6;
        l[3] = 9;
        l[4] = 4;

        r[0] = 5;
        r[1] = 6;
        r[2] = 5;
        r[3] = 6;
        r[4] = 0;

        p[0] = f[0] / c[0];
        q[0] = r[0] / c[0];

        for (int k = 1; k < n; k++){
            p[k] = (f[k] - l[k] * p[k-1]) / (c[k] - l[k] * q[k - 1]);
            q[k] = r[k] / (c[k] - l[k] * q[k-1]);
        }
        x[n - 1] = p[n - 1];

        for (int k = n - 2; k >= 0; k--) {
            x[k] = p[k] - q[k] * x[k + 1];
        }

        // --- вывод результата ---
        System.out.println("вектор решения x:");
        for (int k = 0; k < n; k++) {
            System.out.println("x[" + k + "] = " + x[k]);
        }
    }
}