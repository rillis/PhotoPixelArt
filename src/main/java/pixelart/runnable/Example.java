package pixelart.runnable;

import pixelart.utils.BlockImage;
import pixelart.utils.ImageEngine;
import pixelart.utils.Resources;

import java.io.File;

public class Example {
    public static void main(String[] args) {
        Resources.gatherResources(); //Necessary
        BlockImage.init(); //Necessary
        File result = ImageEngine.work("src/main/resources/image/example.png");
    }
}
