/*
* andrius.kolenda@mif.stud.vu.lt
* Purpose: chatbot's UI methods
* 2025-03-20
*/

package projectPkgs.UI;

public class UI {
    public static void printBotPhrase(String phrase, Object... args) {
        System.out.print("\n> ");
        System.out.printf(phrase, args);
        System.out.println();
    }
}

