package imms.utils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class Utils {
    public static LocalTime toTime(String time){
        String[] hms = time.split(":|：");
        if(hms.length < 2){
            System.out.println("输入出错！");
            return null;
        }else if(hms.length > 3){
            System.out.println("输入出错！");
            return null;
        }else{
            int hour = Integer.parseInt(hms[0]);
            int min = Integer.parseInt(hms[1]);
            if(hour<0 || hour>24 || min<0 || min > 60){
                System.out.println("输入出错！");
                return null;
            }
            LocalTime rightTime = LocalTime.of(hour,min);
            return rightTime;
        }
    }

    public static String dateCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("hhhmmssSSSS");
        return sdf.format(System.currentTimeMillis());
    }
}
