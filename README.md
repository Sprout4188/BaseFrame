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
  ## request server
    * Create yourclass extents HttpAction<E> like:
  `public class LoginAction extends HttpAction<LoginEntity> {

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
    }`
 
    * Use the class you created in the first step:
    `new LoginAction()
        .addPara("18523641110", "123456")
        .addInterceptor(InterceptorUtil.buildCircleProgressbar(new CircleProgressBar(this, "加载中")))
        .onSuccess(new Action1<HttpResult>() {
            @Override
            public void call(HttpResult result) {
                LoginEntity entity = (LoginEntity) result.getEntity();
            }
        }).onFailToast(this).execute();`
  ## running permission
    * just extends BasePermissionActivity instead extends BaseActivity
    `public class MainActivity extends BasePermissionActivity {`
    * and use like this:
    `String[] permissions = new String[]{P_AUDIO, P_CAMERA, P_CONTACTS_GET};
     queryPermissions(permissions, new OnPermissionResult() {
         @Override
         public void onPermissionResult(boolean isPermit) {
             if (isPermit) {
                 LogUtil.debug("申请权限成功");
             } else {
                 LogUtil.debug("申请权限失败");
             }
         }
     });`
  
