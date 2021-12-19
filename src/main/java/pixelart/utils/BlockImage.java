package pixelart.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BlockImage {
    public static ArrayList<BlockImage> blockImages = new ArrayList<>();

    public static void init(){
        Log.log("Initializing block images");
        File[] files = Resources.getResources();
        for (File file : files){
            BufferedImage bImage = null;
            try {
                bImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int w = bImage.getWidth();
            int h = bImage.getHeight();
            int[] soma = new int[3];
            Color c;
            for( int i = 0; i < w; i++ ) {
                for (int j = 0; j < h; j++) {
                    c = new Color(bImage.getRGB(i, j));
                    soma[0] += c.getRed();
                    soma[1] += c.getGreen();
                    soma[2] += c.getBlue();
                }
            }
            blockImages.add(new BlockImage(soma[0]/(w*h), soma[1]/(w*h), soma[2]/(w*h), file.getPath(), file.getName()));
        }
    }


    private int r;
    private int g;
    private int b;
    private String path;
    private String name;
    private BufferedImage bufferedImage;

    public BlockImage(int r, int g, int b, String path, String name) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.path = path;
        this.name = name;
        try {
            this.bufferedImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor(){
        return new Color(r,g,b);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public String toString() {
        return "BlockImage{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
