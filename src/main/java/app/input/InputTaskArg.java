package app.input;

import java.util.HashMap;
import java.util.Map;

public class InputTaskArg {

    private final ConsoleInput input;

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

    public boolean confirmAction(String question) {

        return input.readYesOrNo(question);

    }

    public String readTaskStage() {

        return input.readLine();

    }

    public String readTaskDueDate() {

        return input.readLineDate();

    }

}
