package shared.commands;

import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

import java.io.Serializable;

public class Clear implements Command, Serializable {

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.clear(user);
    }
}
