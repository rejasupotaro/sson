package main.com.rejasupotaro.sson.sexpr;

public class SexprObject extends SexprElement {

    private SexprElement car;
    private SexprElement cdr;

    public SexprObject() {
    }

    public SexprObject setCar(SexprElement car) {
        if (car == null) {
            this.car = SexprEmpty.INSTACE;
        } else {
            this.car = car;
        }
        return this;
    }

    public SexprElement getCar() {
        return this.car;
    }

    public SexprObject setCdr(SexprElement cdr) {
        if (cdr == null) {
            this.cdr = SexprEmpty.INSTACE;
        } else {
            this.cdr = cdr;
        }
        return this;
    }

    public SexprElement getCdr() {
        return this.cdr;
    }

    public static SexprObject cons(Object car, Object cdr) {
        SexprObject sexpr = new SexprObject();
        sexpr.setCar(car instanceof SexprElement ?
                (SexprElement) car : SexprPrimitive.newInstace(car));
        sexpr.setCdr(cdr instanceof SexprElement ?
                (SexprElement) cdr : SexprPrimitive.newInstace(cdr));
        return sexpr;
    }

    public static SexprElement car(SexprElement sexpr) {
        return sexpr.getCar();
    }

    public static SexprElement cdr(SexprElement sexpr) {
        return sexpr.getCdr();
    }
}
