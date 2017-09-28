package com.jph.takephoto.model;

import java.io.Serializable;

/**
 * Create by Sprout at 2017/7/17
 * 配置是否使用自带的相册选图(选多图时默认使用)
 * 配置拍照后是否纠正角度
 */
public class TakePhotoOptions implements Serializable {
    /**
     * 是否使用TakePhoto自带的相册进行图片选择，默认不使用，但选择多张图片会使用
     */
    private boolean withOwnGallery;
    /**
     * 拍照后是否纠正角度
     */
    private boolean correctImage;

    private TakePhotoOptions() {
    }

    public boolean isWithOwnGallery() {
        return withOwnGallery;
    }

    public void setWithOwnGallery(boolean withOwnGallery) {
        this.withOwnGallery = withOwnGallery;
    }

    public boolean isCorrectImage() {
        return correctImage;
    }

    public void setCorrectImage(boolean correctImage) {
        this.correctImage = correctImage;
    }

    public static class Builder {
        private TakePhotoOptions options;

        public Builder() {
            this.options = new TakePhotoOptions();
        }

        public Builder setWithOwnGallery(boolean withOwnGallery) {
            options.setWithOwnGallery(withOwnGallery);
            return this;
        }

        public Builder setCorrectImage(boolean isCorrectImage) {
            options.setCorrectImage(isCorrectImage);
            return this;
        }

        public TakePhotoOptions create() {
            return options;
        }
    }
}
