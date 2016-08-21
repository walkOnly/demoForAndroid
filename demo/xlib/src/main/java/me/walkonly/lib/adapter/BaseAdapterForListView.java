package me.walkonly.lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseAdapterForListView<VH extends RecyclerView.ViewHolder> extends BaseAdapter {

    private Context context;
    private List<?> dataList;
    private BaseItemHandler itemHandler;

    public BaseAdapterForListView(Context context, List<?> dataList, BaseItemHandler itemHandler) {
        this.context = context;
        this.dataList = dataList;
        this.itemHandler = itemHandler;
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
            convertView = LayoutInflater.from(context).inflate(itemHandler.getItemLayout(), parent, false);;
            viewHolder = itemHandler.onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }

        itemHandler.onBindViewHolder(viewHolder, position, dataList);

        return convertView;
    }

}
