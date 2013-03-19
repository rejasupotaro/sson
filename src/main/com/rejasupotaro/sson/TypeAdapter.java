package main.com.rejasupotaro.sson;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import main.com.rejasupotaro.sson.stream.SexprWriter;

public abstract class TypeAdapter<T> {

    public abstract void write(SexprWriter out, T value) throws IOException;
    
    public final void toSexpr(Writer out, T value) throws IOException {
        SexprWriter writer = new SexprWriter(out);
        write(writer, value);
    }
    
    public final String toSexpr(T value) throws IOException {
        StringWriter stringWriter = new StringWriter();
        toSexpr(stringWriter, value);
        return stringWriter.toString();
    }
}
