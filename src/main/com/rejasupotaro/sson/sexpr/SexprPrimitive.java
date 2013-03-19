package main.com.rejasupotaro.sson.sexpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class SexprPrimitive extends SexprElement {

    @SuppressWarnings("serial")
    private static final List<Class<?>> PRIMITIVE_TYPES =
    Collections.unmodifiableList(new ArrayList<Class<?>>() {{
        add(int.class);
        add(long.class);
        add(short.class);
        add(float.class);
        add(double.class);
        add(byte.class);
        add(boolean.class);
        add(char.class);
        add(Integer.class);
        add(Long.class);
        add(Short.class);
        add(Float.class);
        add(Double.class);
        add(Byte.class);
        add(Boolean.class);
        add(Character.class);
    }});

    private Object value;

    public SexprPrimitive(Boolean bool) {
        setValue(bool);
    }

    public SexprPrimitive(Number number) {
        setValue(number);
    }

    public SexprPrimitive(String string) {
        setValue(string);
    }

    public SexprPrimitive(Character c) {
        setValue(c);
    }

    public SexprPrimitive(Object primitive) {
        setValue(primitive);
    }

    void setValue(Object primitive) {
        if (primitive instanceof Character) {
            char c = ((Character) primitive).charValue();
            this.value = String.valueOf(c);
        } else {
            this.value = primitive;
        }
    }

    public boolean isBoolean() {
        return value instanceof Boolean;
    }

    Boolean getAsBooleanWrapper() {
        return (Boolean) value;
    }

    public boolean getAsBoolean() {
        if (isBoolean()) {
            return getAsBooleanWrapper().booleanValue();
        } else {
            // Check to see if the value as a String is "true" in any case.
            return Boolean.parseBoolean(getAsString());
        }
    }

    public boolean isNumber() {
        return value instanceof Number;
    }

    public Number getAsNumber() {
        return (Number) value;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public String getAsString() {
        if (isNumber()) {
            return getAsNumber().toString();
        } else if (isBoolean()) {
            return getAsBooleanWrapper().toString();
        } else {
            return (String) value;
        }
    }

    public double getAsDouble() {
        return isNumber() ?
                getAsNumber().doubleValue() : Double.parseDouble(getAsString());
    }

    public int getAsInt() {
        return isNumber() ?
                getAsNumber().byteValue() : Integer.parseInt(getAsString());
    }

    public static boolean isPrimitiveOrString(Object target) {
        if (target instanceof String) return true;

        Class<?> classOfPrimitive = target.getClass();
        for (Class<?> standardPrimitive : PRIMITIVE_TYPES) {
            if (standardPrimitive.isAssignableFrom(classOfPrimitive)) {
                return true;
            }
        }

        return false;
    }
}
