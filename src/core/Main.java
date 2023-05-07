package core;

import core.commands.ShowCommandFactory;
import core.io.managers.ConsoleManager;
import core.io.readers.IdInput;
import core.io.readers.PortInput;
import core.io.readers.structure.WrongInputException;
import core.managers.ClientManager;
import core.managers.ConnectionManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);
        var in = new ConsoleManager(sc);
        int port = 8000;
        try {
            port = new PortInput(in,"Введите порт подключения port = ").read();
        } catch (WrongInputException ignored) {

        }catch (NoSuchElementException e)
        {
            System.exit(0);
        }

        var c = new ClientManager(port);

        while(true)
        {
            try {
                c.execute(in.getIn().next(), in);
            }catch (NoSuchElementException e)
            {
                c.execute("exit",in);
            }
        }
    }
}
