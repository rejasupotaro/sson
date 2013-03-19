package main.com.rejasupotaro.sson;

import main.com.rejasupotaro.sson.reflect.TypeToken;

public interface TypeAdapterFactory {
    <T> TypeAdapter<T> create(Sson sson, TypeToken<T> type);
}
