package pic.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class ConvertText {

    public static HashMap<String, Integer> convertText(ArrayList<String> text) {
        HashMap<String, Integer> convertedText = new HashMap<>();

        for (String s : text) {

            //TODO: refactor all of this
            int value = 0;
            for (int i = 0; i < s.length(); i++) {
                byte[] b = s.getBytes();

                for (int j = 0; j < b.length; j++) {
                    value += b[j];
                }
            }
            convertedText.put(s, value);
        }
        return convertedText;
    }
}
