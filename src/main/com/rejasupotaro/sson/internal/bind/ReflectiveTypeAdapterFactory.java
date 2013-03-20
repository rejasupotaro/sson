package main.com.rejasupotaro.sson.internal.bind;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import main.com.rejasupotaro.sson.Sson;
import main.com.rejasupotaro.sson.TypeAdapter;
import main.com.rejasupotaro.sson.TypeAdapterFactory;
import main.com.rejasupotaro.sson.internal.$Sson$Types;
import main.com.rejasupotaro.sson.internal.Primitives;
import main.com.rejasupotaro.sson.reflect.TypeToken;
import main.com.rejasupotaro.sson.stream.SexprWriter;

public class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Sson sson, TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();

        if (!Object.class.isAssignableFrom(raw)) {
            return null; // it's a primitive!
        }

        return new Adapter<T>(getBoundFields(sson, type, raw));
    }

    private Map<String, BoundField> getBoundFields(Sson context, TypeToken<?> type, Class<?> raw) {
        Map<String, BoundField> result = new LinkedHashMap<String, BoundField>();
        if (raw.isInterface()) {
            return result;
        }

        Type declaredType = type.getType();
        while (raw != Object.class) {
            Field[] fields = raw.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Type fieldType = $Sson$Types.resolve(type.getType(), raw, field.getGenericType());
                BoundField boundField = createBoundField(context, field, field.getName(), TypeToken.get(fieldType));
                BoundField previous = result.put(boundField.name, boundField);
                if (previous != null) {
                    throw new IllegalArgumentException(declaredType
                            + " declares multiple JSON fields named " + previous.name);
                }
            }
            type = TypeToken.get($Sson$Types.resolve(type.getType(), raw, raw.getGenericSuperclass()));
            raw = type.getRawType();
        }
        return result;
    }

    private ReflectiveTypeAdapterFactory.BoundField createBoundField(
            final Sson context, final Field field, final String name, final TypeToken<?> fieldType) {

        return new ReflectiveTypeAdapterFactory.BoundField(name) {
            final TypeAdapter<?> typeAdapter = context.getAdapter(fieldType);
            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override void write(SexprWriter writer, Object value) throws IOException {
                try {
                    Object fieldValue = field.get(value);
                    TypeAdapter t =
                            new TypeAdapterRuntimeTypeWrapper(context, this.typeAdapter, fieldType.getType());
                    t.write(writer, fieldValue);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    }

    static abstract class BoundField {
        final String name;

        protected BoundField(String name) {
            this.name = name;
        }

        abstract void write(SexprWriter writer, Object value) throws IOException, IllegalArgumentException;
    }

    public final class Adapter<T> extends TypeAdapter<T> {
        private final Map<String, BoundField> boundFields;

        private Adapter(Map<String, BoundField> boundFields) {
            this.boundFields = boundFields;
        }

        @Override public void write(SexprWriter out, T value) throws IOException {
            if (value == null) {
                out.emptyValue();
                return;
            }

            out.beginSexpr();
            for (BoundField boundField : boundFields.values()) {
                out.label(boundField.name);
                boundField.write(out, value);
            }
            out.endSexpr();
        }
    }
}
