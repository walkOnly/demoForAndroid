package me.walkonly.lib.annotation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import me.walkonly.lib.fragment.BaseFragment;

public class FragmentAnnotationConfig extends BaseAnnotationConfig {

    public int layoutId; // @C_Fragment
    public int titleId; // @C_Title
    public boolean eventBus; // @C_EventBus

    private static final FragmentAnnotationConfig NONE = new FragmentAnnotationConfig(); // 表示某个类无注解

    private static final Map<String, FragmentAnnotationConfig> map = new HashMap<>(); // 避免重复解析注解

    public static FragmentAnnotationConfig getConfig(Class<? extends BaseFragment> clazz) {
        FragmentAnnotationConfig ret = map.get(clazz.getName());
        if (ret == null) {
            FragmentAnnotationConfig temp = parseAnnotation(clazz);
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

    private static FragmentAnnotationConfig parseAnnotation(Class<? extends BaseFragment> clazz) {
        Annotation a1 = clazz.getAnnotation(C_Fragment.class);
        Annotation a2 = clazz.getAnnotation(C_Title.class);
        Annotation a3 = clazz.getAnnotation(C_EventBus.class);

        if (a1 == null) return null;

        FragmentAnnotationConfig config = new FragmentAnnotationConfig();

        if (a1 != null) {
            C_Fragment c = (C_Fragment) a1;
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
