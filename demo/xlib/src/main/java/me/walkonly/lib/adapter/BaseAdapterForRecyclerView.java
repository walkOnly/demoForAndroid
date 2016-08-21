package me.walkonly.lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapterForRecyclerView<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context context;
    private List<?> dataList;
    private BaseItemHandler itemHandler;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            itemHandler.onItemClick(v, getAdapterPosition(), dataList);
        }
    };

    public BaseAdapterForRecyclerView(Context context, List<?> dataList, BaseItemHandler itemHandler) {
        this.context = context;
        this.dataList = dataList;
        this.itemHandler = itemHandler;
    }

    public void updateData(List<?> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(itemHandler.getItemLayout(), parent, false);
        view.setOnClickListener(mOnClickListener);
        return itemHandler.onCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        itemHandler.onBindViewHolder(viewHolder, position, dataList);
    }

}
