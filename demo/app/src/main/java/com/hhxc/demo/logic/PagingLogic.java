package com.hhxc.demo.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页逻辑
 */
public class PagingLogic<T> {

    private int p = 1;
    private int hasMore = 0;
    private boolean isLoading;

    private List<T> dataList = new ArrayList<>();

    private IGetNetworkData getNetworkData;

    public PagingLogic(IGetNetworkData getNetworkData) {
        this.getNetworkData = getNetworkData;
    }

    public int getCurPage() {
        return p;
    }

    public List<T> getAllData() {
        return dataList;
    }

    public void setLoading() {
        isLoading = true;
    }

    public void doRefresh() {
        if (isLoading) return;

        isLoading = true;
        getNetworkData.loadData(1);
    }

    public void doLoadMore() {
        if (isLoading) return;

        isLoading = true;
        getNetworkData.loadData(p);
    }

    public void onError() {
        isLoading = false;
    }

    public void onNewData(List<T> newData, int hasMore) {
        isLoading = false;
        if (p == 1)
            onRefreshFinished(newData);
        else
            onLoadMoreFinished(newData, hasMore);
    }

    private void onRefreshFinished(List<T> newData) {
        dataList.addAll(newData); // TODO
    }

    private void onLoadMoreFinished(List<T> newData, int hasMore) {
        this.hasMore = hasMore;
        if (newData != null && newData.size() > 0)
            dataList.addAll(newData); // TODO

    }

    public interface IGetNetworkData<T> {
        void loadData(int p);
    }

}
