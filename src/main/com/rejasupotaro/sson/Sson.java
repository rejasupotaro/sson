package main.com.rejasupotaro.sson;

import java.lang.reflect.Type;

import main.com.rejasupotaro.sson.sexpr.SexprObject;

import test.com.rejasupotaro.sson.Person;


public class Sson {

    public String toSson(Object src) {
        if (src == null) {
            return toSson(SsonEmpty.INSTANCE);
        } else {
            return toSson(src, src.getClass());
        }
    }

    private String toSson(SexprObject sexpr) {
        return ""; // TODO
    }

    private String toSson(Object src, Type typeOfSrc) {
        return new TypeAdapter().toSexpr(src);
    }

    public static void main(String[] args) {
        Sson sson = new Sson();
        Person person = new Person("rejasupotaro", 23);
        System.out.println("person.toString() => " + person.toString());
        String personSexpr = sson.toSson((person));
        System.out.println("sson.toSson => " + personSexpr);
    }
}
