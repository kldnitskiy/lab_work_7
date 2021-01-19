package shared.commands;

import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Print_ascending implements Command {

    @Override
    public Message execute(CollectionWorker collectionManager, User user) {return collectionManager.print_ascending(user);}
}
