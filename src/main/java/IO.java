import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class IO {
    public void saveKey(BigInteger P, BigInteger G, BigInteger PublicKey, String fullPath){
        try (PrintWriter out = new PrintWriter(fullPath+".txt")) {
            out.print(P.toString() + "---" + G.toString() + "---" + PublicKey);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMessage(String message, String fullPath){
        try (PrintWriter out = new PrintWriter(fullPath)) {
            out.print(message);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] readKey(String fullPath) {
        // return P, G, PublicKey
        String str = null;
        try {
            str = Files.readString(Path.of(fullPath + ".txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str.split("---");
    }

    public String readMessage(String fullPath) {
        String str = null;
        try {
            str = Files.readString(Path.of(fullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
}
