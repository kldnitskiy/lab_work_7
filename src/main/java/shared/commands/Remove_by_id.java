package shared.commands;

import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Remove_by_id implements Command {
    private final Long id;
    public Remove_by_id(Long id){this.id = id;}

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.removeByID(id, user);
    }
}

