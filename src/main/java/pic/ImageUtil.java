package pic;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

/***
 * Note:
 * @author Ryan M. Kane
 * github: https://github.com/ryankane/FragileWatermark
 */

public class ImageUtil {

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
}
