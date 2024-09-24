package Utilities;

import org.apache.logging.log4j.LogManager;

public class LogsUtils {

    public static String logsPath = "Test-outputs/Logs";

    public static void getTrace(String message) {
        LogManager.getLogger(Thread.currentThread()
                .getStackTrace()[2].toString()).trace(message);
    }


    public static void getDebug(String message) {
        LogManager.getLogger(Thread.currentThread()
                .getStackTrace()[2].toString()).debug(message);
    }

    public static void getInfo(String message) {
        LogManager.getLogger(Thread.currentThread()
                .getStackTrace()[2].toString()).info(message);
    }

    public static void getWarning(String message) {
        LogManager.getLogger(Thread.currentThread()
                .getStackTrace()[2].toString()).warn(message);
    }


    public static void getError(String message) {
        LogManager.getLogger(Thread.currentThread()
                .getStackTrace()[2].toString()).error(message);
    }

    public static void getFatal(String message) {
        LogManager.getLogger(Thread.currentThread()
                .getStackTrace()[2].toString()).fatal(message);
    }

}

