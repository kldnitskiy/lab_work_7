package shared.commands;

import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

import java.io.Serializable;

public interface Command extends Serializable {
    Message execute(CollectionWorker collectionManager, User user);
}
