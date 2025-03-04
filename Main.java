import java.util.Random;
import java.util.Scanner;

/*

What to fix:
    1) Add more bot's questions
    2) The code repeats similar logic for handling different moods in several places. For example, the switch statement inside startConversationLoop() could be refactored into a single method that accepts the mood as a parameter.

*/


class Main {
    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.startConversationLoop();
    }
}

class Bot {
    private final String botName = "Dr. Chat";
    private String userName = "";
    private final byte MAX_USERNAME_LENGTH = 15;

    // variables for randomly chosen bot phrases
    private final Random rand = new Random();
    private String randomBotQuestion = "";
    private String randomBotResponse = "";
    
    private final Scanner scanner = new Scanner(System.in);
    private String userInput = "";
    
    // standart bot's phrases
    private final String botIntroductionPhrase = "Greetings! I am a psychologist bot named %s. Throughout our conversation you can type \"bye\" to end or \"main menu\" to start new.";
    private final String askUserNamePhrase = "Please enter your name below to start our therapy! Maximum length of name is %d characters.";
    private final String userNameTooLongPhrase = "Your user name is longer than %d! Please enter your name below again...";
    private final String invalidUserNameCharactersPhrase = "Your name should only contain basic letters! Try again...";
    private final String userNameAcceptedPhrase = "Fantastic! Nice to meet you, %s!";

    private final String firstMetPhrase = "Are you feeling happy, sad, stressed, or neutral?";
    private final String emptyInputErrorPhrase = "Sorry, but input cannot be blank! Please try again...";

    private final String botUnrecognizedInputPhrase = "Sorry, I don't understand. Could you describe it in a different way?";

    private final String happyGoodbyePhrase = "It was great chatting with you, %s! Keep spreading that positive energy! Have a fantastic day!";
    private final String sadGoodbyePhrase = "I\'m really glad we talked, %s. Remember, you\'re not alone, and I\'m always here if you need me. Take care.";
    private final String stressedGoodbyePhrase = "I know things might be tough, %s, but you\'ve got this! Try to take a deep breath and do something nice for yourself today. See you next time!";
    private final String neutralGoodbyePhrase = "Thanks for stopping by, %s! If you ever want to chat again, I\'m here. Have a great day!";
    private final String defaultGoodbyePhrase = "If you don\'t want to have a conversation with me it is totally fine, have a nice day!";

    
    // IMPORTANT! Bot speaking is dependent on patient's (user's) mood
    // IMPORTANT! If bot sees in patient's input, that patient is happy/sad/worried/neutral, bot starts printing random phrases that belong to the "happy/sad/worried/neutral mood" arrays

    // initializing bot first state
    private Mood patientMood = Mood.BASE;

    // initializing bot's questions and responses for each patient's mood
    private final Phrase[] greatMoodQuestionList = {
        new Phrase("Could you elaborate, %s, on what made your day particularly good?"),
        new Phrase("Tell me more, %s, about the best thing that happened to you today."),
        new Phrase("Describe in detail, %s, some fun plans you have coming up."),
        new Phrase("In what ways, %s, did someone or something make your day special today?"),
        new Phrase("Imagine you're celebrating right now, %s. How would you describe the celebration?"),
        new Phrase("Share the story, %s, behind a small thing that made you smile today."),
        new Phrase("What aspects of your life, %s, are you currently feeling grateful for, and why?"),
        new Phrase("Describe the experience, %s, of sharing your happiness with someone today."),
        new Phrase("If you were to explain why a song perfectly matches your mood, %s, what would you say?"),
        new Phrase("If today had a theme song, %s, what would it be and what moments would it highlight?"),
        new Phrase("Could you share the steps, %s, you took to achieve a recent goal you're proud of?"),
        new Phrase("Describe the moment today, %s, that felt particularly joyful to you."),
        new Phrase("Explain the positive thought, %s, you had today and how it made you feel."),
        new Phrase("Tell me more, %s, about a future event you're excited about and why it's special to you."),
        new Phrase("How has improving a skill, %s, brought you joy recently?"),
        new Phrase("Describe the activity, %s, that made you feel most alive today."),
        new Phrase("Could you share the context, %s, of a compliment you received and how it made you feel?"),
        new Phrase("Tell me about a memory, %s, you recalled that brought you joy and why it was significant."),
        new Phrase("Explain what excited you, %s, about a new thing you discovered."),
        new Phrase("Describe a specific way, %s, you spread joy to others today and how it felt.")
    };

