package pic.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class PicDeconstruction {

    public static ArrayList<Color> colorBreakdown(BufferedImage image, ArrayList<Color> colors) {
        System.out.println("Height: " + image.getHeight());
        System.out.println("Width: " + image.getWidth());

        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                //System.out.printf("Coordinates: %d %d\n", row, column);
                Color color = new Color(image.getRGB(column, row));
                colors.add(color);
            }
        }
        return colors;
    }

    public static ArrayList<ArrayList> groupIntoBlocks(ArrayList<Color> colors,ArrayList<ArrayList> blockGroups){
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
        return blockGroups;
    }

    public static ArrayList<Integer> breakdownBlocksIntoBlock(ArrayList<ArrayList> blockGroups ,ArrayList<Integer> blockValues){
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
        System.out.println("block values size: "+blockValues.size());
        return blockValues;
    }

    public static HashMap<Integer, Integer> removeColorDuplicates(HashMap<Integer, Integer> colorOccurrences, ArrayList<Integer> blockValues){
        for (Integer blockValue: blockValues) {

            if(colorOccurrences.containsKey(blockValue)){
                int occurrence = colorOccurrences.get(blockValue);
                colorOccurrences.put(blockValue, occurrence + 1);
            }
            else{
                colorOccurrences.put(blockValue, 1);
            }
        }
        return colorOccurrences;
    }
}



