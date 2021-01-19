package client.Instances;

import shared.city.City;


public class CollectionMessage extends Message {

    private final City[] cities;

    public CollectionMessage(String content, City[] cities) {
        super(content);
        this.cities = cities;
    }

    public City[] getCities() {
        return cities;
    }
}
