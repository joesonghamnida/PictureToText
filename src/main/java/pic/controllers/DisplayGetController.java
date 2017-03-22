package pic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class DisplayGetController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return ("home");
    }

    @RequestMapping(path = "/displayResults", method = RequestMethod.GET)
    public String displayResults(HttpSession session, Model model) throws FileNotFoundException, IOException {

        File file = (File) session.getAttribute("picture");

        BufferedImage image = ImageIO.read(file);

        ArrayList<Color> colors = new ArrayList<>();

        System.out.println("Height: " + image.getHeight());
        System.out.println("Width: " + image.getWidth());

        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                //System.out.printf("Coordinates: %d %d\n", row, column);
                Color color = new Color(image.getRGB(column, row));
                colors.add(color);
            }
        }

        //TODO: this will need to be refactored into it's own function
        //TODO: why am I grouping this into blocks anyway?
        //TODO: allow for adjustable block sizes - allows user to see what words occur
        //have some value greater than color total cap
        //group pixels into block, and store block in array list
        ArrayList<ArrayList> blockGroups = new ArrayList<>();
        int block = 0;
        while (block < colors.size()) {

            ArrayList<Color> colorBlock = new ArrayList<>();

            int limit = 0;

            //check current block position to prevent OOB exceptions
            if (block + 8 <= colors.size()) {
                limit = 8;
            } else {
                limit = colors.size() - block;
            }

            //add # of pixels to block
            for (int i = 0; i < limit; i++) {
                colorBlock.add(colors.get(block + i));
            }
            blockGroups.add(colorBlock);
            block += 8;
        }

        //break down list of blocks into individual block
        //block the block down into colors
        ArrayList<Integer> blockValues = new ArrayList();
        for(int i = 0; i < blockGroups.size(); i++){
            ArrayList<Color> colorBlock = blockGroups.get(i);

            int blockValue = 0;
            for(int j = 0; j < colorBlock.size(); j ++){
                Color color = colorBlock.get(j);
                blockValue += color.getBlue();
                blockValue += color.getGreen();
                blockValue += color.getRed();
                //this is commented out to lower upper range of value
                //blockValue += color.getAlpha();
            }
            blockValues.add(blockValue);
        }
        System.out.println(blockValues.size());

        //add up # of color values, remove duplicates
        //key should be renamed to something clearer
        HashMap<Integer, Integer> colorOccurrences = new HashMap<>();
        for (Integer blockValue: blockValues) {

            if(colorOccurrences.containsKey(blockValue)){
                int occurrence = colorOccurrences.get(blockValue);
              colorOccurrences.put(blockValue, occurrence + 1);
            }
            else{
                colorOccurrences.put(blockValue, 1);
            }
        }

        System.out.println(colorOccurrences.size());

        //read in text and break down into values
        //check color values against text values, store in hashmap?
        //maybe do some other analysis?
        //pass to frontend

        Path path = Paths.get("public/files/", file.getName());
        Files.delete(path);

        return ("home");
    }
}
