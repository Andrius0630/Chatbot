/*
* andrius.kolenda@mif.stud.vu.lt
* Purpose: creation and initialization of chatbot's main conversation loop
* 2025-03-20
*/

import projectPkgs.Bot.BotLogic;


public class Main {
    public static void main(String[] args) {
        try {
            BotLogic bot = new BotLogic();
            bot.startConversationLoop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}