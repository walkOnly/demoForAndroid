package me.walkonly.lib.adapter;

import android.view.View;

public interface OnItemClickListener<T> {

    void onItemClick(View view, int position, T item);

}
