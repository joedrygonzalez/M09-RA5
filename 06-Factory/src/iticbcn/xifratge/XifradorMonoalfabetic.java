package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class XifradorMonoalfabetic implements Xifrador {
    private final char[] majuscules = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    private char[] permutat;

    public XifradorMonoalfabetic() {
        permutat = permutaAlfabet(majuscules);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        comprovaClau(clau);
        String xifrat = encriptar(msg, majuscules, permutat);
        return new TextXifrat(xifrat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        comprovaClau(clau);
        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
        return encriptar(text, permutat, majuscules);
    }

    // El monoalfabètic no fa servir clau: ha de ser null
    private void comprovaClau(String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratge monoalfabètic no suporta clau != null");
        }
    }

    public char[] permutaAlfabet(char[] alfabet) {
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

    public String encriptar(String cadena, char[] origen, char[] desti) {
        String text = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (Character.isLowerCase(c)) {
                int posicio = posicio(origen, Character.toUpperCase(c));
                if (posicio != -1) {
                    text += Character.toLowerCase(desti[posicio]);
                } else {
                    text += c;
                }
            } else {
                int posicio = posicio(origen, c);
                if (posicio != -1) {
                    text += desti[posicio];
                } else {
                    text += c;
                }
            }
        }
        return text;
    }

    public int posicio(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
