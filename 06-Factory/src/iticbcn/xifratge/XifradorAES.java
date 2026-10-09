package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private byte[] iv = new byte[MIDA_IV];

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            return new TextXifrat(xifraAES(msg, clau));
        } catch (Exception e) {
            System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            return desxifraAES(xifrat.getBytes(), clau);
        } catch (Exception e) {
            System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            System.exit(1);
            return null;
        }
    }

    public byte[] xifraAES(String msg, String clau) throws Exception {
        iv = generaIv();
        SecretKeySpec hash = generaHash(clau);

        Cipher c = Cipher.getInstance(FORMAT_AES);
        c.init(Cipher.ENCRYPT_MODE, hash, new IvParameterSpec(iv));

        byte[] xifrat = c.doFinal(msg.getBytes(StandardCharsets.UTF_8));

        byte[] resultat = new byte[MIDA_IV + xifrat.length];
        System.arraycopy(iv, 0, resultat, 0, MIDA_IV);
        System.arraycopy(xifrat, 0, resultat, MIDA_IV, xifrat.length);
        return resultat;
    }

    public String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        byte[] ivExtret = extreureIv(bIvIMsgXifrat);
        byte[] msgXifrat = getBytesXifrats(bIvIMsgXifrat);
        SecretKeySpec hash = generaHash(clau);

        Cipher c = Cipher.getInstance(FORMAT_AES);
        c.init(Cipher.DECRYPT_MODE, hash, new IvParameterSpec(ivExtret));

        byte[] original = c.doFinal(msgXifrat);
        return new String(original, StandardCharsets.UTF_8);
    }

    private byte[] generaIv() {
        byte[] nouIv = new byte[MIDA_IV];
        new SecureRandom().nextBytes(nouIv);
        return nouIv;
    }

    private SecretKeySpec generaHash(String clau) throws Exception {
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = md.digest(clau.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }

    public byte[] extreureIv(byte[] bIvIMsgXifrat) {
        return Arrays.copyOfRange(bIvIMsgXifrat, 0, MIDA_IV);
    }

    private byte[] getBytesXifrats(byte[] bIvIMsgXifrat) {
        return Arrays.copyOfRange(bIvIMsgXifrat, MIDA_IV, bIvIMsgXifrat.length);
    }
}
