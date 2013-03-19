package main.com.rejasupotaro.sson.sexpr;

public abstract class SexprElement {

    public boolean isSexprObject() {
        return this instanceof SexprObject;
    }

    public boolean isSexprPrimitive() {
        return this instanceof SexprPrimitive;
    }

    public boolean isSexprEmpty() {
        return this instanceof SexprEmpty;
    }

    public SexprObject getAsSexprObject() {
        if (isSexprObject()) {
            return (SexprObject) this;
        }
        throw new IllegalStateException("Not a Sexpr Object: " + this);
    }

    public SexprPrimitive getAsSexprPrimitive() {
        if (isSexprPrimitive()) {
            return (SexprPrimitive) this;
        }
        throw new IllegalStateException("This is not a Sexpr Primitive.");
    }

    public SexprEmpty getAsSexprEmpty() {
        if (isSexprEmpty()) {
            return (SexprEmpty) this;
        }
        throw new IllegalStateException("This is not a Sexpr Empty.");
    }

    abstract public SexprElement getCar();
    abstract public SexprElement getCdr();
}
