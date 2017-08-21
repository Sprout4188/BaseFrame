package com.sprout.frame.baseframe.http.typeAdapterFactory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.sprout.frame.baseframe.http.typeAdapter.NullStringAdapter;

/**
 * Created by Sprout on 2017/8/16
 */
public class NullStringTypeAdapterFactory<T> implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType == String.class) {
            return (TypeAdapter<T>) new NullStringAdapter();
        }
        return null;
    }
}
