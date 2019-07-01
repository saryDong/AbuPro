package com.abu.abupro.ui.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.abu.abupro.R;
import com.abu.abupro.app.Constant;
import com.abu.abupro.data.ArticleDataSource;
import com.abu.abupro.data.model.Article;
import com.abu.abupro.data.model.User;
import com.abu.abupro.data.model.UserArticleFavourMap;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ArticleDetailActivity extends BaseActivity {

    @Inject
    ArticleDataSource mArticleDataSource;

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.favour)
    AppCompatImageButton mFavour;

    private boolean isFavoured = false;
    private Article mArticle;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void init() {
        super.init();
        initArticle();
        initFavour();
        initWebView();
    }

    private void initArticle() {
        Intent intent = getIntent();
        String articleString = intent.getStringExtra(Constant.EXTRA_ARTICLE);
        try {
            mArticle = (Article) AVObject.parseAVObject(articleString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void initFavour() {
        User user = AVUser.getCurrentUser(User.class);
        isFavoured = user.isFavouredArticle(mArticle.getObjectId());
        mFavour.setSelected(isFavoured);
    }

    private void initWebView() {
        mWebView.loadUrl(mArticle.getUrl());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.favour)
    public void onViewClicked() {
        isFavoured = !isFavoured;
        mFavour.setSelected(isFavoured);
        User user = AVUser.getCurrentUser(User.class);
        if (isFavoured) {
            UserArticleFavourMap.buildFavourMap(user, mArticle);
        } else {
            UserArticleFavourMap.breakFavourMap(user, mArticle);
        }
    }
}
