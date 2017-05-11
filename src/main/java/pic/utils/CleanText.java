package pic.utils;


import java.util.ArrayList;

public class CleanText {

    public static ArrayList<String> cleanText(ArrayList<String> rawText) {

        ArrayList<String> cleanedText = new ArrayList<>();

        ArrayList<String> punctuationRemoved = removePunctuation(rawText);
        ArrayList<String> numbersRemoved = removeNumbers(punctuationRemoved);
        ArrayList<String> blankStringsRemoved = removeBlankStrings(numbersRemoved);
        ArrayList<String> websitesRemoved = removeWebsites(blankStringsRemoved);

        //debugCleaning(rawText, punctuationRemoved, numbersRemoved, blankStringsRemoved, websitesRemoved);

        cleanedText = websitesRemoved;
        return cleanedText;
    }

    public static ArrayList<String> removeBlankStrings(ArrayList<String> rawText) {
        ArrayList<String> blanksRemoved = new ArrayList<>();

        for (String string : rawText) {
            char[] chars = string.toCharArray();
            string = "";
            for (char c : chars) {
                if (c != ' ') {
                    string += c;
                }
            }
            blanksRemoved.add(string);
        }

        return blanksRemoved;
    }

    public static ArrayList<String> removeElements(ArrayList<String> rawText, String[] elements){
        ArrayList<String> elementsRemoved = new ArrayList<>();

        for (String word : rawText) {
            String cleanedWord = "";
            for (String element : elements) {
                if (word.contains(element)) {
                    cleanedWord = word.replace(element, "");
                    cleanedWord.trim();
                    word = cleanedWord;
                } else {
                    cleanedWord = word;
                }
            }
            elementsRemoved.add(cleanedWord);
        }
        return elementsRemoved;
    }

    public static ArrayList<String> removeNumbers(ArrayList<String> rawText) {
        ArrayList<String> numbersRemoved = new ArrayList<>();

        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

        numbersRemoved = removeElements(rawText, numbers);

        return numbersRemoved;
    }


    public static ArrayList<String> removePunctuation(ArrayList<String> rawText) {
        ArrayList<String> cleanedText = new ArrayList<>();
        String[] punctuationMarks = {".", ",", ":", ";", "?", "<", ">", "\\", "'", "{", "}", "*", "-", "_", "(", ")", "[", "]", "|", "!", "/", "@", "#", "$", "%", "^", "&", "\"", "+", "~", "`", "="};

        cleanedText = removeElements(rawText, punctuationMarks);

        return cleanedText;
    }

    public static ArrayList<String> removeWebsites(ArrayList<String> rawText) {
        ArrayList<String> websitesRemoved = new ArrayList<>();

        String[] protocols = {"https","http", "www"};

        websitesRemoved = removeElements(rawText, protocols);

        return websitesRemoved;
    }

    public static void debugCleaning(ArrayList<String> rawText, ArrayList<String> punctuationRemoved, ArrayList<String> numbersRemoved, ArrayList<String> blankStringsRemoved, ArrayList<String> websitesRemoved) {

        System.out.println("Raw text size: " + rawText.size());
        System.out.println("Punctuation: " + punctuationRemoved.size());
        System.out.println("Numbers : " + numbersRemoved.size());
        System.out.println("Blank strings: " + blankStringsRemoved.size());
        System.out.println("Websites: " + websitesRemoved.size());

        if (!(punctuationRemoved.size() <= rawText.size())) {
            System.out.println("Something went wrong cleaning punctuation");
        } else if (!(numbersRemoved.size() <= punctuationRemoved.size())) {
            System.out.println("Something went wrong cleaning numbers");
        } else if (!(websitesRemoved.size() <= numbersRemoved.size())) {
            System.out.println("Something went wrong cleaning websites");
        } else {
            System.out.println("Okay so far");
        }
    }
}

