package me.walkonly.lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseAdapterForListView<VH extends BaseAdapterForListView.ViewHolder> extends BaseAdapter {

    protected Context context;
    protected List<?> dataList;

    public BaseAdapterForListView(Context context, List<?> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void updateData(List<?> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getItemLayout(), parent, false);;
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }

        onBindViewHolder(viewHolder, position, dataList);

        return convertView;
    }

    public abstract int getItemLayout();

    public abstract VH onCreateViewHolder(View view);

    public abstract void onBindViewHolder(VH viewHolder, int position, List<?> dataList);

    public static abstract class ViewHolder {
        public final View itemView;

        public ViewHolder(View itemView) {
            if (itemView == null)
                throw new IllegalArgumentException("itemView is null");
            this.itemView = itemView;
        }

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
