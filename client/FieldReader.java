package client;

import shared.city.Climate;
import shared.city.Government;

import java.util.Scanner;

/**
 * Класс с статическмими методами для вывода сообщений и считывания строчных полей класса
 * {@link shared.city.City}
 * В методы встроена проверка после которой они возвращают значения или кидают эксепшн
 */
public class FieldReader {
    /**
     * Метод для чтения полея типа String
     *
     * @param scanner      - сканер
     * @param notification - сообщение
     * @return возвращает поле строчного типа с клавы
     */
    public static String readString(Scanner scanner, String notification) {
        System.out.print(notification);
        String name = "";
        do {
            name = scanner.nextLine().trim();
            if (name.isEmpty() | name.equals("")) {
                System.out.println("Поле не может быть пустым. Пожалуйста повторите ввод");
            } else {
                break;
            }
        }
        while (true);
        return name;
    }

    /**
     * Ленивый считыватель инт
     * @param scanner - сканр
     * @param msg - сообщение для вывода при вводе
     * @return - возвращает число большое 0
     */
    public static Integer readInt(Scanner scanner, String msg){
        System.out.println(msg);
        int number = -1;
        do {
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number < 0) {
                    System.out.println("Число не может быть меньше 0.");
                    System.out.println("Повторите ввод:");
                }
                else{
                    break;
                }
            }
            catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода. Вводите число без пробелов и разделителей.");
                System.out.println("Повторите ввод:");
            }
        }while (number < 0);
        return number;
    }
    /**
     * Метод для считывания координаты X
     *
     * @param scanner - сканнер
     * @return - возвращает координату X
     */
    public static float readX(Scanner scanner) {
        System.out.println("Ввод координат.");
        System.out.println("Введите х:");
        float x;
        do {
            try {
                x = Float.parseFloat(scanner.nextLine());
                return x;
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите X без пробелов и разделителей!");
            }
        } while (true);

    }

    /**
     * Метод считывающий координату Y с клавы
     *
     * @param scanner - сканер
     * @return возвращает Y считанное с клавы
     */
    public static long readY(Scanner scanner) {
        System.out.println("Ввод координат.");
        System.out.println("Введите Y:");
        long y;
        do {
            try {
                y = Long.parseLong(scanner.nextLine());
                return y;
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите Y без пробелов и разделителей!");
            }
        } while (true);

    }

    /**
     * Метод считывающий площадь города с клавы
     *
     * @param scanner -сканер
     * @return - возвращает площадь>0
     */
    public static int readArea(Scanner scanner) {
        int area = -1;
        boolean pass = true;
        System.out.println("Ввод площади города. Площадь должна быть больше 0.");
        System.out.println("Введите площадь:");
        do {
            try {
                area = Integer.parseInt(scanner.nextLine());
                if (area <= 0) {
                    System.out.println("Площадь не может быть меньше или равна 0.");
                    System.out.println("Повторите ввод:");
                } else {
                    pass = false;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите площадь без пробелов и разделителей!");
                pass = true;
            }
        } while (pass);
        return area;

    }

    /**
     * Метод для считывания населения города
     *
     * @param scanner
     * @return - возвращает население города>0
     */
    public static int readPopulation(Scanner scanner) {
        int population = -1;
        boolean pass = true;
        System.out.println("Ввод населения города. Население должно быть больше 0.");
        System.out.println("Введите население города:");
        do {
            try {
                population = Integer.parseInt(scanner.nextLine());
                if (population <= 0) {
                    System.out.println("Население не может быть меньше или равна 0.");
                    System.out.println("Повторите ввод:");
                } else {
                    pass = false;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите кол-во населения без пробелов и разделителей!");
                pass = true;
            }
        } while (pass);
        return population;
    }

    /**
     * Метод для считывания высоты над уровнем моря
     *
     * @param scanner - сканер
     * @return возвращает значения поля считанного с клавы
     */
    public static int readMetersAboveSeaLevel(Scanner scanner) {
        int number = 9000;
        boolean pass = true;
        System.out.println("Ввод высоты над уровнем моря.");
        System.out.println("Введите высоту над уровнем моря:");
        do {
            try {
                pass = false;
                number = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Ошибка формата ввода.");
                System.out.println("Введите число без пробелов и разделителей.");
                pass = true;
            }
        } while (pass);
        return number;
    }

    public static String readClimate(Scanner scan) {
        System.out.println("Ввод типа климата. Введите строго по примерам ниже");
        System.out.println("RAIN_FOREST");
        System.out.println("HUMIDCONTINENTAL");
        System.out.println("MEDITERRANIAN");
        System.out.println("SUBARCTIC");
        System.out.println("Введите тип климата:");
        String line;
        Climate climate;
        do {
            try {
                line = scan.nextLine();
                if (line.isEmpty()) {
                    System.out.println("Строка не может быть пустой.");
                    System.out.println("Повторите ввод:");
                } else {
                    climate = Climate.valueOf(line);
                    return line;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Тип введён в неправильном формате.");
                System.out.println("Вводить название климата строго по примерам. Климаты городов:");
                System.out.println("RAIN_FOREST");
                System.out.println("HUMIDCONTINENTAL");
                System.out.println("MEDITERRANIAN");
                System.out.println("SUBARCTIC");
                System.out.println("Повторите ввод:");
            }
        } while (true);
    }

    public static int readPopulationDensity(Scanner scan) {
        int populationDensity;
        System.out.println("Ввод плотности населения. Плотность населения должна быть выше 0.");
        System.out.println("Введите плотность населения.");
        do {
            try {
                populationDensity = Integer.parseInt(scan.nextLine());
                if (populationDensity <= 0) {
                    System.out.println("Плотность населения должна быть выше 0. Повторите ввод");
                } else {
                    return populationDensity;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Ошибка формата ввода.");
                System.out.println("Введите число без пробелов и разделителей.");
            }

        }while (true) ;
    }
    public static String readGovernment(Scanner scan) {
        System.out.println("Ввод госсударственного строя. Введите строго по примерам ниже. При отсутствии данной информации не вводите ничего.");
        System.out.println("ANARCHY");
        System.out.println("PLUTOCRACY");
        System.out.println("STRATOCRACY");
        System.out.println("Введите тип гос. строя:");
        String line;
        Government government;
        do {
            try {
                line = scan.nextLine();
                if (line.isEmpty()) {
                    return (null);
                } else {
                    government = Government.valueOf(line);
                    return line;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Тип введён в неправильном формате.");
                System.out.println("Вводить название государственного строя строго по примерам. Типы гос. строя:");
                System.out.println("ANARCHY");
                System.out.println("PLUTOCRACY");
                System.out.println("STRATOCRACY");
                System.out.println("Повторите ввод:");
            }
        } while (true);
    }
    public static String readGovernorName(Scanner scan){
        String name;
        System.out.println("Введите имя гос. руководителя");
        do{
            try {
                name=scan.nextLine();
                if (name==null){
                    throw new NullValueException("Получена пустая строка.");
                }
                if (name==null){
                    throw new InvalidValueException("Получена null строка.");
                }
                return name;
            }
            catch (NullValueException e){
                System.out.println("Введена пустая строка. Повторите ввод.");
            } catch (InvalidValueException e) {
                System.out.println("Введена пустая строка. Повторите ввод.");
            }

        } while(true);
    }
    public static float readGovernorHeight(Scanner scan) {
        float height;
        System.out.println("Введите рост гос. руководителя");
        do {
            try {
                height =Float.parseFloat(scan.nextLine());
                if (height<=0) {
                    throw new InvalidValueException("Получен отрицательный или нулевой рост.");
                }
                return height;
            } catch (InvalidValueException e) {
                System.out.println("Введеный рост меньше или равен нулю. Повторите ввод.");
            }
        } while (true);
    }
}
