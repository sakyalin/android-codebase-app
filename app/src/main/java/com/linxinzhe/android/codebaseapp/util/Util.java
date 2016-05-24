package com.linxinzhe.android.codebaseapp.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.linxinzhe.android.codebaseapp.R;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okio.BufferedSink;
import okio.Okio;

/**
 * @author linxinzhe on 5/24/16.
 */
public class Util {

    public static void printMapData(String filename, Map<String, String> data) {
        File appExternalFile = Environment.getExternalStorageDirectory();
        File userInfoFile = new File(appExternalFile, filename + ".txt");

        BufferedSink bs = null;
        try {
            bs = Okio.buffer(Okio.sink(userInfoFile));
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                builder.append("key=");
                builder.append(entry.getKey());
                builder.append(" ");
                builder.append("value=");
                builder.append(entry.getValue());
                builder.append("\n");
            }
            String txt = builder.toString();
            bs.writeUtf8(txt);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bs != null) {
                try {
                    bs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * format "time for 2 days 7 hours and 53 minutes before now"
     */
    public static String formatTimeLeft(Context context, long timeInMillis) {
        long delta = System.currentTimeMillis() - timeInMillis;
        long hours = delta / (1000 * 60 * 60);
        long minutes = delta / (1000 * 60) % 60;
        long days = hours / 24;
        hours = hours % 24;

        String daySeq = (days == 0) ? "" :
                (days == 1) ? context.getString(R.string.day) :
                        context.getString(R.string.days, Long.toString(days));

        String minSeq = (minutes == 0) ? "" :
                (minutes == 1) ? context.getString(R.string.minute) :
                        context.getString(R.string.minutes, Long.toString(minutes));

        String hourSeq = (hours == 0) ? "" :
                (hours == 1) ? context.getString(R.string.hour) :
                        context.getString(R.string.hours, Long.toString(hours));

        boolean dispDays = days > 0;
        boolean dispHour = hours > 0;
        boolean dispMinute = minutes > 0;

        int index = (dispDays ? 1 : 0) |
                (dispHour ? 2 : 0) |
                (dispMinute ? 4 : 0);

        String[] formats = context.getResources().getStringArray(R.array.alarm_set);
        return String.format(formats[index], daySeq, hourSeq, minSeq);
    }

    public static void popAlarmSetToast(Context context, long timeInMillis) {
        String toastText = formatTimeLeft(context, timeInMillis);
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        toast.show();
    }
}
