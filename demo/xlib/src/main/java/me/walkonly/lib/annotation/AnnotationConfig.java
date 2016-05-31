package me.walkonly.lib.annotation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class AnnotationConfig {

    public int titleId;
    public int activityLayoutId;
    public int fragmentLayoutId;
    public boolean eventBus;

    public static final AnnotationConfig NONE = new AnnotationConfig(); // 表示某个类无注解

    // 避免重复解析注解
    private static final Map<String, AnnotationConfig> map = new HashMap<>();

    public static AnnotationConfig getConfig(Class clazz) {
        return map.get(clazz.getName());
    }

    public static void setConfig(Class clazz, AnnotationConfig config) {
        map.put(clazz.getName(), config);
    }

    public static AnnotationConfig parseAnnotation(Class clazz) {
        Annotation a1 = clazz.getAnnotation(C_Activity.class);
        Annotation a2 = clazz.getAnnotation(C_Fragment.class);
        Annotation a3 = clazz.getAnnotation(C_EventBus.class);

        if (a1 == null && a2 == null && a3 == null)
            return null;

        AnnotationConfig config = new AnnotationConfig();

        if (a1 != null) {
            C_Activity c = (C_Activity) a1;
            config.activityLayoutId = c.value();
            //config.titleId = c.titleId();
        }

        if (a2 != null) {
            C_Fragment c = (C_Fragment) a1;
            config.fragmentLayoutId = c.value();
        }

        if (a3 != null) {
            config.eventBus = true;
        }

        return config;
    }

//    public static class C_Activity {
//        int value;
//        int titleId;
//    }

//    public static class C_Fragment {
//        int value;
//    }

//    public static class C_EventBus {
//        int value;
//    }

}
