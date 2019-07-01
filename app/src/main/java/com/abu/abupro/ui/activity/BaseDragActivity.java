package com.abu.abupro.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.abu.abupro.ItemDragHelperCallback;
import com.abu.abupro.R;
import com.abu.abupro.adapter.ChannelAdapter;
import com.abu.abupro.data.model.ChannelEntity;

import java.util.List;

import butterknife.BindView;

public abstract class BaseDragActivity extends BaseActivity {
    private GridLayoutManager mManager;
    public ChannelAdapter mAdapter;
    private List<ChannelEntity> items;
    private List<ChannelEntity> otherItems;

    @BindView(R.id.recy)
    RecyclerView mRecyclerView;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_demo;
    }

    @Override
    public void init() {
        mManager=getLayoutManager();
        mRecyclerView.setLayoutManager(mManager);
        items=getItems();
        otherItems=getOtherItems();

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
        mAdapter= new ChannelAdapter(this, helper, items, otherItems);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(BaseDragActivity.this, items.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public GridLayoutManager getLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = mAdapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        return manager;
    }

    public abstract List<ChannelEntity> getItems();

    public abstract List<ChannelEntity> getOtherItems();
}
