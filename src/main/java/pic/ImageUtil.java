package pic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;

/***
 * Note:
 * @author Ryan M. Kane
 * github: https://github.com/ryankane/FragileWatermark
 */

public class ImageUtil {

    public static BufferedImage loadImage(String filename)throws FileNotFoundException, IOException{
        InputStream is = null;

        try{
            is = new FileInputStream(new File(filename));
            return ImageIO.read(is);
        }catch (Exception e){
            is = null;
        }

        try{
            is = ImageUtil.class.getClassLoader().getResourceAsStream(filename);
            return ImageIO.read(is);
        }catch (Exception e){
            System.err.printf("Could not print %s\n", filename);
        }finally{
            try{
                is.close();
            }catch (IOException e){

            }
        }
        return null;
    }

    public static BufferedImage[][] partitionImage(BufferedImage image, int blockSize){
        int width = image.getWidth();
        int height = image.getHeight();
        int rows = (int) Math.ceil(height / (float) blockSize);
        int columns = (int) Math.ceil(height / (float) blockSize);
        int remainderY = height % blockSize;
        int remainderX = width % blockSize;
        boolean isDivisibleY = remainderY == 0;
        boolean isDivisibleX = remainderX == 0;
        BufferedImage[][] blocks = new BufferedImage[rows][columns];
        for(int y = 0; y < rows; y++){
            int blockHeight = (y < rows - 1) ? blockSize : isDivisibleY ? blockSize: remainderY;
            int yOff = y * blockSize;
            for(int x = 0; x < columns; x++){
                int blockWidth = (x < columns - 1) ? blockSize : isDivisibleX ? blockSize : remainderX;
                int xOff = x * blockSize;
                blocks[x][y] = ImageUtil.cloneImage(image.getSubimage(xOff, yOff, blockWidth, blockHeight));
            }
        }
        return blocks;
    }

    public static BufferedImage cloneImage(BufferedImage originalImage){
        return cloneImage(originalImage, originalImage.getType());
    }

    public static BufferedImage cloneImage(BufferedImage originalImage, int imageType){
        BufferedImage copyImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), imageType);
        Graphics graphics = copyImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, null);
        graphics.dispose();
        return copyImage;
    }
}
