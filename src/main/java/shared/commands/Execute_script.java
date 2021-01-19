package shared.commands;

import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Execute_script implements Command {
    private final Command[] commands;

    public Execute_script(Command[] commands) {
        this.commands=commands;
    }


    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return null;
    }

    public Command[] getCommands() {
        return commands;
    }
}
