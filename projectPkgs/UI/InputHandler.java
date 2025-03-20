/*
* andrius.kolenda@mif.stud.vu.lt
* Purpose: universal input handling
* 2025-03-20
*/

package projectPkgs.UI;

import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner = new Scanner(System.in);

    public boolean isBasicText(String input) {
        return input.matches("[a-zA-Z ]+");
    }

    public boolean isInputTooLong(String input, byte maxLength) {
        return input.length() > maxLength;
    }

    public String getBasicUserInput() {
        return scanner.nextLine();
    }

    
}