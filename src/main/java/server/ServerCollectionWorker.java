package server;

import shared.city.City;
import shared.city.Government;
import shared.collection.CollectionWorker;
import shared.commands.Command;
import shared.message.CollectionMessage;
import shared.message.Message;
import shared.message.User;

import java.util.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ServerCollectionWorker implements CollectionWorker {
    private LocalDateTime time;
    private List<City> cities;
    private final Database database;

    public ServerCollectionWorker(Database database) {
        cities = Collections.synchronizedList(new ArrayList<>(database.readAllCities()));
        this.time = LocalDateTime.now();
        this.database = database;
        cities.addAll(database.readAllCities());

    }


    @Override
    public Message help(User user) {
        return new Message("Вызвана команда help, идёт вывод доступных комманд.\n" +
                "help : вывести справку по доступным командам.\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении.\n" +
                "add {element} : добавить новый элемент в коллекцию.\n" +
                "update {id} : обновить значение элемента коллекции, id которого равен заданному.\n" +
                "remove_by_id {id} : удалить элемент из коллекции по его id.\n" +
                "clear : очистить коллекцию.\n" +
                "execute_script : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "add_if_max {element} : добавить новый элемент, если он превышает максимальный элемент коллекции.\n" +
                "add_if_min {element} : добавить новый элемент, если он не превышает минимальный элемент коллекции.\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный.\n" +
                "print_field_ascending_population_density : вывести в порядке возрастания поля population_density элементов коллекции.\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания.");
    }

    @Override
    public Message removeByID(Long id, User owner) {
        // cities = cities.stream().filter(h -> !h.getId().equals(id)).collect(Collectors.toCollection(() -> new PriorityQueue<>()));
        City city = cities.stream().filter(h -> h.getId().equals(id)).findAny().orElse(null);
        if (city == null) {
            return new Message("Элемент не найден");
        }
        if (city.getOwnerUsername().equals(owner.getName())) {
            if (database.removeCity(id)) {
                return new Message("Элемент удалён");
            } else {
                return new Message("В базе данных ошибка.");
            }
        } else {
            return new Message("Вы кто такой, я вас не звал, это не ваш объект. Идите кхм-кхм, отдыхайте!");
        }
    }

    /**
     * if (number == cities.size()) {
     * return new Message("Элемент не найден");
     * }
     * boolean a;
     * a = database.removeCity(id);
     * if (a) {
     * return new Message("Элемент удалён");
     * } else {
     * return new Message("В базе данных ошибка.");
     * }
     * }
     **/

    @Override
    public Message remove_any_by_government(Government gov, User user) {
        ArrayList<City> toRemove = cities.stream()
                .filter(h -> h.getGovernment().equals(gov))
                .filter(h -> h.getOwnerUsername().equals(user.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
        int checker=0;
        for(int i=0;i<toRemove.size();++i){
            City city = toRemove.get(i);
            if (database.removeCity(city))++checker;
            cities.remove(city);
        }
        return new Message("Все созданные вами городишки с выбранным гос. аппаратом уничтожены. Уничтожено "+String.valueOf(checker)+" элементов.");
    }

    /**
     * PriorityQueue<City> oldCollection = cities;
     * cities = cities.stream().filter(h -> {
     * if (h.getGovernment().equals(gov)){
     * if (database.removeCity(h.getId())){
     * return false;
     * }
     * else return true;
     * }
     * return true;
     * }
     * ).collect(Collectors.toCollection(() -> new PriorityQueue<>()));
     * if (oldCollection.size() == cities.size()) {
     * return new Message("Элементы не найдены");
     * }
     * return new Message("Элементы удалены");
     * }
     **/

    @Override
    public Message clear(User user) {
        String username = user.getName();
        List<City> toRemove = cities.stream()
                .filter(p -> p.getOwnerUsername().equals(username))
                .collect(Collectors.toList());

        if (toRemove.size() > 0) {
            boolean[] success = new boolean[]{true};

            toRemove.forEach(p -> {
                if (database.removeCity(p))
                    cities.remove(p);
                else
                    success[0] = false;
            });

            return success[0]
                    ? new Message("Все элементы были удалены")
                    : new Message("Не все элементы были удалены из-за ошибки в БД");
        }

        return new Message("У вас нет элементов для удаления");
    }


    private City getMaxCity() {
        return cities.stream().max(City::compareTo).orElse(null);
    }

    private City getMinCity() {
        return cities.stream().min(City::compareTo).orElse(null);
    }

    @Override
    public Message info(User user) {
        StringBuilder parasha = new StringBuilder();
        parasha.append("Вызвана команда info. Информация о коллекции:");
        parasha.append("\nТип коллекции: PriorityQueue");
        parasha.append("\nКоллекция содержит элементы класса: City");
        parasha.append(String.format("\nКоличество элементов коллекции: %d\n", this.cities.size()));
        if (this.cities.size() > 0) {
            parasha.append(String.format("\nДата инициализации коллекции: %s\n", this.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            parasha.append(String.format("\nМаксимальный элемент коллекции: \n%s\n", this.getMaxCity().toString()));
        }
        return new Message(parasha.toString());
    }

    @Override
    public Message add(City city, User owner) {
        if (database.initializeAndInsertCity(city, owner)) {
            cities.add(city);
            return new Message("Город добавлен.");
        } else {
            return new Message("Город не добавлен из-за ошибки в Базе данных.");
        }
    }

    @Override
    public Message show(User user) {
        return new CollectionMessage("collection", cities.toArray(new City[0]));
    }

    @Override
    public Message update(City newCity, Long id, User owner) {
        City toRemove = cities.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (toRemove == null)
            return new Message("Городов с id " + id+" не существует.");

        if (!toRemove.getOwnerUsername().equals(owner.getName()))
            return new Message("Город с id = " + id + " не принадлежит вам. Не трогать!");

        newCity.setOwnerUsername(owner.getName());
        newCity.setId(id);


        if (database.removeCity(id) && database.insertCityWithoutInitialization(newCity)) {
            cities.remove(toRemove);
            cities.add(newCity);
            return new Message("Город с id = " + id + " обновлён.");
        } else {
            return new Message("Ошибка БД.");
        }
    }

    @Override
    public Message add_if_min(City city, User owner) {
        long elementsLowerThanThis = cities.stream()
                .filter(p -> p.compareTo(city) <= 0)
                .count();

        return elementsLowerThanThis == 0
                ? add(city, owner)
                : new Message("Элемент превышает минимальный элемент в коллекции и не был добавлен.");
    }

    @Override
    public Message add_if_max(City city, User owner) {
        long elementsLargerThanThis = cities.stream()
                .filter(p -> p.compareTo(city) >= 0)
                .count();

        return elementsLargerThanThis == 0
                ? add(city, owner)
                : new Message("Элемент не превышает наибольший элемент коллекции и не был добавлен.");
    }

    @Override
    public Message print_ascending(User user) {
        return new Message(String.join("\n", cities.stream().sorted().map(City::toString).toArray(h -> new String[h])));
    }

    @Override
    public Message print_field_ascending_population_density(User user) {
        return new Message(String.join("\n", cities.stream().sorted().map(City::getPopulationDensity).map(String::valueOf).toArray(h -> new String[h])));
    }

    @Override
    public Message remove_lower(City city, User owner) {
        List<City> toRemove = cities.stream()
                .filter(p -> p.compareTo(city) < 0)
                .filter(p -> p.getOwnerUsername().equals(city.getOwnerUsername()))
                .collect(Collectors.toList());

        int[] cnt = new int[]{0};
        toRemove.forEach(p -> {
            if (database.removeCity(p)) {
                cities.remove(p);
                ++cnt[0];
            }
        });

        String answer = cnt[0] + " городишек уничтожено.";
        if (toRemove.size() != cnt[0])
            answer += "\n Ошибка БД.";

        return new Message(answer);
    }

    public List<City> getCollection() {
        return cities;
    }

    public Message execute(Command command, User user) {
        return command.execute(this, user);
    }
}
