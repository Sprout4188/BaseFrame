# BaseFrame
  The base frame for developing Android easily.
  
# Des
  This frame encapsulates the basic functionality like network framework which we use okhttp, The running permission check for Android      6.0, ORM, take picture from camera or gallery or file system, and so on.
  
# Features
  * Encapsulates OkHttp. (include Loading Interceptor)
  * Customed Gson for parseing null String which responsed by server.
  * Create lifecycle manager to manage whatever has lifecycles.
  * Encapsulates SharedPreferences.
  * Integrate Encryption and Decryption module
  * Integrate immersive statusbar
  * Integrate ORM
  * Include RxAndroid, ButterKnife, EventBus, NiceToasty, ActivityManager
  * Take picture from camera or gallery or file system with crop and compress options
  
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

## take picture(选择图片)
### use by activity
* create your own activity extends TakePhotoActivity and use like this:
```
/**
 * 拍照和选择照片示例, 需要继承TakePhotoActivity
 */
public class CaptureSimpleActivity extends TakePhotoActivity {

    @BindView(R.id.llShowImages)
    LinearLayout llShow;

    private TakePhoto takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_capture_simple);
        setTitle("拍照和选择照片示例");
        init();
    }

    private void init() {
        takePhoto = getTakePhoto();
        //配置选图工具和拍照后是否纠正角度
        TakePhotoOptions options = new TakePhotoOptions.Builder()
                .setWithOwnGallery(true)    //是否使用TakePhoto自带的相册进行图片选择，默认不使用，但选择多张图片会使用
                .setCorrectImage(true)      //拍照后是否纠正角度
                .create();
        takePhoto.setTakePhotoOptions(options);
        //不压缩
//        takePhoto.onEnableCompress(null, false);
        //开启压缩
        takePhoto.onEnableCompress(getCompressOptions(false), false);
    }

    @OnClick({R.id.btTestCapture, R.id.btTestSelect})
    public void click(View view) {
        //创建拍照或裁剪后的图片存储路径
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri outUri = Uri.fromFile(file);

        switch (view.getId()) {
            case R.id.btTestCapture:    //从相机拍照
                //从相机拍照, 不裁剪
//                takePhoto.onPickFromCapture(outUri);
                //从相机拍照, 并裁剪
                takePhoto.onPickFromCaptureWithCrop(outUri, getCropOptions(true));
                break;
            case R.id.btTestSelect:     //从相册或文件选图
                //从相册选图, 不裁剪
//                takePhoto.onPickFromGallery();
                //从相册选图, 并裁剪
                takePhoto.onPickFromGalleryWithCrop(outUri, getCropOptions(true));
                //从文件选图, 不裁剪
//                takePhoto.onPickFromDocuments();
                //从文件选图, 并裁剪
//                takePhoto.onPickFromCaptureWithCrop(outUri, getCropOptions(true));
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ArrayList<TImage> images = result.getImages();
        showImg(images);

        //可从TResult中获取如下信息
        for (int i = 0; i < images.size(); i++) {
            String originalPath = images.get(i).getOriginalPath();
            String compressPath = images.get(i).getCompressPath();
            TImage.FromType type = images.get(i).getFromType();
            boolean cropped = images.get(i).isCropped();
            boolean compressed = images.get(i).isCompressed();
            LogUtil.debug("原图或裁剪路径 = " + originalPath);  //若裁剪, 则表示裁剪路径; 若不裁剪, 则表示原图路径
            LogUtil.debug("压缩路径 = " + compressPath);       //若不压缩, 则为null
            LogUtil.debug("图片来源 = " + (type == TImage.FromType.CAMERA ? "相机" : "其它"));
            LogUtil.debug("是否裁剪 = " + cropped);
            LogUtil.debug("是否压缩 = " + compressed);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    /**
     * 获取裁剪配置
     */
    private CropOptions getCropOptions(boolean useOwn) {
        return new CropOptions.Builder()
                .setAspectX(480)        //裁剪框任意可变
                .setAspectY(320)
                .setOutputX(16)         //裁剪框固定比例
                .setOutputY(9)
                .setWithOwnCrop(useOwn) //是否使用TakePhoto自带的裁剪工具进行裁切(自带的裁剪小区域有一个放大的动画效果)
                .create();
    }

    /**
     * 获取压缩配置
     *
     * @param isLuban true鲁班压缩, false自带压缩
     */
    private CompressOptions getCompressOptions(boolean isLuban) {
        CompressOptions compressOptions;
        if (isLuban) {
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(480)
                    .setMaxWidth(320)
                    .setMaxSize(100 * 1024)
                    .create();
            compressOptions = CompressOptions.ofLuban(option);
        } else {
            compressOptions = new CompressOptions.Builder()
                    .setMaxPixel(480)           //长或宽不超过的最大像素,单位px
                    .setMaxSize(100 * 1024)     //压缩到的最大大小，单位B
                    .enablePixelCompress(true)  //是否开启像素压缩
                    .enableQualityCompress(true)//是否开启质量压缩
                    //.enableReserveRaw(true)   //是否保留原文件
                    .create();
        }
        compressOptions.enableReserveRaw(true); //是否保留原文件
        return compressOptions;
    }

    /**
     * 将选择的图片展示在界面上
     */
    private void showImg(List<TImage> images) {
        llShow.removeAllViews();

        for (int i = 0, j = images.size(); i < j - 1; i += 2) {
            View view = LayoutInflater.from(this).inflate(R.layout.image_show, null);
            ImageView imageView1 = (ImageView) view.findViewById(R.id.imgShow1);
            ImageView imageView2 = (ImageView) view.findViewById(R.id.imgShow2);
            if (images.get(i).isCompressed()) { //如果压缩, 则显示压缩后的图片
                Glide.with(this).load(new File(images.get(i).getCompressPath())).into(imageView1);
                Glide.with(this).load(new File(images.get(i + 1).getCompressPath())).into(imageView2);
            } else {                            //如果没压缩, 则显示原图或裁剪后的图片
                Glide.with(this).load(new File(images.get(i).getOriginalPath())).into(imageView1);
                Glide.with(this).load(new File(images.get(i + 1).getOriginalPath())).into(imageView2);
            }
            llShow.addView(view);
        }
        if (images.size() % 2 == 1) {
            View view = LayoutInflater.from(this).inflate(R.layout.image_show, null);
            ImageView imageView1 = (ImageView) view.findViewById(R.id.imgShow1);
            if ((images.get(images.size() - 1).isCompressed())) {
                Glide.with(this).load(new File(images.get(images.size() - 1).getCompressPath())).into(imageView1);
            } else {
                Glide.with(this).load(new File(images.get(images.size() - 1).getOriginalPath())).into(imageView1);
            }
            llShow.addView(view);
        }
    }
}
```
