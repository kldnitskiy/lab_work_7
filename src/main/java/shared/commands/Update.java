package shared.commands;

import shared.city.City;
import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

public class Update implements Command {
    private final City city;
    private final Long id;

    public Update(City city, Long id){
        this.city = city;
        this.id = id;
    }
    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.update(city, id, user);
    }
}


