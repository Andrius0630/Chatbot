/*
* kolendarik123@gmail.com
* main (todo)
* 2025-03-19
*/

import projectPkgs.Bot.BotLogic;

/*
What to fix:
    1) use tryCatch
    2) more modular funcs
*/

public class Main {
    public static void main(String[] args) {
        try {
            BotLogic bot = new BotLogic();
            bot.startConversationLoop();
        } catch (Exception _) {

        }
    }
}