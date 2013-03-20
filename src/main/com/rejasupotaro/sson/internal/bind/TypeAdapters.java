package main.com.rejasupotaro.sson.internal.bind;

import java.io.IOException;

import main.com.rejasupotaro.sson.Sson;
import main.com.rejasupotaro.sson.TypeAdapter;
import main.com.rejasupotaro.sson.TypeAdapterFactory;
import main.com.rejasupotaro.sson.reflect.TypeToken;
import main.com.rejasupotaro.sson.stream.SexprWriter;

public class TypeAdapters {
    public static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
        @Override
        public void write(SexprWriter out, String value) throws IOException {
            out.value(value);
        }
    };

    public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);

    public static <TT> TypeAdapterFactory newFactory(
            final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked")
            public <T> TypeAdapter<T> create(Sson sson, TypeToken<T> typeToken) {
                return typeToken.getRawType() ==
                        type ? (TypeAdapter<T>) typeAdapter : null;
            }
            @Override public String toString() {
                return "Factory[type=" + type.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }
}
