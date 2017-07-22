package com.example.ckz.demo1.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by CKZ on 2017/2/12.
 */

public class SPUtils {
    public static SharedPreferences sp;
    public static SharedPreferences.Editor ed;

    public static String getStringSp(Context context, String key) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        String result = sp.getString(key, "");
        return result;
    }

    public static long getlongSp(Context context, String key) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        long result = sp.getLong(key, 0);
        return result;
    }

    public static int getIntSp(Context context, String key) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        int result = sp.getInt(key, 0);
        return result;
    }

    public static boolean getBooleanSp(Context context, String key, boolean defaultValue) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean result = sp.getBoolean(key, defaultValue);
        return result;
    }

    @SuppressLint("CommitPrefEdits")
    public static void putStringSp(Context context, String key, String value) {
        ed = PreferenceManager.getDefaultSharedPreferences(context).edit();
        ed.putString(key, value);
        ed.commit();
    }

    public static void putlongSp(Context context, String key, long value) {
        ed = PreferenceManager.getDefaultSharedPreferences(context).edit();
        ed.putLong(key, value);
        ed.commit();
    }

    public static void putIntSp(Context context, String key, int value) {
        ed = PreferenceManager.getDefaultSharedPreferences(context).edit();
        ed.putInt(key, value);
        ed.commit();
    }

    public static void putBooleanSp(Context context, String key, boolean value) {
        ed = PreferenceManager.getDefaultSharedPreferences(context).edit();
        ed.putBoolean(key, value);
        ed.commit();
    }

    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    public static void putBean(Context context, String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                ed = PreferenceManager.getDefaultSharedPreferences(context).edit();
                ed.putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public static Object getBean(Context context, String key) {
        Object obj = null;
        try {
            String base64 = PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;


    }
}
