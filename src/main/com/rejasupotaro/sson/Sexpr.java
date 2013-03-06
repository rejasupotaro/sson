package main.com.rejasupotaro.sson;

import java.io.IOException;

public class Sexpr {
    boolean isAtom;
    boolean isNIL;
    boolean isNumber;
    boolean isID;
    Sexpr car;
    Sexpr cdr;
    String value;
    //int pcounter = 0;

    public static Sexpr NIL = new Sexpr("NIL");
    public static Sexpr T = new Sexpr("T");

    public Sexpr() {
        // TODO 空のS式にする
    }
    
    public Sexpr(String value) {
        this.value = value.toUpperCase();
        isAtom =true;
        isNumber = false;
        isID =false;
        //isNIL = this.Value.equals("NIL");
        if (this.value.equals("NIL")) {
            isNIL = true;
        } else if (this.value.equals("NULL")) {
            isNIL = true;
        }
    }

    public Sexpr(Sexpr car, Sexpr cdr) {
        this.car = car;
        this.cdr = cdr;
        isAtom = false;
        isNumber = false;
        isNIL = false;
    }

    public int sexprLength(Sexpr sexpr) {
        int n = 0;
        while(sexpr.isNIL != true) {
            if (sexpr.isAtom == true) {
                return -1;
            } else {
                sexpr = sexpr.cdr;
                n = n + 1;
                if (sexpr.car == null) {
                    break;
                }
            }
        }
        return n;
    }

    public Sexpr cloneSexpr(Sexpr sexpr) {
        Sexpr ret = new Sexpr("NIL");
        ret.isAtom = false;
        ret.isID = false;
        ret.isNIL = false;
        ret.isNumber = false;
        if (sexpr.isAtom == false) {
            ret.car = cloneSexpr(sexpr.car);
            ret.cdr = cloneSexpr(sexpr.cdr);
            return ret;
        } else {
            if (sexpr.isID == true) {
                ret.value = sexpr.value;
                ret.isAtom = true;
                ret.isID = true;
                ret.isNumber = false;
                ret.isNIL = false;
                return ret;
            } else if(sexpr.isNumber == true) {
                ret.value = sexpr.value;
                ret.isAtom = true;
                ret.isID = false;
                ret.isNIL = false;
                ret.isNumber = true;
                return ret;
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        try {
            return "(" + toString(this) + ")";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    private String toString(Sexpr sexpr) throws Exception {
        if (sexpr.cdr != null) {
            return sexprPrinter(sexpr.car) + " " + toString(new Sexpr(sexpr.cdr, null));
        } else {
            return sexprPrinter(sexpr.car);
        }
    }

    public String atomPrinter(Sexpr sexpr) throws Exception {
        if (sexpr.isAtom == true) {
            return sexpr.value;
        } else {
            throw new Exception("The S-expr is not an atom "+ sexpr.value);
        }
    }

    public String sexprPrinter(Sexpr sexpr) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        boolean needSpace = false;
        if (sexpr.isAtom == true) {
            stringBuilder.append(this.atomPrinter(sexpr));
        } else {
            stringBuilder.append("(");
            do {
                if (needSpace == true) {
                    stringBuilder.append(" ");
                } else {
                    needSpace = true;
                }

                if (sexpr.car != null) {
                    if (sexpr.car.isAtom == true) {
                        stringBuilder.append(this.atomPrinter(sexpr.car));
                    } else {
                        stringBuilder.append(this.sexprPrinter(sexpr.car));
                    }

                }

                if (sexpr.cdr != null) {
                    if (sexpr.cdr.isAtom == true) {
                        if (sexpr.cdr.value.equals("NIL") == true) {
                            stringBuilder.append((")"));
                            return stringBuilder.toString();
                        }
                    }

                    if (sexpr.cdr.isAtom == true) {
                        stringBuilder.append(".");
                        stringBuilder.append(this.atomPrinter(sexpr.cdr));
                        stringBuilder.append(")");
                        return stringBuilder.toString();
                    }
                }

                sexpr = sexpr.cdr;
            } while (true);
        }

        return stringBuilder.toString();
    }
}
