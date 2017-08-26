# BaseFrame
  The base frame for developing Android easily.
  
# Des
  This frame encapsulates the basic functionality like network framework which we use okhttp, The running permission check for Android      6.0, and so on.
  
# Features
  * Encapsulates OkHttp. (include Loading Interceptor)
  * Customed Gson for parseing null String which responsed by server.
  * Create lifecycle manager to manage whatever has lifecycles.
  * Encapsulates SharedPreferences.
  * Integrate Encryption and Decryption module
  * Integrate immersive statusbar
  * Include RxAndroid, ButterKnife, EventBus, NiceToasty, ActivityManager
  
# Sample

## request server(网络请求)

* Create yourclass extents HttpAction<E> like:
  
```
public class LoginAction extends HttpAction<LoginEntity> {
    public LoginAction() {
        super(Api.API_LOGIN);
    }
    public LoginAction addPara(String username, String password) {
        add("username", encryUsername);
        add("password", encryPassword);
        return this;
    }
    @Override
    public LoginEntity decodeModel(String response, HttpResult<LoginEntity> result, Gson gson) {
        return gson.fromJson(response, LoginEntity.class);
    }
}
```

* Use the class you created in the first step:

```
new LoginAction()
    .addPara("18588886666", "123456")
    .addInterceptor(InterceptorUtil.buildCircleProgressbar(new CircleProgressBar(this, "I'm loading, please wait!")))
    .onSuccess(new Action1<HttpResult>() {
        @Override
        public void call(HttpResult result) {
            LoginEntity entity = (LoginEntity) result.getEntity();
        }
    }).onFailToast(this).execute();
```
        
## running permission(动态权限)

* just extends BasePermissionActivity instead extends BaseActivity

```public class MainActivity extends BasePermissionActivity {```  

* and use like this:

```
String[] permissions = new String[]{P_AUDIO, P_CAMERA, P_CONTACTS_GET};
queryPermissions(permissions, new OnPermissionResult() {
    @Override
    public void onPermissionResult(boolean isPermit) {
        if (isPermit) {
            LogUtil.debug("Query My Permissions Success");
        } else {
            LogUtil.debug("Query My Permissions Fail");
        }
    }
});
```

## ORM(数据库)
* create java bean which will be stored in sqlite like this
```
@Table(name = "user")   //指定表名
public class User {

    @Column(name = "_id", primaryKey = true)  //指定列名(最多可有一个主键, 也可以不指定)
    private int uid;

    @Column(name = "name")
    private String uname;

    @Column(name = "addr")
    private String uaddress;

    //getter and setter method
}
```
* create class extends BaseDao
```
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    @Override
    public long insert(User user) {
        SQLiteDatabase db = getDB();
        ContentValues values = new ContentValues();
        values.put("_id", user.getUid());
        values.put("name", user.getUname());
        values.put("addr", user.getUaddress());
        long count = db.insert(tableName, null, values);
        db.close();
        return count;
    }

    @Override
    public int delete(String rowID) {
        //删
    }

    @Override
    public int update(User user) {
        //改
    }

    @Override
    public User query(String rowID) {
        //查
    }

    @Override
    public List<User> queryAll() {
        //查(所有)
    }
}
```
* and use like this:
```
UserDao userDao = new UserDao();
User user = new User();
user.setUname("sprout" + uid);
user.setUaddress("chengdu" + uid);
userDao.insert(user);
```
