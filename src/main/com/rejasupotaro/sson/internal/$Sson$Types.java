package main.com.rejasupotaro.sson.internal;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

public class $Sson$Types {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[] {};

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else {
            String className =
                    type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                    + "GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            return clazz.isArray() ?
                    new GenericArrayTypeImpl(canonicalize(clazz.getComponentType())) : clazz;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) type;
            return new ParameterizedTypeImpl(
                    p.getOwnerType(),
                    p.getRawType(),
                    p.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            GenericArrayType g = (GenericArrayType) type;
            return new GenericArrayTypeImpl(g.getGenericComponentType());
        } else if (type instanceof WildcardType) {
            WildcardType w = (WildcardType) type;
            return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
        } else {
            return type;
        }
    }

    private static final class GenericArrayTypeImpl implements GenericArrayType {
        private final Type componentType;

        public GenericArrayTypeImpl(Type componentType) {
            this.componentType = canonicalize(componentType);
        }

        public Type getGenericComponentType() {
            return componentType;
        }
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
            if (rawType instanceof Class<?>) {
                Class<?> rawTypeAsClass = (Class<?>) rawType;
                // TODO checkArgument
            }

            this.ownerType = ownerType == null ? null : canonicalize(ownerType);
            this.rawType = canonicalize(rawType);
            this.typeArguments = typeArguments.clone();
            for (int t = 0; t < this.typeArguments.length; t++) {
                this.typeArguments[t] = canonicalize(this.typeArguments[t]);
            }
        }

        public Type[] getActualTypeArguments() {
            return typeArguments.clone();
        }

        public Type getRawType() {
            return rawType;
        }

        public Type getOwnerType() {
            return ownerType;
        }
    }

    private static final class WildcardTypeImpl implements WildcardType {
        private final Type upperBound;
        private final Type lowerBound;

        public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
            if (lowerBounds.length == 1) {
                this.lowerBound = canonicalize(lowerBounds[0]);
                this.upperBound = Object.class;
            } else {
                this.lowerBound = null;
                this.upperBound = canonicalize(upperBounds[0]);
            }
        }

        public Type[] getUpperBounds() {
            return new Type[] { upperBound };
        }

        public Type[] getLowerBounds() {
            return lowerBound != null ? new Type[] { lowerBound } : EMPTY_TYPE_ARRAY;
        }
    }
}