    private final Phrase[] greatMoodResponseList = {
        new Phrase("That's great to hear, %s!"),
        new Phrase("That's wonderful to hear, %s!"),
        new Phrase("Happiness is contagious, %s!"),
        new Phrase("I love hearing good news, %s!"),
        new Phrase("You seem to be in a fantastic mood, %s!"),
        new Phrase("Life's little joys make a big difference, %s!"),
        new Phrase("Your positivity is inspiring, %s!"),
        new Phrase("Keep shining and spreading joy, %s!"),
        new Phrase("Moments like these make life beautiful, %s!"),
        new Phrase("Smiles are meant to be shared, %s!"),
        new Phrase("That's absolutely brilliant, %s!"),
        new Phrase("You're glowing with happiness, %s!"),
        new Phrase("What a fantastic outlook, %s!"),
        new Phrase("You've truly made my day brighter, %s!"),
        new Phrase("Your happiness is a gift, %s!"),
        new Phrase("That sounds like a perfect day, %s!"),
        new Phrase("You're a beacon of positivity, %s!"),
        new Phrase("Your energy is infectious, %s!"),
        new Phrase("That's truly heartwarming, %s!"),
        new Phrase("You're making the most of every moment, %s!")
    };

    private final Phrase[] sadMoodQuestionList = {
        new Phrase("Describe something, %s, that usually helps you feel better when you're sad."),
        new Phrase("Tell me more, %s, about one thing you're proud of, even when feeling down."),
        new Phrase("Could you describe, %s, how you've tried to take care of yourself today?"),
        new Phrase("What specifically, %s, brings you comfort during difficult times?"),
        new Phrase("If you could do one thing right now to feel better, %s, explain what it would be and why."),
        new Phrase("Describe a recent act of kindness, %s, you've shown yourself."),
        new Phrase("Tell me about a past experience, %s, similar to this and how you managed to get through it."),
        new Phrase("Could you describe the specific thought or situation, %s, that's contributing to your sadness?"),
        new Phrase("Explain a small step, %s, you could take to feel a bit better and why you chose it."),
        new Phrase("Describe something, %s, you enjoy that you haven't done in a while and why you like it."),
        new Phrase("Is there someone, %s, you'd like to talk to? Explain what you'd like to share."),
        new Phrase("Tell me about a memory, %s, that used to bring you comfort and why it's significant."),
        new Phrase("Explain a way, %s, you can show yourself compassion right now."),
        new Phrase("Describe a simple activity, %s, that might help distract you for a bit and why you chose it."),
        new Phrase("What positive affirmation, %s, can you tell yourself and why does it resonate with you?"),
        new Phrase("Explain a healthy way, %s, you can express your sadness."),
        new Phrase("Describe a goal, %s, you can set for yourself to work on feeling better and how you'll approach it."),
        new Phrase("Explain a way, %s, you can connect with nature to feel more grounded."),
        new Phrase("Describe a hobby, %s, you used to enjoy and why you'd like to pick it up again."),
        new Phrase("Explain a small act of self-care, %s, you can do right now.")
    };

