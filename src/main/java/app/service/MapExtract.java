package app.service;

import app.param.StringParam;

import java.lang.reflect.Method;
import java.util.Map;

public class MapExtract {

    public static void extract(Map<String, String> map) throws NoSuchMethodException {

        Method method = Console.class.getMethod("consoleCreateTask", String.class, String.class, byte.class, String.class);

        var paramMap = StringParam.getParams(method, 4);

    }

}
