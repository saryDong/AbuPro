package com.abu.abupro.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.abu.abupro.R;
import com.abu.abupro.app.Constant;
import com.abu.abupro.data.ArticleDataSource;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.SaveCallback;
import com.abu.abupro.data.model.Article;
import com.abu.abupro.data.model.ArticleCollect;
import com.abu.abupro.data.model.ChannelEntity;
import com.abu.abupro.data.model.User;
import com.abu.abupro.event.TabRefresh;
import com.abu.abupro.ui.MainActivity;
import com.abu.abupro.utils.RegexUtils;
import com.abu.abupro.widget.TagLayout;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ShareArticleActivity extends BaseActivity {

    @BindView(R.id.article_url)
    TextView mArticleUrl;
    @BindView(R.id.article_title)
    EditText mArticleTitle;
    @BindView(R.id.article_description)
    EditText mArticleDescription;
    @BindView(R.id.tag_layout)
    TagLayout mTagLayout;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;


    private String mTag = "Java";

    @Inject
    ArticleDataSource mArticleDataSource;

    List<String> title;

    ArticleCollect articleCollect;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_share_article;
    }

    @Override
    protected void init() {
        super.init();
        User user = AVUser.getCurrentUser(User.class);
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.share);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        mArticleUrl.setText(RegexUtils.matchShareUrl(text));

        requestTabList();
    }

    /**
     * 1，请求标签数据
     */
    private void requestTabList() {
        mArticleDataSource.getArticleCollectes(new LoadCallback<ArticleCollect>() {
            @Override
            public void onLoadSuccess(final List<ArticleCollect> list) {
                parse(list.get(0));
            }

            @Override
            public void onLoadFailed(String errorMsg) {
            }
        });
    }

    /**
     * 2，tab标签解析并显示
     *
     * @param list
     */
    public void parse(ArticleCollect list) {
        this.articleCollect = list;
        if (articleCollect == null) {
            articleCollect = new ArticleCollect();
            articleCollect.setArticleCollect("[\"java\",\"Java\"]");
        }
        Gson gson = new Gson();
        title = gson.fromJson(list.getArticleCollect(), new TypeToken<List<String>>() {
        }.getType());
        mTagLayout.setTags(title);
        mTagLayout.setOnTagSelectedListener(new TagLayout.OnTagSelectedListener() {
            @Override
            public void onTagSelected(String tag, int position) {
                //mTag = position + 1;
                mTag = title.get(position);
            }
        });
    }

    /**
     * 1，添加标签
     */
    private void savaTag(final String key) {
        if (articleCollect == null) {
            articleCollect = new ArticleCollect();
            User user = AVUser.getCurrentUser(User.class);
            articleCollect.setUser(user);
            articleCollect.setArticleCollect("[\"java\",\"Java\"]");
            articleCollect.saveInBackground();// 保存到服务端
        }
        // 第一参数是 className,第二个参数是 objectId
        AVQuery<AVObject> avQuery = new AVQuery<>("ArticleCollect");
        avQuery.getInBackground(articleCollect.getObjectId(), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (avObject != null) {
                    update(avObject);
                }
            }
        });

    }

    private void update(AVObject todo) {
        // 修改 content
        Gson gson = new Gson();
        String result = gson.toJson(title);
        todo.put("article_collect", result);
        todo.setObjectId(articleCollect.getObjectId());
        // 保存到云端
        todo.saveInBackground(new com.avos.avoscloud.SaveCallback() {
            @Override
            public void done(AVException e) {
                EventBus.getDefault().post(new TabRefresh());
            }
        });
    }

    @OnClick(R.id.add_tag)
    public void click() {
        requestTabList();
        if (title == null) title = new ArrayList<>();
        final EditText editText = new EditText(ShareArticleActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(ShareArticleActivity.this);
        builder.setTitle("请输入标签名称");
        builder.setView(editText);
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                title.add(editText.getText().toString());
                mTagLayout.setTags(null);
                mTagLayout.setTags(title);
                savaTag(editText.getText().toString());
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateTo(MainActivity.class);
                break;
            case R.id.publish:
                publishArticle();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        navigateTo(MainActivity.class);
    }

    private void publishArticle() {
        String title = mArticleTitle.getText().toString().trim();
        if (title.length() == 0) {
            Snackbar.make(mScrollView, getString(R.string.title_not_null), Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (mTag == null) {
            Snackbar.make(mScrollView, getString(R.string.tag_not_null), Snackbar.LENGTH_SHORT).show();
            return;
        }

        Article article = new Article();
        article.setTitle(title);
        String desc = mArticleDescription.getText().toString().trim();
        article.setDesc(desc);
        article.setUrl(mArticleUrl.getText().toString());
        article.setTag(mTag);

        User user = AVUser.getCurrentUser(User.class);
        article.setUser(user);
        mArticleDataSource.saveArticle(article, new SaveCallback() {
            @Override
            public void onSaveSuccess() {
                Snackbar.make(mScrollView, getString(R.string.publish_success), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onSaveFailed(String errorMsg) {
                Snackbar.make(mScrollView, getString(R.string.publish_failed), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}


