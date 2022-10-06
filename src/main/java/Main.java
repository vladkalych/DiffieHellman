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
    private static IO io = new IO();;

    private static AES256 aes256;

    public static void mainFail(String[] args) {

        // BigInteger P = new BigInteger(8, new Random()); //  Generate P
        // BigInteger G = new BigInteger(8, new Random()); // Generate G
        BigInteger BigRandomNumber = new BigInteger(8, new Random()); // Random big number

        DiffieHellman diffieHellman = new DiffieHellman();
        // BigInteger publicKey = diffieHellman.getPublicKey(new BigInteger("81"), new BigInteger("247"), BigRandomNumber);// Public Key

        // System.out.println(P + " - P");
        // System.out.println(G + " - G");
        // System.out.println(BigRandomNumber + " - BigRandomNumber");
        // System.out.println(publicKey + "- Public Key");

        BigInteger privateKey = diffieHellman.getPrivateKey(new BigInteger("58"), new BigInteger("81"), new BigInteger("178"));
        System.out.println(privateKey);

    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("1. Create public key\n" +
                    "2. Load public key\n" +
                    "3. Encrypt message");
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();

            switch (index){
                case 1:
                    P = new BigInteger(16, new Random()); //  Generate P
                    G = new BigInteger(16, new Random()); // Generate G
                    randomNumber = new BigInteger(16, new Random()); // Random big number
                    BigInteger publicKey = diffieHellman.getPublicKey(P, G, randomNumber);// Public Key
                    io.saveToFile(P, G, publicKey, "publicKey.txt");
                    System.out.println("Key created");
                    break;
                case 2:
                    String[] strings = io.readFromFile("publicKey.txt");
                    P = new BigInteger(strings[0]);
                    G = new BigInteger(strings[1]);
                    otherPublicKey = new BigInteger(strings[2]);
                    break;
                case 3:
                    System.out.println("Write your message: ");
                    String message = scanner.next();
                    privateKey = diffieHellman.getPrivateKey(otherPublicKey, P, randomNumber);
                    AES256.SECRET_KEY = getSHA256Hash(privateKey.toString());
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
