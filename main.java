import java.util.Random;
import java.util.Scanner;

/*

What to fix:
    1) No Input Length Validation for Name
    2) The code repeats similar logic for handling different moods in several places. For example, the switch statement inside botLaunchMainConversationLoop() could be refactored into a single method that accepts the mood as a parameter.
    3) The user has to shut down the program to return to the menu, word lists could be arranged horizontally to save space, it is good practice to start method names with a verb

*/


class Main {
    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.botLaunchMainConversationLoop();
    }
}

class Bot {
    private final String botName = "Dr. Chat";
    private String userName = "";
    private final byte MAX_USERNAME_SIZE = 15;

    // variables for randomly chosen bot phrases
    private final Random rand = new Random();
    private String randomBotQuestion = "";
    private String randomBotResponse = "";
    
    private final Scanner scanner = new Scanner(System.in);
    private String userInput = "";
    
    // standart bot's phrases
    private final String firstMetPhrase = "Are you feeling happy, sad, stressed, or neutral?";
    

    
    // IMPORTANT! Bot speaking is dependent on patient's (user's) mood
    // IMPORTANT! If bot sees in patient's input, that patient is happy/sad/worried/neutral, bot starts printing random phrases that belong to the "happy/sad/worried/neutral mood" arrays

    // initializing bot first state
    private Mood patientMood = Mood.BASE;



    // initializing bot's questions and responses for each patient's mood
    private final Phrase[] greatMoodQuestionList = {
        new Phrase("What made your day so good?"),
        new Phrase("What's the best thing that happened today?"),
        new Phrase("Do you have any fun plans coming up?"),
        new Phrase("Is there something or someone that made your day special?"),
        new Phrase("If you could celebrate right now, how would you do it?"),
        new Phrase("What\'s a small thing that made you smile today?"),
        new Phrase("What\'s something you\'re grateful for?"),
        new Phrase("Have you shared your happiness with anyone else today?"),
        new Phrase("What\'s a song that perfectly matches your mood right now?"),
        new Phrase("If today had a theme song, what would it be?")
    };

    private final Phrase[] greatMoodResponseList = {
        new Phrase("That's great to hear, %s!"),
        new Phrase("That\'s wonderful to hear, %s!"),
        new Phrase("Happiness is contagious, %s!"),
        new Phrase("I love hearing good news, %s!"),
        new Phrase("You seem to be in a fantastic mood, %s!"),
        new Phrase("Life\'s little joys make a big difference, %s!"),
        new Phrase("Your positivity is inspiring, %s!"),
        new Phrase("Keep shining and spreading joy, %s!"),
        new Phrase("Moments like these make life beautiful, %s!"),
        new Phrase("Smiles are meant to be shared, %s!")
    };

    private final Phrase[] sadMoodQuestionList = {
        new Phrase("Is there something that usually makes you feel better?"),
        new Phrase("What's one thing you're proud of?"),
        new Phrase("Have you been able to take care of yourself today?"),
        new Phrase("What is something that brings you comfort?"),
        new Phrase("If you could do anything right now to feel better, what would it be?"),
        new Phrase("What\'s something kind you\'ve done for yourself recently?"),
        new Phrase("Have you had moments like this before, and how did you get through them?")
    };

    private final Phrase[] sadMoodResponseList = {
        new Phrase("I'm really sorry to hear that, %s..."),
        new Phrase("It's okay to feel sad sometimes, %s!"),
        new Phrase("You're not alone in this, %s!"),
        new Phrase("I believe in you, %s!"),
        new Phrase("Tough times don\'t last, but strong people do, %s!"),
        new Phrase("Your feelings are valid, and I\'m here for you, %s!"),
        new Phrase("You deserve kindness, including from yourself, %s!"),
        new Phrase("I wish I could give you a virtual hug right now, %s!"),
        new Phrase("Remember, emotions come and go - you're stronger than you think, %s!")
    };

    private final Phrase[] stressedMoodQuestionList = {
        new Phrase("Have you tried taking a few deep breaths?"),
        new Phrase("What is your favorite way to relax?"),
        new Phrase("What\'s worked for you in the past when you\'ve felt like this?"),
        new Phrase("Is there anything specific that\'s causing you stress? Describe it."),
        new Phrase("What\'s one thing you can control right now? Describe it."),
        new Phrase("How can you be kind to yourself today?")
    };

