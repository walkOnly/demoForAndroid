package me.walkonly.lib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseAdapterForListView extends BaseAdapter {

    protected Context context;
    protected List<?> dataList;

    public BaseAdapterForListView(Context context, List<?> dataList) {
        this.context = context;
        this.dataList = dataList;
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
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = onCreateViewHolder(parent, 0);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        onBindViewHolder(viewHolder, position);

        return viewHolder.itemView;
    }

    //@Override
    public abstract ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType);

    //@Override
    public abstract void onBindViewHolder(ViewHolder viewHolder, int position);

    public static abstract class ViewHolder {
        public final View itemView;

        public ViewHolder(View itemView) {
            if (itemView == null)
                throw new IllegalArgumentException("itemView is null");
            this.itemView = itemView;
        }

    }

}
