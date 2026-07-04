package app.parser;

import app.constants.ConstantHandler;
import app.exception.*;
import app.input.InputTaskArg;
import app.prefix.BaseMethod;
import app.service.Console;

import java.io.FileNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    private final Console console;
    private final InputTaskArg inputHandler;

    public CommandParser(Console console, InputTaskArg inputHandler) {

        this.console = console;
        this.inputHandler = inputHandler;

    }

    public  String parser(String command)  {
        String outPutLine = "";

        Pattern pattern = Pattern.compile("^/(add|show|update|delete|com|exit)");
        Matcher matcher = pattern.matcher(command.trim());

        if (!matcher.find()) {

            throw new CommandNotFound("Invalid command");

        }

        String[] string = command.split(" ");

        switch (string[0]) {

            case "/add" -> {

                var taskDate = inputHandler.collectTaskDate();
                outPutLine = console.consoleAdd(taskDate.get("name"), taskDate.get("body"), taskDate.get("importanceLevel"), taskDate.get("dueDate"));
                return outPutLine;

            }

            case "/show" -> {

                if (console.getSizeTasks() == 0) {

                    return "There are no tasks yet";

                } else if (string.length == 1) {

                    return  console.consoleShow();

                }


                switch (string[1]) {

                    case "-a" -> console.consoleShow(Integer.MAX_VALUE);

                    case "-i" -> {

                        if (string.length != 3) {

                            throw new CommandNotArgument(string.length);

                        }

                        try {

                            Long id = ValueParser.parsePositiveLong(string[2]);

                            return console.consoleShowById(id);

                        } catch (NumberFormatException e) {

                            throw new NumberError("Invalid number format", e);

                        }

                    }

                    case "-in" -> {

                        if (string.length < 3) {

                            throw new CommandNotArgument(string.length);

                        }

                        StringBuilder partNameTask = new StringBuilder();

                        int count = 0;

                        for (var partName : string) {

                            if (count < 2) {

                                count++;
                                continue;

                            }

                            partNameTask.append(partName);
                            partNameTask.append(" ");

                        }

                        outPutLine = console.consoleShowByName(partNameTask.toString().trim());
                        return outPutLine;

                    }

                    case "-l" ->  console.consoleShowImportanceTask();


                    case "-li" -> {

                        if (string.length != 3) {

                            throw new CommandNotArgument(string.length);

                        }

                        outPutLine = console.consoleShowImportanceTask(string[2]);
                        return outPutLine;


                    }

                    default ->  console.consoleShow(Integer.MAX_VALUE);

                }
            }

            case  "/update" -> {

                if (string.length != 3 || string[1].isEmpty() || string[2].isEmpty()) {

                    throw new TaskNotUpdated(new IncorrectDataEntry("Invalid command input. Insufficient data to proceed"));

                }

                outPutLine = BaseMethod.updateTask(console, string[1], string[2], inputHandler);

                return outPutLine;
            }

            case "/delete" -> {

                if (string.length > 3) {

                    throw new CommandNotArgument(string.length);

                }

                if (string.length == 2) {

                    if (string[1].isEmpty()) {

                        throw new TaskNotDeleted(new IncorrectDataEntry("Invalid command input. Insufficient data to proceed"));

                    }

                    return BaseMethod.deleteAllTasks(console, string[1], inputHandler);

                } else if (string.length > 2) {

                    if (string[1].isEmpty() || string[2].isEmpty()) {

                        throw new TaskNotDeleted(new IncorrectDataEntry("Invalid command input. Insufficient data to proceed"));

                    }

                    try {

                        return BaseMethod.deleteTaskFromId(console, string[1], ValueParser.parsePositiveLong(string[2]), inputHandler);

                    } catch (NumberFormatException e) {

                        throw new TaskNotDeleted(new NumberError("Invalid ID format. Use only numbers [0-9]"));

                    } catch (FileNotFoundException e) {

                        throw new RuntimeException(e);

                    }
                }
            }

            case "/com" -> ConstantHandler.getStartMenu();

        }

        return "";

    }
}