    private final Phrase[] sadMoodResponseList = {
        new Phrase("I'm really sorry to hear that, %s..."),
        new Phrase("It's okay to feel sad sometimes, %s!"),
        new Phrase("You're not alone in this, %s!"),
        new Phrase("I believe in you, %s!"),
        new Phrase("Tough times don't last, but strong people do, %s!"),
        new Phrase("Your feelings are valid, and I'm here for you, %s!"),
        new Phrase("You deserve kindness, including from yourself, %s!"),
        new Phrase("I wish I could give you a virtual hug right now, %s!"),
        new Phrase("Remember, emotions come and go - you're stronger than you think, %s!"),
        new Phrase("I'm here to listen without judgment, %s."),
        new Phrase("Your strength in sharing your feelings is admirable, %s."),
        new Phrase("You're not a burden, %s. Your feelings matter."),
        new Phrase("You're worthy of comfort and support, %s."),
        new Phrase("It's okay to ask for help, %s."),
        new Phrase("You're not defined by your sadness, %s."),
        new Phrase("You're taking a brave step by acknowledging your feelings, %s."),
        new Phrase("You're not alone in feeling this way, %s."),
        new Phrase("You're doing your best, and that's enough, %s."),
        new Phrase("You're allowed to feel your feelings, %s."),
        new Phrase("You're not weak for feeling sad, %s.")
    };

    private final Phrase[] stressedMoodQuestionList = {
        new Phrase("Have you tried taking a few deep breaths, %s?"),
        new Phrase("What's your favorite way to relax, %s?"),
        new Phrase("What's worked for you in the past, %s, when you've felt like this?"),
        new Phrase("Is there anything specific, %s, that's causing you stress? Describe it."),
        new Phrase("What's one thing, %s, you can control right now? Describe it."),
        new Phrase("How can you be kind to yourself today, %s?"),
        new Phrase("What's a way, %s, to break down your tasks into smaller, manageable steps?"),
        new Phrase("What's a healthy boundary, %s, you can set to reduce stress?"),
        new Phrase("What's a way, %s, to prioritize your tasks and focus on what's most important?"),
        new Phrase("What's a way, %s, to create a calming environment for yourself?"),
        new Phrase("What's a way, %s, to practice mindfulness or meditation to reduce stress?"),
        new Phrase("What's a way, %s, to get some physical activity to release tension?"),
        new Phrase("What's a way, %s, to express your stress in a healthy way, like journaling or talking to someone?"),
        new Phrase("What's a way, %s, to identify and challenge negative thought patterns?"),
        new Phrase("What's a way, %s, to practice self-compassion and reduce self-criticism?"),
        new Phrase("What's a way, %s, to create a support system to help you manage stress?"),
        new Phrase("What's a way, %s, to practice time management and avoid procrastination?"),
        new Phrase("What's a way, %s, to get enough sleep and maintain a healthy sleep routine?"),
        new Phrase("What's a way, %s, to nourish your body with healthy foods to support your well-being?"),
        new Phrase("What's a way, %s, to take a break and engage in a relaxing activity?")
    };

    private final Phrase[] stressedMoodResponseList = {
        new Phrase("Stress can be overwhelming, but you're not alone, %s!"),
        new Phrase("Sometimes a quick walk or listening to music can help, %s!"),
        new Phrase("It sounds like you have a lot on your mind, but you're strong enough to handle it, %s!"),
        new Phrase("I know things can feel tough, but you've handled challenges before, %s!"),
        new Phrase("Remember to take things one step at a time, %s!"),
        new Phrase("Even small breaks can help reset your mind, %s!"),
        new Phrase("You're doing better than you think, %s!"),
        new Phrase("It's okay to slow down and breathe, %s!"),
        new Phrase("You have the strength to get through this, %s!"),
        new Phrase("Be kind to yourself - you're doing your best, %s!"),
        new Phrase("You're allowed to feel stressed, %s. It's a normal human emotion."),
        new Phrase("You're not responsible for everything, %s. It's okay to delegate or ask for help."),
        new Phrase("You're not alone in feeling this way, %s. Many people experience stress."),
        new Phrase("You're taking a positive step by acknowledging your stress, %s."),
        new Phrase("You're not defined by your stress, %s. It doesn't define your worth."),
        new Phrase("You're capable of managing your stress, %s. You have the tools and resources."),
        new Phrase("You're not weak for feeling stressed, %s. It takes strength to acknowledge it."),
        new Phrase("You're doing your best to cope, %s. And that's commendable."),
        new Phrase("You're not alone in facing challenges, %s. We all have our struggles."),
        new Phrase("You're allowed to prioritize your well-being, %s. Take time for yourself.")
    };

