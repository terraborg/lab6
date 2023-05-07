package core.commands.structure;

import core.managers.CallbackManager;
import core.managers.structure.CallbackConsumer;

import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

public class CommandUnit implements Serializable {
    @Serial
    private static final long serialVersionUID = 4842046006849211136L;
    private final String name;
    private final Serializable[] args;

    private transient CallbackConsumer consumer;

    public void setConsumer(CallbackConsumer consumer) {
        this.consumer = consumer;
    }

    public CommandUnit(String name, Serializable... args) {
        this.name = name;
        this.args = args;
        consumer = CallbackManager.getEmpty();
    }

    public Serializable[] getArgs() {
        return args;
    }

    public String getName() {
        return name;
    }

    public CallbackConsumer getConsumer() {
        return consumer;
    }
}
