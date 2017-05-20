package pic.utils;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by joe on 5/19/17.
 */
public class MapWordToBlock {

    public static ArrayList<ArrayList<String>> mapWordToBlock(HashMap<String, Integer> textValues, HashMap<Integer, Integer> colorOccurrences) {
        //arraylist of arrays? [word, value, # of occurrences
        ArrayList<ArrayList<String>> wordArrays = new ArrayList<>();
        //TODO: fix this later. ugly
        HashMap<Integer, String> textValuesSwap = new HashMap<>();

        for(String s : textValues.keySet()){
            textValuesSwap.put(textValues.get(s), s);
        }

        //TODO: make this work with arrays
        for (Integer i : colorOccurrences.keySet()) {
            if (textValues.containsValue(i)) {
                ArrayList<String> word = new ArrayList<>();
                word.add(textValuesSwap.get(i));
                //word.add(String.valueOf(i));
                word.add(String.valueOf(colorOccurrences.get(i)));
                wordArrays.add(word);
            }
        }
        return wordArrays;
    }
}
