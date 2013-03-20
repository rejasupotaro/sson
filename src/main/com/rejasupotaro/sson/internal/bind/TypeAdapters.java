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

    public static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>() {
        @Override
        public void write(SexprWriter out, Number value) throws IOException {
            out.value(value);
        }
    };

    public static final TypeAdapterFactory INTEGER_FACTORY = newFactory(int.class, Integer.class, INTEGER);

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

    public static <TT> TypeAdapterFactory newFactory(
            final Class<TT> unboxed, final Class<TT> boxed, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
            public <T> TypeAdapter<T> create(Sson gson, TypeToken<T> typeToken) {
                Class<? super T> rawType = typeToken.getRawType();
                return (rawType == unboxed || rawType == boxed) ? (TypeAdapter<T>) typeAdapter : null;
            }
            @Override public String toString() {
                return "Factory[type=" + boxed.getName()
                        + "+" + unboxed.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }
}
