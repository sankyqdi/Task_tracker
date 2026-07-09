package app.exception;

import lombok.Getter;

@Getter
public enum ListErrors {

    SYSTEM_ERROR(101, "Code error. Cause -> {}"),
    ROOT_ERROR(102, "Root access is missing."),

    USER_INPUT_ERROR(201, "User input error"),
    USER_CANCELLED(202, "User cancelled"),
    DATE_NOT_FORMAT(202, "Data not format"),
    NUMBER_NOT_FORMAT(203, "Number not format. {}"),

    TASK_NOT_FOUND(301, "Task not Found. {}"),
    TASK_NOT_CREATED(302, "Task not created"),
    TASK_NOT_UPDATED(303, "Task not updated"),
    TASK_NOT_DELETED(304, "Task not deleted"),

    JSON_FILE_NOT_FOUND(401, "JSON file not found"),
    JSON_FILE_NOT_FIXED(402, "JSON file not fixed"),
    JSON_FILE_NOT_READ(403, "JSON file cannot be read. {}"),
    JSON_FILE_NOT_SAVE(404, "JSON file not saved"),
    JSON_COPY_FILE_NO_SAVE(405, "Error copy to JSON"),
    JSON_NOT_DELETE_LINE(406, "Don`t delete line from json file. Cause -> {}"),

    COMMAND_NOT_FOUND(501, "Command not found. {}"),
    COMMAND_WITHOUT_REQUIRED_ARGUMENTS(502, "Command without required arguments. Number of required arguments: {}"),

    FORMAT_OUTPUT_FAILED(601, "The message with details could not be displayed. Error -> {}");


    private final int code;
    private final String baseMessage;

    ListErrors(int code, String baseMassage) {

        this.code = code;
        this.baseMessage = baseMassage;

    }

    public String format(Object... args) {

        if (args.length == 0) {
            return baseMessage;
        }

        String result = baseMessage;
        for (Object arg : args) {
            result = result.replaceFirst("\\{\\}", arg != null ? arg.toString() : "null");
        }

        return result;

    }

}
