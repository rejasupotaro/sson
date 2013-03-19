package main.com.rejasupotaro.sson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import main.com.rejasupotaro.sson.sexpr.SexprObject;


public class TypeAdapter {

    /*
    public String toSexpr(Object src) {
        Field[] fields;
        Class<?> clazz = src.getClass();
        fields = getFieldsAndAddTraversal(clazz);
        return toSexpr(src, fields);
    }

    private Field[] getFieldsAndAddTraversal(Class<?> clazz) {
        List<Field> fieldsList = new ArrayList<Field>();
        for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                fieldsList.add(field);
            }
        }
        return fieldsList.toArray(new Field[fieldsList.size()]);
    }

    private String toSexpr(Object src, Field[] fields) {
        SexprObject rootSexpr = null;
        try {
            SexprObject lastSexpr = null;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];

                String label = field.getName();
                Object value = field.get(src);
                dumpvalue(value);

                Parser parser = new Parser(label, value.toString());

                SexprObject sexpr = parser.buildTree();
                if (rootSexpr == null) {
                    rootSexpr = sexpr.cloneSexpr(sexpr);
                    rootSexpr.car = sexpr;
                    rootSexpr.cdr = null;
                    lastSexpr = sexpr;
                } else {
                    if (rootSexpr.cdr == null) {
                        rootSexpr.cdr = sexpr;
                        lastSexpr = rootSexpr.cdr;
                    } else {
                        lastSexpr.cdr = sexpr;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootSexpr == null ? null : rootSexpr.toString();
    }

    private void dumpvalue(Object value) {
        if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            for (int j = 0; j < length; j++) {
                System.out.println(Array.get(value, j));
            }
        } else {
            System.out.println(value.toString());
        }
    }
    */
}
