/*

Автор: Прасков Д. Е. (23 вариант) (https://github.com/VetrovSV/OOP/blob/master/C/tasks.md)         <- ссылка на табличку с заданиями
                                  (https://ivtipm.github.io/Programming/Glava04/index04.htm#z78)   <- ссылка на задание

Задача 78в:
Вычислить сумму ряда, заданного формулой:

//S = 1/a + 1/(a*(a+1)) + 1/(a*(a+1)*(a+2)) + ... + 1/(a*(a+1)*(a+2)*...*(a+n))

Где:
- a — целое число, не равное нулю
- n — натуральное число (n ≥ 0)

*/

import java.util.Scanner;

// public - означает, что класс можно использовать из любого другого класса
public class main_78v {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите число а: ");
        double a = scanner.nextDouble();

        System.out.print("Введите число n: ");
        int n = scanner.nextInt();

        double sum = 0.0;

        double product = 1.0;  // для дальнейшего накапливания произведения

        // рассчитываем остальные слагаемые, используя предыдущее произведение
        for (int i = 1; i <= n; i++) {

            // тут я получаю 1 * а (т.к. цикл идёт с 1, то получается число а)
            product *= (a + i - 1);

            // так и получаю первое слагаемое
            double term = 1.0 / product;

            // записываю её в сумму
            sum += term;

            /*

            Далее у меня по циклу
            i = 2;
            product = a + 1 - уже второе слогаемое
            term = 1 / a (т.к самый первый product получился равный а) * (a+1)
            sum = (1/a) + (1/(a * (a+1)))
            и т.д.

             */

            // переменные i, term, product будет преобразовано в строковый тип.
            System.out.println("слагаемое для i=" + i + ": " + term + " (знаменатель = " + product + ")");
        }
        System.out.println("Итоговая сумма = " + sum);
    }
}