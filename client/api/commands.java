package client.api;

import shared.message.Message;

import java.io.IOException;
import java.net.Socket;

public class commands {
    public static void send_command(Socket socket, Message msg) throws IOException {
        socket.getOutputStream().write(Encoding.serialize(msg));
        socket.getOutputStream().flush();

    }
}
