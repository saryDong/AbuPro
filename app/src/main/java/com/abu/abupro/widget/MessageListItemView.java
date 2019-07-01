package com.abu.abupro.widget;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abu.abupro.R;
import com.abu.abupro.app.Constant;
import com.abu.abupro.app.GlideApp;
import com.abu.abupro.data.model.Answer;
import com.abu.abupro.data.model.Article;
import com.abu.abupro.data.model.Message;
import com.abu.abupro.data.model.User;
import com.abu.abupro.ui.activity.AnswerDetailActivity;
import com.abu.abupro.ui.activity.ArticleDetailActivity;
import com.abu.abupro.ui.activity.ProfileActivity;
import com.abu.abupro.ui.activity.WebViewActivity;
import com.abu.abupro.utils.DateUtils;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @date: 2019/5/17 9:57
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
public class MessageListItemView extends CardView {
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.answer)
    TextView mAnswerText;
    @BindView(R.id.publish_date)
    TextView mPublishDate;
    @BindView(R.id.answer_container)
    ConstraintLayout mAnswerContainer;

    private Message mAnswer;

    public MessageListItemView(Context context) {
        this(context, null);
    }

    public MessageListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_message_item, this);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        layoutParams.setMargins(0, margin, 0, 0);
        setLayoutParams(layoutParams);

        ButterKnife.bind(this, this);
    }

    public void bindView(Message answer) {
        mAnswer = answer;
        mAnswerText.setText(answer.getTitle());
        Date createAt = answer.getCreatedAt();
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), createAt));
        GlideApp.with(this)
                .load(answer.getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(mAvatar);
    }

    @OnClick({R.id.avatar,R.id.answer_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.answer_container:
                navigateToArticleDetailActivity();
                break;
                default:
        }
    }

    private void navigateToArticleDetailActivity() {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra(Constant.ARTICLE_URL, mAnswer.getContent());
        getContext().startActivity(intent);
    }

}
