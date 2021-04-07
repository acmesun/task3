import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    private static final String HMAC_ALG = "HmacSHA256";
    private static final int KEY_SIZE = 16;
    private static String[] arguments;

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        arguments = args;
        if (arguments.length % 2 == 0 && arguments.length < 3) {
            System.out.println("Exception! there must be 3 or more arguments (odd number). Please, try again.");
        } else {
            printMenu();
            int user = userMove();
            int pc = pcMove();
            byte[] key = generateKey();
            System.out.println("HMAC: " + hex(generateDigest(arguments[pc].getBytes(UTF_8), key)));
            System.out.println("PC move: " + arguments[pc]);

            game(user, pc, arguments.length);

            System.out.println("KEY: " + hex(key));
        }
    }

    private static int pcMove() {
        return new Random().nextInt(arguments.length);
    }

    private static void game(int user, int pc, int length) {
        int range = (length - 1) / 2;
        int window = pc - range < 0 ? arguments.length + (pc - range) : pc - range;
        if (pc == user) {
            System.out.println("Nobody wins!");
        } else if (window < pc) {
            if (user >= window && user < pc) {
                System.out.println("PC wins!");
            } else {
                System.out.println("User wins!");
            }
        } else {
            if (user >= window || user < pc) {
                System.out.println("PC wins!");
            } else {
                System.out.println("User wins!");
            }
        }
    }

    private static void printMenu() {
        int i = 1;
        System.out.println("Available move: ");
        for (String word : arguments) {
            if (i < arguments.length + 1) {
                System.out.println(i + ". " + word);
                i++;
            }
        }
        System.out.println("0. Exit");
        System.out.println("Make your choice, user!");
    }

    private static int userMove() {
        Scanner scanner = new Scanner(System.in);
        int user = Integer.parseInt(scanner.nextLine());
        if (user > arguments.length || user == 0) {
            System.out.println("Wrong button. Choose again, please.");
            printMenu();
            return userMove();
        }
        System.out.println("Your move: " + arguments[user - 1]);
        return user - 1;
    }

    private static String hex(byte[] bytes) {
        return String.format("%0" + (bytes.length << 1) + "x%n", new BigInteger(1, bytes));
    }

    private static byte[] generateKey() {
        byte[] key = new byte[KEY_SIZE];
        new SecureRandom().nextBytes(key);
        return key;
    }

    private static byte[] generateDigest(byte[] message, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_ALG);
        SecretKeySpec secretKey = new SecretKeySpec(key, HMAC_ALG);
        mac.init(secretKey);
        return mac.doFinal(message);
    }
}
