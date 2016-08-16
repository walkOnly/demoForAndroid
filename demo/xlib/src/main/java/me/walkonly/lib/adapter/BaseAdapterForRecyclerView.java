package me.walkonly.lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapterForRecyclerView<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context context;
    protected List<?> dataList;
    private OnItemClickListener mOnItemClickListener;

    public BaseAdapterForRecyclerView(Context context, List<?> dataList) {
        this.context = context;
        this.dataList = dataList;
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
        View view = LayoutInflater.from(context).inflate(getItemLayout(), parent, false);;
        return onCreateViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(final VH viewHolder, final int position) {
        onBindViewHolder2(viewHolder, position, dataList);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(viewHolder.itemView, position);
            }
        });
    }

    public abstract int getItemLayout();

    public abstract VH onCreateViewHolder2(View view);

    public abstract void onBindViewHolder2(VH viewHolder, int position, List<?> dataList);

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public static void showView(View v) {
        if (v != null)
            v.setVisibility(View.VISIBLE);
    }

    public static void goneView(View v) {
        if (v != null)
            v.setVisibility(View.GONE);
    }

}
