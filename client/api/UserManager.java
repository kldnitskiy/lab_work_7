package client.api;

import shared.message.Message;

import java.io.*;
import java.net.Socket;

public class UserManager {

    public static Message decode(Socket socket) throws IOException{
        try {
            byte[] data = new byte[10000];
            socket.getInputStream().read(data);
            return Decoding.deserialize(data);
        }catch (ClassNotFoundException ex){
            return null;
        }
    }

    public static void sendData(Socket con, Message body) throws IOException {
        con.getOutputStream().write(Encoding.serialize(body));
        con.getOutputStream().flush();
    }
}
