package xyz.trevonir.plcipher;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class PlCipher {
    private static final String SYMBOLS = " ',.pyfgcrl/=\\aoeuidhtns-;qjkxbmwvz\"<>PYFGCRL?+|AOEUIDHTNS_:QJKXBMWVZ1234567890[]`~!@#$%^&*(){}";

    private final String language;
    private final long key;
    private Random random;

    public PlCipher(final String language, final long key) {
        this.language = language.toLowerCase();
        this.key      = key;
    }

    public String encrypt(final String message) {
        setupRandom();
        return join(message.chars().mapToObj(i -> (char) i).map(c -> randomise().indexOf(c)).map(this::intToLang).toList(), true);
    }

    public String decrypt(final String message) {
        setupRandom();
        return join(Arrays.stream(message.split(" ")).map(this::langToInt).map(i -> randomise().charAt(i)).toList(), false);
    }

    private int getMinLength() {
        int t = 2 * language.length(), minLength = 1;
        while (t < SYMBOLS.length()) {
            t *= (language.length() + 1);
            minLength++;
        }

        return minLength * language.length();
    }

    private String intToLang(final int i) {
        final StringBuilder ret = new StringBuilder();
        final String bin = Integer.toBinaryString(i);
        final char[] arr = ("0".repeat(getMinLength() - bin.length()) + bin).toCharArray();
        for (int j = 0; j < arr.length; j++) {
            char c = language.charAt(j % language.length());
            ret.append(arr[j] == '1' ? Character.toUpperCase(c) : c);
        }

        return ret.toString();
    }

    private int langToInt(final String lang) {
        return Integer.parseInt(join(chars(lang).map(c -> Character.isUpperCase(c) ? '1' : '0').toList(), false), 2);
    }

    private <T> String join(final List<T> list, final boolean space) {
        final StringBuilder ret = new StringBuilder();
        for (final T o: list)
            ret.append(o.toString()).append(space ? " " : "");
        return ret.toString();
    }

    private Stream<Character> chars(final String s) {
        return s.chars().mapToObj(i -> (char) i);
    }

    private void setupRandom() {
        random = new Random(key);
    }

    private String randomise() {
        String s = SYMBOLS;
        final StringBuilder ret = new StringBuilder();
        while (!s.isEmpty()) {
            ret.insert(random.nextInt(ret.length() + 1), s.charAt(0));
            s = s.substring(1);
        }

        return ret.toString();
    }
}
