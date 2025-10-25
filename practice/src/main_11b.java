
/*

Автор: Прасков Д. Е. (23 вариант) (https://github.com/VetrovSV/OOP/blob/master/C/tasks.md)          <- ссылка на табличку с заданиями
                                  (https://ivtipm.github.io/Programming/Glava01/index01.htm#z11)    <- ссылка на задание
                                  (https://ivtipm.github.io/Programming/Files/spisocall.htm)        <- ссылка на задачник

Задача номер 11б:

        1. a = (3 + e^(y-1)) / (1 + x^2 * |y - tg(z)|)

        2. b = 1 + |y - x| + (y - x)^2 / 2 + |y - x|^3 / 3

    нужно найти а и б

 */

import java.util.Scanner;

public class main_11b {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите число x: ");
        double x = scanner.nextDouble();

        System.out.print("Введите число y: ");
        double y = scanner.nextDouble();

        System.out.print("Введите число z: ");
        double z = scanner.nextDouble();

        double tang_z = Math.tan(z);

        double module_in_a = Math.abs(y - tang_z);

        double module_one_in_b = Math.abs(y - x);

        double module_two_in_b = Math.pow(Math.abs(y - 3), 3);

        double result_a = (3 + Math.exp(y - 1)) / (1 + Math.pow(x, 2) * module_in_a);

        double result_b = 1 + module_one_in_b + Math.pow((y - x), 2) / 2 + module_two_in_b / 3;

        System.out.print("Ваше число а: ");
        System.out.println(result_a);

        System.out.print("Ваше число b: ");
        System.out.println(result_b);
    }
}
