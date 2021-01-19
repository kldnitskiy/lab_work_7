package client;

import client.Instances.*;
import client.api.InputListener;
import shared.commands.Command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            //Инициализуем класс для настройки клиента
            Client_Instance client = new Client_Instance("localhost", 14000, false);
            client.sayPort();

            //Читаем консоль и сохраняем порт
            Scanner scan = new Scanner(System.in);
            int port = Integer.parseInt(scan.nextLine());
            client.setCurrent_port(port);
            client.establishConnection();

            InputListener command = new InputListener(System.in);

            //Авторизуем пользователя
            do{
                client.logMessage();
                String auth_status = scan.nextLine();
                client.AuthManager(auth_status);

            }while (!client.logged);

            System.out.println("Введите команду (help - для справки)");

            do{

                Command cmd = command.readCommand();
                client.Operations(cmd);

            }while (!client.work_is_done);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

