package com.rejasupotaro.sson.reflect;

import java.lang.reflect.Type;

import com.rejasupotaro.sson.internal.$Sson$Types;

public class TypeToken<T> {
    final Class<? super T> rawType;
    final Type type;
    final int hashCode;

    @SuppressWarnings("unchecked")
    public TypeToken(Type type) {
        if (type == null) throw new NullPointerException();
        this.type = type;
        this.rawType = (Class<? super T>) $Sson$Types.getRawType(type);
        this.hashCode = type.hashCode();
    }
    
    public final Type getType() {
        return this.type;
    }

    public static TypeToken<?> get(Type type) {
        return new TypeToken<Object>(type);
    }
}
