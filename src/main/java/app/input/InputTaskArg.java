package app.input;

import app.config.ConfigurationManager;
import app.constants.ConstantHandler;
import app.model.TaskTag;
import app.util.ConsoleHelper;

import java.nio.file.Path;
import java.util.*;

public class InputTaskArg {

    private final ConsoleInput input;
    private final static ConfigurationManager instance = ConfigurationManager.getInstance();

    public InputTaskArg(ConsoleInput input) {

        this.input = input;

    }

    public  Map<String, String> collectTaskDate() {

        Map<String, String> taskDate = new HashMap<>();

        taskDate.put("name", input.readLine("name: "));
        taskDate.put("body", input.readLine("body: "));
        taskDate.put("importanceLevel", input.readLineByte("importanceLevel: "));
        taskDate.put("dueDate", input.readLineDate("Completion date: "));

        return taskDate;

    }

    public Map<String, List<String>> collectTaskTags () {

        Map<String, List<String>> taskTag = new HashMap<>();
        taskTag.put("builtInTags", selectTag());
        taskTag.put("customTags", selectCustomTag());

        return taskTag;

    }

    public boolean confirmAction(String question) {

        return input.readYesOrNo(question);

    }

    public String readTaskStage() {

        return input.readLine();

    }

    public String readTaskDueDate() {

        return input.readLineDate();

    }

    public List<String> selectTag() {

        ConsoleHelper.println(ConstantHandler.getMessage(Path.of("resources/text/MESSAGE_ONE_CREATE.txt")));

        List<TaskTag> tags = new ArrayList<>(List.of(TaskTag.values()));
        Set<String> stringTags = new LinkedHashSet<>();

        int userInput;

        do {

            userInput = input.readLineInteger(-1, 15);

            if (userInput > 0) {

                stringTags.add(tags.get(userInput - 1).getFormattedTag());

            } else {

                instance.setEnableCustomTag(true);
                stringTags.add(tags.get(15).getFormattedTag());
                return new ArrayList<>(stringTags);

            }

        } while (userInput != 0);

        return new ArrayList<>(stringTags);

    }

    public List<String> selectCustomTag() {

        if (!instance.getEnableCustomTag()) {

            return null;

        }

        Set<String> customTag = new LinkedHashSet<>();

        while(true) {

            String inputUser = input.readLine("Введите кастомный тег для задачи. (Минимум 1 символ - максимум 35) \n "
                    +
                    "Или введите '/end', если добавили достаточно кастомных тегов", 35);

            if (inputUser.equals("/end")) {

                return new ArrayList<>(customTag);

            }

            customTag.add(inputUser);


        }

    }
}
