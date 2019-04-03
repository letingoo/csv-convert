import annotation.ExcelField;
import util.FieldRelectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

public class CsvExportUtil {


    public static String exportCsv(List<?> dataList) {

        if (dataList == null || dataList.isEmpty()) {
            throw new RuntimeException("csv data is empty");
        }

        Class<?> clz = dataList.get(0).getClass();
        StringBuilder stringBuilder = new StringBuilder();

        // 1. 构造表头
        List<Field> fieldList = new LinkedList<Field>();
        if (clz.getDeclaredFields() != null && clz.getDeclaredFields().length > 0) {
            for (Field field : clz.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    fieldList.add(field);
                }
            }
        }

        if (fieldList.isEmpty()) {
            throw new RuntimeException("data-field cannot be empty");
        }

        for (Field field : fieldList) {
            ExcelField excelField = field.getAnnotation(ExcelField.class);

            String columnName = excelField.name();
            stringBuilder.append(columnName).append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append('\n');

        // 2. 填数据
        for (Object dataObj : dataList) {
            for (Field field : fieldList) {
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(dataObj);
                    String fieldValueString  = FieldRelectionUtil.formatValue(field, fieldValue);
                    stringBuilder.append(fieldValueString).append(',');
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).append('\n');
        }

        return stringBuilder.toString();
    }
}
