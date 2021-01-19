package client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {
    public static String encrypt(String input) {
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = sha256.digest(input.getBytes());

        StringBuilder result = new StringBuilder();
        for (byte b : hash) {
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }
}

