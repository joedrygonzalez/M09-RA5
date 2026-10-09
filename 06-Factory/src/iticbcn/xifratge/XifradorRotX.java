package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;

public class XifradorRotX implements Xifrador {
    private static final String abcedari = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    private final char[] minuscules = abcedari.toCharArray();
    private final char[] majuscules = abcedari.toUpperCase().toCharArray();
    private final int arrayLength = minuscules.length;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int desplacament = llegeixClau(clau);
        String xifrat = xifraRotX(msg, desplacament);
        return new TextXifrat(xifrat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int desplacament = llegeixClau(clau);
        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
        return desxifraRotX(text, desplacament);
    }

    private int llegeixClau(String clau) throws ClauNoSuportada {
        try {
            return Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer");
        }
    }

    public String xifraRotX(String cadena, int desplacament) {
        String xifrat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            boolean Maj = Character.isUpperCase(c);
            char[] alfabet = Maj ? majuscules : minuscules;
            boolean trobada = false;
            for (int j = 0; j < arrayLength; j++) {
                if (c == alfabet[j]) {
                    int newChar = Math.floorMod(j + desplacament, arrayLength);
                    xifrat += alfabet[newChar];
                    trobada = true;
                    break;
                }
            }
            if (!trobada) {
                xifrat += c;
            }
        }
        return xifrat;
    }

    public String desxifraRotX(String cadena, int desplacament) {
        return xifraRotX(cadena, -desplacament);
    }
}
