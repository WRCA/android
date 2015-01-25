package info.jiangchuan.wrca.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class TimeUtil {

    public static String time () {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        return sdf.format(new Date());
    }
}
