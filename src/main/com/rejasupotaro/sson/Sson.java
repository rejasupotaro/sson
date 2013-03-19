package main.com.rejasupotaro.sson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.com.rejasupotaro.sson.sexpr.SexprElement;
import main.com.rejasupotaro.sson.sexpr.SexprEmpty;

public class Sson {

    private final List<TypeAdapterFactory> factories;

    public Sson() {
        this(Collections.<TypeAdapterFactory>emptyList());
    }

    Sson(List<TypeAdapterFactory> typeAdapterFactories) {
        List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
        factories.addAll(typeAdapterFactories);

        this.factories = Collections.unmodifiableList(factories);
    }

    public String toSexpr(Object src) {
        if (src == null) {
            return toSexpr(SexprEmpty.INSTANCE);
        } else {
            return toSexpr(src, src.getClass());
        }
    }

    private String toSexpr(SexprElement sexpr) {
        return sexpr.toString();
    }

    private String toSexpr(Object src, Type typeOfSrc) {
        //TypeAdapter<?> adapter = getAdapter(TypeToken.get(typeOfSrc));
        return "";//new TypeAdapter().toSexpr(src);
    }
}
