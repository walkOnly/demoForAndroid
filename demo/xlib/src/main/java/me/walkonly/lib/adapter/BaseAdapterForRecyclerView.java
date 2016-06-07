package me.walkonly.lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapterForRecyclerView<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    protected Context context;
    protected List<?> dataList;

    public BaseAdapterForRecyclerView(Context context, List<?> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

//    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
//        mOnItemClickListener = listener;
//    }

}
