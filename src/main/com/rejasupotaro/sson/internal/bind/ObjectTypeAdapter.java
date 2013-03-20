package main.com.rejasupotaro.sson.internal.bind;

import java.io.IOException;

import main.com.rejasupotaro.sson.Sson;
import main.com.rejasupotaro.sson.TypeAdapter;
import main.com.rejasupotaro.sson.TypeAdapterFactory;
import main.com.rejasupotaro.sson.reflect.TypeToken;
import main.com.rejasupotaro.sson.stream.SexprWriter;

public class ObjectTypeAdapter extends TypeAdapter<Object> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Sson sson, TypeToken<T> type) {
            if (type.getRawType() == Object.class) {
                return (TypeAdapter<T>) new ObjectTypeAdapter(sson);
            }
            return null;
        }
    };

    private final Sson sson;

    private ObjectTypeAdapter(Sson sson) {
        this.sson = sson;
    }

    @SuppressWarnings("unchecked")
    @Override public void write(SexprWriter out, Object value) throws IOException {
        if (value == null) {
            out.emptyValue();
            return;
        }

        TypeAdapter<Object> typeAdapter = (TypeAdapter<Object>) sson.getAdapter(value.getClass());
        if (typeAdapter instanceof ObjectTypeAdapter) {
            out.beginSexpr();
            out.endSexpr();
            return;
        }

        typeAdapter.write(out, value);
    }

}
