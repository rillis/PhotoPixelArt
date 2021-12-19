package pixelart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static void log(String msg){
        System.out.println("["+getCurrentTimeStamp()+"] "+msg);
    }
}
