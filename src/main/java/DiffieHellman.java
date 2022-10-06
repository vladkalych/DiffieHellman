import java.math.BigInteger;

public class DiffieHellman {

    public BigInteger getPublicKey(BigInteger P, BigInteger G, BigInteger a_b) {

        return calculatePower(G, a_b, P);
    }

    public BigInteger getPrivateKey(BigInteger x_y, BigInteger P, BigInteger a_b) {

        return calculatePower(x_y, a_b, P);
    }

    private static BigInteger calculatePower(BigInteger x, BigInteger y, BigInteger P) {
        BigInteger result = BigInteger.ZERO;
        if (y.equals(BigInteger.ONE)) {
            return x;
        } else {
//            result = x.pow(y.intValue()).mod(P);
            result = x.modPow(y, P);
            return result;
        }
    }
}
