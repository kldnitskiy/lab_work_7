package shared.commands;

import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Info implements Command {
    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.info(user);
    }
}

