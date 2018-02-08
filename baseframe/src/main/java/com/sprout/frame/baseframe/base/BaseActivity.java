package com.sprout.frame.baseframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sprout.frame.baseframe.R;
import com.sprout.frame.baseframe.lifecycle.ButterKnifeLifecycle;
import com.sprout.frame.baseframe.lifecycle.ILifecycle;
import com.sprout.frame.baseframe.lifecycle.LifecycleManager;
import com.sprout.frame.baseframe.lifecycle.RxBusLifecycle;
import com.sprout.frame.baseframe.utils.statusbar.StatusBarUtil;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Create by Sprout at 2017/8/15
 */
public abstract class BaseActivity extends AppCompatActivity {
    public String TAG;

    private LinearLayout llRoot;    //根布局
    private View actionBar;         //标题栏布局
    private View contentView;       //内容布局
    private RelativeLayout rlBack;  //返回箭头
    private TextView tvTitle;       //标题

    private LifecycleManager manager = new LifecycleManager();

    protected void onCreate(@Nullable Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
//        //设置状态栏字体颜色为黑色
//        StatusBarUtil.statusBarLightMode(this);
        //设置状态栏背景颜色
        StatusBarUtil.setStatusBarColor(this, R.color.color_statusbar);
        //设置根布局
        setContentView(R.layout.activity_root);
        //设置标题栏
        llRoot = (LinearLayout) findViewById(R.id.ll_root);
        addActionBar(setMyActionBar());
        //设置内容布局
        addContentView(View.inflate(this, layoutId, null));

        addLifecycle(new ButterKnifeLifecycle(this));
        addLifecycle(new RxBusLifecycle(this));
        manager.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        manager.onShow();
    }

    @Override
    protected void onStop() {
        super.onStop();
        manager.onHide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.onDestory();
        if (!TextUtils.isEmpty(TAG)) OkHttpUtils.getInstance().cancelTag(TAG);
    }

    /**
     * 添加生命周期管理
     */
    protected void addLifecycle(ILifecycle lifecycle) {
        manager.add(lifecycle);
    }

    /**
     * 添加actionbar
     */
    private void addActionBar(View view) {
        if (llRoot != null && view != null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            actionBar = view;
            llRoot.addView(view, lp);
            //默认隐藏, 只有当setTitle()时才显示
            actionBar.setVisibility(View.GONE);
        }
    }

    /**
     * 添加contentview
     */
    private void addContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (llRoot != null && view != null) {
            contentView = view;
            llRoot.addView(view, lp);
        }
    }

    /**
     * 获取acitonbar
     */
    protected View setMyActionBar() {
        View view = getLayoutInflater().inflate(R.layout.actionbar_default_layout, null);
        rlBack = (RelativeLayout) view.findViewById(R.id.image_left);
        tvTitle = (TextView) view.findViewById(R.id.textView_title);

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClick();
            }
        });
        return view;
    }

    /**
     * 后退箭头点击事件, 默认finish当前界面
     */
    public void onBackClick() {
        finish();
    }

    /**
     * 设置title
     */
    public void setTitle(String title) {
        if (tvTitle != null && !TextUtils.isEmpty(title)) {
            actionBar.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        } else {
            actionBar.setVisibility(View.GONE);
        }
    }

    /**
     * 显示左边按钮
     */
    public void showBack() {
        if (rlBack != null) rlBack.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏后退箭头
     */
    public void hideBack() {
        if (rlBack != null) rlBack.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取根布局
     */
    public LinearLayout getMyRootView() {
        return llRoot;
    }

    /**
     * 获取actionbar
     */
    public View getMyActionBar() {
        return actionBar;
    }

    /**
     * 获取contentView
     */
    public View getMyContentView() {
        return contentView;
    }
}
