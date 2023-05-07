package core.commands.structure;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Класс в котором хранится обратная связь передаваемоя от команды клиенту
 */

public class CallbackUnit implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private final boolean hasMessage;
    private final boolean isSuccess;

    private transient final boolean isLast;
    private String message;

    private Serializable[] args;

    public boolean isLast() {
        return isLast;
    }

    public CallbackUnit(boolean isSuccess) {
        this.isSuccess = isSuccess;
        hasMessage = false;
        isLast = true;
    }

    public CallbackUnit(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
        hasMessage = true;
        isLast = true;
    }
    public CallbackUnit(boolean isSuccess, String message, Serializable... args) {
        this.isSuccess = isSuccess;
        this.message = message;
        hasMessage = true;
        this.args=args;
        isLast = true;
    }
    public CallbackUnit(boolean isSuccess, boolean isLast, String message, Serializable... args) {
        this.isSuccess = isSuccess;
        this.message = message;
        hasMessage = true;
        this.args=args;
        this.isLast = isLast;
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
            return message + Arrays.toString(args);
        return message;
    }

}
