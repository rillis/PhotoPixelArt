package pixelart.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

public class Zip {
    public static void unZip(String zip, String destFolder){
        File z = new File(zip);
        try {
            ZipFile zipFile = new ZipFile(z);
            zipFile.extractAll(destFolder);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
