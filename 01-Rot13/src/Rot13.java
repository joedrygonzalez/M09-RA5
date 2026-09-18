public class Rot13 {
    private static char[] minuscules = {
        'a', 'á', 'à', 'b', 'c', 'ç', 'd', 'e', 'é', 'è',
        'f', 'g', 'h', 'i', 'í', 'ì', 'ï', 'j', 'k', 'l',
        'm', 'n', 'ñ', 'o', 'ó', 'ò', 'p', 'q', 'r', 's',
        't', 'u', 'ú', 'ù', 'ü', 'v', 'w', 'x', 'y', 'z'
    };
    private static char[] majuscules = {
        'A', 'Á', 'À', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È',
        'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L',
        'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S',
        'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'
    };
    private static int sum = 13;
    private static int arrayLength = minuscules.length;

    public static void main(String[] args) {
        String[] texts = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

        int maxLen = 0;
        for (String t : texts) {
            maxLen = Math.max(maxLen, t.length());
        }
        String format = "%-" + maxLen + "s\t=>  %s%n";

        String[] TextsXifrats = new String[texts.length];
        System.out.println("Xifrat\n---------");
        for (int i = 0; i < texts.length; i++) {
            TextsXifrats[i] = xifraRot13(texts[i]);
            System.out.printf(format, texts[i], TextsXifrats[i]);
        }

        String[] TextsDesxifrats = new String[TextsXifrats.length];
        System.out.println("\nDesxifrat\n---------");
        for (int i = 0; i < TextsXifrats.length; i++) {
            TextsDesxifrats[i] = desxifraRot13(TextsXifrats[i]);
            System.out.printf(format, TextsXifrats[i], TextsDesxifrats[i]);
        }
    }

    public static String xifraRot13(String cadena) {
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
                        int newChar = (j + sum) % arrayLength;
                        xifrat += majuscules[newChar];
                    }
                } else {
                    if (c == minuscules[j]) {
                        int newChar = (j + sum) % arrayLength;
                        xifrat += minuscules[newChar];
                    }
                }
            }
        }
        return xifrat;
    }

    public static String desxifraRot13(String cadena) {
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
                        int newChar = ((j - sum) % arrayLength + arrayLength) % arrayLength;
                        desxifrat += majuscules[newChar];
                    }
                } else {
                    if (c == minuscules[j]) {
                        int newChar = ((j - sum) % arrayLength + arrayLength) % arrayLength;
                        desxifrat += minuscules[newChar];
                    }
                }
            }
        }
        return desxifrat;
    }
}