public class Main {

    // ---------- Уравнение (a): 6x + 5^x + 4 = 0 ----------
    static double fA(double x) {
        return 6 * x + Math.pow(5, x) + 4;
    }

    static double dfA(double x) {
        return 6 + Math.log(5) * Math.pow(5, x);
    }

    static double ddfA(double x) {
        double ln5 = Math.log(5);
        return ln5 * ln5 * Math.pow(5, x);
    }

    // ---------- Уравнение (b): 2x^3 + 4x^2 + x + 3 = 0 ----------
    static double fB(double x) {
        return 2 * x * x * x + 4 * x * x + x + 3;
    }

    static double dfB(double x) {
        return 6 * x * x + 8 * x + 1;
    }

    static double ddfB(double x) {
        return 12 * x + 8;
    }

    // ---------- Метод хорд ----------
    static double chords(double a, double b, double eps, int eq) {
        double fa, fb, fc, cNew, cOld;

        // Вычисляем c0
        // сокращённый if-else
        fa = (eq == 1) ? fA(a) : fB(a);
        fb = (eq == 1) ? fA(b) : fB(b);
        cNew = (a * fb - b * fa) / (fb - fa);

        do {
            fc = (eq == 1) ? fA(cNew) : fB(cNew);
            fa = (eq == 1) ? fA(a) : fB(a);

            if (fa * fc < 0) {
                b = cNew;
            } else {
                a = cNew;
            }

            cOld = cNew;
            fa = (eq == 1) ? fA(a) : fB(a);
            fb = (eq == 1) ? fA(b) : fB(b);
            cNew = (a * fb - b * fa) / (fb - fa);

        } while (Math.abs(cNew - cOld) >= eps);

        return cNew;
    }

    // ---------- Метод касательных ----------
    static double tangents(double a, double b, double eps, int eq) {
        double fa, fb, ddf_a, ddf_b, dNew, dOld;

        // Выбираем начальную точку
        fa = (eq == 1) ? fA(a) : fB(a);
        fb = (eq == 1) ? fA(b) : fB(b);
        ddf_a = (eq == 1) ? ddfA(a) : ddfB(a);
        ddf_b = (eq == 1) ? ddfA(b) : ddfB(b);

        if (fa * ddf_a > 0) {
            dNew = a - fa / ((eq == 1) ? dfA(a) : dfB(a));
        } else if (fb * ddf_b > 0) {
            dNew = b - fb / ((eq == 1) ? dfA(b) : dfB(b));
        } else {
            // fallback — берём a
            dNew = a - fa / ((eq == 1) ? dfA(a) : dfB(a));
        }

        do {
            dOld = dNew;
            fa = (eq == 1) ? fA(a) : fB(a);
            fb = (eq == 1) ? fA(b) : fB(b);
            ddf_a = (eq == 1) ? ddfA(a) : ddfB(a);
            ddf_b = (eq == 1) ? ddfA(b) : ddfB(b);

            if (fa * ddf_a > 0) {
                a = dNew;
                dNew = a - ((eq == 1) ? fA(a) : fB(a)) / ((eq == 1) ? dfA(a) : dfB(a));
            } else if (fb * ddf_b > 0) {
                b = dNew;
                dNew = b - ((eq == 1) ? fA(b) : fB(b)) / ((eq == 1) ? dfA(b) : dfB(b));
            } else {
                a = dNew;
                dNew = a - ((eq == 1) ? fA(a) : fB(a)) / ((eq == 1) ? dfA(a) : dfB(a));
            }

        } while (Math.abs(dNew - dOld) >= eps);

        return dNew;
    }

    // ---------- Комбинированный метод ----------
    static double combined(double a, double b, double eps, int eq) {
        double fa, fb, ddf_a, ddf_b, c, d;

        do {
            fa = (eq == 1) ? fA(a) : fB(a);
            fb = (eq == 1) ? fA(b) : fB(b);
            c = (a * fb - b * fa) / (fb - fa);

            ddf_a = (eq == 1) ? ddfA(a) : ddfB(a);
            ddf_b = (eq == 1) ? ddfA(b) : ddfB(b);

            if (fa * ddf_a > 0) {
                d = a - fa / ((eq == 1) ? dfA(a) : dfB(a));
                a = d;
                b = c;
            } else if (fb * ddf_b > 0) {
                d = b - fb / ((eq == 1) ? dfA(b) : dfB(b));
                b = d;
                a = c;
            } else {
                a = (a + b) / 2;
                b = a;
                break;
            }

        } while (Math.abs(b - a) >= 2 * eps);

        return (a + b) / 2.0;
    }

    // ---------- Главный метод ----------
    public static void main(String[] args) {
        double a = -1.0, b = 1.0;
        double eps = 0.000001;

        System.out.println("=== Уравнение (a): 6x + 5^x + 4 = 0 ===");
        System.out.printf("Метод хорд:           %.8f%n", chords(a, b, eps, 1));
        System.out.printf("Метод касательных:    %.8f%n", tangents(a, b, eps, 1));
        System.out.printf("Комбинированный метод: %.8f%n", combined(a, b, eps, 1));

        System.out.println("\n=== Уравнение (b): 2x^3 + 4x^2 + x + 3 = 0 ===");
        System.out.printf("Метод хорд:           %.8f%n", chords(a, b, eps, 2));
        System.out.printf("Метод касательных:    %.8f%n", tangents(a, b, eps, 2));
        System.out.printf("Комбинированный метод: %.8f%n", combined(a, b, eps, 2));
    }
}