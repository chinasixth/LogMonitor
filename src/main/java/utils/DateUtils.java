package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ Author ：liuhao
 * @ Date   ：Created in 15:08 2018/9/18
 * @ 日期转换工具类
 */
public class DateUtils {
    private static SimpleDateFormat sdf;

    public static String getDateTime() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
