package com.jph.takephoto.model;

import android.app.Activity;
import android.net.Uri;

import com.jph.takephoto.uitl.TImageFiles;
import com.jph.takephoto.uitl.TUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Sprout at 2017/7/17
 */
public class MultiCrop {
    private ArrayList<Uri> srcUris;     //待裁剪的图片路径集
    private ArrayList<Uri> outUris;     //裁剪后图片的存放路径集
    private ArrayList<TImage> tImages;  //裁剪后的图片信息集
    private TImage.FromType fromType;   //来源(相机或其它)
    public boolean hasFailed;           //是否有裁切失败的标识

    public static MultiCrop of(ArrayList<Uri> srcUris, Activity activity, TImage.FromType fromType) throws TException {
        return new MultiCrop(srcUris, activity, fromType);
    }

    public static MultiCrop of(ArrayList<Uri> srcUris, ArrayList<Uri> outUris, TImage.FromType fromType) {
        return new MultiCrop(srcUris, outUris, fromType);
    }

    private MultiCrop(ArrayList<Uri> srcUris, Activity activity, TImage.FromType fromType) throws TException {
        this.srcUris = srcUris;
        ArrayList<Uri> outUris = new ArrayList<>();
        for (Uri uri : srcUris) {
            outUris.add(Uri.fromFile(TImageFiles.getTempFile(activity, uri)));//生成临时裁切输出路径
        }
        this.outUris = outUris;
        this.tImages = TUtils.getTImagesWithUris(outUris, fromType);
        this.fromType = fromType;
    }

    private MultiCrop(ArrayList<Uri> srcUris, ArrayList<Uri> outUris, TImage.FromType fromType) {
        this.srcUris = srcUris;
        this.outUris = outUris;
        this.tImages = TUtils.getTImagesWithUris(outUris, fromType);
        this.fromType = fromType;
    }

    public ArrayList<Uri> getSrcUris() {
        return srcUris;
    }

    public void setSrcUris(ArrayList<Uri> srcUris) {
        this.srcUris = srcUris;
    }

    public ArrayList<Uri> getOutUris() {
        return outUris;
    }

    public void setOutUris(ArrayList<Uri> outUris) {
        this.outUris = outUris;
    }

    public ArrayList<TImage> gettImages() {
        return tImages;
    }

    public void settImages(ArrayList<TImage> tImages) {
        this.tImages = tImages;
    }

    /**
     * 为被裁切的图片设置已被裁切的标识
     *
     * @param uri 被裁切的图片
     * @return 该图片是否是最后一张
     */
    public Map setCropWithUri(Uri uri, boolean cropped) {
        if (!cropped) hasFailed = true;
        int index = outUris.indexOf(uri);
        tImages.get(index).setCropped(cropped);
        Map result = new HashMap();
        result.put("index", index);
        result.put("isLast", index == outUris.size() - 1 ? true : false);
        return result;
    }
}
