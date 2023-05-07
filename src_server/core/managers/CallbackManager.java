package core.managers;

import core.commands.structure.CallbackUnit;
import core.managers.structure.CallbackConsumer;
import core.utils.Lgr;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;

public class CallbackManager implements CallbackConsumer {
    public SocketChannel getChannel() {
        return channel;
    }

    private final SocketChannel channel;

    public CallbackManager(SocketChannel channel) {
        this.channel = channel;
    }
    public CallbackManager(){
        channel = null;
    }

    private final ByteArrayOutputStream buf = new ByteArrayOutputStream();
    private ObjectOutputStream out;

    @Override
    public void putCallback(CallbackUnit callbackUnit) {
        if(channel==null) {
            return;
        }
        send(callbackUnit);
    }
    private void send(Serializable obj)
    {
        try {
            out = new ObjectOutputStream(buf);
            out.writeObject(obj);
            channel.write(ByteBuffer.wrap(buf.toByteArray()));
            //channel.socket().getOutputStream().flush();
            out.flush();
            buf.reset();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    static private CallbackManager console;
    static public CallbackManager console()
    {
        if(console != null)
            return console;
        console = new CallbackManager(){
            @Override
            public void putCallback(CallbackUnit callbackUnit) {
                var logger = Lgr.getLogger();
                if(callbackUnit.isSuccess())
                    logger.info("Команда выполнена успешно!");
                if(callbackUnit.hasMessage())
                    logger.info(callbackUnit.getMessage());
            }
        };
        return console;
    }
    static private CallbackManager empty;
    static public CallbackManager getEmpty(){
        if(empty!=null)
            return empty;
        empty = new CallbackManager(){
            @Override
            public void putCallback(CallbackUnit callbackUnit) {

            }
        };
        return empty;
    }
}
