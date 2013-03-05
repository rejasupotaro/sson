package com.rejasupotaro.sson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;


public class Sson {

    public String toSson(Object src) {
        if (src == null) {
            return toSson(SsonEmpty.INSTANCE);
        } else {
            return toSson(src, src.getClass());
        }
    }

    private String toSson(Sexpr sexpr) {
        return ""; // TODO
    }

    private String toSson(Object src, Type typeOfSrc) {
        Sexpr rootSexpr = null;
        Field[] fields = src.getClass().getFields();
        try {
            Sexpr lastSexpr = null;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Parser parser = new Parser(field.getName(), field.get(src).toString());
                Sexpr sexpr = parser.buildTree();
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

    public static void main(String[] args) {
        Sson sson = new Sson();
        Person person = new Person("rejasupotaro", 23);
        System.out.println("person.toString() => " + person.toString());
        String personSexpr = sson.toSson((person));
        System.out.println("sson.toSson => " + personSexpr);
    }
}
