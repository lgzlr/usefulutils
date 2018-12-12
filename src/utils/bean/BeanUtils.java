package utils.bean;

import org.dom4j.Document;
import utils.reflection.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanUtils {

    /**
     * 覆盖默认构造函数,防止通过构造函数得到当前工具类对象
     */
    private BeanUtils() {};

    /**
     * 校验给定的对象的对应属性是否为空
     * @param t 需要校验的对象
     * @param property 需要校验的属性
     * @throws Exception
     */
    public static <T> void validateGivenPropertyIsNull(T t, String property) throws Exception {
        //调用反射工具类进行数据校验
        ReflectionUtils.validateHasProperty(t, property);
        //获得对应属性的get方法
        Object obj = ReflectionUtils.getBeanPropertyValue(t, property);
        if (obj == null) {
            throw new Exception("对象" + t.getClass().getSimpleName() + "的属性" + property +"为空");
        }
    }

    /**
     * 校验给定的对象数组的对应属性是否为空
     * @param t 需要校验的对象数组
     * @param property 需要校验的属性
     * @unhadled 判断对象是否包含对应属性和对应属性的get方法的时候，取得是数组的第一个对象判断的。还没有其他更好的方法
     */
    public static <T> void validateGivenPropertyIsNull(T[] t, String property) throws Exception {
        //取数组第一个对象进行校验
        ReflectionUtils.validateHasProperty(t[0], property);
        for (T s : t) {
            validateGivenPropertyIsNull(s, property);
        }
    }

    /**
     * 校验给定的对象链表的对应属性是否为空
     * @param list 需要校验的对象链表
     * @param property 需要校验的属性
     * @unhadled 判断对象是否包含对应属性和对应属性的get方法的时候，取得是链表的第一个对象判断的。还没有其他更好的方法
     */
    public static <T> void validateGivenPropertyIsNull(List<T> list, String property) throws Exception {
        validateGivenPropertyIsNull(list.toArray(new String[list.size()]), property);
    }

    /**
     * 批量校验给定对象的属性数组内的属性是否为空
     * @param t 需要校验的对象
     * @param properties 需要校验的属性数组
     * @throws Exception
     */
    public static <T> void validateGivenPropertiesIsNull(T t, String[] properties) throws Exception {
        if (properties == null || properties.length == 0) {
            return;
        }
        for (String column : properties) {
            validateGivenPropertyIsNull(t, column);
        }
    }

    /**
     * 批量校验给定对象的属性链表内的属性是否为空
     * @param t 需要校验的对象
     * @param properties 需要校验的属性链表
     * @throws Exception
     */
    public static <T> void validateGivenPropertiesIsNull(T t, List<String> properties) throws Exception {
        validateGivenPropertiesIsNull(t, properties.toArray(new String[properties.size()]));
    }

    /**
     * 批量校验给定对象数组的属性数组内的属性是否为空
     * @param t 需要校验的对象数组
     * @param properties 需要校验的属性数组
     * @throws Exception
     */
    public static <T> void validateGivenBeansPropertiesIsNull(T[] t, String[] properties) throws Exception {
        for (T s : t) {
            validateGivenPropertiesIsNull(t, properties);
        }
    }

    /**
     * 批量校验给定对象数组的属性链表内的属性是否为空
     * @param t 需要校验的对象数组
     * @param properties 需要校验的属性链表
     * @throws Exception
     */
    public static <T> void validateGivenBeansPropertiesIsNull(T[] t, List<String> properties) throws Exception {
        for (T s : t) {
            validateGivenPropertiesIsNull(t, properties);
        }
    }

    /**
     * 批量校验给定对象链表的属性数组内的属性是否为空
     * @param list 需要校验的对象链表
     * @param properties 需要校验的属性数组
     * @throws Exception
     */
    public static <T> void validateGivenBeansPropertiesIsNull(List<T> list, String[] properties) throws Exception {
        for (T t : list) {
            validateGivenPropertiesIsNull(t, properties);
        }
    }

    /**
     * 批量校验给定对象链表的属性链表内的属性是否为空
     * @param list 需要校验的对象链表
     * @param properties 需要校验的属性链表
     * @throws Exception
     */
    public static <T> void validateGivenBeansPropertiesIsNull(List<T> list, List<String> properties) throws Exception {
        for (T t : list) {
            validateGivenPropertiesIsNull(t, properties);
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
        return ReflectionUtils.setBeanProperty(t, property, value);
    }

    /**
     * 批量设置对象属性值
     * @param t 需要设置属性的对象
     * @param properties 需要设置的属性名称数组
     * @param map 对象名称以及对象值的map
     * @return 原始传入的对象
     * @throws Exception
     */
    public static <T> T setBeanProperties(T t, String[] properties, Map<String, Object> map) throws Exception {
        if (map == null || map.isEmpty()) {
            return ReflectionUtils.getNewInstance(t);
        }

        for (String property : properties) {
            ReflectionUtils.validateHasProperty(t, property);
            Object obj = map.get(property);
            ReflectionUtils.setBeanProperty(t, property, obj);
        }
        return t;
    }

    /**
     * 读取xml文件内容生成对应的对象,使用dom4j解析XML,需要dom4j支持
     * @param content xml文件内容
     * @param <T> 需要生成的对象
     * @return
     */
    public static <T> T generateBeanFromXmlFile(T t, String content) throws Exception {






        return null;
    }

    /**
     * 读取xml文件内容生成对应的对象
     * @param clazz 需要生成的对象类型
     * @param content xml文件内容
     * @param <T> 需要生成的对象
     * @return
     */
    public static <T> T generateBeanFromXmlFile(Class clazz, String content) throws Exception {
        return generateBeanFromXmlFile((T)clazz.newInstance(), content);
    }
}
