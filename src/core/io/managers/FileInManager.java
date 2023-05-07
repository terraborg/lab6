package core.io.managers;

import java.util.Scanner;

/**
 * Манаджер по вводу данных из файла, не выводит в консоль приглашения для ввода, но выводит сообщения о результате выполнения комманд
 */
public class FileInManager extends IOManager{
    public FileInManager(Scanner in) {
        super(in);
    }

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void printInv(String message) {

    }
}
