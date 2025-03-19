package projectPkgs.Bot;

import java.util.Random;
import java.util.Scanner;
import projectPkgs.Structures.Phrase;

public class BotLogic {
    private final String botName = "Dr. Chat";
    private String userName = "";
    private final byte MAX_USERNAME_LENGTH = 15;

    // variables for randomly chosen bot phrases
    private final Random rand = new Random();
    private String randomBotQuestion = "";
    private String randomBotResponse = "";

    private final Scanner scanner = new Scanner(System.in);
    private String userInput = "";

    // standard bot's phrases
    private final String botIntroductionPhrase = "Greetings! I am a psychologist bot named %s."
            + " Throughout our conversation you can type \"bye\" to end or \"main menu\" to start new.";
    private final String askUserNamePhrase = "Please enter your name below to start our therapy! Maximum length of name is %d characters.";
    private final String userNameTooLongPhrase = "Your user name is longer than %d! Please enter your name below again...";
    private final String invalidUserNameCharactersPhrase = "Your name should only contain basic letters! Please enter your name below again...";
    private final String userNameAcceptedPhrase = "Fantastic! Nice to meet you, %s!";

    private final String firstMetPhrase = "Are you feeling happy, sad, stressed, or neutral?";
    private final String emptyInputErrorPhrase = "Sorry, but input cannot be blank! Please try again...";

    private final String botUnrecognizedInputPhrase = "Sorry, I don't understand. Could you describe it in a different way?";

    private final String happyGoodbyePhrase = "It was great chatting with you, %s! Keep spreading that positive energy! Have a fantastic day!";
    private final String sadGoodbyePhrase = "I\'m really glad we talked, %s. Remember, you\'re not alone, and I\'m always here if you need me. Take care.";
    private final String stressedGoodbyePhrase = "I know things might be tough, %s, but you\'ve got this!"
            + " Try to take a deep breath and do something nice for yourself today. See you next time!";

    private final String neutralGoodbyePhrase = "Thanks for stopping by, %s! If you ever want to chat again, I\'m here. Have a great day!";
    private final String defaultGoodbyePhrase = "If you don\'t want to have a conversation with me it is totally fine, have a nice day!";


    // IMPORTANT! Bot speaking is dependent on patient's (user's) mood
    // IMPORTANT! If bot sees in patient's input, that patient is happy/sad/worried/neutral, bot starts printing random phrases that belong to the "happy/sad/worried/neutral mood" arrays

    // initializing bot first state
    private Mood patientMood = Mood.BASE;

