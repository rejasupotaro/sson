package main.com.rejasupotaro.sson.internal.bind;

import java.io.IOException;

import main.com.rejasupotaro.sson.TypeAdapter;
import main.com.rejasupotaro.sson.stream.SexprWriter;

public class TypeAdapters {
    public static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
        @Override
        public void write(SexprWriter out, String value) throws IOException {
            out.value(value);
        }
    };
}
