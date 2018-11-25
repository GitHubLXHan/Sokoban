package com.example.hany.sokoban.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author 6小h
 * @e-mail 1026310040@qq.com
 * @date 2018/11/13 18:55
 * @filName ScreenSizeUtil
 * @describe ...
 */
public class ScreenSizeUtil {

    private static ScreenSizeUtil instance;
    private int screenWidth, screenHeight;

    /**
     * 单例模式
     * @param context
     * @return
     */
    public static ScreenSizeUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (ScreenSizeUtil.class) {
                if (instance == null) {
                    instance = new ScreenSizeUtil(context);
                }
            }
        }
        return instance;
    }

    /**
     * 构造函数
     * 获取屏幕的宽高
     * @param context
     */
    private ScreenSizeUtil(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕的宽度
     * @return
     */
    public int getScreenWidth() {
        return this.screenWidth;
    }

    /**
     * 获取屏幕的高度
     * @return
     */
    public int getScreenHeight() {
        return this.screenHeight;
    }

}
