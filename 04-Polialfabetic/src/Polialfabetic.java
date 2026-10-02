import java.util.Random;

public class Polialfabetic {
    private static final char[] minuscules = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    private static char[] permuta = minuscules.clone();
    private static Random random = new Random(); 
    private static final long clauSecreta = 24122007L;

    public static void permutaAlfabet() {
        permuta = minuscules.clone();
        for (int i = permuta.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char cambi = permuta[i];
            permuta[i] = permuta[j];
            permuta[j] = cambi;
        }
    }

    public static void initRandom(long clau) {
        random = new Random(clau);
    }

    public static String xifraPoliAlfa(String msg) {
        return encriptar(msg, true);
    }

    public static String desxifraPoliAlfa(String msgXifrat) {
        return encriptar(msgXifrat, false);
    }

    public static String encriptar(String cadena, boolean xifrar) {
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

    public static int posicio(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String msgs[] = {
            "Test 01 àrbritre, caixó, Perímetre",
            "Test 02 Taüll, DÍA, año",
            "Test 03 Àgora, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge\n--------");
        for (int i = 0;i < msgs.length;i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n",msgs[i],msgsXifrats[i]);
        }
        
        System.out.println("Desxifratge\n--------");
        for (int i = 0;i < msgs.length;i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n",msgsXifrats[i],msg);
        }
    }
}
