package com.jph.takephoto.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Create by Sprout at 2017/7/17
 * TakePhoto 操作成功返回的处理结果
 */
public class TImage implements Serializable {
    private String originalPath;    //原始路径或裁剪后的路径(若开启裁剪,则表示裁剪后的路径; 若不裁剪,则表示原图)
    private String compressPath;    //压缩路径(若未开启压缩, 则为null)
    private FromType fromType;      //来源(相机或其它)
    private boolean cropped;        //是否已被裁剪
    private boolean compressed;     //是否已被压缩

    public static TImage of(String path, FromType fromType) {
        return new TImage(path, fromType);
    }

    public static TImage of(Uri uri, FromType fromType) {
        return new TImage(uri, fromType);
    }

    private TImage(String path, FromType fromType) {
        this.originalPath = path;
        this.fromType = fromType;
    }

    private TImage(Uri uri, FromType fromType) {
        this.originalPath = uri.getPath();
        this.fromType = fromType;
    }

    /**
     * 获取原图路径或裁剪后的路径(若开启裁剪, 则表示裁剪后的路径; 若不裁剪, 则表示原图路径)
     */
    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    /**
     * 获取压缩后的路径(若未开启压缩, 则为null)
     */
    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    /**
     * 图片来源(相机或其它)
     */
    public FromType getFromType() {
        return fromType;
    }

    public void setFromType(FromType fromType) {
        this.fromType = fromType;
    }

    /**
     * 是否已被裁剪
     */
    public boolean isCropped() {
        return cropped;
    }

    public void setCropped(boolean cropped) {
        this.cropped = cropped;
    }

    /**
     * 是否已被压缩
     */
    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public enum FromType {CAMERA, OTHER}
}
