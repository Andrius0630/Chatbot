package projectPkgs.UI;

public class UserInterface {
    public static void printBotPhrase(String phrase, Object... args) {
        System.out.print("\n> ");
        System.out.printf(phrase, args);
        System.out.println();
    }


}