    private final Phrase[] stressedMoodResponseList = {
        new Phrase("Stress can be overwhelming, but you\'re not alone, %s!"),
        new Phrase("Sometimes a quick walk or listening to music can help, %s!"),
        new Phrase("It sounds like you have a lot on your mind, but you\'re strong enough to handle it, %s!"),
        new Phrase("I know things can feel tough, but you've handled challenges before, %s!"),
        new Phrase("Remember to take things one step at a time, %s!"),
        new Phrase("Even small breaks can help reset your mind, %s!"),
        new Phrase("You're doing better than you think, %s!"),
        new Phrase("It's okay to slow down and breathe, %s!"),
        new Phrase("You have the strength to get through this, %s!"),
        new Phrase("Be kind to yourself - you\'re doing your best, %s!")
    };

    private final Phrase[] neutralMoodQuestionList = {
        new Phrase("Did anything interesting happen today? Describe it."),
        new Phrase("What\'s something you\'re looking forward to?"),
        new Phrase("What\'s a hobby or activity you enjoy?"),
        new Phrase("If you could be anywhere right now, where would you go?"),
        new Phrase("Is there something new you\'ve been wanting to try?"),
        new Phrase("Do you prefer quiet moments or exciting adventures?"),
        new Phrase("What\'s a simple pleasure that makes your day better?"),
        new Phrase("If you could instantly master any skill, what would it be?"),
        new Phrase("What\'s the last thing that made you laugh?")
    };

    private final Phrase[] neutralMoodResponseList = {
        new Phrase("Sounds like a regular day, %s."),
        new Phrase("Sometimes quiet days are the best, %s."),
        new Phrase("It\'s nice to have a balance between excitement and peace, %s."),
        new Phrase("Neutral is a good place to be, %s."),
        new Phrase("Even uneventful days can have small joys, %s."),
        new Phrase("Routine can be comforting in its own way, %s."),
        new Phrase("A calm day can be refreshing, %s."),
        new Phrase("It\'s always good to have moments to just breathe, %s."),
        new Phrase("Not every day has to be extraordinary to be meaningful, %s."),
        new Phrase("Appreciating the little things can make all the difference, %s.")
    };



    // initializing words that bot checks in patient's input
    private final String[] greatWordsToCheckList = {
        "fine",
        "happy",
        "great",
        "good",
        "amazing",
        "fantastic",
        "awesome",
        "wonderful",
        "excited"
    };

    private final String[] sadWordsToCheckList = {
        "sad",
        "down",
        "upset",
        "unhappy",
        "depressed",
        "miserable",
        "lonely",
        "heartbroken"
    };

    private final String[] stressedWordsToCheckList = {
        "stressed",
        "overwhelmed",
        "anxious",
        "worried",
        "tense",
        "nervous",
        "frustrated",
        "exhausted",
        "burned out",
        "drained",
        "tired",
        "angry"
    };

    private final String[] neutralWordsToCheckList = {
        "neutral",
        "ok",
        "normal",
        "alright",
        "meh",
        "average",
        "nothing special",
        "decent",
        "not bad"
    };


    private void botGreet() {
        botSay("Greetings! I am a psychologist bot named %s. Throughout our conversation you can type \"bye\" to end.", botName);
        botSay("Please enter your name below to start our therapy! Maximum length of name is %d characters.", MAX_USERNAME_SIZE);
        while (true) {
            userName = getUserInput();
            if (userName.length() > MAX_USERNAME_SIZE)
                botSay("Your user name is longer than %d! Please enter your name below again...", MAX_USERNAME_SIZE);
            else
                break;
        }
        
       botSay("Fantastic! Nice to meet you, %s!", userName);
    }

    private void botSay(String phrase, Object... args) {
        System.out.printf("\n> ");
        System.out.printf(phrase, args);
        System.out.printf("\n");
    }

    private String getUserInput() {
        String input = "";
        while (true) {
            System.out.printf("> ");
            input = scanner.nextLine();

            if (!input.isBlank())
                break;
            else
                botSay("Sorry, but input cannot be blank! Please try again...");
        }
        
        // if patient types word "bye" bot ends conversation
        if (input.toLowerCase().contains("bye"))
            botBye();
        
        System.out.printf("\n");
        return input;
    }

