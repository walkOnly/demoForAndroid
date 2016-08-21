package me.walkonly.lib.adapter;

import android.view.View;

import java.util.List;

public interface OnItemClickListener {

    void onItemClick(View view, int position, List<?> dataList);

}
