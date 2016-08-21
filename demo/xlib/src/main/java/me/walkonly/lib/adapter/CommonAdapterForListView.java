package me.walkonly.lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public final class CommonAdapterForListView<T, VH extends RecyclerView.ViewHolder> extends BaseAdapter {

    private Context context;
    private List<T> dataList;
    private BaseItemHandler<T, VH> itemHandler;

    public CommonAdapterForListView(Context context, List<T> dataList, BaseItemHandler<T, VH> itemHandler) {
        this.context = context;
        this.dataList = dataList;
        this.itemHandler = itemHandler;
    }

    public void updateData(List<T> dataList) {
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
            convertView = LayoutInflater.from(context).inflate(itemHandler.getItemLayout(), parent, false);;
            viewHolder = itemHandler.onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }

        itemHandler.onBindViewHolder(viewHolder, position, dataList.get(position));

        return convertView;
    }

}
