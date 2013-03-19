package main.com.rejasupotaro.sson.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import main.com.rejasupotaro.sson.internal.$Sson$Types;


public class TypeToken<T> {
    final Class<? super T> rawType;
    final Type type;
    final int hashCode;

    @SuppressWarnings("unchecked")
    protected TypeToken() {
        this.type = getSuperclassTypeParameter(getClass());
        this.rawType = (Class<? super T>) $Sson$Types.getRawType(type);
        this.hashCode = type.hashCode();
    }

    @SuppressWarnings("unchecked")
    TypeToken(Type type) {
        this.type = type;
        this.rawType = (Class<? super T>) $Sson$Types.getRawType(type);
        this.hashCode = type.hashCode();
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Sson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public final Class<? super T> getRawType() {
        return rawType;
    }

    public final Type getType() {
        return this.type;
    }

    public static TypeToken<?> get(Type type) {
        return new TypeToken<Object>(type);
    }

    public static <T> TypeToken<T> get(Class<T> type) {
        return new TypeToken<T>(type);
    }
}
