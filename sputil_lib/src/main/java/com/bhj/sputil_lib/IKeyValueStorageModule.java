package com.bhj.sputil_lib;

import android.support.annotation.NonNull;

public interface IKeyValueStorageModule {

    void put(@NonNull final String key, final Object value, Class<?> clz);

    Object get(@NonNull final String key, Class<?> clz, Object defaultValue);

}
