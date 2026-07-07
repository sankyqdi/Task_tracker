package app.screen;

import app.constants.ConstantHandler;
import app.constants.DataPathTextFile;
import app.exception.FundamentError;
import app.exception.GlobalExceptionHandler;
import app.input.ConsoleInput;
import app.parser.CommandParser;
import app.service.Console;
import app.util.LogUtil;
import org.slf4j.Logger;

public class ConsoleWin {

    private static final Logger log = LogUtil.getLogger(ConsoleWin.class);
    private static final GlobalExceptionHandler globalExc = GlobalExceptionHandler.getInstance();

    public static void startWindow(ConsoleInput consoleInput, CommandParser commandParser, Console console) {

        log.info("Display turned on");

        ImageToAscii.printFromFile(DataPathTextFile.PATH_FOR_IMAGE, 185);

        System.out.println(ConstantHandler.getStartMenu());

        String input = consoleInput.readLine();
        log.debug("User input {}", input);

        switch (input) {

            case "/start" -> {

                log.info("Start work app");

                String inputCommand = "";

                System.out.println(ConstantHandler.getBaseCommand());
                try {

                    console.consoleStartApp();

                } catch (RuntimeException e) {

                    System.out.println(globalExc.handleException(e));

                }


                do {

                    inputCommand = consoleInput.readLine();

                    try {

                        System.out.println(commandParser.parser(inputCommand));

                    } catch (FundamentError e) {

                        System.out.println(globalExc.handleException(e));

                    }

                } while(!inputCommand.trim().equals("/exit"));

                console.consoleEndApp();
                System.exit(0);

            }
            case "/exit" -> {
                System.exit(0);
            }

            default -> {
                System.out.println("Неправильная команда. Попытайтесь снова");
            }
        }
    }
}