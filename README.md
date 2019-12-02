
# SPUtil 
### 数据存储工具类,支部String,float,int,long,boolean数据类型
### 使用:
#### 1.导入库，暂时不支持AndroidX
```
implementation 'com.github.weiwangshuai:SPUtil:1.0.0'
annotationProcessor 'com.github.weiwangshuai.SPUtil:sputil_compiler:1.0.0'
```

```
android{
.....
    defaultConfig｛
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    ｝
}
```
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
#### 2.注册
```
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SPUtils.init(this);
    }
}
```
#### 3.以储存userName为例，在类前面加入@SharedPreferences
```
@SharedPreferences
public class User {
    public String userName;
}
```
#### 4.Rebuiled Project,重新构建你的项目时，会自动生成储存的相关代码
```
public class SPUser {
  public static String getUserName() {
    return SPUtils.getInstance().getString("User_userName");
  }

  public static void putUserName(String userName) {
    SPUtils.getInstance().put("User_userName",userName);
  }
}
```
