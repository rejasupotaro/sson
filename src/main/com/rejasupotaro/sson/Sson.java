package main.com.rejasupotaro.sson;

import java.lang.reflect.Type;

import main.com.rejasupotaro.sson.sexpr.SexprEmpty;
import main.com.rejasupotaro.sson.sexpr.SexprObject;
import test.com.rejasupotaro.sson.Person;

public class Sson {

    public String toSexpr(Object src) {
        if (src == null) {
            return toSexpr(SexprEmpty.INSTANCE);
        } else {
            return toSexpr(src, src.getClass());
        }
    }

    private String toSexpr(SexprObject sexpr) {
        return ""; // TODO
    }

    private String toSexpr(Object src, Type typeOfSrc) {
        return "";//new TypeAdapter().toSexpr(src);
    }

    public static void main(String[] args) {
        Sson sson = new Sson();
        Person person = new Person("rejasupotaro", 23);
        System.out.println("person.toString() => " + person.toString());
        String personSexpr = sson.toSexpr((person));
        System.out.println("sson.toSson => " + personSexpr);
    }
}
