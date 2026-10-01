import java.util.Random;

public class Monoalfabetic {
    private static final char[] majuscules = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    private static final char[] permutat = permutaAlfabet(majuscules);

    public static void main(String[] args) {
        String[] texts = {"Test 01 àrbritre, caixó, Perímetre","Test 02 Taüll, DÍA, año", "Test 03 Àgora, Òrrius, Bòvila"};
        
        int maxLen = 0;
        for (String t : texts) {
            maxLen = Math.max(maxLen, t.length());
        }
        String format = "%-" + maxLen + "s -> %s%n";

        System.out.println(espais(majuscules));
        System.out.println(espais(permutat));

        String[] TextsXifrats = new String[texts.length];
        System.out.println("Xifratge:");
        for (int i = 0; i < texts.length; i++) {
            TextsXifrats[i] = xifraMonoAlfa(texts[i]);
            System.out.printf(format, texts[i], TextsXifrats[i]);
        }

        String[] TextsDesxifrats = new String[TextsXifrats.length];
        System.out.println("Desxifratge:");
        for (int i = 0; i < TextsXifrats.length; i++) {
            TextsDesxifrats[i] = desxifraMonoAlfa(TextsXifrats[i]);
            System.out.printf(format, TextsXifrats[i], TextsDesxifrats[i]);
        }
    }
    
    public static char[] permutaAlfabet(char[] alfabet) {
        char[] result = alfabet.clone();
        Random random = new Random();
        for (int i = result.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char x = result[i];
            result[i] = result[j];
            result[j] = x;
        }
        return result;
    }

    public static String xifraMonoAlfa(String cadena) {
    
    }

    public static String desxifraMonoAlfa(String cadena) {

    }

    public static String espais(char[] array) {
        String result = "";
        for (char c : array) {
            result += c + " ";
        }
        return result;
    }
}
