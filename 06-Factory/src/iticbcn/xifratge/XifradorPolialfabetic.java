package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    private final char[] minuscules = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    private char[] permuta = minuscules.clone();
    private Random random = new Random();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        initRandom(llegeixClau(clau));
        String xifrat = encriptar(msg, true);
        return new TextXifrat(xifrat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        initRandom(llegeixClau(clau));
        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
        return encriptar(text, false);
    }

    private long llegeixClau(String clau) throws ClauNoSuportada {
        try {
            return Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de Polialfabètic ha de ser un String convertible a long");
        }
    }

    public void permutaAlfabet() {
        permuta = minuscules.clone();
        for (int i = permuta.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char cambi = permuta[i];
            permuta[i] = permuta[j];
            permuta[j] = cambi;
        }
    }

    public void initRandom(long clau) {
        random = new Random(clau);
    }

    public String encriptar(String cadena, boolean xifrar) {
        String text = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            permutaAlfabet();

            char[] origen = xifrar ? minuscules : permuta;
            char[] desti = xifrar ? permuta : minuscules;

            if (Character.isUpperCase(c)) {
                int posicio = posicio(origen, Character.toLowerCase(c));
                if (posicio != -1) {
                    text += Character.toUpperCase(desti[posicio]);
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
