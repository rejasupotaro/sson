package main.com.rejasupotaro.sson;

public final class SsonEmpty extends Sexpr {

    public static final SsonEmpty INSTANCE = new SsonEmpty();

    @Override
    public int hashCode() {
        return SsonEmpty.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof SsonEmpty;
    }
}