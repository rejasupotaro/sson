package main.com.rejasupotaro.sson.sexpr;

public final class SexprEmpty extends SexprElement {

    public static final SexprEmpty INSTACE = new SexprEmpty();

    @Deprecated
    public SexprEmpty() {
        // Do nothing
    }

    @Override
    public int hashCode() {
        return SexprEmpty.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof SexprEmpty;
    }

    @Override public SexprElement getCar() { return null; }
    @Override public SexprElement getCdr() { return null; }
}
