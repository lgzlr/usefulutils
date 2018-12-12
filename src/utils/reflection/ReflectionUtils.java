package utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class ReflectionUtils {

    /**
     * 覆盖默认构造函数,防止通过构造函数得到当前工具类对象
     */
    private ReflectionUtils() {}

    /**
     * 校验某一种对象中是否存在名称为{@param column}的对象属性
     * @param clazz 需要校验的Class对象
     * @param column 需要校验的列名
     * @throws Exception 还未封装的原始异常类
     */
    public static void validateHasProperty(Class clazz, String column) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        if (column == null || "".equals(column.trim())) {
            throw new Exception("传入的列名不能为空");
        }
        for (Field field : fields) {
            if (field.getName().equals(column)) {
                return;
            }
        }
        throw new Exception("对象中不存在名称为"+column+"的列");
    }

    /**
     * 校验某一种对象中是否存在名称为{@param column}的对象属性
     * @param t 需要校验的对象
     * @param column 需要校验的列名
     * @throws Exception 还未封装的原始异常类
     */
    public static <T> void validateHasProperty(T t, String column) throws Exception {
        validateHasProperty(t.getClass(), column);
    }

    /**
     * 得到传入对象的对应属性名称的值
     * @param t 需要取值的对象
     * @param propertyName 需要取的属性名称
     * @return 对应属性的值
     * @throws Exception
     */
    public static <T> Object getBeanPropertyValue(T t, String propertyName) throws Exception {
        validateHasProperty(t, propertyName);
        Field field = t.getClass().getDeclaredField(propertyName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return field.get(t);
    }

    /**
     * 获得对象某一字段对应的get方法
     * 取名需要遵循get+column的标准形式，如果方法名不标准，可能找不到对应的方法
     * @param clazz 需要处理的对象的Class对象
     * @param column 对象的某一字段
     * @return 字段名的get方法
     */
    public static Method getPropertyGetMethod(Class clazz, String column) {
        Method[] methods = clazz.getDeclaredMethods();
        if(methods == null){
            return null;
        }
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("get")) {
                    if (name.substring(1,3).toLowerCase().equals(column)) {
                        return method;
                    }
            }
        }
        return null;
    }

    /**
     * 获得对象某一字段对应的get方法
     * 取名需要遵循get+column的标准形式，如果方法名不标准，可能找不到对应的方法
     * @param t 需要处理的对象
     * @param column 对象的某一字段
     * @return 字段名的get方法
     */
    public static <T> Method getPropertyGetMethod(T t, String column){
        return getPropertyGetMethod(t.getClass(), column);
    }

    /**
     * 校验对象中时候存在某一属性的get方法
     * @param clazz 需要处理的对象的Class对象
     * @param column 对象的某一字段
     * @throws Exception
     */
    public static void validateHasGetMethod(Class clazz, String column) throws Exception {
        Method method = getPropertyGetMethod(clazz, column);
        if(method == null){
            throw new Exception(clazz.getName()+"对象中不存在"+column+"的get方法");
        }
    }

    /**
     * 校验对象中时候存在某一属性的get方法
     * @param t 需要处理的对象
     * @param column 对象的某一字段
     * @throws Exception
     */
    public static <T> void validateHasGetMethod(T t, String column) throws Exception {
        Method method = getPropertyGetMethod(t, column);
        if(method == null){
            throw new Exception(t.getClass().getName()+"对象中不存在"+column+"的get方法");
        }
    }

    /**
     * 设置给定对象的对应属性的值
     * @param t 需要设置属性的对象
     * @param property 需要设置的属性名称
     * @param value 需要设置的属性值
     * @return 原始传入的对象
     * @throws Exception
     */
    public static <T> T setBeanProperty(T t, String property, Object value) throws Exception {
        validateHasProperty(t, property);
        Class clazz = t.getClass();
        Field field = clazz.getDeclaredField(property);
        if (!field.getType().equals(value.getClass())) {
            throw new Exception("传入数据的类型与对象"+t.getClass()+"属性"+property+"需要的数据类型不一致");
        }
        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
        }
        field.set(t, value);
        return t;
    }

    public static <T> T getNewInstance(T t) throws IllegalAccessException, InstantiationException {
        return (T)t.getClass().newInstance();
    }



}
