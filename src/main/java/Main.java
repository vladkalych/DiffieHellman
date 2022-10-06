import jakarta.xml.bind.DatatypeConverter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final DiffieHellman diffieHellman = new DiffieHellman();
    private static BigInteger P;
    private static BigInteger G;
    private static BigInteger randomNumber;
    private static BigInteger otherPublicKey;
    private static BigInteger privateKey;
    private static IO io = new IO();

    private static AES256 aes256;

    public static void main(String[] args) {

        while (true) {
            System.out.println("1. Create public key\n" +
                    "2. Load public key\n" +
                    "3. Encrypt message\n" +
                    "4. Decrypt message");
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();

            switch (index) {
                case 1:
                    // P = new BigInteger(8, new Random()); //  Generate P
                    // G = new BigInteger(8, new Random()); // Generate G

                    P = new BigInteger("38798347981204819398502385032180218059309500347698347958298364329598327983276"); //  Generate P
                    G = new BigInteger("12984091275032750928311053288798461247423865327093760270982304982375903609932"); //  Generate G

                    randomNumber = new BigInteger(512, new Random()); // Random big number
                    BigInteger publicKey = diffieHellman.getPublicKey(P, G, randomNumber);// Public Key
                    System.out.println("Write name of key: ");
                    String nameOfCreatedKey = scanner.next();
                    io.saveKey(P, G, publicKey, nameOfCreatedKey);
                    System.out.println("Key created");
                    System.out.println("\nPrivate key: " + privateKey + "\n");
                    break;
                case 2:
                    System.out.println("Write name of key: ");
                    String nameOfLoadKey = scanner.next();
                    String[] strings = io.readKey(nameOfLoadKey);
                    P = new BigInteger(strings[0]);
                    G = new BigInteger(strings[1]);
                    otherPublicKey = new BigInteger(strings[2]);
                    System.out.println("\nPrivate key: " + privateKey + "\n");
                    break;
                case 3:
                    System.out.println("Write your message: ");
                    String message = scanner.next();
                    privateKey = diffieHellman.getPrivateKey(otherPublicKey, P, randomNumber);
                    AES256.SECRET_KEY = getSHA256Hash(privateKey.toString());
                    String encrypt = AES256.encrypt(message);
                    io.saveMessage(encrypt, "message.txt");
                    System.out.println("Message was created");
                    System.out.println("\nPrivate key: " + privateKey + "\n");
                    break;
                case 4:
                    privateKey = diffieHellman.getPrivateKey(otherPublicKey, P, randomNumber);
                    System.out.println("\nPrivate key: " + privateKey + "\n");
                    String s = io.readMessage("message.txt");
                    AES256.SECRET_KEY = getSHA256Hash(privateKey.toString());
                    String decrypt = AES256.decrypt(s);
                    System.out.println(decrypt);
                default:
                    break;
            }
        }

    }

    private static String getSHA256Hash(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash); // make it printable
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private static String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}