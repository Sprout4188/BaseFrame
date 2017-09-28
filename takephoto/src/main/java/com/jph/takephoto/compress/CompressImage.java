package com.jph.takephoto.compress;

import com.jph.takephoto.model.TImage;
import java.util.ArrayList;

/**
 * Create by Sprout at 2017/7/17
 * 压缩照片
 */
public interface CompressImage {
    void compress();

    /**
     * 压缩结果监听器
     */
    interface CompressListener {
        /**
         * 压缩成功
         * @param images 已经压缩图片
         */
        void onCompressSuccess(ArrayList <TImage> images);

        /**
         * 压缩失败
         * @param images 压缩失败的图片
         * @param msg 失败的原因
         */
        void onCompressFailed(ArrayList <TImage> images,String msg);
    }
}
