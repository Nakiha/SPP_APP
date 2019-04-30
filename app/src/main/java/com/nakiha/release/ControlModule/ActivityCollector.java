package com.nakiha.release.ControlModule;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    private static List<Activity> sActivities = new ArrayList<>();

    /*Package*/static void addActivity(Activity activity) { sActivities.add(activity); }
    /*Package*/static void removeActivity(Activity activity) { sActivities.remove(activity); }

    /*Package*/static void finishAll(){
        for (Activity tempAty: sActivities
             ) {
            if (!tempAty.isFinishing()) tempAty.finish();
        }
        sActivities.clear();
    }
}
