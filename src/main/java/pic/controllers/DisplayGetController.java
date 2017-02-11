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

import pic.ImageUtil;

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

        //BufferedImage[][] blocks = ImageUtil.partitionImage(image, 8);

        ArrayList<Color> colors = new ArrayList<>();

        System.out.println("Height: " + image.getHeight());
        System.out.println("Width: " + image.getWidth());

            for (int row = 0; row < image.getHeight(); row++) {//image.getHeight()
                for (int column = 0; column < image.getWidth(); column++) {
                //System.out.printf("Coordinates: %d %d\n", row, column);
                Color color = new Color(image.getRGB(column, row));
                colors.add(color);
            }
        }

        //add up numerical values & either remove duplicates or tally # of occurrences
        //read in text and break down into values
        //check color values against text values, store in hashmap?
        //maybe do some other analysis?
        //pass to frontend

        Path path = Paths.get("public/files/", file.getName());
        Files.delete(path);

        return ("home");
    }
}
