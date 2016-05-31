package me.walkonly.lib.annotation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import me.walkonly.lib.activity.BaseActivity;
import me.walkonly.lib.fragment.BaseFragment;

public class ActivityAnnotationConfig {

    public int layoutId; // @C_Activity
    public int titleId; // @C_Title
    public boolean eventBus; // @C_EventBus

    private static final ActivityAnnotationConfig NONE = new ActivityAnnotationConfig(); // 表示某个类无注解

    private static final Map<String, ActivityAnnotationConfig> map = new HashMap<>(); // 避免重复解析注解

    public static ActivityAnnotationConfig getConfig(Class<? extends BaseActivity> clazz) {
        ActivityAnnotationConfig ret = map.get(clazz.getName());
        if (ret == null) {
            ActivityAnnotationConfig temp = parseAnnotation(clazz);
            if (temp == null) {
                map.put(clazz.getName(), NONE);
                return null;
            } else {
                map.put(clazz.getName(), temp);
                return temp;
            }
        } else if (ret == NONE) {
            return null;
        }
        return ret;
    }

    private static ActivityAnnotationConfig parseAnnotation(Class<? extends BaseActivity> clazz) {
        Annotation a1 = clazz.getAnnotation(C_Activity.class);
        Annotation a2 = clazz.getAnnotation(C_Title.class);
        Annotation a3 = clazz.getAnnotation(C_EventBus.class);

        if (a1 == null) return null;

        ActivityAnnotationConfig config = new ActivityAnnotationConfig();

        if (a1 != null) {
            C_Activity c = (C_Activity) a1;
            config.layoutId = c.value();
        }

        if (a2 != null) {
            C_Title c = (C_Title) a2;
            config.titleId = c.value();
        }

        if (a3 != null) {
            config.eventBus = true;
        }

        return config;
    }

}
