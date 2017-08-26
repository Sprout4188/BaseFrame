package com.sprout.frame.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sprout.frame.baseframe.base.BasePermissionActivity;
import com.sprout.frame.baseframe.database.simple.User;
import com.sprout.frame.baseframe.database.simple.UserDao;
import com.sprout.frame.baseframe.entity.LoginEntity;
import com.sprout.frame.baseframe.event.NetworkStatusChangeEvent;
import com.sprout.frame.baseframe.http.HttpResult;
import com.sprout.frame.baseframe.http.InterceptorUtil;
import com.sprout.frame.baseframe.httpAction.LoginAction;
import com.sprout.frame.baseframe.lifecycle.EventBusLifecycle;
import com.sprout.frame.baseframe.utils.LogUtil;
import com.sprout.frame.baseframe.widgets.nicetoast.Toasty;
import com.sprout.frame.baseframe.widgets.progressbar.CircleProgressBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class MainActivity extends BasePermissionActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        addLifecycle(new EventBusLifecycle(this));
        setTitle("还车");
        userDao = new UserDao();
    }

    @OnClick(R.id.btTestNet)
    public void clickBt1() {
        new LoginAction()
                .addPara("18523641110", "123456")
                .addInterceptor(InterceptorUtil.buildCircleProgressbar(new CircleProgressBar(this, "加载中")))
                .onSuccess(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult result) {
                        LoginEntity entity = (LoginEntity) result.getEntity();
                        String[] permissions = new String[]{P_AUDIO, P_CAMERA, P_CONTACTS_GET};
                        requestPermissions(permissions, new OnPermissionResult() {
                            @Override
                            public void onPermissionResult(boolean isPermit) {
                                if (isPermit) {
                                    LogUtil.debug("申请权限成功");
                                } else {
                                    LogUtil.debug("申请权限失败");
                                }
                            }
                        });
                    }
                }).onFailToast(this).execute();
    }

    @OnClick(R.id.btTestDB)
    public void clickBt2() {

    }

    @BindView(R.id.tvInsertSingle)
    TextView tvInsertSingle;
    @BindView(R.id.tvInsertMulti)
    TextView tvInsertMulti;
    @BindView(R.id.tvDeleteSingle)
    TextView tvDeleteSingle;
    @BindView(R.id.tvDeleteMulti)
    TextView tvDeleteMulti;
    @BindView(R.id.tvDeleteAll)
    TextView tvDeleteAll;
    @BindView(R.id.tvUpdateSingle)
    TextView tvUpdateSingle;
    @BindView(R.id.tvUpdateMulti)
    TextView tvUpdateMulti;
    @BindView(R.id.tvQuerySingle)
    TextView tvQuerySingle;
    @BindView(R.id.tvQueryMulti)
    TextView tvQueryMulti;
    @BindView(R.id.tvQueryAll)
    TextView tvQueryAll;

    @BindView(R.id.etDeleteSingle)
    EditText etDeleteSingle;
    @BindView(R.id.etDeleteMulti)
    EditText etDeleteMulti;
    @BindView(R.id.etUpdateSingle)
    EditText etUpdateSingle;
    @BindView(R.id.etUpdateMulti)
    EditText etUpdateMulti;
    @BindView(R.id.etQuerySingle)
    EditText etQuerySingle;
    @BindView(R.id.etQueryMulti)
    EditText etQueryMulti;

    private int uid = 1;

    @OnClick({R.id.btInsertSingle, R.id.btInsertMulti, R.id.btDeleteSingle, R.id.btDeleteMulti, R.id.btDeleteAll,
            R.id.btUpdateSingle, R.id.btUpdateMulti, R.id.btQuerySingle, R.id.btQueryMulti, R.id.btQueryAll})
    public void click(View view) {

        switch (view.getId()) {
            case R.id.btInsertSingle:   //增单行
                User user1 = new User();
                user1.setUid(uid);
                user1.setUname("wfh" + uid);
                user1.setUaddress("chengdu" + uid);
                userDao.insert(user1);
                tvInsertSingle.setText(user1.toString());
                break;
            case R.id.btInsertMulti:    //增多行
                List<User> list = new ArrayList<>();
                User user2 = new User();
                user2.setUid(uid);
                user2.setUname("wfh" + uid);
                user2.setUaddress("chengdu" + uid);
                list.add(user2);
                uid++;
                User user3 = new User();
                user3.setUid(uid);
                user3.setUname("wfh" + uid);
                user3.setUaddress("chengdu" + uid);
                list.add(user3);
                uid++;
                userDao.insert(list);
                tvInsertMulti.setText(list.toString());
                break;
            case R.id.btDeleteSingle:   //删单行
                String id1 = etDeleteSingle.getText().toString().trim();
                User query = userDao.query(id1);
                if (query != null) tvDeleteSingle.setText(query.toString());
                userDao.delete(id1);
                break;
            case R.id.btDeleteMulti:    //删多行
                String[] id2 = etDeleteMulti.getText().toString().trim().split(",");
                List<User> query1 = userDao.query(id2);
                if (query1 != null) tvDeleteMulti.setText(query1.toString());
                userDao.delete(id2);
                break;
            case R.id.btDeleteAll:      //删所有
                List<User> list2 = userDao.queryAll();
                if (list2 != null) tvDeleteAll.setText(list2.toString());
                userDao.deleteAll();
                break;
            case R.id.btUpdateSingle:   //改单行
                String id3 = etUpdateSingle.getText().toString().trim();
                User user4 = new User();
                user4.setUid(Integer.valueOf(id3));
                user4.setUname("wfh" + id3 + "改");
                user4.setUaddress("chengdu" + id3 + "改");
                userDao.update(user4);
                tvUpdateSingle.setText(user4.toString());
                break;
            case R.id.btUpdateMulti:    //改多行
                List<User> list1 = new ArrayList<>();
                String[] id4 = etUpdateMulti.getText().toString().trim().split(",");
                User user5 = new User();
                user5.setUid(Integer.valueOf(id4[0]));
                user5.setUname("wfh" + id4[0] + "改");
                user5.setUaddress("chengdu" + id4[0] + "改");
                list1.add(user5);
                User user6 = new User();
                user6.setUid(Integer.valueOf(id4[1]));
                user6.setUname("wfh" + id4[1] + "改");
                user6.setUaddress("chengdu" + id4[1] + "改");
                list1.add(user6);
                userDao.update(list1);
                tvUpdateMulti.setText(list1.toString());
                break;
            case R.id.btQuerySingle:    //查单行
                String id5 = etQuerySingle.getText().toString().trim();
                User user7 = userDao.query(id5);
                if (user7 != null) tvQuerySingle.setText(user7.toString());
                break;
            case R.id.btQueryMulti:     //查多行
                String[] id6 = etQueryMulti.getText().toString().trim().split(",");
                List<User> user8 = userDao.query(id6);
                if (user8 != null) tvQueryMulti.setText(user8.toString());
                break;
            case R.id.btQueryAll:       //查所有
                LogUtil.debug("数据总数 = " + userDao.getCount());
                List<User> list3 = userDao.queryAll();
                if (list3 != null) tvQueryAll.setText(list3.toString());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetChange(NetworkStatusChangeEvent event) {
        Toasty.warning(this, "hahahaha").show();
    }
}
