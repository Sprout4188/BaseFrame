package com.sprout.frame.baseframe.widgets.progressbar;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.sprout.frame.baseframe.R;


/**
 * Create by Sprout at 2017/8/15
 */
public class CircleProgressBar extends BaseProgressbar {

    public CircleProgressBar(Activity mActivity, String msg) {
        super(mActivity);
        ImageView ivDes = (ImageView) baseView.findViewById(R.id.progress_view);
        TextView tvDes = (TextView) baseView.findViewById(R.id.progress_text);
        tvDes.setText(msg.trim());

        ViewAnimator
                .animate(ivDes)
                .rotation(360)
                .duration(500)
                .repeatCount(ValueAnimator.INFINITE)
                .repeatMode(ValueAnimator.RESTART)
                .start();
    }

    @Override
    public int loadXml() {
        return R.layout.progress_circle;
    }

    @Override
    public void onDismiss() {}
}
