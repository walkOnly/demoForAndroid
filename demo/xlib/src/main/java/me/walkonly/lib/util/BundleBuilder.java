package me.walkonly.lib.util;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

public final class BundleBuilder {

    private Bundle mData = new Bundle();

    public BundleBuilder() { }

    public Bundle build() { return mData; }

    // boolean
    public BundleBuilder putBoolean(String name, boolean value) {
        mData.putBoolean(name, value);
        return this;
    }

    // int
    public BundleBuilder putInt(String name, int value) {
        mData.putInt(name, value);
        return this;
    }

    // long
    public BundleBuilder putLong(String name, long value) {
        mData.putLong(name, value);
        return this;
    }

    // float
    public BundleBuilder putFloat(String name, float value) {
        mData.putFloat(name, value);
        return this;
    }

    // double
    public BundleBuilder putDouble(String name, double value) {
        mData.putDouble(name, value);
        return this;
    }

    // String
    public BundleBuilder putString(String name, String value) {
        mData.putString(name, value);
        return this;
    }

    // Serializable
    public BundleBuilder putSerializable(String name, Serializable value) {
        mData.putSerializable(name, value);
        return this;
    }

    // Parcelable
    public BundleBuilder putParcelable(String name, Parcelable value) {
        mData.putParcelable(name, value);
        return this;
    }

    // 示例
    public static void main(String[] args) {
        Bundle b = new BundleBuilder()
                .putBoolean("1", true)
                .putInt("2", 20)
                .putString("3", "300")
                .build();
        System.out.println(b.toString());
    }

}
