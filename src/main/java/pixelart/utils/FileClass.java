package pixelart.utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.*;

public class FileClass {

    public static void createFolder(String name){
        File folder = new File(name);
        if(!folder.exists()){
            folder.mkdir();
        }
    }

    public static String getExtension(String f){
        return getExtension(new File(f));
    }

    public static String getExtension(File f){
        String[] parts = f.getName().split("\\.");
        return parts[parts.length-1];
    }

    public static void delete(String path){
        File f = new File(path);
        try {
            if(f.exists()){
                if(f.isDirectory()){
                    FileUtils.deleteDirectory(f);
                }else{
                    f.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveFolder(String from, String to){
        try {
            Files.move(new File(from).toPath(), new File(to).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
