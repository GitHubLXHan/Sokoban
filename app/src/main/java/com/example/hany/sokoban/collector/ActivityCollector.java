package com.example.hany.sokoban.collector;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 6小h
 * @e-mail 1026310040@qq.com
 * @date 2018/11/6 23:19
 * @filName ActivityCollector
 * @describe ...
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    /**
     * 添加活动
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 删除活动
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 关闭所有活动
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        Log.d("finishAll","yes");
        activities.clear();
    }

}
