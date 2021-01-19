package shared.commands;

import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Print_field_ascending_population_density implements Command {

    @Override
    public Message execute(CollectionWorker collectionManager, User user) {return collectionManager.print_field_ascending_population_density(user);}
}
