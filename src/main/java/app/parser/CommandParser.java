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

    public String parser(String command) {
        String outPutLine = "";

        Pattern pattern = Pattern.compile("^/(add|show|update|delete|help|com|exit)");
        Matcher matcher = pattern.matcher(command.trim());

        if (!matcher.find()) {

            throw new CommandNotFound("❌ Неизвестная команда. Используйте /com для справки.");

        }

        String[] string = command.split(" ");

        switch (string[0]) {

            case "/add" -> {

                var taskDate = inputHandler.collectTaskDate();
                outPutLine = console.consoleCreateTask(taskDate.get("name"), taskDate.get("body"), Byte.parseByte(taskDate.get("importanceLevel")), taskDate.get("stage"), ValueParser.parseDateOrNull(taskDate.get("dueDate")));
                return outPutLine;

            }

            case "/show" -> {

                if (console.getSizeTasks() == 0) {

                    return "⚠️  Нет задач для отображения. Создайте новую задачу с помощью /add\n";

                } else if (string.length == 1) {

                    return console.consoleShow();

                }

                switch (string[1]) {

                    case "-a" -> {
                        return console.consoleShow(Integer.MAX_VALUE);
                    }

                    case "-i" -> {

                        if (string.length != 3) {

                            throw new CommandNotArgument(string.length);

                        }

                        try {

                            Long id = ValueParser.parsePositiveLong(string[2]);

                            return console.consoleShowById(id);

                        } catch (NumberFormatException e) {

                            throw new NumberError("❌ Неверный формат ID. Используйте только цифры (0-9)", e);

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

                    case "-l" -> {
                        return console.consoleShowImportanceTask();
                    }

                    case "-li" -> {

                        if (string.length != 3) {

                            throw new CommandNotArgument(string.length);

                        }

                        byte level = ValueParser.parsingByte(string[2]);
                        outPutLine = console.consoleShowImportanceTask(String.valueOf(level));
                        return outPutLine;

                    }

                    default -> {
                        return "❌ Неизвестный префикс: " + string[1] + "\n📋 Используйте: /show -a, -i, -in, -l, -li\n";
                    }

                }
            }

            case "/update" -> {

                if (string.length < 2 || string[1].isEmpty()) {

                    throw new TaskNotUpdated(new IncorrectDataEntry("❌ Укажите префикс и ID. Формат: /update <-префикс> <id>"));

                }

                if (string.length == 2) {
                    return "❌ Укажите ID задачи. Формат: /update " + string[1] + " <id>";
                }

                outPutLine = BaseMethod.updateTask(console, string[1], string[2], inputHandler);

                return outPutLine;
            }

            case "/delete" -> {

                if (string.length > 3) {

                    throw new CommandNotArgument(string.length);

                }

                if (string.length == 1) {
                    return "❌ Укажите префикс. Форматы: /delete -a, -aR, -i <id>, -iR <id>";
                }

                if (string.length == 2) {

                    if (string[1].isEmpty()) {

                        throw new TaskNotDeleted(new IncorrectDataEntry("❌ Укажите префикс удаления"));

                    }

                    return BaseMethod.deleteAllTasks(console, string[1], inputHandler);

                } else if (string.length > 2) {

                    if (string[1].isEmpty() || string[2].isEmpty()) {

                        throw new TaskNotDeleted(new IncorrectDataEntry("❌ Укажите ID задачи"));

                    }

                    try {

                        return BaseMethod.deleteTaskFromId(console, string[1], ValueParser.parsePositiveLong(string[2]), inputHandler);

                    } catch (NumberFormatException e) {

                        throw new TaskNotDeleted(new NumberError("❌ Неверный формат ID. Используйте только цифры (0-9)"));

                    } catch (FileNotFoundException e) {

                        throw new RuntimeException(e);

                    }
                }
            }

            case "/help" -> {
                return "📖 СПРАВКА ПО КОМАНДАМ\n\n"
                        + "Основные команды:\n"
                        + "  /add                    - Создать новую задачу\n"
                        + "  /show [ПРЕФИКС] [ID]   - Показать задачи\n"
                        + "  /update <ПРЕФИКС> <ID> - Обновить задачу\n"
                        + "  /delete <ПРЕФИКС> [ID] - Удалить задачу(и)\n"
                        + "  /help                   - Показать эту справку\n"
                        + "  /com                    - Полная документация\n"
                        + "  /exit                   - Выход\n\n"
                        + "Для полной документации введите: /com\n";
            }

            case "/com" -> {
                return ConstantHandler.getBaseCommand();
            }

            case "/exit" -> {
                return "До свидания! 👋\n";
            }

        }

        return "";

    }
}