package core.commands.structure;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс в котором хранится обратная связь передаваемоя от команды клиенту
 */

public class CallbackUnit implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private final boolean hasMessage;
    private final boolean isSuccess;
    private String message;

    private Serializable[] args;

    public Serializable[] getArgs() {
        return args;
    }

    public CallbackUnit(boolean isSuccess) {
        this.isSuccess = isSuccess;
        hasMessage = false;

    }

    public CallbackUnit(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
        hasMessage = true;

    }
    public CallbackUnit(boolean isSuccess, String message, Serializable... args) {
        this.isSuccess = isSuccess;
        this.message = message;
        hasMessage = true;
        this.args=args;
    }
    public boolean isSuccess()
    {
        return isSuccess;
    }
    public boolean hasMessage()
    {
        return hasMessage;
    }
    public String getMessage() {
        if(args!=null && args.length>0)
            return message + Arrays.toString(args).replace(',', ' ').replace(']',' ').replace('[',' ');
        return message;
    }

}
