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
}
