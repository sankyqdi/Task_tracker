package app.prefix;

import app.format.UpdateFormat;
import app.input.InputTaskArg;
import app.parser.ValueParser;
import app.service.Console;
import java.io.FileNotFoundException;
import app.exception.TaskNotDeleted;


public class BaseMethod {

    private BaseMethod() {}

    public static String updateTask(Console console, String prefix, String id, InputTaskArg input) {

        long longId = ValueParser.parsePositiveLong(id);

        switch (prefix) {

            case "-s" -> {

                if (input.confirmAction(STR."""
Задача \{console.consoleShowNameById(longId)} имеет на данный момент стадию: \{console.consoleShowStageById(longId)} Вы уверены, что хотите изменить?\s
 да/нет(yes/no)""")) {

                    System.out.println("Укажите новую стадию -> ");
                    String stage = input.readTaskStage();

                    console.consoleSetStage(longId, stage);
                    System.out.println("✅ Стадия выполнения задачи успешно обновлена!");


                } else {

                    return "Отказано. Данные не были обновлены";

                }

                return UpdateFormat.taskFormat(console.getTaskById(longId));

            }

            case "-dt" -> {

                if (input.confirmAction(STR."""
Задача \{console.consoleShowNameById(longId)} имеет на данный момент срок выполнения: \{console.consoleShowDueDateById(longId)} Вы уверены, что сократить или увеличить срок выполнения?\s
 да/нет(yes/no)""")) {

                    System.out.println("Укажите новый срок выполнения задачи -> ");
                    String dueDate = input.readTaskDueDate();

                    console.consoleSetDueDate(longId, dueDate);
                    System.out.println("✅ Срок выполнения задачи успешно обновлена!");

                    return UpdateFormat.taskFormat(console.getTaskById(longId));

                } else {

                    return "Отказано. Данные не были обновлены";

                }

            }

        }

        return "Error prefix";

    }

    public static String deleteAllTasks (Console console, String prefix, InputTaskArg input) {

        final String CANCELLED_MESSAGE = "❌ Операция отменена. Данные не были удалены.";
        final String NULL_STORAGE_MESSAGE =  "ℹ️ Хранилище пусто. Данные не удалены";
        final String SUCCESS_MESSAGE = "✅ Все прошло успешно! Хранилище приложения полностью пустое";


        switch (prefix) {

            case "-a" -> {

                if(console.consoleGetSizeTasks() == 0) {

                    return NULL_STORAGE_MESSAGE;

                }

                String message = "Вы уверены, что хотите удалить все задачи из временного хранилища?\n"
                        +
                        "❌ Несохраненные данные невозможно будет восстановить\n"
                        +
                        "да/нет ";

                if (!input.confirmAction(message)) {

                    return CANCELLED_MESSAGE;

                }

                String deleteString = console.consoleDeleteAll();
                System.out.println(SUCCESS_MESSAGE);
                return deleteString;

            }

            case "-aR" -> {

                if(console.checkIsEmpty()) {

                    return NULL_STORAGE_MESSAGE;

                }

                String message = """
                        ПРЕДУПРЕЖДЕНИЕ! Данная команда удалит все когда-либо записанные задачи в памяти.\
                        Прежде чем соглашаться, еще раз подумайте!
                        
                        
                        \
                        Вы уверены, что хотите удалить все задачи из внутреннего хранилища?
                        ❌ Удаленные данные невозможно будет восстановить
                        да/нет\s""";

                if (!input.confirmAction(message)) {

                    return CANCELLED_MESSAGE;

                }

                String outPutLine = console.consoleDeleteAll();
                console.getJsonManager().turningEmptyFile();
                System.out.println(SUCCESS_MESSAGE + " Начните все с чистого листа!");
                return outPutLine;

            }
        }

        return "Error prefix";

    }


    public static String deleteTaskFromId(Console console, String prefix, Long id, InputTaskArg input) throws FileNotFoundException {

        final String CANCELLED_MESSAGE = "❌ Операция отменена. Данные не были удалены.";
        final String NULL_STORAGE_MESSAGE =  "ℹ️ В хранилище нет такой задачи. Данные не удалены";

        switch (prefix) {

            case "-i" -> {

                if (console.consoleGetSizeTasks() == 0) {

                    return NULL_STORAGE_MESSAGE;

                }

                String message = "Вы уверены, что хотите удалить эту задачу из временного хранилища?\n"
                        +
                        "❌ Несохраненные данные невозможно будет восстановить\n"
                        +
                        "да/нет ";

                if(!input.confirmAction(message)) {

                    return CANCELLED_MESSAGE;

                }

                try {

                    String deleteString = console.consoleDeleteById(id);
                    System.out.println("✅ Все прошло успешно! Задача с id: " + id + " полностью удалена из хранилища. ");
                    return deleteString;

                } catch (Exception e) {

                    throw new TaskNotDeleted(e);

                }

            }

            case "-iR" -> {

                if(console.checkIsEmpty() || !console.checkingAvailability(id)) {

                    return NULL_STORAGE_MESSAGE;

                }

                String message = """
                ПРЕДУПРЕЖДЕНИЕ! Данная команда удалит все данные задачи когда-либо записанные  в памяти.
                Прежде чем соглашаться, еще раз подумайте!
                Вы уверены, что хотите удалить все данные этой задачи из внутреннего хранилища?
                ❌ Удаленные данные невозможно будет восстановить!
                да/нет
                """;

                if (!input.confirmAction(message)) {

                    return CANCELLED_MESSAGE;

                }

                String outPutLine = console.consoleDeleteById(id);
                console.deleteLineJson(id);
                System.out.println("✅ Все прошло успешно! Задача с id: " + id + " полностью удалена из всех хранилищ. ");

                return outPutLine;

            }
        }

        return "Error delete task";

    }
}
