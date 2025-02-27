import java.util.Random;
import java.util.Scanner;
//import java.util.HashMap;

class main {
    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.botLaunchMainConversationLoop();
    }
}

class Bot {
    private String botName = "Dr. Chat";
    private String botThematic = "psychologist";
    private String userName;

    Random rand = new Random();
    String randomBotQuestion;
    String randomBotResponse;
    //private HashMap<String, Integer> people = new HashMap<String, Integer>();

    
    private Scanner scanner = new Scanner(System.in);
    private String userInput;
    
    private String firstMetPhraseList = "Are you feeling happy, sad, stressed, or neutral?";
    //"How was your day?"

    private String[] greatMoodQuestionList = {
        "What made your day so good?",
        "What\'s the best thing that happened today?",
        "Do you have any fun plans coming up?",
        "Is there something or someone that made your day special?",
        "If you could celebrate right now, how would you do it?",
        "What\'s a small thing that made you smile today?",
    };

    private String[] greatMoodResponseList = {
        "That's great to hear",
        "That\'s wonderful to hear",
        "Happiness is contagious",
        "I love hearing good news",
        "You seem to be in a great mood",
        "Life\'s little joys make a big difference",
    };

    private String[] sadMoodQuestionList = {
        "Do you want to talk about it?",
        "Is there something that usually makes you feel better?",
        "Would you like some advice or just someone to listen?",
        "What\'s one thing you\'re proud of?"
    };

    private String[] sadMoodResponseList = {
        "I'm really sorry to hear that",
        "It's okay to feel sad sometimes",
        "You're not alone in this",
        "I believe in you",
        "Tough times don\'t last, but strong people do"
    };

    private String[] stressedMoodQuestionList = {
        "Have you tried taking a few deep breaths?",
        "Do you have a favorite way to relax?",
        "It sounds like you have a lot on your mind. Want to break it down into smaller steps?",
        "What\'s worked for you in the past?"
    };

    private String[] stressedMoodResponseList = {
        "Stress can be overwhelming",
        "Sometimes a quick walk or listening to music can help",
        "It sounds like you have a lot on your mind",
        "I know things can feel tough, but you've handled challenges before"
    };

    private String[] neutralMoodQuestionList = {
        "Did anything interesting happen today?",
        "What\'s something you\'re looking forward to?",
        "Would you rather have the ability to fly or be invisible?",
        "What\'s a hobby or activity you enjoy?"

    };

    private String[] neutralMoodResponseList = {
        "Sounds like a regular day",
        "Sometimes quiet days are the best",
        "I can ask you a fun random question",
        "Neutral is a good place to be"
    };
    
    private void botGreet() {
        System.out.printf("> Greetings! I am a %s bot named %s. Throughout our conversation you can type \"bye\" to end.\n", botThematic, botName);
        System.out.printf("> Please enter your name below to start our therapy!\n");
        userName = userTurn();
        System.out.printf("> Fantastic! Nice to meet you, %s!\n", userName);
    }

    private void botSays(String phrase) {
        System.out.printf("\n> ");
        System.out.println(phrase);
    }

    private String userTurn() {
        System.out.printf("> ");
        String input = scanner.nextLine();
        if (input.contains("bye")) botBye();
        System.out.printf("\n");
        return input;
    }

    public void botLaunchMainConversationLoop() {
        botGreet();
        botSays(firstMetPhraseList);

        userInput = userTurn().toLowerCase();
        
        if (userInput.contains("fine") || userInput.contains("happy")) {
            while (true) {
                randomBotResponse = greatMoodResponseList[rand.nextInt(greatMoodResponseList.length)];
                randomBotQuestion = greatMoodQuestionList[rand.nextInt(greatMoodQuestionList.length)];
                System.out.printf("> %s, %s! ", randomBotResponse, userName);
                botSays(randomBotQuestion);
                userInput = userTurn().toLowerCase();
            }
            
        }
        else if (userInput.contains("sad")) {
            while (true) {
                randomBotResponse = sadMoodResponseList[rand.nextInt(sadMoodResponseList.length)];
                randomBotQuestion = sadMoodQuestionList[rand.nextInt(sadMoodQuestionList.length)];
                System.out.printf("> %s, %s! ", randomBotResponse, userName);
                botSays(randomBotQuestion);
                userInput = userTurn().toLowerCase();
            }
        }
        else if (userInput.contains("stressed") || userInput.contains("bored") || userInput.contains("boring")) {
            while (true) {
                randomBotResponse = stressedMoodResponseList[rand.nextInt(stressedMoodResponseList.length)];
                randomBotQuestion = stressedMoodQuestionList[rand.nextInt(stressedMoodQuestionList.length)];
                System.out.printf("> %s, %s! ", randomBotResponse, userName);
                botSays(randomBotQuestion);
                userInput = userTurn().toLowerCase();
            }
        }
        else if (userInput.contains("neutral") || userInput.contains("ok") || userInput.contains("normal")) {
            while (true) {
                randomBotResponse = neutralMoodResponseList[rand.nextInt(neutralMoodResponseList.length)];
                randomBotQuestion = neutralMoodQuestionList[rand.nextInt(neutralMoodQuestionList.length)];
                System.out.printf("> %s, %s! ", randomBotResponse, userName);
                botSays(randomBotQuestion);
                userInput = userTurn().toLowerCase();
            }
        } else {
            System.out.println("\n> Sorry, I don't understand. Could you describe it in a different way?");
        }
    }

    private void botBye() {
        if (userName != null) {
            System.out.printf("> Good bye, %s!\n", userName);
        } else {
            System.out.printf("> If you don\'t want to have a conversation with me it is totally fine, have a nice day!\n");
        }
        
        //System.out.printf("> Take care, %s! I am always here if you need me.\n", userName);
        System.exit(0);
    }
}
