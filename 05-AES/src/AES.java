
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv =  new byte[MIDA_IV];
    private static final String CLAU = "AHYCLT115";

    public static byte[] xifraAES(String msg, String clau) throws Exception {
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

    public static String desxifraAES (byte[] bIvIMsgXifrat, String clau) throws Exception {
        byte[] ivExtret = extreureIv(bIvIMsgXifrat);

        byte[] msgXifrat = getBytesXifrats(bIvIMsgXifrat);

        SecretKeySpec hash = generaHash(clau);

        Cipher c = Cipher.getInstance(FORMAT_AES);
        c.init(Cipher.DECRYPT_MODE, hash, new IvParameterSpec(ivExtret));

        byte[] original = c.doFinal(msgXifrat);
        return new String(original, StandardCharsets.UTF_8);
    }

    private static byte[] generaIv() {
        byte[] nouIv = new byte[MIDA_IV];
        new SecureRandom().nextBytes(nouIv);
        return nouIv;
    }
    
    private static SecretKeySpec generaHash(String clau) throws Exception {
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = md.digest(clau.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }

    public static byte[] extreureIv(byte[] bIvIMsgXifrat) {
        return Arrays.copyOfRange(bIvIMsgXifrat, 0, MIDA_IV);
    }

    private static byte[] getBytesXifrats(byte[] bIvIMsgXifrat) {
        return Arrays.copyOfRange(bIvIMsgXifrat, MIDA_IV, bIvIMsgXifrat.length);
    }

    public static void main(String[] args) {
        String msgs[] = {
            "Lorem ipsum dicet",
            "Hola Andrés cómo está tu cuñado",
            "Àgora ïlla Ôtto"
        };

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];

            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            }
            
            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }
}
