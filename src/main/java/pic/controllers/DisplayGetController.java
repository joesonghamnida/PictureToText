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

import pic.utils.*;

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
        colors = PicDeconstruction.colorBreakdown(image, colors);


        //TODO: why am I grouping this into blocks anyway?
        //TODO: allow for adjustable block sizes - allows user to see what words occur
        ArrayList<ArrayList> blockGroups = new ArrayList<>();
        blockGroups = PicDeconstruction.groupIntoBlocks(colors, blockGroups);

        ArrayList<Integer> blockValues = new ArrayList();
        blockValues = PicDeconstruction.breakdownBlocksIntoBlock(blockGroups, blockValues);

        HashMap<Integer, Integer> colorOccurrences = new HashMap<>();
        colorOccurrences = PicDeconstruction.removeColorDuplicates(colorOccurrences, blockValues);
        System.out.println("color occurrences size: "+colorOccurrences.size());
        
        //read in text and break down into values
        ArrayList<String> text = new ArrayList<>();
        text = ReadText.readFile("text.txt");
        text = CleanText.cleanText(text);

        //probably some bug in here when dealing with the text. Only 351542 values vs 479k+
        HashMap<String, Integer> textValues = new HashMap<>();
        textValues = ConvertText.convertText(text);
        System.out.println("text values size: "+textValues.size());

        ArrayList<ArrayList<String>> wordsAndValues = MapWordToBlock.mapWordToBlock(textValues, colorOccurrences);

        System.out.println(wordsAndValues.size());

        Path path = Paths.get("public/files/", file.getName());
        Files.delete(path);


        /*

        quicksort here

        */

        //write to file
        WriteToFile.writeFile(file.getName()+".txt", wordsAndValues.toString());


        //pass to frontend
        //limiter for testing purposes
        model.addAttribute("results",wordsAndValues.subList(0,10));

        return ("home");
    }
}