    private final Phrase[] neutralMoodQuestionList = {
        new Phrase("Describe anything interesting, %s, that happened to you today."),
        new Phrase("Tell me more, %s, about something you're looking forward to and why."),
        new Phrase("Explain what you enjoy, %s, about a particular hobby or activity."),
        new Phrase("If you could travel anywhere right now, %s, describe where you'd go and what you'd do."),
        new Phrase("What's something new, %s, you've been wanting to try and what's holding you back?"),
        new Phrase("Describe the kind of experiences, %s, you prefer: quiet moments or exciting adventures."),
        new Phrase("What's a simple pleasure, %s, that makes your day better and why?"),
        new Phrase("If you could master any skill, %s, describe what it would be and how you'd use it."),
        new Phrase("Tell me about the last thing, %s, that made you laugh and why it was funny."),
        new Phrase("Describe a book, movie, or show, %s, you've enjoyed recently and what you liked about it."),
        new Phrase("Explain a way, %s, you can add a bit of excitement to your routine."),
        new Phrase("Describe a new interest or passion, %s, you'd like to explore and how you'd start."),
        new Phrase("Explain a way, %s, you can connect with friends or family and why it's important to you."),
        new Phrase("Describe a creative activity, %s, you'd like to engage in and why it appeals to you."),
        new Phrase("Explain a way, %s, you can learn something new and what topic interests you."),
        new Phrase("Describe a way, %s, you can give back to your community and why it's meaningful."),
        new Phrase("Explain a way, %s, you can explore nature and the outdoors and what you hope to gain from it."),
        new Phrase("Describe a way, %s, you can challenge yourself and step outside your comfort zone."),
        new Phrase("Explain a way, %s, you can appreciate the present moment and find joy in the little things."),
        new Phrase("Describe a way, %s, you can reflect on your goals and aspirations.")
    };

    private final Phrase[] neutralMoodResponseList = {
        new Phrase("Sounds like a regular day, %s."),
        new Phrase("Sometimes quiet days are the best, %s."),
        new Phrase("It's nice to have a balance between excitement and peace, %s."),
        new Phrase("Neutral is a good place to be, %s."),
        new Phrase("Even uneventful days can have small joys, %s."),
        new Phrase("Routine can be comforting in its own way, %s."),
        new Phrase("A calm day can be refreshing, %s."),
        new Phrase("It's always good to have moments to just breathe, %s."),
        new Phrase("Not every day has to be extraordinary to be meaningful, %s."),
        new Phrase("Appreciating the little things can make all the difference, %s."),
        new Phrase("It's good to have a sense of stability, %s."),
        new Phrase("You're taking time to recharge, %s, and that's important."),
        new Phrase("You're in a good place to reflect and plan, %s."),
        new Phrase("You're finding contentment in the present, %s."),
        new Phrase("You're open to new possibilities, %s."),
        new Phrase("You're creating space for growth, %s."),
        new Phrase("You're finding balance in your life, %s."),
        new Phrase("You're in a state of calm and clarity, %s."),
        new Phrase("You're finding peace in the ordinary, %s."),
        new Phrase("You're embracing the present moment, %s.")
    };



    // initializing words that bot checks in patient's input
    private final String[] greatWordsToCheckList = {
        "fine", "happy", "great", "good", "amazing", "fantastic", "awesome", "wonderful", "excited"
    };

    private final String[] sadWordsToCheckList = {
        "sad", "down", "upset", "unhappy", "depressed", "miserable", "lonely", "heartbroken"
    };

