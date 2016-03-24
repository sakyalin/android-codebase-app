package com.linxinzhe.android.codebaseapp.util;

import android.app.Activity;

import java.util.Stack;

/**
 * @author linxinzhe
 */
public class ActivityHelper {

    private static Stack<Activity> activities;

    private static ActivityHelper ActivityHelper;

    private ActivityHelper() {
    }

    public static ActivityHelper instance() {
        if (ActivityHelper == null) {
            ActivityHelper = new ActivityHelper();
        }
        return ActivityHelper;
    }

    public void add(Activity activity) {
        if (activities == null) {
            activities = new Stack<>();
        }
        activities.add(activity);
    }

    public void remove(Activity activity) {
        if (activities != null)
            activities.remove(activity);
    }

    public void finish(Class<? extends Activity> cls) {
        if (activities == null) {
            return;
        }
        for (int i = 0; i < activities.size(); i++) {
            Activity activity1 = activities.get(i);
            if (activity1.getClass().equals(cls)) {

                if (!activity1.isFinishing()) {
                    activity1.finish();
                }
                activities.remove(activity1);
                return;
            }
        }
    }

    public void finishAllBut(Class<? extends Activity> cls) {
        if (activities == null)
            return;
        for (int i = 0; i < activities.size(); i++) {
            Activity activity1 = activities.get(i);
            if (!activity1.getClass().equals(cls)) {
                i--;
                if (!activity1.isFinishing()) {
                    activity1.finish();

                }
                activities.remove(activity1);
            }
        }
    }
}
