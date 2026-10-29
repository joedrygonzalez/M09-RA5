
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    public int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(salt.getBytes());
        byte[] bytes = md.digest(pw.getBytes());
        
        HexFormat hex = HexFormat.of();

        return hex.formatHex(bytes);
    }
    
    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        int iteracions = 65536;
        int longitudClau = 128; 

        KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), iteracions, longitudClau);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] bytes = factory.generateSecret(spec).getEncoded();
 
        HexFormat hex = HexFormat.of();
        return hex.formatHex(bytes);
    }
    
    private boolean comprova(String pw, String alg, String hashObjectiu, String salt) throws Exception {
        npass++;
        String hashProvat = alg.equals("SHA-512")
                ? getSHA512AmbSalt(pw, salt)
                : getPBKDF2AmbSalt(pw, salt);
        return hashProvat.equals(hashObjectiu);
    }

    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        String charset = "abcdefABCDEF1234567890!";
        npass = 0;
        return forcaBrutaRec(alg, hash, salt, charset.toCharArray(), "", 6);
    }

    private String forcaBrutaRec(String alg, String hash, String salt,
                                char[] chars, String actual, int maxLen) throws Exception {
        if (actual.length() > 0 && comprova(actual, alg, hash, salt)) {
            return actual;
        }
        if (actual.length() == maxLen) {
            return null;
        }
        for (char c : chars) {
            String resultat = forcaBrutaRec(alg, hash, salt, chars, actual + c, maxLen);
            if (resultat != null) return resultat;
        }
        return null;
    }
    
    public String getInterval(long t1, long t2) {
        long diff = t2 - t1;
 
        long dies = diff / (1000 * 60 * 60 * 24);
        diff %= (1000 * 60 * 60 * 24);
 
        long hores = diff / (1000 * 60 * 60);
        diff %= (1000 * 60 * 60);
 
        long minuts = diff / (1000 * 60);
        diff %= (1000 * 60);
 
        long segons = diff / 1000;
        long millis = diff % 1000;
 
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis",
                dies, hores, minuts, segons, millis);
    }
    
    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
 
        Hashes h = new Hashes();
 
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = { "SHA-512", "PBKDF2" };
 
        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("============================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("-------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");
 
            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();
 
            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("-------------------------\n\n");
        }
    }
}