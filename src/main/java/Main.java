import java.util.Scanner;

public class Main {
    private static String[] arguments;
    public static String[] getArguments() {
        return arguments;
    }

    public static void main(String[] args) {
        arguments = args;
        printMenu();
        int user = userMove();
        int length = arguments.length;
        int pc = (int) (Math.random() * (length) + 1);
        int range = (length - 1) / 2;
        System.out.println(pc);

        int window = pc - range < 0 ? arguments.length + (pc - range) : pc - range;
        if (window < pc) {
            if (user >= window && user < pc) {
                System.out.println("PC wins");
            } else {
                System.out.println("User wins");
            }
        } else {
            if (user >= window || user < pc) {
                System.out.println("PC wins");
            } else {
                System.out.println("User wins");
            }
        }
    }

    private static void printMenu() {
        String[] args = getArguments();
        if (args.length % 2 == 0 && args.length < 3) {
            System.out.println("Exception! there must be 3 or more arguments and an odd number. Please, try again.");
        }
        int i = 1;
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
        if (user == 0) {
            printMenu();
        }
        return user;
    }
}
