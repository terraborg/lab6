package core.managers;

import core.commands.structure.CallbackUnit;
import core.commands.structure.CommandUnit;
import core.database.DataBaseHolder;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectionManager implements ServerContext {
    private final InetAddress address;

    public void setPort(int port) {
        this.port = port;
    }

    private int port;
    private CallbackConsumer client;
    private final CallbackConsumer clientManager;

    public ConnectionManager(InetAddress address, int port, CallbackConsumer clientManager) throws IOException {
        this.address = address;
        this.port = port;
        client = clientManager;
        this.clientManager = clientManager;
    }


    @Override
    public DataBaseHolder getDataBaseHolder() {
        return null;
    }

    private Socket socket;
    @Override
    public void executeCommand(CommandUnit command) {
        if(socket == null || socket.isClosed())
            try
            {
                this.socket = new Socket(address,port);
            } catch (IOException e) {
                client.putCallback(new CallbackUnit(false,"Сервер временно недоступен"));
                return;
            }


        try {
            socket.setSoTimeout(3_000);
            send(command);
            if(command.getClient()!=null)
                client = command.getClient();
            else
                client = clientManager;
            tryReceive();

        } catch (SocketException e)
        {
            socket = null;
            executeCommand(command);
        } catch (IOException e) {
            //e.printStackTrace();
            client.putCallback(new CallbackUnit(false,"Сервер временно недоступен"));
        }
        catch (ClassNotFoundException e) {
            //e.printStackTrace();
            client.putCallback(new CallbackUnit(false, "Сервер не поддерживает такое форматирование, обновите клиент"));
        }
    }

    private void send(CommandUnit command) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        var b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        out.writeObject(command);
        o.writeObject(command);
        /*System.out.println(Arrays.toString(b.toByteArray()));
        System.out.println(b.toByteArray().length);
        System.out.println(new String(b.toByteArray(),0,b.toByteArray().length));*/
    }

    private void tryReceive() throws IOException, ClassNotFoundException {
        long startTime = -1;
        long waitTime = 7_500;

        while(socket.isConnected()) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                var callback = (CallbackUnit)in.readObject();
                client.putCallback(callback);
                break;
            }catch (SocketTimeoutException e)
            {
                if(startTime == -1) {
                    startTime = System.currentTimeMillis();
                    continue;
                }
                if(System.currentTimeMillis() - startTime <= waitTime)
                    continue;
                System.out.println("Превышено время ожидания ответа от сервера");
                break;
            }catch (StreamCorruptedException e)
            {
                //e.printStackTrace();
            }
        }
    }
}
