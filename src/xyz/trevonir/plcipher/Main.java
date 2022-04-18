package xyz.trevonir.plcipher;

public class Main {
    public static void main(String[] args) {
        final String text = "aaaaa";
        final PlCipher keralo = new PlCipher("Keralo", 69420);
        System.out.println(keralo.encrypt(text) + "= " + keralo.decrypt(keralo.encrypt(text)));
    }
}
