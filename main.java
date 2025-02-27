import java.util.Random;
import java.util.Scanner;

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

    // variables for randomly chosen bot phrases
    private Random rand = new Random();
    private String randomBotQuestion;
    private String randomBotResponse;
    
    private Scanner scanner = new Scanner(System.in);
    private String userInput;
    
    // phrase that bot says after entering your name
    private String firstMetPhrase = "Are you feeling happy, sad, stressed, or neutral?";


    
    // IMPORTANT! Bot speaking is dependent on patient's (user's) mood
    // IMPORTANT! If bot sees in patient's input, that patient is happy/sad/worried/neutral, bot starts printing phrases that belong to the "happy/sad/worried/neutral mood" arrays

    // initializing bot first state
    private Mood patientMood = Mood.BASE;



    // initializing bot's questions and responses for each patient's mood
    private String[] greatMoodQuestionList = {
        "What made your day so good?",
        "What's the best thing that happened today?",
        "Do you have any fun plans coming up?",
        "Is there something or someone that made your day special?",
        "If you could celebrate right now, how would you do it?",
        "What\'s a small thing that made you smile today?",
        "What\'s something you\'re grateful for?",
        "Have you shared your happiness with anyone else today?",
        "What\'s a song that perfectly matches your mood right now?",
        "If today had a theme song, what would it be?"
    };

    private String[] greatMoodResponseList = {
        "That's great to hear",
        "That\'s wonderful to hear",
        "Happiness is contagious",
        "I love hearing good news",
        "You seem to be in a fantastic mood",
        "Life\'s little joys make a big difference",
        "Your positivity is inspiring",
        "Keep shining and spreading joy",
        "Moments like these make life beautiful",
        "Smiles are meant to be shared"
    };


    private String[] sadMoodQuestionList = {
        "Is there something that usually makes you feel better?",
        "What's one thing you're proud of?",
        "Have you been able to take care of yourself today?",
        "What is something that brings you comfort?",
        "If you could do anything right now to feel better, what would it be?",
        "What\'s something kind you\'ve done for yourself recently?",
        "Have you had moments like this before, and how did you get through them?"
    };

    private String[] sadMoodResponseList = {
        "I'm really sorry to hear that",
        "It's okay to feel sad sometimes",
        "You're not alone in this",
        "I believe in you",
        "Tough times don\'t last, but strong people do",
        "Your feelings are valid, and I\'m here for you",
        "You deserve kindness, including from yourself",
        "I wish I could give you a virtual hug right now",
        "Remember, emotions come and go - you're stronger than you think"
    };

    private String[] stressedMoodQuestionList = {
        "Have you tried taking a few deep breaths?",
        "What is your favorite way to relax?",
        "What\'s worked for you in the past when you\'ve felt like this?",
        "Is there anything specific that\'s causing you stress? Describe it.",
        "What\'s one thing you can control right now? Describe it.",
        "How can you be kind to yourself today?"
    };

    private String[] stressedMoodResponseList = {
        "Stress can be overwhelming, but you\'re not alone",
        "Sometimes a quick walk or listening to music can help",
        "It sounds like you have a lot on your mind, but you\'re strong enough to handle it",
        "I know things can feel tough, but you've handled challenges before",
        "Remember to take things one step at a time",
        "Even small breaks can help reset your mind",
        "You're doing better than you think",
        "It's okay to slow down and breathe",
        "You have the strength to get through this",
        "Be kind to yourself - you\'re doing your best"
    };

    private String[] neutralMoodQuestionList = {
        "Did anything interesting happen today? Describe it.",
        "What\'s something you\'re looking forward to?",
        "What\'s a hobby or activity you enjoy?",
        "If you could be anywhere right now, where would you go?",
        "Is there something new you\'ve been wanting to try?",
        "Do you prefer quiet moments or exciting adventures?",
        "What\'s a simple pleasure that makes your day better?",
        "If you could instantly master any skill, what would it be?",
        "What\'s the last thing that made you laugh?"
    };

    private String[] neutralMoodResponseList = {
        "Sounds like a regular day",
        "Sometimes quiet days are the best",
        "It\'s nice to have a balance between excitement and peace",
        "Neutral is a good place to be",
        "Even uneventful days can have small joys",
        "Routine can be comforting in its own way",
        "A calm day can be refreshing",
        "It\'s always good to have moments to just breathe",
        "Not every day has to be extraordinary to be meaningful",
        "Appreciating the little things can make all the difference"
    };



    // initializing words that bot checks in patient's input
    private String[] greatWordsToCheckList = {
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

    private String[] sadWordsToCheckList = {
        "sad",
        "down",
        "upset",
        "unhappy",
        "depressed",
        "miserable",
        "lonely",
        "heartbroken"
    };

    private String[] stressedWordsToCheckList = {
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

    private String[] neutralWordsToCheckList = {
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
        System.out.printf("> Greetings! I am a %s bot named %s. Throughout our conversation you can type \"bye\" to end.\n", botThematic, botName);
        System.out.printf("> Please enter your name below to start our therapy!\n");
        userName = getUserInput();
        System.out.printf("> Fantastic! Nice to meet you, %s!\n", userName);
    }

    private void botSays(String phrase) {
        System.out.printf("\n> ");
        System.out.println(phrase);
    }

    private String getUserInput() {
        System.out.printf("> ");
        String input = scanner.nextLine();

        // if patient types word "bye" bot ends conversation
        if (input.toLowerCase().contains("bye"))
            botBye();
        
        System.out.printf("\n");
        return input;
    }

    // main dialog loop between patient and bot 
    public void botLaunchMainConversationLoop() {
        botGreet(); // bot greets and asks for patient's name

        // main conversation loop
        while (true) {
            // depending on patient's mood bot randomly picks and prints phrases that correspond to the patient's mood
            switch (patientMood) {
                case HAPPY -> {
                    randomBotResponse = greatMoodResponseList[rand.nextInt(greatMoodResponseList.length)];
                    randomBotQuestion = greatMoodQuestionList[rand.nextInt(greatMoodQuestionList.length)];
                    System.out.printf("> %s, %s! ", randomBotResponse, userName);
                    botSays(randomBotQuestion);
                }
                case SAD -> {
                    randomBotResponse = sadMoodResponseList[rand.nextInt(sadMoodResponseList.length)];
                    randomBotQuestion = sadMoodQuestionList[rand.nextInt(sadMoodQuestionList.length)];
                    System.out.printf("> %s, %s! ", randomBotResponse, userName);
                    botSays(randomBotQuestion);
                }
                case STRESSED -> {
                    randomBotResponse = stressedMoodResponseList[rand.nextInt(stressedMoodResponseList.length)];
                    randomBotQuestion = stressedMoodQuestionList[rand.nextInt(stressedMoodQuestionList.length)];
                    System.out.printf("> %s, %s! ", randomBotResponse, userName);
                    botSays(randomBotQuestion);
                }
                case NEUTRAL -> {
                    randomBotResponse = neutralMoodResponseList[rand.nextInt(neutralMoodResponseList.length)];
                    randomBotQuestion = neutralMoodQuestionList[rand.nextInt(neutralMoodQuestionList.length)];
                    System.out.printf("> %s, %s! ", randomBotResponse, userName);
                    botSays(randomBotQuestion);
                }
                default -> botSays(firstMetPhrase);
            }
            // getting patient's input
            userInput = getUserInput().toLowerCase();

            // checking for words in patient's input that can describe patient's mood
            if (checkForMoodWords(greatWordsToCheckList))
                patientMood = Mood.HAPPY;
            else if (checkForMoodWords(sadWordsToCheckList))
                patientMood = Mood.SAD;
            else if (checkForMoodWords(stressedWordsToCheckList))
                patientMood = Mood.STRESSED;
            else if (checkForMoodWords(neutralWordsToCheckList))
                patientMood = Mood.NEUTRAL;
            else if (patientMood == Mood.BASE)
                System.out.println("\n> Sorry, I don't understand. Could you describe it in a different way?");
        }
    }

    private void botBye() {
        // check for patient's name
        // if patient entered "bye" instead of his name, then conversation is ended with unique bot's response
        if (patientMood == Mood.BASE) {
            System.out.printf("\n> If you don\'t want to have a conversation with me it is totally fine, have a nice day!\n");
        } else {
            // bot decides what to say in the end depending on patient's mood
            switch (patientMood) {
                case HAPPY    -> System.out.printf("\n> It was great chatting with you, %s! Keep spreading that positive energy! Have a fantastic day!\n", userName);
                case SAD      -> System.out.printf("\n> I\'m really glad we talked, %s. Remember, you\'re not alone, and I\'m always here if you need me. Take care.\n", userName);
                case STRESSED -> System.out.printf("\n> I know things might be tough, %s, but you\'ve got this! Try to take a deep breath and do something nice for yourself today. See you next time!\n", userName);
                case NEUTRAL  -> System.out.printf("\n> Thanks for stopping by, %s! If you ever want to chat again, I\'m here. Have a great day!\n", userName);
            }
        }
        System.exit(0);
    }

    // function that check's words that can describe patient's mood
    private boolean checkForMoodWords(String[] arrayOfMoodWords) {
        for (String word : arrayOfMoodWords) {
            if (userInput.contains(word)) {
                return true;
            }
        }
        return false;
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