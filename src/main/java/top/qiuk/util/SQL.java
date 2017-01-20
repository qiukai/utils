package top.qiuk.util;

import java.lang.reflect.Field;

/**
 * Created by 邱凯 on 2016/12/29.
 * 拼接sql
 */
public class SQL {

    private StringBuilder sb;

    public SQL() {
        sb = new StringBuilder();
    }

    public <T> SQL insert(T t, String table) {
        Class<?> clazz = t.getClass();
        sb.append("insert into ");
        sb.append(table).append(" (");
        sb.append(StringUtil.objectToString(clazz));
        sb.append(") values (");
        Field[] fields = clazz.getDeclaredFields();
        int fieldsLength = fields.length;
        String[] strings = new String[fieldsLength];
        for (int i = 0; i < fieldsLength; i++) {
            strings[i] = "?";
        }
        sb.append(StringUtil.connectionString(strings, ","));
        sb.append(")");
        return this;
    }

    public String toSql() {
        return sb.toString();
    }

    public SQL setSql(String sql) {
        sb.append(sql);
        return this;
    }
}
