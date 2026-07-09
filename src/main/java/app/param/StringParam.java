package app.param;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class  StringParam {

    public static Map<Integer, String> getParams(Method method, int sizeParams) {

        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < sizeParams; i++) {

            String param = method.getParameters()[i].getAnnotation(Param.class).value();
            map.put(i, param);

        }

        return map;
    }




}
