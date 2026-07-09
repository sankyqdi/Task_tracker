package app.util;

import lombok.Getter;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ConsoleHelper {

    @Getter
    private static final PrintStream out;
    @Getter
    private static final PrintStream err;

    static {
        try {
            out = new PrintStream(System.out, true, StandardCharsets.UTF_8.name());
            err = new PrintStream(System.err, true, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to set UTF-8 encoding for console", e);
        }
    }

    public static void print(String message) {
        out.print(message);
    }

    public static void println(String message) {
        out.println(message);
    }

    public static void println() {
        out.println();
    }

    public static void printf(String format, Object... args) {
        out.printf(format, args);
    }

    public static void printError(String message) {
        err.println(message);
    }

}