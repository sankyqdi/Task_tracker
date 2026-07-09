package app.input;

import app.parser.ValueParser;
import app.util.ConsoleHelper;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleInput {

    private static final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private static ConsoleInput instance;

    private ConsoleInput(){}

    public static ConsoleInput getInstance() {

        if (instance == null) {

            instance = new ConsoleInput();

        }

        return instance;

    }

    public String readLine() {



        while(true) {

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                continue;

            }

            return input;

        }
    }

    public String readLine(String message) {

        System.out.println(message);

        while(true) {


            String input = scanner.nextLine().trim();

            if(input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым");
                continue;

            }

            return input;

        }

    }

    public String readLine(String message, int limitSymbol) {

        System.out.println(message);

        while(true) {


            String input = scanner.nextLine().trim();

            if (input.length() > limitSymbol && input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым и не должен превышать лимит в " + limitSymbol + " символов");
                continue;

            }

            return input;
        }

    }

    public Integer readLineInteger() {

        while (true) {


            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым");
                continue;

            }

            return ValueParser.parsingInt(input);

        }

    }

    public Integer readLineInteger(int min, int max) {
        while (true) {


            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым");
                continue;

            }

            int integerInput = ValueParser.parsingInt(input);

            if (integerInput < min || integerInput > max) {

                ConsoleHelper.println("Число не входит в заданный промежуток. min - " + min + " max - " + max);
                continue;

            }

            return integerInput;

        }
    }

    public Long readLineLong(String message) {

        System.out.println(message);

        while (true) {

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым");
                continue;

            }

            return ValueParser.parsePositiveLong(input);


        }

    }

    public String readLineByte(String message) {

        System.out.println(message);

        while (true) {

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым");
                continue;

            }

            if(!ValueParser.parsingByteBool(input)) {

                System.out.println("Неверный формат записи");
                continue;

            }

            return input;

        }

    }

    public String readLineDate(String message) {

        System.out.print(message);

        while (true) {

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым");
                continue;

            }

            if(!input.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")) {

                System.out.println("Неверный формат даты");
                continue;

            }

            return input;


        }
    }

    public String readLineDate() {

        while (true) {

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым");
                continue;

            }

            if(!input.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")) {

                System.out.println("Неверный формат даты");
                continue;

            }

            return input;


        }

    }


    public boolean readYesOrNo(String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Ввод не должен быть пустым");
                continue;

            }

            switch (input.toLowerCase()) {

                case "yes", "да" -> {

                    return true;

                }

                case "no", "нет" -> {

                    return false;

                }
            }
        }
    }

    public void close() {

        scanner.close();

    }

}
