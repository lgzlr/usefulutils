package utils.transfer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanTransfer {

    /**
     * 覆盖默认构造函数,防止通过构造函数得到当前工具类对象
     */
    private BeanTransfer() {}

    public static  <T, K> Object TransferToGivenBean (K k, Class clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        //反射得到原始传入对象的Class对象
        if (k == null) {
            return clazz.newInstance();
        }
        Class sourceClass = k.getClass();
        Field[] fields = sourceClass.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        if (methods != null) {
            for (Method method : methods) {
                if (method.getName().equals("getName")) {
                    //Type[] types = method.getGenericParameterTypes();
                    Object obj = method.invoke(k);
                    System.out.println(obj);
                }
            }
        }

        return null;

    }


}
