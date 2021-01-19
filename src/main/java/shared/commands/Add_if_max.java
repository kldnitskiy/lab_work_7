package shared.commands;

import shared.city.City;
import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Add_if_max implements Command {
    private final City city;
    public Add_if_max(City city){this.city=city;}
    @Override
    public Message execute(CollectionWorker collectionWorker, User user) { return collectionWorker.add_if_max(city, user); }
}
