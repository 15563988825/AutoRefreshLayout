package com.fiate.autorefresh;

/**
 * Created by wangyunxiu on 16/2/4.
 */
public interface OnAotoRefreshListener {
    public void OnRefreshStart(AutoRefreshLayout autoRefreshLayout);
    public void OnRefreshSucceed(AutoRefreshLayout autoRefreshLayout);
    public void OnRefreshFaild(AutoRefreshLayout autoRefreshLayout);

}
