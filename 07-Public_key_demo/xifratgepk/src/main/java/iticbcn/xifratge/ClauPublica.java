package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class ClauPublica {
    public KeyPair generaParellClausRSA() throws Exception {
        KeyPairGenerator generaClau = KeyPairGenerator.getInstance("RSA");
        generaClau.initialize(2048);
        return generaClau.generateKeyPair();
    }

    public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, clauPublica);
        byte[] textXifrat = c.doFinal(msg.getBytes(StandardCharsets.UTF_8));
        return textXifrat;
    }

    public String desxifraRSA(byte[] msgXifrat, PrivateKey clauPrivada) throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.DECRYPT_MODE, clauPrivada);
        byte[] textPla = c.doFinal(msgXifrat);
        String msgDesxifrat = new String(textPla, StandardCharsets.UTF_8);
        return msgDesxifrat;
    }

}
