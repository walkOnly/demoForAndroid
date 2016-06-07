package com.hhxc.demo.event;

import java.util.ArrayList;
import java.util.List;

public class FinishActivityEvent {

    private List<String> classNameList = new ArrayList<>();

    public FinishActivityEvent() {

    }

    public void addItem(Class<?> clazz) {
        classNameList.add(clazz.getSimpleName());
    }

    public boolean contains(Class<?> clazz) {
        return classNameList.contains(clazz.getSimpleName());
    }

}
