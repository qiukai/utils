package top.qiuk.util;

import top.qiuk.constant.DateTypeEnum;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DBUtil {

    public static class SQL {

        /**
         * 生成插入语句
         *
         * @param clazz
         * @param table
         * @return
         */
        public static String getInsertSql(Class<?> clazz, String table) {
            StringBuilder sb = new StringBuilder("insert into ");
            sb.append(table).append(" (");
            sb.append(StringUtil.objectToString(clazz));
            sb.append(") values (");
            Field[] fields = clazz.getDeclaredFields();
            String[] strings = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                strings[i] = "?";
            }
            sb.append(StringUtil.connectionString(strings, ","));
            sb.append(")");
            return sb.toString();
        }

        /**
         * 生成select 语句
         *
         * @param clazz
         * @param table
         * @return
         */
        public static String getSelectSql(Class<?> clazz, String table) {
            StringBuilder sb = new StringBuilder("select ");
            sb.append(StringUtil.objectToString(clazz)).append(" from ").append(table);
            return sb.toString();
        }

        /**
         * 拼接where条件
         *
         * @param sql
         * @param map
         * @param paramsValues
         * @return
         */
        public static String getWhereSql(String sql, Map<String, Object> map, List<Object> paramsValues) {

            if (null == map || 0 == map.size()) {
                return sql;
            }

            StringBuilder sb = new StringBuilder(sql);
            sb.append(" where ");
            for (String key : map.keySet()) {

                Object value = map.get(key);
                if (value instanceof Date[]) {
                    Date[] date = (Date[]) value;
                    sb.append(key);
                    if (null != date[0]) {
                        sb.append(" >= ?");
                        paramsValues.add(date[0]);
                    }
                    if (null != date[1]) {
                        sb.append(" <= ?");
                        paramsValues.add(date[1]);
                    }
                } else if (value instanceof Set) {
                    @SuppressWarnings("unchecked")
                    Set<Object> set = (Set<Object>) value;
                    sb.append(" ( ");
                    int i = 0;
                    for (Object object : set) {
                        if (i != 0) {
                            sb.append(" or ");
                        }
                        i++;
                        sb.append(key);
                        sb.append(" = ? ");
                        paramsValues.add(object);
                    }
                    sb.append(" ) ");
                } else if (value instanceof String) {
                    sb.append(key).append(" like ?");
                    paramsValues.add("%" + value + "%");
                } else {
                    sb.append(key).append(" = ?");
                    paramsValues.add(value);
                }
                sb.append(" and ");
            }

            return sb.toString().substring(0, sb.length() - 4);
        }
    }

    /**
     * 将查询结果转成list
     *
     * @param rs
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> List<T> getList(ResultSet rs, Class<T> clazz) throws Exception {
        if (null == rs) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        while (rs.next()) {
            list.add(DBUtil.getOne(rs, clazz));
        }
        return list;
    }

    /**
     * 将查询结果转成Object
     *
     * @param rs
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T getOne(ResultSet rs, Class<T> clazz) throws Exception {
        if (null == rs) {
            return null;
        }
        T t = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            Object value = null;
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            if (fieldType.isAssignableFrom(Integer.class)) {
                value = rs.getInt(fieldName);
            } else if (fieldType.isAssignableFrom(Long.class)) {
                value = rs.getLong(fieldName);
            } else if (fieldType.isAssignableFrom(String.class)) {
                value = rs.getString(fieldName);
            } else if (fieldType.isAssignableFrom(Float.class)) {
                value = rs.getFloat(fieldName);
            } else if (fieldType.isAssignableFrom(Date.class)) {
                Object object = rs.getObject(fieldName);
                if (object instanceof Date) {
                    value = object;
                } else {
                    value = DateUtil.getDate(object, DateTypeEnum._yMdHms);
                }
            }
            ReflectUtil.setter(t, field, value);
        }
        return t;
    }

    /**
     * 插入语句，对预编译对象赋值
     *
     * @param t 要插入的对象
     * @param ps
     * @throws Exception
     */
    public static <T> void insert(T t, PreparedStatement ps) throws Exception {
        Field[] fields = t.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; ) {

            String fieldName = fields[i].getName();
            Class<?> fieldType = fields[i].getType();

            if (fieldType.isAssignableFrom(Integer.class)) {
                ps.setInt(++i, Integer.valueOf(ReflectUtil.getter(t, fieldName).toString()));

            } else if (fieldType.isAssignableFrom(Long.class)) {
                ps.setLong(++i, Long.valueOf((String) ReflectUtil.getter(t, fieldName)));

            } else if (fieldType.isAssignableFrom(String.class)) {
                ps.setString(++i, (String) ReflectUtil.getter(t, fieldName));

            } else if (fieldType.isAssignableFrom(Date.class)) {
                ps.setString(++i, DateUtil.getString((Date) ReflectUtil.getter(t, fieldName), DateTypeEnum._yMdHms));

            } else if (fieldType.isAssignableFrom(Float.class)) {
                ps.setFloat(++i, (Float) ReflectUtil.getter(t, fieldName));
            }
        }
    }

    /**
     * 执行查询
     * @param ps 预编译对象
     * @param paramsValues 预编译的值
     * @return 返回rs结果
     * @throws Exception
     */
    public static ResultSet executeQuery(PreparedStatement ps, List<Object> paramsValues) throws Exception {

        if (null != paramsValues && 0 < paramsValues.size()) {
            for (int i = 0; i < paramsValues.size(); ) {
                Object object = paramsValues.get(i);
                if (object instanceof Date) {
                    Date date = (Date) object;
                    ps.setString(++i, DateUtil.getString(date, DateTypeEnum._yMdHms));
                } else if (object instanceof Integer) {
                    ps.setInt(++i, (Integer) object);
                } else if (object instanceof String) {
                    ps.setString(++i, object.toString());
                }
            }
        }
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
