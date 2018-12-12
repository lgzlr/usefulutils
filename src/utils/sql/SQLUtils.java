package utils.sql;

import utils.reflection.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SQLUtils {

    /**
     * 覆盖默认构造函数,防止通过构造函数得到当前工具类对象
     */
    private SQLUtils() {
    }

    /**
     * 得到SQL语句where条件中拼接的字符串
     * 类似于select * from table where column in ('A', 'B', 'C');
     * 语句中的'A', 'B', 'C'形式
     *
     * @param array 接收参数是String类型数组，里面是所有需要拼接的字段值
     * @return inSQL 拼接完成的字符串
     */
    public static String getInSqlFromArray(String[] array) {
        String inSQL = "";
        int size = array.length;
        if (size == 0) {
            return inSQL;
        }
        for (int i = 0; i < size; i++, inSQL += ",") {
            inSQL += array[i];
            if (i == size - 1) {
                break;
            }
        }
        return inSQL;
    }

    /**
     * 得到SQL语句where条件中拼接的字符串
     * 类似于select * from table where column in ('A', 'B', 'C');
     * 语句中的'A', 'B', 'C'形式
     *
     * @param list 接收参数是String类型链表，里面是所有需要拼接的字段值
     * @return inSQL 拼接完成的字符串
     */
    public static String getInSqlFromList(List<String> list) {
        return getInSqlFromArray(list.toArray(new String[list.size()]));
    }

    /**
     * 从传入的对象数组中取出对象的某一列拼接SQL
     * @attention 注意处理对象的toString()方法，保证从列中取出的值能够正常转化为String
     * @unhadled <h4>校验对象中是否存在名称为传入的column的属性，现行方法是取出数组中第一个对象进行校验，还没很好的校验方法</h4>
     * @param a 需要处理的对象数组
     * @param column 需要从对象中取出的列名
     * @param <T> 需要处理的对象
     * @return 拼接生成的SQL字段
     * @throws Exception
     */
    public static <T> String getInSqlFromList(T[] a, String column) throws Exception {
        if (a == null || a.length == 0) {
            return "";
        }
        if (column == null || "".equals(column.trim())) {
            throw new Exception("传入的列名不能为空");
        }
        //这里先取出数组第一个对象，校验是否存在名称为传入的column的属性
        ReflectionUtils.validateHasProperty(a[0].getClass(), column);
        //校验是否存在名为getXXX()的方法
        ReflectionUtils.validateHasGetMethod(a[0].getClass(), column);
        List<String> list = new ArrayList<String>();
        for (T t : a) {
            Method method = ReflectionUtils.getPropertyGetMethod(t, column);
            Object obj = method.invoke(t);
            //通过java的默认机制，在这里隐含调用了字段的toString()方法
            list.add(obj+"");
        }
        return getInSqlFromList(list);
    }
}
