package pixelart.utils;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Resources {
    public static boolean hasResources(){
        return listFilesForFolder("src/main/resources/blocks/", false).length > 0;
    }

    public static File[] getResources(){
        return listFilesForFolder("src/main/resources/blocks/", true);
    }

    private static File download(String file,String url){
        File f = new File(file);
        try {
            FileUtils.copyURLToFile(new URL(url), f);
            return f;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static File[] listFilesForFolder(String folderName, boolean onlyFiles) {
        File folder = new File(folderName);
        ArrayList<File> list = new ArrayList<>();
        if (folder.listFiles() == null) return new File[0];
        for (final File file : folder.listFiles()) {
            if (onlyFiles) {
                if(!file.isDirectory()){
                    list.add(file);
                }
            }else{
                list.add(file);
            }
        }
        return list.toArray(new File[0]);
    }

    private static void cleanBlocks(){
        File[] files = listFilesForFolder("src/main/resources/blocks/", false);

        for (File file : files){
            if(!FileClass.getExtension(file).equals("png") || file.getName().contains("destroy") || file.getName().contains("stained_glass") || file.getName().contains("honey_")){
                file.delete();
            }else{
                BufferedImage img = null;
                try {
                    img = ImageIO.read(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(img.getHeight()!=16 || img.getWidth()!=16){
                    file.delete();
                }else{
                    boolean hasTransparency = false;
                    for( int i = 0; i < img.getWidth(); i++ ) {
                        for (int j = 0; j < img.getHeight(); j++) {
                            int rgb = img.getRGB(i, j);
                            if ((rgb >> 24) == 0x00) {
                                hasTransparency = true;
                                break;
                            }
                        }
                        if(hasTransparency){
                            break;
                        }
                    }
                    if(hasTransparency){
                        file.delete();
                    }
                }



            }
        }
    }

    public static void gatherResources() {
        JSONObject jsonObject = null;

        if(!hasResources()){
            Log.log("Started gathering resources (only need to be run once)");
            Log.log("Getting latest Minecraft version");
            jsonObject = Json.readJsonFromUrl("https://launchermeta.mojang.com/mc/game/version_manifest.json");
            String latest = jsonObject.getJSONObject("latest").getString("release");

            Log.log("Downloading Minecraft assets for "+latest);
            File zipFile = download("src/main/resources/mc.zip", "https://codeload.github.com/InventivetalentDev/minecraft-assets/legacy.zip/refs/heads/"+latest);

            Log.log("Unzipping Minecraft assets");
            Zip.unZip("src/main/resources/mc.zip", "src/main/resources/test/");
            FileClass.createFolder("src/main/resources/blocks/");

            Log.log("Cleaning up (1/2)");
            String tmpName = listFilesForFolder("src/main/resources/test/", false)[0].getName();
            FileClass.moveFolder("src/main/resources/test/"+tmpName+"/assets/minecraft/textures/block/", "src/main/resources/blocks/");
            FileClass.delete("src/main/resources/test/");
            FileClass.delete("src/main/resources/mc.zip");

            Log.log("Cleaning up (2/2)");
            cleanBlocks();
        }
        Log.log("Resources OK");
    }
}
