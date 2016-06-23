package me.walkonly.lib.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;

public class MyLog {
    private static boolean isDebug = true;

    // log2file config
    private static boolean V_log2file = false;
    private static boolean D_log2file = false;
    private static boolean I_log2file = false;
    private static boolean W_log2file = true;
    private static boolean E_log2file = true;

    public static void v(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.v(tag, msg);
        if (V_log2file)
            log2file("V/" + tag, msg, null);
    }

    public static void v(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.v(tag, msg);
        if (V_log2file)
            log2file("V/" + tag, msg, null);
    }

    public static void d(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.d(tag, msg);
        if (D_log2file)
            log2file("D/" + tag, msg, null);
    }

    public static void d(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.d(tag, msg);
        if (D_log2file)
            log2file("D/" + tag, msg, null);
    }

    public static void i(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.i(tag, msg);
        if (I_log2file)
            log2file("I/" + tag, msg, null);
    }

    public static void i(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.i(tag, msg);
        if (I_log2file)
            log2file("I/" + tag, msg, null);
    }

    public static void w(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.w(tag, msg);
        if (W_log2file)
            log2file("W/" + tag, msg, null);
    }

    public static void w(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.w(tag, msg);
        if (W_log2file)
            log2file("W/" + tag, msg, null);
    }

    public static void e(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.e(tag, msg);
        if (E_log2file)
            log2file("E/" + tag, msg, null);
    }

    public static void e(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.e(tag, msg);
        if (E_log2file)
            log2file("E/" + tag, msg, null);
    }

    // 打印字符串的16进制形式
    public static void printHexString(String tag, String str) {
        //MyLog.e(tag, "=== [0x] " + StringUtils.toHexString(str) + " ===");
    }

    /**
     * 打印 Throwable 的堆栈信息。这个不能替换为 MyLog.printStackTrace()，避免递归调用。
     */
    public static void printStackTrace(Throwable t) {
        if (!isDebug) return;

        t.printStackTrace();
        if (E_log2file) {
            log2file("Exception/", getThrowableMsg(t), null);
        }
    }

    // 获取 Throwable 的堆栈信息
    public static String getThrowableMsg(Throwable t) {
        if (t == null)
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append(t.toString());
        sb.append("\n");

        StackTraceElement[] stack = t.getStackTrace();
        for (StackTraceElement a : stack) {
            sb.append("\t\t\t");
            sb.append(a.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    // 把log写入文件
    private static synchronized void log2file(String tag, String msg, Throwable t) {
        //String filename = "/sdcard/" + Deeper.getContext().getPackageName() + "/log_20150101.txt";
        String dir = "/sdcard/mylog";
        String filename = "log_20150101.txt";

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(dir + "/" + filename, "rw");
            raf.seek(raf.length());

            raf.write(tag.getBytes());
            raf.write(": ".getBytes());
            raf.write(msg.getBytes());
            raf.write("\n".getBytes());

            if (t != null) {
                raf.write(getThrowableMsg(t).getBytes());
                raf.write("\n".getBytes());
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
