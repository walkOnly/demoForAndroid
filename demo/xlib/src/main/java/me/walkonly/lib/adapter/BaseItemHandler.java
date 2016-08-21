package me.walkonly.lib.adapter;


import android.view.View;

import java.util.List;

public abstract class BaseItemHandler <VH extends RecyclerView.ViewHolder> implements OnItemClickListener {

    public abstract int getItemLayout();

    public abstract VH onCreateViewHolder(View view);

    public abstract void onBindViewHolder(VH viewHolder, int position, List<?> dataList);

    @Override
    public void onItemClick(View view, int position, List<?> dataList) {
        // 使用 RecyclerView 的时候需要实现此方法
    }

}
