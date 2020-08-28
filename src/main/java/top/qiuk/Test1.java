package top.qiuk;

import top.qiuk.constant.StringType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test1 {



    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<StringType> type = StringType.class;

        String object = "STRING";

        Method method = type.getMethod("valueOf",String.class);
        Object invoke = method.invoke(null, object);

        StringType.valueOf("123");
        System.out.println(invoke.toString());

    }
}
