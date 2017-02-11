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

        //Color color = new Color(image.getRGB(0, 0));
        //colors.add(color);

        //TODO: issue with image coordinates when using the full image size
        //keeps saying out of bounds @ row: 660 column: 0 for bit.jpg
        //breaks on row 0 width 540 for sangeo.jpg
        for (int row = 0; row < image.getHeight()/2; row++) {//image.getHeight()
            for (int column = 0; column < image.getWidth()/2; column++) {
                System.out.printf("Coordinates: %d %d\n", row, column);
                Color color = new Color(image.getRGB(row, column));
                colors.add(color);
            }
        }

        Path path = Paths.get("public/files/", file.getName());
        Files.delete(path);

        return ("home");
    }
}
