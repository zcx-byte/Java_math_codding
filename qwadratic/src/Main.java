import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int n = 4;
        double[][] A = new double[n][n];
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];
        double[] B = new double[n];
        double[] Y = new double[n];
        double[] X = new double[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                A[i][j] = rand.nextDouble();
                A[j][i] = A[i][j];
            }
            A[i][i] += n;
        }

        for (int i = 0; i < n; i++) {
            B[i] = rand.nextDouble();
        }

        L[0][0] = Math.sqrt(A[0][0]);

        for (int i = 1; i < n; i++) {
            L[i][0] = A[i][0] / L[0][0];
        }

        for (int k = 1; k < n; k++) {
            double sqSum = 0;
            for (int m = 0; m < k; m++) {
                sqSum += L[k][m] * L[k][m];
            }
            L[k][k] = Math.sqrt(A[k][k] - sqSum);

            if (k < n - 1) {
                for (int i = k + 1; i < n; i++) {
                    double pairSum = 0;
                    for (int m = 0; m < k; m++) {
                        pairSum += L[i][m] * L[k][m];
                    }
                    L[i][k] = (A[i][k] - pairSum) / L[k][k];
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                U[i][j] = L[j][i];
            }
        }

        Y[0] = B[0] / L[0][0];
        for (int i = 1; i < n; i++) {
            Y[i] = B[i];
            for (int m = 0; m < i; m++) {
                Y[i] -= L[i][m] * Y[m];
            }
            Y[i] /= L[i][i];
        }

        X[n - 1] = Y[n - 1] / U[n - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            X[i] = Y[i];
            for (int m = i + 1; m < n; m++) {
                X[i] -= U[i][m] * X[m];
            }
            X[i] /= U[i][i];
        }

        System.out.println("L:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%.4f ", L[i][j]);
            }
            System.out.println();
        }

        System.out.println();

        System.out.println("U:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%.4f ", U[i][j]);
            }
            System.out.println();
        }

        System.out.println();

        System.out.print("Y = [");
        for (int i = 0; i < n; i++) {
            System.out.printf("%.4f ", Y[i]);
        }
        System.out.println("]");

        System.out.print("X = [");
        for (int i = 0; i < n; i++) {
            System.out.printf("%.4f ", X[i]);
        }
        System.out.println("]");
    }
}