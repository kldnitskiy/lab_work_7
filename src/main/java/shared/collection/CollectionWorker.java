package shared.collection;

import shared.city.City;
import shared.city.Government;
import shared.commands.Command;
import shared.message.Message;
import shared.message.User;

import java.io.File;

public interface CollectionWorker {

    Message help(User user);

    Message removeByID(Long id, User user);

    Message remove_any_by_government(Government gov, User user);

    Message clear(User user);

    Message info(User user);

    Message add(City city, User owner);

    Message show(User user);

    Message update(City city, Long id, User owner);

    Message add_if_min(City city, User owner);

    Message add_if_max(City city, User owner);

    Message print_ascending(User user);

    Message print_field_ascending_population_density(User user);

    Message remove_lower(City city, User owner);


}