    // initializing bot's questions and responses for each patient's mood
    private final Phrase[] greatMoodQuestionList = {
            new Phrase("Could you elaborate on what made your day particularly good?"),
            new Phrase("Tell me more about the best thing that happened to you today."),
            new Phrase("Describe in detail some fun plans you have coming up."),
            new Phrase("In what ways did someone or something make your day special today?"),
            new Phrase("Imagine you're celebrating right now, %s. How would you describe the celebration?"),
            new Phrase("Share the story behind a small thing that made you smile today."),
            new Phrase("What aspects of your life are you currently feeling grateful for, and why?"),
            new Phrase("Describe the experience of sharing your happiness with someone today."),
            new Phrase("If you were to explain why a song perfectly matches your mood what would you say?"),
            new Phrase("If today had a theme song what would it be and what moments would it highlight?"),
            new Phrase("Could you share the steps you took to achieve a recent goal you're proud of?"),
            new Phrase("Describe the moment today that felt particularly joyful to you."),
            new Phrase("Explain the positive thought you had today and how it made you feel."),
            new Phrase("Tell me more about a future event you're excited about and why it's special to you."),
            new Phrase("How has improving a skill brought you joy recently?"),
            new Phrase("Describe the activity that made you feel most alive today."),
            new Phrase("Could you share the context of a compliment you received and how it made you feel?"),
            new Phrase("Tell me about a memory you recalled that brought you joy and why it was significant."),
            new Phrase("Explain what excited you about a new thing you discovered."),
            new Phrase("Describe a specific way you spread joy to others today and how it felt.")
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
            new Phrase("Describe something that usually helps you feel better when you're sad."),
            new Phrase("Tell me more about one thing you're proud of, even when feeling down."),
            new Phrase("Could you describe how you've tried to take care of yourself today?"),
            new Phrase("What specifically brings you comfort during difficult times?"),
            new Phrase("If you could do one thing right now to feel better explain what it would be and why."),
            new Phrase("Describe a recent act of kindness you've shown yourself."),
            new Phrase("Tell me about a past experience similar to this and how you managed to get through it."),
            new Phrase("Could you describe the specific thought or situation that's contributing to your sadness?"),
            new Phrase("Explain a small step you could take to feel a bit better and why you chose it."),
            new Phrase("Describe something you enjoy that you haven't done in a while and why you like it."),
            new Phrase("Is there someone you'd like to talk to? Explain what you'd like to share."),
            new Phrase("Tell me about a memory that used to bring you comfort and why it's significant."),
            new Phrase("Explain a way you can show yourself compassion right now."),
            new Phrase("Describe a simple activity that might help distract you for a bit and why you chose it."),
            new Phrase("What positive affirmation can you tell yourself and why does it resonate with you?"),
            new Phrase("Explain a healthy way you can express your sadness."),
            new Phrase("Describe a goal you can set for yourself to work on feeling better and how you'll approach it."),
            new Phrase("Explain a way you can connect with nature to feel more grounded."),
            new Phrase("Describe a hobby you used to enjoy and why you'd like to pick it up again."),
            new Phrase("Explain a small act of self-care you can do right now.")
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
            new Phrase("Have you tried taking a few deep breaths?"),
            new Phrase("What's your favorite way to relax?"),
            new Phrase("What's worked for you in the past, when you've felt like this?"),
            new Phrase("Is there anything specific that's causing you stress? Describe it."),
            new Phrase("What's one thing, you can control right now? Describe it."),
            new Phrase("How can you be kind to yourself today?"),
            new Phrase("What's a way to break down your tasks into smaller, manageable steps?"),
            new Phrase("What's a healthy boundary, you can set to reduce stress?"),
            new Phrase("What's a way to prioritize your tasks and focus on what's most important?"),
            new Phrase("What's a way to create a calming environment for yourself?"),
            new Phrase("What's a way to practice mindfulness or meditation to reduce stress?"),
            new Phrase("What's a way to get some physical activity to release tension?"),
            new Phrase("What's a way to express your stress in a healthy way, like journaling or talking to someone?"),
            new Phrase("What's a way to identify and challenge negative thought patterns?"),
            new Phrase("What's a way to practice self-compassion and reduce self-criticism?"),
            new Phrase("What's a way to create a support system to help you manage stress?"),
            new Phrase("What's a way to practice time management and avoid procrastination?"),
            new Phrase("What's a way to get enough sleep and maintain a healthy sleep routine?"),
            new Phrase("What's a way to nourish your body with healthy foods to support your well-being?"),
            new Phrase("What's a way to take a break and engage in a relaxing activity?")
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
            new Phrase("Describe anything interesting that happened to you today."),
            new Phrase("Tell me more about something you're looking forward to and why."),
            new Phrase("Explain what you enjoy about a particular hobby or activity."),
            new Phrase("If you could travel anywhere right now describe where you'd go and what you'd do."),
            new Phrase("What's something new you've been wanting to try and what's holding you back?"),
            new Phrase("Describe the kind of experiences you prefer: quiet moments or exciting adventures."),
            new Phrase("What's a simple pleasure that makes your day better and why?"),
            new Phrase("If you could master any skill describe what it would be and how you'd use it."),
            new Phrase("Tell me about the last thing that made you laugh and why it was funny."),
            new Phrase("Describe a book, movie, or show you've enjoyed recently and what you liked about it."),
            new Phrase("Explain a way you can add a bit of excitement to your routine."),
            new Phrase("Describe a new interest or passion you'd like to explore and how you'd start."),
            new Phrase("Explain a way you can connect with friends or family and why it's important to you."),
            new Phrase("Describe a creative activity you'd like to engage in and why it appeals to you."),
            new Phrase("Explain a way you can learn something new and what topic interests you."),
            new Phrase("Describe a way you can give back to your community and why it's meaningful."),
            new Phrase("Explain a way you can explore nature and the outdoors and what you hope to gain from it."),
            new Phrase("Describe a way you can challenge yourself and step outside your comfort zone."),
            new Phrase("Explain a way you can appreciate the present moment and find joy in the little things."),
            new Phrase("Describe a way you can reflect on your goals and aspirations.")
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
        printPhrase(botIntroductionPhrase, botName);
        printPhrase(askUserNamePhrase, MAX_USERNAME_LENGTH);
        while (true) {
            userName = getAppropriateUserInput();

            if (!isBasicText(userName))
                printPhrase(invalidUserNameCharactersPhrase);
            else if (isInputTooLong(userName, MAX_USERNAME_LENGTH))
                printPhrase(userNameTooLongPhrase, MAX_USERNAME_LENGTH);
            else
                break;
        }

        printPhrase(userNameAcceptedPhrase, userName);
    }

