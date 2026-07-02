package app.exception;

import java.io.File;
import java.nio.file.Path;

public class JsonError extends FundamentError{

    //401
    public JsonError(Path path, Throwable cause) {

        super(ListErrors.JSON_FILE_NOT_FOUND, "Файл по пути -> " + path.toString() + "не найден", cause);

    }

    //402
    public JsonError(File file, Throwable cause) {

        super(ListErrors.JSON_FILE_NOT_FIXED, "Файл по пути -> " + file.getPath() + "не исправлен", cause);

    }

    //403
    public JsonError(String message) {

        super(ListErrors.JSON_FILE_NOT_READ, message);

    }

    //404
    public JsonError(String message, Throwable cause) {

        super(ListErrors.JSON_FILE_NOT_SAVE, message, cause);

    }

    //405
    public JsonError(String message, Path pathCopy, Throwable cause) {

        super(ListErrors.JSON_COPY_FILE_NO_SAVE, message + "Путь до временного файла -> " + pathCopy.toString(), cause);

    }

    //405
    public JsonError(String message, Path pathCopy, Throwable cause, Throwable twoCause) {

        super(ListErrors.JSON_COPY_FILE_NO_SAVE,  String.format("Ошибка записи в json файл '%s'", cause.getMessage()) + message
                +
                "Путь до временного файла -> " + pathCopy.toString(), twoCause);

    }
}
