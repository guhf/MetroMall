package com.metro.metromall.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * 文件命名工具类
 * Created by guhf on 2017/12/13.
 */

public class NamedUtil {
    //通过UUID生成字符串文件名
    public static String getUUIDName() {
        String name = UUID.randomUUID().toString();
        return name;
    }

    //通过Random()类生成数组命名
    public static String getRandomName() {
        Random random = new Random();
        String name = String.valueOf(random.nextInt(Integer.MAX_VALUE));
        return name;
    }

    //根据时间命名
    public static String getDateName() {
        Calendar now = new GregorianCalendar();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String name = simpleDate.format(now.getTime());
        return name;
    }

    /**
     * 将字符串转化为MD5命名
     * @param string 需要转换的字符串
     * @return 字符串对应的MD5值
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        String name = hex.toString();
        return name;
    }
}
