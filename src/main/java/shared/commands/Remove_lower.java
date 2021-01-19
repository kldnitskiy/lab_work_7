package shared.commands;

import shared.city.City;
import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Remove_lower implements Command {
    private final City city;

    public Remove_lower(City city) {this.city = city;}

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {return collectionWorker.remove_lower(city, user);}
}
