package com.abu.abupro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import com.abu.abupro.app.Constant;
import com.abu.abupro.widget.CardLoadingView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:44
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public abstract class BaseLoadingListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<T> mList;

    /**
     * 定义条目类型：普通条目和加载进度条
     */
    private static final int ITEM_TYPE_NORMAL = 0;
    private static final int ITEM_TYPE_LOADING = 1;


    public BaseLoadingListAdapter(Context context) {
        this(context, null);
    }

    public BaseLoadingListAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            return onCreateNormalViewHolder();
        } else {
            return onCreateLoadingViewHolder();
        }
    }

    @NonNull
    protected LoadingViewHolder onCreateLoadingViewHolder() {
        return new LoadingViewHolder(new CardLoadingView(mContext));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL) {
            onBindNormalViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //当数据集合大小小于默认大小10时，不显示进度条，条目类型均为正常条目
        if (mList.size() < Constant.DEFAULT_PAGE_SIZE) {
            return ITEM_TYPE_NORMAL;
        }

        if (position == getItemCount() - 1) {
            return ITEM_TYPE_LOADING;
        }else {
            return ITEM_TYPE_NORMAL;
        }
    }

    /**
     * 返回条目个数
     * @return 条目个数
     */
    @Override
    public int getItemCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        } else if (mList.size() < Constant.DEFAULT_PAGE_SIZE) {
            //如果数据集合大小小于默认大小10，则不显示进度条
            return mList.size();
        } else {
            //如果数据集合大小大于等于10，条目个数为数据集合大小 + 1（进度条）
            return mList.size() +1;
        }
    }

    /**
     * 进度条视图的ViewHolder
     */
    static class LoadingViewHolder extends RecyclerView.ViewHolder{

        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected Context getContext() {
        return mContext;
    }

    protected List<T> getDataList() {return mList;}

    /**
     * 更新数据集合
     * @param dataList 数据集合
     */
    public void replaceData(List<T> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 创建正常ViewHolder
     * @return 返回ViewHolder
     */
    abstract RecyclerView.ViewHolder onCreateNormalViewHolder();

    /**
     * 绑定ViewHolder时调用
     * @param holder ViewHolder
     * @param position item 位置
     */
    abstract void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position);


}
