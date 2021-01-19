package shared.commands;

import shared.city.City;
import shared.collection.CollectionWorker;
import shared.message.Message;
import shared.message.User;

import java.io.Serializable;

public class Add implements Command {
    private final City city;

    public Add(City city) {
        this.city = city;
    }

        @Override
        public Message execute(CollectionWorker collectionWorker, User user) {
            return collectionWorker.add(city, user);
        }
    }
