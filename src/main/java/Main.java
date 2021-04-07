import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {
    private static String[] arguments;

    public static String[] getArguments() {
        return arguments;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        arguments = args;
        printMenu();
        //user move
        int user = userMove();
        int length = arguments.length;
        //pc move
        int pc = (int) (Math.random() * (length) + 1);
        String pcMove = arguments[pc];
        //key
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] digest = sha256_HMAC.doFinal(pcMove.getBytes());
        BigInteger bigInteger = new BigInteger(1, digest);
        System.out.printf("%0" + (digest.length << 1) + "x%n", bigInteger);
        //System.out.println(hex(digest));

        //game
        int range = (length - 1) / 2;
        System.out.println("PC move: " + arguments[pc]);
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
        System.out.printf("%0" + (key.length << 1) + "x%n", new BigInteger(1, key));
        //System.out.println(hex(key));
    }

    private static void printMenu() {
        String[] args = getArguments();
        if (args.length % 2 == 0 && args.length < 3) {
            System.out.println("Exception! there must be 3 or more arguments and an odd number. Please, try again.");
        }
        int i = 1;
        System.out.println("Available move: ");
        for (String word : args) {
            if (i < args.length + 1) {
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
            userMove();
        }
        System.out.println("Your move: " + arguments[user - 1]);
        return user;
    }

    /*private static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            //result.append(String.format("%02x", aByte));
            // upper case
            result.append(String.format("%02X", aByte));
        }
        return result.toString();
    }*/


}
