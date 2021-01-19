package client.api;

import client.FieldReader;
import shared.city.*;
import shared.commands.*;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.*;

public class InputListener {
    private final BufferedReader input;
    String decoded_argument;
    public InputListener(InputStream inputStream) {
        this.input = new BufferedReader(new InputStreamReader(inputStream));
    }

    public Command readCommand() throws IOException {
        String line = null;
        try {
            line = input.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] input_command = line.split(" ");
        Integer command_length = input_command.length;
        String decoded_command = input_command[0];

        switch (decoded_command) {
            case "help":
                if (command_length == 1) return new Help();
                else{
                    break;
                }
            case "info":
                if (command_length == 1) return new Info();
                else{
                    break;
                }
            case "show":
                if (command_length == 1) return new Show();
                else{
                    break;
                }
            case "print_ascending":
                if (command_length == 1) return new Print_ascending();
                else{
                    break;
                }
            case "print_field_ascending_population_density":
                if (command_length == 1) return new Print_field_ascending_population_density();
                else{
                    break;
                }
            case "clear":
                if (command_length == 1) return new Clear();
                else{
                    break;
                }
            case "add":
                return new Add(readCity());
            case "add_if_max":
                return new Add_if_max(readCity());
            case "add_if_min":
                return new Add_if_min(readCity());
            case "remove_by_id":
                if (command_length == 2){
                    decoded_argument = input_command[1];
                    return new Remove_by_id(Long.parseLong(decoded_argument));
                }
                else{
                    break;
                }
            case "remove_lower":
                if (command_length == 1)
                    return new Remove_lower(readCity());
                else{
                    break;
                }
            case "update":

                if (command_length == 2){
                    decoded_argument = input_command[1];
                    return new Update(readCity(), Long.parseLong(decoded_argument));
                }
                else{
                    break;
                }
            case "exit":
                return new Exit();

            case "execute_script":
                try {
                    if (command_length == 2) {
                        decoded_argument = input_command[1];
                        File file = new File(decoded_argument);
                        if (!file.exists())
                            throw new FileNotFoundException();
                        InputListener commandListener = new InputListener(new FileInputStream(file));
                        return new Execute_script(commandListener.readAllCommands());
                    }
                    else{
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Скрипт некорректен");
                }
            default:
                System.out.println("Вы ввели неверную команду. Вызвана команда help с справкой.");
                return new Help();
        }
        System.out.println("Вы ввели неверную команду. Вызвана команда help с справкой.");
        return new Help();

    }

    public Command[] readAllCommands() throws IOException {
        ArrayList<Command> commands = new ArrayList<>();
        while (input.ready()) {
            commands.add(readCommand());
        }
        return commands.toArray(new Command[0]);
    }

    private City readCity() {
        City city = new City();
        Scanner scanner = new Scanner(System.in);
        city.setName(FieldReader.readString(scanner, "Введите название Города."));
        Float x = FieldReader.readX(scanner);
        Long y = FieldReader.readY(scanner);
        city.setCoordinates(new Coordinates(x, y));
        city.setArea(FieldReader.readArea(scanner));
        city.setClimate(Climate.valueOf(FieldReader.readClimate(scanner)));
        city.setGovernor(new Human(FieldReader.readGovernorName(scanner), FieldReader.readGovernorHeight(scanner)));
        city.setMetersAboveSeaLevel(FieldReader.readMetersAboveSeaLevel(scanner));
        city.setPopulation(FieldReader.readPopulation(scanner));
        city.setPopulationDensity(FieldReader.readPopulationDensity(scanner));
        city.setCreationDateTime(LocalDate.now());
        city.setGovernment(Government.valueOf(FieldReader.readGovernment(scanner)));
        return city;
    }
}

