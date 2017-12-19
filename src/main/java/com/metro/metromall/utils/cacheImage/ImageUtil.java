package com.metro.metromall.utils.cacheImage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by guhf on 2017/11/24.
 */

public class ImageUtil {
    /**
     * 从网络获取图片，并缓存在指定的文件中
     * @param url 图片url
     * @param file 缓存文件
     * @return
     */
    public static Bitmap loadBitmapFromWeb(String url, File file) {
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            is = conn.getInputStream();
            try{
                os = new FileOutputStream(file);
                copyStream(is, os);//将图片缓存到磁盘中
                bitmap = decodeFile(file);
            }catch (Exception ex){
                bitmap = BitmapFactory.decodeStream(is);
            }
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if(os != null) os.close();
                if(is != null) is.close();
                if(conn != null) conn.disconnect();
            } catch (IOException e) {    }
        }
    }

    public static Bitmap decodeFile(File f) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(f),null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
     * 得到图片字节流 数组大小
     * */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
    private  static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (;;) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
                //os.close();
                //os.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
