import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class IO {
    public void saveToFile(BigInteger P, BigInteger G, BigInteger PublicKey, String fullPath){
        try (PrintWriter out = new PrintWriter(fullPath)) {
            out.println(P.toString() + "---" + G.toString() + "---" + PublicKey);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] readFromFile(String fullPath) {
        // return P, G, PublicKey
        String str = null;
        try {
            str = Files.readString(Path.of(fullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str.split("---");
    }
}
