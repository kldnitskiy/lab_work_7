package client.api;

import shared.message.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Encoding {
    public static byte[] serialize(Message data) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(stream);
            output.writeObject(data);
            return stream.toByteArray();
    }
}
