public class RotX {
    private static final String abcedari = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    private static final char[] minuscules = abcedari.toCharArray();
    private static final char[] majuscules = abcedari.toUpperCase().toCharArray();
    private static final int arrayLength = minuscules.length;

    public static void main(String[] args) {
        String[] texts = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        
        int maxLen = 0;
        for (String t : texts) {
            maxLen = Math.max(maxLen, t.length());
        }
        int desplaçaments = 0;
        String format = "(%d)-%-" + maxLen + "s\t=>  %s%n";

        String[] TextsXifrats = new String[texts.length];
        System.out.println("Xifrat\n------");
        for (int i = 0; i < texts.length; i++) {
            TextsXifrats[i] = xifraRotX(texts[i], desplaçaments);
            System.out.printf(format, desplaçaments, texts[i], TextsXifrats[i]);
            desplaçaments = desplaçaments + 2;
        }

        desplaçaments = 0;

        String[] TextsDesxifrats = new String[texts.length];
        System.out.println("\nDesxifrat\n------");
        for (int i = 0; i < texts.length; i++) {
            TextsDesxifrats[i] = desxifraRotX(TextsXifrats[i], desplaçaments);
            System.out.printf(format, desplaçaments, TextsXifrats[i], TextsDesxifrats[i]);
            desplaçaments = desplaçaments + 2;
        }

        String MissatgeXifrat = TextsXifrats[3];
        String TextForcaBruta = MissatgeXifrat;
        System.out.println("\nMissatge xifrat: " + TextForcaBruta + "\n----------------");
        for (desplaçaments = 0; desplaçaments < arrayLength; desplaçaments++) {
            TextForcaBruta = forcaBrutaRotX(MissatgeXifrat, desplaçaments);
            if (TextForcaBruta.equals(texts[3])) {
                System.out.printf("\033[0;1m(%d)->%s%n\033[0m", desplaçaments, TextForcaBruta);
            } else {
                System.out.printf("(%d)->%s%n", desplaçaments, TextForcaBruta);
            }
            
        }
    }

    public static String xifraRotX(String cadena,int desplaçament) {
        String xifrat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            boolean Maj = false;
            boolean lletra = false;
            if (Character.isUpperCase(c)) {
                Maj = true;
            }
            if (Character.isLetter(c)) {
                lletra = true;
            }
            if (!lletra) {
                xifrat += c;
                continue;
            }
            for (int j = 0; j < arrayLength; j++) {
                if (Maj) {
                    if (c == majuscules[j]) {
                        int newChar = (j + desplaçament) % arrayLength;
                        xifrat += majuscules[newChar];
                    }
                } else {
                    if (c == minuscules[j]) {
                        int newChar = (j + desplaçament) % arrayLength;
                        xifrat += minuscules[newChar];
                    }
                }
            }
        }
        return xifrat;
    }

    public static String desxifraRotX(String cadena,int desplaçament) {
        String desxifrat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            boolean Maj = false;
            boolean lletra = false;
            if (Character.isUpperCase(c)) {
                Maj = true;
            }
            if (Character.isLetter(c)) {
                lletra = true;
            }
            if (!lletra) {
                desxifrat += c;
                continue;
            }
            for (int j = 0; j < arrayLength; j++) {
                if (Maj) {
                    if (c == majuscules[j]) {
                        int newChar = (j - desplaçament + arrayLength) % arrayLength;
                        desxifrat += majuscules[newChar];
                    }
                } else {
                    if (c == minuscules[j]) {
                        int newChar = (j - desplaçament + arrayLength) % arrayLength;
                        desxifrat += minuscules[newChar];
                    }
                }
            }
        }
        return desxifrat;
    }

    public static String forcaBrutaRotX(String cadenaXifrada, int desplaçament) {
        return desxifraRotX(cadenaXifrada, desplaçament);
    }
}