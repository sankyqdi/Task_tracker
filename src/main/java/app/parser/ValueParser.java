package app.parser;

import app.exception.DateError;
import app.exception.NumberError;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValueParser {

    private ValueParser(){}

    public static Long parsePositiveLong(String id) {

        try {

            if (id == null || id.isEmpty()) {

                throw new NumberError("ID не может быть пустым.");

            }

            long longId = Long.parseLong(id.trim());

            if (longId <= 0) {

                throw new NumberError("Id не может быть отрицательным или равен нулю");

            }

            return longId;

        } catch (NumberFormatException e){

            throw new NumberError("Присутствуют символы отличимые от цифр");

        }
    }

    public static LocalDate parsingDate(String date) {

        try {

            return LocalDate.parse(date);

        } catch (DateTimeParseException e) {

            throw new DateError("Неверный формат даты");

        }

    }

    public static boolean parsingByteBool(String input) {

        try {

            byte number = Byte.parseByte(input);
            return true;

        } catch (NumberFormatException e) {

            return false;

        }

    }
}