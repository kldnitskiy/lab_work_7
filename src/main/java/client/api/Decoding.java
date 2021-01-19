package client.api;

import shared.message.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Decoding {
    public static Message deserialize(byte[] input) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(input));
        return (Message) stream.readObject();
    }
}
