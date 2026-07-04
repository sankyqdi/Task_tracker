package app;

import app.input.ConsoleInput;
import app.input.InputTaskArg;
import app.parser.CommandParser;
import app.screen.ConsoleWin;
import app.service.Console;
import app.util.LogUtil;
import org.slf4j.Logger;

public class Main {

    private static final Logger log = LogUtil.getLogger(Main.class);

    public static void main(String[] args) {

        Console console = new Console();

        ConsoleInput consoleInput = ConsoleInput.getInstance();

        InputTaskArg inputTaskArg = new InputTaskArg(consoleInput);

        CommandParser commandParser = new CommandParser(console, inputTaskArg);

        LogUtil.logAppStart();
        log.info("Task Tracker started");
        try {

            ConsoleWin.startWindow(consoleInput, commandParser, console);

        } catch (Exception e) {

            log.error("Critical error in application {}", String.valueOf(e));

        } finally {

            LogUtil.logAppStop();
            log.info("Task Tracker stopped");

        }

    }
}