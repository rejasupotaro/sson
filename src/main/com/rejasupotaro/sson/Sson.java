package main.com.rejasupotaro.sson;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.com.rejasupotaro.sson.reflect.TypeToken;
import main.com.rejasupotaro.sson.sexpr.SexprElement;
import main.com.rejasupotaro.sson.sexpr.SexprEmpty;
import main.com.rejasupotaro.sson.stream.SexprWriter;

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
        StringWriter writer = new StringWriter();
        toSexpr(src, typeOfSrc, writer);
        return writer.toString();
    }

    private void toSexpr(Object src, Type typeOfSrc, Appendable writer) {
        SexprWriter sexprWriter = new SexprWriter((Writer) writer);
        toSexpr(src, typeOfSrc, sexprWriter);
    }

    @SuppressWarnings("unchecked")
    private void toSexpr(Object src, Type typeOfSrc, SexprWriter writer) {
        TypeAdapter<?> adapter = getAdapter(TypeToken.get(typeOfSrc));
        try {
            ((TypeAdapter<Object>) adapter).write(writer, src);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
        try {
            for (TypeAdapterFactory factory : factories) {
                TypeAdapter<T> candidate = factory.create(this, type);
                if (candidate != null) {
                    return candidate;
                }
            }
            throw new IllegalArgumentException("SSON cannot handle " + type);
        } finally {
        }
    }
}
