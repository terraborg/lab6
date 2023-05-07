package core.commands.structure;

import core.managers.structure.CallbackConsumer;

import java.io.Serial;
import java.io.Serializable;

public class CommandUnit implements Serializable {
    @Serial
    private static final long serialVersionUID = 4842046006849211136L;
    private final String name;
    private final Serializable[] args;

    public CallbackConsumer getClient() {
        return client;
    }

    private transient final CallbackConsumer client;

    public CommandUnit(String name, Serializable... args) {
        this.name = name.trim().split(" ")[0];
        this.args = args;
        client = null;
    }

    public CommandUnit(String name,CallbackConsumer client, Serializable... args) {
        this.name = name.trim().split(" ")[0];
        this.args = args;
        this.client = client;
    }

    public Serializable[] getArgs() {
        return args;
    }

    public String getName() {
        return name;
    }
}
