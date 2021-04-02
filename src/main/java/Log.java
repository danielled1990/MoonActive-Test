import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static Log log = null;
    private final static SimpleDateFormat timeAndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
    private static File file = null;
    private static String lp;

    private Log() {
    }

    public static Log getInstance() {

        if (log == null) {
            file = new File("log.txt");
            file.setWritable(true);
            log = new Log();
        }

        return log;
    }

    public static void WriteToLogFile(String lp, String text) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(String.format("%s:%s - %s\n", timeAndDate.format(timestamp), lp, text));
            fileWriter.close();
            //   bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteToLogFile(String text) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(String.format("%s: %s\n", timeAndDate.format(timestamp), text));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Timestamp getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    public static Date getCurrentDate() {
        Date date = new Date();
        currentDate.format(date);
        return date;
    }


    public static String getlp() {
        return lp;
    }

    public static void setlp(String lp1) {
        lp = lp1;
    }
}
