package app.parser;

import app.exception.DateError;
import app.exception.NumberError;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValueParser {

    private ValueParser(){}

    public static Long parsingToString(String id) {

        try {

            return Long.parseLong(id);

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

}
