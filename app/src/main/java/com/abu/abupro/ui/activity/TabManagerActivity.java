package com.abu.abupro.ui.activity;

import com.abu.abupro.adapter.ChannelAdapter;
import com.abu.abupro.data.ArticleDataSource;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.model.ArticleCollect;
import com.abu.abupro.data.model.ChannelEntity;
import com.abu.abupro.event.TabRefresh;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.google.gson.Gson;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TabManagerActivity extends BaseDragActivity {
    private List<ChannelEntity> items=new ArrayList<>();
    private List<ChannelEntity> otherItems=new ArrayList<>();
    private ArticleCollect list;

    @Inject
    ArticleDataSource mArticleDataSource;

    @Override
    public void init() {
        mArticleDataSource.getArticleCollectes(new LoadCallback<ArticleCollect>() {
            @Override
            public void onLoadSuccess(final List<ArticleCollect> list) {
                    parse(list.get(0));
            }

            @Override
            public void onLoadFailed(String errorMsg) {
            }
        });
        super.init();

        mAdapter.onRemove(new ChannelAdapter.RemoveInterface() {
            @Override
            public void onRemove(List<ChannelEntity> entities) {
                deleteCollections(entities);
            }
        });

    }

    public void parse(ArticleCollect list){
        this.list=list;
        try {
            JSONArray jsonArray=new JSONArray(list.getArticleCollect());
            ChannelEntity entity;
            for (int i=0;i<jsonArray.length();i++){
                entity=new ChannelEntity();
                entity.setName((String) jsonArray.get(i));
                items.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
    }

    public void deleteCollections(List<ChannelEntity> entities){
        List<String> list1=new ArrayList<>();
        for (int i=0;i<entities.size();i++){
                list1.add(entities.get(i).getName());
        }
        // 修改 content
        Gson gson = new Gson();
        String result = gson.toJson(list1);
        // 第一参数是 className,第二个参数是 objectId
        AVObject todo = AVObject.createWithoutData("ArticleCollect", list.getObjectId());

        // 修改 content
        todo.put("article_collect",result);
        // 保存到云端
        todo.saveInBackground(new com.avos.avoscloud.SaveCallback() {
            @Override
            public void done(AVException e) {
                EventBus.getDefault().post(new TabRefresh());
            }
        });
    }

    @Override
    public List<ChannelEntity> getItems() {
        return items;
    }

    @Override
    public List<ChannelEntity> getOtherItems() {
        for (int i = 0; i < 20; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("其他" + i);
            otherItems.add(entity);
        }
        return otherItems;
    }
}
