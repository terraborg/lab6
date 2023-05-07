package core.io.managers;

import java.util.Scanner;

/**
 * Абстрактный класс для менеджера ввода и вывода
 */
public abstract class IOManager {
    private final Scanner in;

    public IOManager(Scanner in) {
        this.in = in;
    }

    /**
     * Функция для вывода сообщения
     * @param message - выводимое сообщение
     */
    abstract public void print(String message);

    /**
     * Функция для вывода сообщения приглашения к вводу данных
     * @param message - выводимое сообщение
     */
    abstract public void printInv(String message);

    public Scanner getIn() {
        return in;
    }
}
