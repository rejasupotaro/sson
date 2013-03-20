package main.com.rejasupotaro.sson.internal.bind;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import main.com.rejasupotaro.sson.Sson;
import main.com.rejasupotaro.sson.TypeAdapter;
import main.com.rejasupotaro.sson.reflect.TypeToken;
import main.com.rejasupotaro.sson.stream.SexprWriter;

public class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
    private final Sson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    TypeAdapterRuntimeTypeWrapper(Sson context, TypeAdapter<T> delegate, Type type) {
      this.context = context;
      this.delegate = delegate;
      this.type = type;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void write(SexprWriter out, T value) throws IOException {
      // Order of preference for choosing type adapters
      // First preference: a type adapter registered for the runtime type
      // Second preference: a type adapter registered for the declared type
      // Third preference: reflective type adapter for the runtime type (if it is a sub class of the declared type)
      // Fourth preference: reflective type adapter for the declared type

      TypeAdapter chosen = delegate;
      Type runtimeType = getRuntimeTypeIfMoreSpecific(type, value);
      if (runtimeType != type) {
        TypeAdapter runtimeTypeAdapter = context.getAdapter(TypeToken.get(runtimeType));
        if (!(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
          // The user registered a type adapter for the runtime type, so we will use that
          chosen = runtimeTypeAdapter;
        } else if (!(delegate instanceof ReflectiveTypeAdapterFactory.Adapter)) {
          // The user registered a type adapter for Base class, so we prefer it over the
          // reflective type adapter for the runtime type
          chosen = delegate;
        } else {
          // Use the type adapter for runtime type
          chosen = runtimeTypeAdapter;
        }
      }
      chosen.write(out, value);
    }

    /**
     * Finds a compatible runtime type if it is more specific
     */
    private Type getRuntimeTypeIfMoreSpecific(Type type, Object value) {
      if (value != null
          && (type == Object.class || type instanceof TypeVariable<?> || type instanceof Class<?>)) {
        type = value.getClass();
      }
      return type;
    }
}
