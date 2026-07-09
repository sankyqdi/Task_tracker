package app.constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConstantHandler {

    public static String getStartMenu() {

        try {

            return Files.readString(DataPathTextFile.START_MENU, StandardCharsets.UTF_8);

        } catch (IOException e) {

            System.out.println("Something went wrong: \n" + e.getMessage());

        }
        return "";
    }

    public static String getBaseCommand() {

        try {

            return Files.readString(DataPathTextFile.BASE_COMAND, StandardCharsets.UTF_8);

        } catch (IOException e) {

            System.out.println("Something went wrong: \n" + e.getMessage());

        }

        return "";

    }

    public static String getMessage(Path path) {

        try {

            return Files.readString(path);

        } catch (IOException e) {

            System.out.println("Something went wrong: \n" + e.getMessage());

        }

        return "";

    }


}
