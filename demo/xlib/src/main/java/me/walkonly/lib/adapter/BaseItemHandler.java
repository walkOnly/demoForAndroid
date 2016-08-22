package me.walkonly.lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseItemHandler <T, VH extends RecyclerView.ViewHolder> implements OnItemClickListener<T> {

    public abstract int getItemLayout();

    public abstract VH onCreateViewHolder(View view);

    public abstract void onBindViewHolder(VH viewHolder, int position, T item);

    @Override
    public void onItemClick(View view, int position, T item) {
        // 使用 RecyclerView 的时候需要实现此方法
    }

    public static void showView(View v) {
        if (v != null)
            v.setVisibility(View.VISIBLE);
    }

    public static void hideView(View v) {
        if (v != null)
            v.setVisibility(View.INVISIBLE);
    }

    public static void goneView(View v) {
        if (v != null)
            v.setVisibility(View.GONE);
    }

}
