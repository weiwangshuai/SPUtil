
# SPUtil 
### 键值存储框架
### 使用:
#### 1.导入库
```
implementation 'com.github.weiwangshuai:SPUtil:1.0.2'
annotationProcessor 'com.github.weiwangshuai.SPUtil:sputil_compiler:1.0.2'
```

```
android{
    compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
}
```
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
#### 2.实现IKeyValueStorageModule接口，并添加@KeyValueStorageModule注解，存储相关代码可接口回调里完成，这里是使用腾讯的键值存储框架MMKV
```
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
```
#### 3.创建一个模型
```
@Storage
public class User {
    public String username;
}
```
#### 4.Rebuiled Project，重新构建你的项目时，被@Storage标记的模型里的所有属性会创建相关代码管理你的数据模型
```
public class UserStorage {
  public static String getUsername() {
    return (String)new MyKeyValueStorage().get("com.bhj.sputil.User_username",String.class,null);
  }

  public static String getUsername(String defaultValue) {
    return (String)new MyKeyValueStorage().get("com.bhj.sputil.User_username",String.class,defaultValue);
  }

  public static void putUsername(String username) {
    new MyKeyValueStorage().put("com.bhj.sputil.User_username",username,String.class);
  }
}
```
