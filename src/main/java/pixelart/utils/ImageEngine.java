package pixelart.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEngine {
    public static int diff(Color c, Color c2){
        return Math.abs(c.getRed()-c2.getRed()) + Math.abs(c.getGreen()-c2.getGreen()) + Math.abs(c.getBlue()-c2.getBlue());
    }

    public static File work(String imagem) {
        Log.log("Converting image");
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagem));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BlockImage[][] besties = new BlockImage[img.getWidth()][img.getHeight()];

        for( int i = 0; i < img.getWidth(); i++ ) {
            for (int j = 0; j < img.getHeight(); j++) {
                Object[] best = new Object[]{10000, null};
                Color c = new Color(img.getRGB(i, j));
                for (BlockImage blockImage : BlockImage.blockImages){
                    int d = diff(c, blockImage.getColor());
                    if (d<(int) best[0]){
                        best[0] = d;
                        best[1] = blockImage;
                    }
                }
                besties[i][j] = (BlockImage) best[1];
            }
        }

        Log.log("Combining blocks");
        BufferedImage combined = new BufferedImage(img.getWidth()*16, img.getHeight()*16, BufferedImage.TYPE_INT_RGB);

        Graphics g = combined.getGraphics();

        for (int i = 0; i < besties.length; i++) {
            for (int j = 0; j < besties[i].length; j++) {
                g.drawImage(besties[i][j].getBufferedImage(), i*16, j*16, null);
            }
        }

        g.dispose();

        Log.log("Saving final image");
        try {
            ImageIO.write(combined, "PNG", new File("src/main/resources/result/"+new File(imagem).getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.log("All done! File: " + "src/main/resources/result/"+new File(imagem).getName());
        return new File("src/main/resources/result/"+new File(imagem).getName());
    }
}
