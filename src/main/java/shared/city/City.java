package shared.city;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.PriorityQueue;

/**
 * Класс города для описания хранящихся в коллекции данных
 */
public class City implements Comparable<City>, Serializable {
    /**
     * ID города Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     */
    private String owner_username;
    private long id;
    private static long numOfCities = 0;
    /**
     * Название города. Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Координаты города. Не может быть null.
     */
    private Coordinates coordinates;
    /**
     * Время создания организации.
     * Т.к. очень неприятно работать с временем, однако в задании описана именно LocalDateTime юзаем как дату через форматтер
     * Потому что мы можем.
     */
    private LocalDate creationDateTime;
    /**
     * Площадь города. Не сразу допёр чё это. Значение поля должно быть больше 0, Поле не может быть null
     */
    private Integer area;
    /**
     * Значение населения. Значение поля должно быть больше 0, Поле не может быть null
     * ну типо кек, город без людей же есть 100%, что за дискриминация а?!)
     */
    private Integer population;
    /**
     * Географическое указание высоты над уровнем моря. Чаще всего положительно)0)0.
     */
    private int metersAboveSeaLevel;
    /**
     * Плотность населения на кв. км. Значение поля должно быть больше 0.
     */
    private int populationDensity;
    /**
     * ну это надеюсь описывать не надо... Поле может быть null
     */
    private Climate climate;//Поле может быть null
    /**
     * Поле с государственным строем.
     * Классная идея реализации через перечисление, т.к. даёт идею для написания консольной стратегической игры с видами гос-в.
     * очень удобно для описания классов в играх.
     * Поле может быть null
     */
    private Government government;

    private Human governor;//не может быть нулл.

    public City(String name, Coordinates coordinates, LocalDate creationDateTime, Integer area, Integer population, int populationDensity, int metersAboveSeaLevel, Government government, Human governor, Climate climate) {

        this.name = name;
        this.coordinates = coordinates;
        this.creationDateTime = creationDateTime;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.populationDensity = populationDensity;
        this.government = government;
        this.governor = governor;
        this.climate = climate;
    }

    public City() {
    } //конструктор для использования в команде Add

    //Далее идут геттеры и сеттеры

    /**
     * Метод для установки названия города
     *
     * @param name - имя
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerUsername(String name) {
        this.owner_username = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDateTime(LocalDate date) {
        this.creationDateTime = date;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setPopulationDensity(int populationDensity) {
        this.populationDensity = populationDensity;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setMetersAboveSeaLevel(int metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    // геттеры
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public int getArea() {
        return this.area;
    }

    public int getPopulation() {
        return this.population;
    }

    public LocalDate getCreationDateTime() {
        return this.creationDateTime;
    }

    public int getPopulationDensity() {
        return this.populationDensity;
    }

    public Government getGovernment() {
        return this.government;
    }

    public int getMetersAboveSeaLevel() {
        return this.metersAboveSeaLevel;
    }

    public Climate getClimate() {
        return this.climate;
    }

    public Human getGovernor() {
        return this.governor;
    }

    public String getOwnerUsername() {
        return this.owner_username;
    }

    public void generateID() {
        id = ++numOfCities;
        this.id = id;
    }

    @Override
    public String toString() {
        return (this.owner_username+"; "+this.name + "; " + this.id + "; " + this.governor + "; " + this.climate + "; " + this.populationDensity + "; " + this.population + "; " + this.getMetersAboveSeaLevel() + "; " + this.government.toString() + "; " + this.area);
    }

    public static boolean idIsUnique(Long id, PriorityQueue<City> queue) {
        for (City city : queue) {
            if (city.getId() == id) {
                return false;
            }
        }
        return true;
    }

    // Сравнение объектов из коллекции по id
    @Override
    public int compareTo(City o) {
        return (int) (this.populationDensity - ((City) o).populationDensity);
    }
    //Результат положителен если объект больше сравнимаего, отрицателен, если меньше и равен 0, если объекты равны.
}
