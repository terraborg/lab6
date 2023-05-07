package core.managers;

import core.commands.structure.CommandUnit;
import core.managers.structure.ServerContext;
import core.utils.Lgr;
import org.apache.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class ConnectionAcceptManager {
    private final int port;

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private Logger logger = Lgr.getLogger();

    public ConnectionAcceptManager(int port) {
        while(true) {
            try {
                selector = Selector.open();
                break;
            } catch (IOException ignored) {}
        }

        while (true) {
            try {
                this.serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                break;
            } catch (IOException e) {
                port++;
            }
        }
        logger.info("Подключен к порту номер " + port);
        this.port = port;
    }

    private ByteBuffer buffer = ByteBuffer.allocate(1024*4);

    public void check(ServerContext server) throws IOException {
        selector.selectNow();
        for (var key : selector.selectedKeys()) {
            if (key.isAcceptable()) {
                connect();
            }
            if(key.isReadable()){
                try {
                    handle(key,server);
                } catch (ClassNotFoundException | IOException e) {
                    //e.printStackTrace();
                }
            }
        }
        checkForUseless();
    }

    private void checkForUseless() throws IOException {
        for (var key : selector.keys())
        {
            var conDat = (ConnectionData) key.attachment();
            if(conDat == null)
                continue;
            if (conDat.getUselessTime() > 100_000) {
                logger.info("Подключение разорвано после ожидания в " + conDat.getUselessTime() / 1000 + " секунд");
                //System.out.println("Подключение разорвано после ожидания в " + conDat.getUselessTime() / 1000 + " секунд");
                key.channel().close();
                key.cancel();
            }
        }
    }

    private void connect() throws IOException {
        var channel = serverSocketChannel.accept();
        if(channel == null)
            return;
        logger.info("Подключение принято :3");
        //System.out.println();
        channel.configureBlocking(false).register(selector, SelectionKey.OP_READ, new ConnectionData(new ByteArrayOutputStream()));
    }

    private void handle(SelectionKey key, ServerContext server) throws IOException, ClassNotFoundException {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            var conDat = (ConnectionData) key.attachment();
            int n;
            var data = conDat.getData();
            while ((n = channel.read(buffer)) > 0) {
                buffer.flip();
                data.write(buffer.array(), 0, n);
                buffer.clear();
            }
            try {
                if (data.size() != 0) {
                    CommandUnit command = (CommandUnit) new ObjectInputStream(new ByteArrayInputStream(data.toByteArray(), 0, data.size())).readObject();
                    command.setConsumer(new CallbackManager(channel));
                    server.executeCommand(command);
                    conDat.use();
                    data.reset();
                    logger.info("Комманда выполнена");
                }
            }catch (EOFException e){
                //e.printStackTrace();
            }

        }catch (SocketException e)
        {
            //e.printStackTrace();
            key.cancel();
            key.channel().close();
        }
    }

    private static class ConnectionData
    {
        private int countOfUsing = 0;
        private final long timeOfStart;
        private long lastTimeUsed;

        private final ByteArrayOutputStream data;

        public ConnectionData(ByteArrayOutputStream data) {
            this.data = data;
            this.timeOfStart = System.currentTimeMillis();
            this.lastTimeUsed = timeOfStart;
        }
        public void use()
        {
            countOfUsing++;
            lastTimeUsed = System.currentTimeMillis();
        }
        public long getUselessTime()
        {
            return System.currentTimeMillis() - lastTimeUsed;
        }

        public int getCountOfUsing() {
            return countOfUsing;
        }
        public ByteArrayOutputStream getData() {
            return data;
        }
    }

}
