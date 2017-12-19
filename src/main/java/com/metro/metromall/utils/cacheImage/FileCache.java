package com.metro.metromall.utils.cacheImage;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by guhf on 2017/11/24.
 * 在磁盘中缓存图片(二级缓存)
 */

public class FileCache {
    //缓存文件目录
    private File mCacheDir;

    /**
     * 创建缓存文件目录，如果有SD卡，则使用SD，如果没有则使用系统自带缓存目录
     *
     * @param context
     * @param cacheDir 图片缓存的一级目录
     */
    public FileCache(Context context, File cacheDir, String dir) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            mCacheDir = new File(cacheDir, dir);
        } else {
            mCacheDir = context.getCacheDir();
        }// 如何获取系统内置的缓存存储路径
        if (!mCacheDir.exists()) {
            mCacheDir.mkdirs();
        }
    }

    public File getFile(String url) {
        File f = null;
        try {
            //对url进行编辑，解决中文路径问题
            String filename = URLEncoder.encode(url, "utf-8");
            f = new File(mCacheDir, filename);
            try {
                f.createNewFile();//必须执行,否则创建文件失败
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return f;
    }

    public void clear() {//清除缓存文件
        File[] files = mCacheDir.listFiles();
        for (File f : files) f.delete();
    }
}
