package com.bhj.sputil;

import android.support.annotation.NonNull;

import com.bhj.sputil_annation.KeyValueStorageModule;
import com.bhj.sputil_lib.IKeyValueStorageModule;
import com.tencent.mmkv.MMKV;

import java.util.Set;

@KeyValueStorageModule()
public class MyKeyValueStorage implements IKeyValueStorageModule {

    @Override
    public void put(@NonNull String key, Object value, Class<?> clz) {
        if (clz.equals(int.class)){
            MMKV.defaultMMKV().putInt(key, (int) value);
        }else if (clz.equals(float.class)){
            MMKV.defaultMMKV().putFloat(key, (float) value);
        }else if (clz.equals(long.class)){
            MMKV.defaultMMKV().putLong(key, (long) value);
        }else if (clz.equals(String.class)){
            MMKV.defaultMMKV().putString(key, (String) value);
        }else if (clz.equals(boolean.class)){
            MMKV.defaultMMKV().putBoolean(key, (boolean) value);
        }else if (clz.equals(byte[].class)){
            MMKV.defaultMMKV().putBytes(key, (byte[]) value);
        }else if (clz.equals(Set.class)){
            MMKV.defaultMMKV().putStringSet(key, (Set<String>) value);
        }else if (clz.equals(Order.class)){
            // TODO 对象的储存操作
        }
    }

    @Override
    public Object get(@NonNull String key, Class<?> clz, Object defaultValue) {
        if (clz.equals(int.class)){
            return MMKV.defaultMMKV().getInt(key, (int) defaultValue);
        }else if (clz.equals(float.class)){
            return MMKV.defaultMMKV().getFloat(key, (float) defaultValue);
        }else if (clz.equals(long.class)){
            return  MMKV.defaultMMKV().getLong(key, (long) defaultValue);
        }else if (clz.equals(String.class)){
            return MMKV.defaultMMKV().getString(key, (String) defaultValue);
        }else if (clz.equals(boolean.class)){
            return MMKV.defaultMMKV().getBoolean(key, (boolean) defaultValue);
        }else if (clz.equals(byte[].class)){
            return MMKV.defaultMMKV().getBytes(key, (byte[]) defaultValue);
        }else if (clz.equals(Set.class)){
            return MMKV.defaultMMKV().getStringSet(key, (Set<String>) defaultValue);
        }else if (clz.equals(Order.class)){
            // TODO 对象的获取操作
            return null;
        }
        return null;
    }
}
