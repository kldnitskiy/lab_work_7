package client;

import client.api.InputListener;
import client.api.UserManager;
import client.api.createUserRequest;
import shared.city.City;
import shared.commands.Command;
import shared.commands.Execute_script;
import shared.commands.Exit;
import shared.message.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client_Instance {
    public boolean logged = false;
    String current_host = "localhost";
    Integer current_port = 14000;
    Boolean current_status = false;
    Boolean work_is_done = false;
    Socket socket;
    String username;
    String password;

    Boolean authenticated = false;
    User user;

    Scanner scan = new Scanner(System.in);


    public void setCurrent_host(String current_host) {
        this.current_host = current_host;
    }
    public String getCurrent_host() {
        return current_host;
    }
    public Integer getCurrent_port() {
        return current_port;
    }
    public void setCurrent_port(Integer new_port) {
        System.out.println("Порт был изменён с " + new_port + " на " + current_port);
        this.current_port = current_port;
    }
    public void setCurrent_status(Boolean current_status) {
        this.current_status = current_status;
    }

    public Boolean getCurrent_status() {
        return current_status;
    }

    public Client_Instance(String host, Integer port, Boolean auth) throws IOException {
        this.current_host = host;
        this.current_port = port;
        this.current_status = auth;
    }

    public void establishConnection() throws IOException {
        this.socket = new Socket(current_host, current_port);
    }

    public void sayPort(){
        System.out.println("Порт по-умолчанию установлен на: " + current_port + ".\nВведите порт для подключения к серверу.");
    }

    public void logMessage(){
        System.out.println("Введите login для авторизации существующего аккаунта или register для создания нового");
    }



    void processResponse(Message msg){
        if (msg instanceof CollectionMessage){
            for (City c : ((CollectionMessage) msg).getCities()) {
                System.out.println(c);
            }
        }
        else{
            System.out.println(msg.getContent());
        }
    }

    public void AuthManager(String auth_status) throws IOException {
        if(auth_status.equals("login")){
            Login();
        }else if(auth_status.equals("register")){
            Register();
        }else{
            System.out.println("Нераспознанная команда " + auth_status);
        }
    }

    public void Login() throws IOException {
        //Сохраняем данные для авторизации
        authenticated = false;
        createUserData("Введите логин для авторизации:");
        UserManager.sendData(this.socket, new AuthMessage(this.user, authenticated));
        AuthResult response = (AuthResult) UserManager.decode(socket);

        Boolean SessionStatus = response.isSuccessful();
        readResponse(response);
        if(SessionStatus){
            logged = true;
        }else{
            System.out.println("Авторизация не пройдена.");
            Login();
        }
    }

    public void Register() throws IOException {
        //Сохраняем данные для авторизации
        authenticated = true;
        createUserData("Введите логин для регистрации:");
        UserManager.sendData(this.socket, new AuthMessage(this.user, authenticated));
        AuthResult response = (AuthResult) UserManager.decode(socket);

        Boolean SessionStatus = response.isSuccessful();
        readResponse(response);
        if(SessionStatus){
            logged = true;
        }else{
            System.out.println("Регистрация не пройдена.");
            Register();
        }
    }

    public void readResponse(AuthResult resp) throws IOException {
        System.out.println(resp.getContent());
    }

    public void createUserData(String console_message){
        System.out.println(console_message);
        this.username = scan.nextLine();
        System.out.println("Введите Ваш пароль:");
        this.password = scan.nextLine();
        user = new User(this.username, Encrypter.encrypt(this.password));
    }

    public void Operations(Command command) throws IOException {
        if(command instanceof Exit){
            System.out.println("Оффаем клиент. Тушите свет, ёпта.");
            scan.close();
            System.out.println("Завершение работы.");
            work_is_done = true;
            System.exit(0);
        } else {
            if (command instanceof Execute_script) {
                for (Command c : ((Execute_script) command).getCommands()) {
                    UserManager.sendData(socket, new CommandMessage("больше чем три", c, user));
                    processResponse(UserManager.decode(socket));
                }
            } else {
                UserManager.sendData(this.socket, new CommandMessage("квадратные треугольники", command, user));
                processResponse(UserManager.decode(socket));
            }
        }

    }
}
