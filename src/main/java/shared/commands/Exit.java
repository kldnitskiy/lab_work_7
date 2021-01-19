package shared.commands;

import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Exit implements Command {
    @Override
    public Message execute(CollectionWorker collectionManager, User user) {
        return new Message("Оффаем клиент. Тушите свет, ёпта.");
    }
}
