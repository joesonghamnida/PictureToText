package pic.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadText {

    public static ArrayList<String> readFile(String text) throws FileNotFoundException {
        ArrayList<String> rawText = new ArrayList<>();

        File f = new File(text);

        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNext()) {
            String line = fileScanner.next();
            line.split(" ");
            line.split(",");
            line.split(";");
            line.split(":");
            line.split(".");

            rawText.add(line);
        }

        return rawText;
    }

}
