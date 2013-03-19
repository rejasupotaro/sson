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

    private String deferredLabel;
    private String separator = ":";

    public SexprWriter(Writer out) {
        if (out == null) {
            throw new NullPointerException("out == null");
        }
        this.out = out;
    }

    public SexprWriter beginSexpr() throws IOException {
        writeDeferredLabel();
        return open(SexprScope.EMPTY_SEXPR, "(");
    }

    public SexprWriter endSexpr() throws IOException {
        return close(SexprScope.EMPTY_SEXPR, SexprScope.NONEMPTY_SEXPR, ")");
    }

    private void writeDeferredLabel() throws IOException {
        if (deferredLabel != null) {
            beforeLabel();
            string(deferredLabel);
            deferredLabel = null;
        }
    }

    private SexprWriter open(SexprScope empty, String openRoundBracket) throws IOException {
        beforeValue(true);
        stack.add(empty);
        out.write(openRoundBracket);
        return this;
    }

    private SexprWriter close(SexprScope empty, SexprScope nonempty, String closeRoundBracket)
            throws IOException {
        SexprScope context = peek();
        if (context != nonempty && context != empty) {
            throw new IllegalStateException("Nesting problem: " + stack);
        }
        if (deferredLabel != null) {
            throw new IllegalStateException("Dangling label: " + deferredLabel);
        }

        stack.remove(stack.size() - 1);
        out.write(closeRoundBracket);
        return this;
    }

    public SexprWriter emptyValue() throws IOException {
        if (deferredLabel != null) {
            beforeLabel();
            string(deferredLabel);
            deferredLabel = null;
        }
        beforeValue(false);
        out.write("null");
        return this;
    }

    private void string(String value) throws IOException {
        out.write(value);
    }

    private void beforeLabel() throws IOException {
        SexprScope context = peek();
        if (context == SexprScope.NONEMPTY_SEXPR) {
            out.write(' ');
        } else if (context != SexprScope.EMPTY_SEXPR) {
            throw new IllegalStateException("Nesting problem: " + stack);
        }
        replaceTop(SexprScope.DANGLING_LABEL);
    }

    private void beforeValue(boolean root) throws IOException {
        switch (peek()) {
        case NONEMPTY_DOCUMENT:
            throw new IllegalStateException();
            // fall-through
        case EMPTY_DOCUMENT:
            replaceTop(SexprScope.NONEMPTY_DOCUMENT);
            break;
        case DANGLING_LABEL:
            out.append(separator);
            replaceTop(SexprScope.NONEMPTY_SEXPR);
            break;
        default:
            throw new IllegalStateException("Nesting problem: " + stack);
        }
    }

    private SexprScope peek() {
        int size = stack.size();
        if (size == 0) {
            throw new IllegalStateException("SexprWriter is closed.");
        }
        return stack.get(size - 1);
    }

    private void replaceTop(SexprScope topOfStack) {
        stack.set(stack.size() - 1, topOfStack);
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