    private final String[] stressedWordsToCheckList = {
        "stressed", "overwhelmed", "anxious", "worried", "tense", "nervous", "frustrated", "exhausted", "burned out", "drained", "tired", "angry"
    };

    private final String[] neutralWordsToCheckList = {
        "neutral", "ok", "normal", "alright", "meh", "average", "nothing special", "decent", "not bad"
    };


    private void greetPatient() {
        patientMood = Mood.BASE;
        botSay(botIntroductionPhrase, botName);
        botSay(askUserNamePhrase, MAX_USERNAME_LENGTH);
        while (true) {
            userName = getUserInput();
            if (!isBasicText(userName))
                botSay(invalidUserNameCharactersPhrase);
            else if (isInputTooLong(userName, MAX_USERNAME_LENGTH))
                botSay(userNameTooLongPhrase, MAX_USERNAME_LENGTH);
            else
                break;
        }
        
       botSay(userNameAcceptedPhrase, userName);
    }

    private boolean isBasicText(String input) {
        return input.matches("[a-zA-Z ]+");
    }

    private boolean isInputTooLong(String input, short maxLength) {
        return input.length() > maxLength;
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
            input = scanner.nextLine().trim();

            if (!input.isBlank())
                break;
            else
                botSay(emptyInputErrorPhrase);
        }
        
        // if patient types word "bye" bot ends conversation
        if (input.toLowerCase().contains("bye"))
            goodBye();
        
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

    private void handleMoodAnswers(Phrase[] responseList, Phrase[] questionList) {
        botChooseRandomAnswer(responseList, questionList);
        botSay(randomBotResponse, userName);
        botSay(randomBotQuestion);
    }

    // main dialog loop between patient and bot 
    public void startConversationLoop() {
        while (true) { 
            greetPatient(); // bot greets and asks for patient's name

            // main conversation loop
            while (true) {
                // depending on patient's mood bot randomly picks and prints phrases that correspond to the patient's mood
                switch (patientMood) {
                    case HAPPY    -> handleMoodAnswers(greatMoodResponseList, greatMoodQuestionList);
                    case SAD      -> handleMoodAnswers(sadMoodResponseList, sadMoodQuestionList);
                    case STRESSED -> handleMoodAnswers(stressedMoodResponseList, stressedMoodQuestionList);
                    case NEUTRAL  -> handleMoodAnswers(neutralMoodResponseList, neutralMoodQuestionList);
                    default       -> botSay(firstMetPhrase);
                }
                // getting patient's input
                userInput = getUserInput().toLowerCase();
                if (userInput.toLowerCase().contains("main menu"))
                    break;


                // checking for words in patient's input that can describe patient's mood
                if (containsMoodKeywords(greatWordsToCheckList))
                    patientMood = Mood.HAPPY;
                else if (containsMoodKeywords(sadWordsToCheckList))
                    patientMood = Mood.SAD;
                else if (containsMoodKeywords(stressedWordsToCheckList))
                    patientMood = Mood.STRESSED;
                else if (containsMoodKeywords(neutralWordsToCheckList))
                    patientMood = Mood.NEUTRAL;
                else if (patientMood == Mood.BASE)
                    botSay(botUnrecognizedInputPhrase);
            }
        }
    }

    private void goodBye() {
        // check for patient's name
        // if patient entered "bye" instead of his name, then conversation is ended with unique bot's response
        if (patientMood == Mood.BASE) {
            botSay(defaultGoodbyePhrase, userName);
        } else {
            // bot decides what to say in the end depending on patient's mood
            switch (patientMood) {
                case HAPPY    -> botSay(happyGoodbyePhrase, userName);
                case SAD      -> botSay(sadGoodbyePhrase, userName);
                case STRESSED -> botSay(stressedGoodbyePhrase, userName);
                case NEUTRAL  -> botSay(neutralGoodbyePhrase, userName);
            }
        }
        scanner.close();
        System.exit(0);
    }

    // function that check's words that can describe patient's mood
    private boolean containsMoodKeywords(String[] arrayOfMoodWords) {
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