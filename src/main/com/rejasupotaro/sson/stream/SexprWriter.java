package main.com.rejasupotaro.sson.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class SexprWriter implements Closeable, Flushable {

    private final Writer out;

    @SuppressWarnings("serial")
    private final List<SexprScope> stack =
    new ArrayList<SexprScope>() {{
        add(SexprScope.EMPTY_SEXPR);
    }};

    public SexprWriter(Writer out) {
        if (out == null) {
            throw new NullPointerException("out == null");
        }
        this.out = out;
    }

    public void flush() throws IOException {
        if (stack.isEmpty()) {
            throw new IllegalStateException("SexprWriter is closed");
        }
        out.flush();
    }

    public void close() throws IOException {
        out.close();

        int size = stack.size();
        if (size >= 1 && stack.get(size - 1) != SexprScope.NONEMPTY_SEXPR) {
            throw new IOException("Incomplete s-expression");
        }
        stack.clear();
    }
}