    private boolean isBasicText(String input) {
        return input.matches("[a-zA-Z ]+");
    }

    private boolean isInputTooLong(String input, byte maxLength) {
        return input.length() > maxLength;
    }

    private void printPhrase(String phrase, Object... args) {
        System.out.printf("\n> ");
        System.out.printf(phrase, args);
        System.out.printf("\n");
    }

    private String getUserInput() {
        return scanner.nextLine().trim();
    }

    private String getAppropriateUserInput() {
        String input = "";

        while (true) {
            System.out.printf("> ");
            input = getUserInput();

            if (!input.isBlank())
                break;
            else
                printPhrase(emptyInputErrorPhrase);
        }

        // if patient types word "bye" bot ends conversation
        if (input.toLowerCase().contains("bye"))
            goodBye();

        System.out.printf("\n");
        return input;
    }

    private boolean userWantsToLogOut(String input) {
        return !(input.toLowerCase().contains("main menu"));
    }

    private void chooseRandomAnswer(Phrase[] responseList, Phrase[] questionList) {
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

    private void makeMoodAnswer(Phrase[] responseList, Phrase[] questionList) {
        chooseRandomAnswer(responseList, questionList);
        printPhrase(randomBotResponse, userName);
        printPhrase(randomBotQuestion);
    }

    private void handleMoodAnswers(Mood mood) {
        switch (mood) {
            case HAPPY    -> makeMoodAnswer(greatMoodResponseList, greatMoodQuestionList);
            case SAD      -> makeMoodAnswer(sadMoodResponseList, sadMoodQuestionList);
            case STRESSED -> makeMoodAnswer(stressedMoodResponseList, stressedMoodQuestionList);
            case NEUTRAL  -> makeMoodAnswer(neutralMoodResponseList, neutralMoodQuestionList);
            default       -> printPhrase(firstMetPhrase);
        }
    }

    // main dialog loop between patient and bot
    public void startConversationLoop() {
        boolean loggedIn = true;
        while (true) {
            greetPatient(); // bot greets and asks for patient's name

            // main conversation loop
            while (loggedIn) {
                // depending on patient's mood bot randomly picks and prints phrases that correspond to the patient's mood
                handleMoodAnswers(patientMood);
                // getting patient's input
                userInput = getAppropriateUserInput().toLowerCase();

                loggedIn = userWantsToLogOut(userInput);

                // checking for words in patient's input that can describe patient's mood
                checkTheMoodOfPatient();
            }
        }
    }

    private void checkTheMoodOfPatient() {
        if (containsMoodKeywords(greatWordsToCheckList))
            patientMood = Mood.HAPPY;
        else if (containsMoodKeywords(sadWordsToCheckList))
            patientMood = Mood.SAD;
        else if (containsMoodKeywords(stressedWordsToCheckList))
            patientMood = Mood.STRESSED;
        else if (containsMoodKeywords(neutralWordsToCheckList))
            patientMood = Mood.NEUTRAL;
        else if (patientMood == Mood.BASE)
            printPhrase(botUnrecognizedInputPhrase);
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

    private void goodByeUsingMoodPhrase(Mood mood) {
        switch (mood) {
            case HAPPY    -> printPhrase(happyGoodbyePhrase, userName);
            case SAD      -> printPhrase(sadGoodbyePhrase, userName);
            case STRESSED -> printPhrase(stressedGoodbyePhrase, userName);
            case NEUTRAL  -> printPhrase(neutralGoodbyePhrase, userName);
        }
    }

    private void goodBye() {
        // check for patient's name
        // if patient entered "bye" instead of his name, then conversation is ended with unique bot's response
        if (patientMood == Mood.BASE) {
            printPhrase(defaultGoodbyePhrase, userName);
        } else {
            // bot decides which phrase to say in the end depending on patient's mood
            goodByeUsingMoodPhrase(patientMood);
        }
        scanner.close();
        System.exit(0);
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
