import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class hashgenerator {
    public static String getSecurePassword(String password, byte[] salt) {
        String passwordHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            passwordHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static void passgen() throws NoSuchAlgorithmException {
        // Encryption
        byte[] salt = getSalt();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the text you want to encrypt: ");
        String inputpassword = input.nextLine();
        String password1 = getSecurePassword(inputpassword, salt);
        String saltstring = new String();

        String saltconvert = new String();
        for (byte b : salt) {
            String st = String.format("%02X", b);
            saltstring += st;
        }

        System.out.println("\nThe salt for hash is " + saltstring);
        System.out.println("The password hash is " + password1);

        // HEX String to Byte conversion
        byte[] val = new byte[saltstring.length() / 2];
        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(saltstring.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        String password2 = getSecurePassword(inputpassword, val);
        if (password1.equals(password2)) {
            System.out.println("Hash match confirmed! Text: " + inputpassword);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        while (true) {
            passgen();
        }
    }
}