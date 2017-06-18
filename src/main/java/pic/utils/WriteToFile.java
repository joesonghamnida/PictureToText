package pic.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by joe on 6/18/17.
 */
public class WriteToFile {

    public static void writeFile(String fileName, String fileContent) throws IOException {
        File f = new File(fileName);
        FileWriter fw = new FileWriter(f);
        fw.write(fileContent);
        fw.close();
    }

}