    private void botChooseRandomAnswer(Phrase[] responseList, Phrase[] questionList) {
        boolean responseHasBeenChosen = false;
        boolean questionHasBeenChosen = false;

        while (!responseHasBeenChosen || !questionHasBeenChosen) {
            int randomResponseIndex = rand.nextInt(responseList.length);
            int randomQuestionIndex = rand.nextInt(questionList.length);

            // Decrement timeouts for responses
            for (Phrase elem : responseList) {
                if (elem.timeoutForPhrase > 0) {
                    elem.timeoutForPhrase--;
                }
            }

            if (responseList[randomResponseIndex].timeoutForPhrase == 0) {
                randomBotResponse = responseList[randomResponseIndex].phrase;
                responseList[randomResponseIndex].timeoutForPhrase = (short) responseList.length;
                responseHasBeenChosen = true;
            }


            // Decrement timeouts for questions
            for (Phrase elem : questionList) {
                if (elem.timeoutForPhrase > 0) {
                    elem.timeoutForPhrase--;
                }
            }

            if (questionList[randomQuestionIndex].timeoutForPhrase == 0) {
                randomBotQuestion = questionList[randomQuestionIndex].phrase;
                questionList[randomQuestionIndex].timeoutForPhrase = (short) questionList.length;
                questionHasBeenChosen = true;
            }
        }
    }

    // main dialog loop between patient and bot 
    public void botLaunchMainConversationLoop() {
        botGreet(); // bot greets and asks for patient's name


        // main conversation loop
        while (true) {
            // depending on patient's mood bot randomly picks and prints phrases that correspond to the patient's mood
            switch (patientMood) {
                case HAPPY -> {
                    botChooseRandomAnswer(greatMoodResponseList, greatMoodQuestionList);
                    botSay(randomBotResponse, userName);
                    botSay(randomBotQuestion);
                }
                case SAD -> {
                    botChooseRandomAnswer(sadMoodResponseList, sadMoodQuestionList);
                    botSay(randomBotResponse, userName);
                    botSay(randomBotQuestion);
                }
                case STRESSED -> {
                    botChooseRandomAnswer(stressedMoodResponseList, stressedMoodQuestionList);
                    botSay(randomBotResponse, userName);
                    botSay(randomBotQuestion);
                }
                case NEUTRAL -> {
                    botChooseRandomAnswer(neutralMoodResponseList, neutralMoodQuestionList);
                    botSay(randomBotResponse, userName);
                    botSay(randomBotQuestion);
                }
                default -> botSay(firstMetPhrase);
            }
            // getting patient's input
            userInput = getUserInput().toLowerCase();

            // checking for words in patient's input that can describe patient's mood
            if (checkForMoodWordsInInput(greatWordsToCheckList))
                patientMood = Mood.HAPPY;
            else if (checkForMoodWordsInInput(sadWordsToCheckList))
                patientMood = Mood.SAD;
            else if (checkForMoodWordsInInput(stressedWordsToCheckList))
                patientMood = Mood.STRESSED;
            else if (checkForMoodWordsInInput(neutralWordsToCheckList))
                patientMood = Mood.NEUTRAL;
            else if (patientMood == Mood.BASE)
                System.out.println("\n> Sorry, I don't understand. Could you describe it in a different way?");
        }
    }

    private void botBye() {
        // check for patient's name
        // if patient entered "bye" instead of his name, then conversation is ended with unique bot's response
        if (patientMood == Mood.BASE) {
            botSay("If you don\'t want to have a conversation with me it is totally fine, have a nice day!");
        } else {
            // bot decides what to say in the end depending on patient's mood
            switch (patientMood) {
                case HAPPY    -> botSay("It was great chatting with you, %s! Keep spreading that positive energy! Have a fantastic day!", userName);
                case SAD      -> botSay("I\'m really glad we talked, %s. Remember, you\'re not alone, and I\'m always here if you need me. Take care.", userName);
                case STRESSED -> botSay("I know things might be tough, %s, but you\'ve got this! Try to take a deep breath and do something nice for yourself today. See you next time!", userName);
                case NEUTRAL  -> botSay("Thanks for stopping by, %s! If you ever want to chat again, I\'m here. Have a great day!", userName);
            }
        }
        System.exit(0);
    }

    // function that check's words that can describe patient's mood
    private boolean checkForMoodWordsInInput(String[] arrayOfMoodWords) {
        for (String word : arrayOfMoodWords) {
            if (userInput.contains(word)) {
                return true;
            }
        }
        return false;
    }
}

class Phrase {
    public String phrase = "";
    public short timeoutForPhrase = 0;

    public Phrase(String newPhrase) {
        this.phrase = newPhrase;
    }
}

// enumerator that contains moods that can be assigned for patient
enum Mood {
    HAPPY,
    SAD,
    STRESSED,
    NEUTRAL,
    BASE
}