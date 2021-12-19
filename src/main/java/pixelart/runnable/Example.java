package pixelart.runnable;

import pixelart.utils.BlockImage;
import pixelart.utils.ImageEngine;
import pixelart.utils.Resources;

public class Example {
    public static void main(String[] args) {
        Resources.gatherResources(); //Necessary
        BlockImage.init(); //Necessary
        ImageEngine.work("src/main/resources/image/example.png");
    }
}
