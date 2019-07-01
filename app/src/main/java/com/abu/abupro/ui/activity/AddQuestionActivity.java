package com.abu.abupro.ui.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;


import com.abu.abupro.R;
import com.abu.abupro.contract.AddQuestionContract;
import com.abu.abupro.data.model.Question;
import com.abu.abupro.presenter.AddQuestionPresenter;

import javax.inject.Inject;

import butterknife.BindView;
/**
 *  @date: 2018/11/23 11:03
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class AddQuestionActivity extends BaseActivity implements AddQuestionContract.View {

    @BindView(R.id.question_title)
    EditText mQuestionTitle;
    @BindView(R.id.question_description)
    EditText mQuestionDescription;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.radioGroup_gender)
    RadioGroup mRadioGroup;

    @Inject
    AddQuestionPresenter mAddQuestionPresenter;

    private  int REQUEST_TYPE= Question.QUESTION;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_question;
    }

    @Override
    protected void init() {
        super.init();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.add_question);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_request:
                        REQUEST_TYPE=Question.QUESTION;
                        mQuestionTitle.setHint("问题详情");
                        mQuestionDescription.setVisibility(View.GONE);
                        break;
                    case R.id.radio_interview:
                        mQuestionTitle.setHint("面试岗位");
                        mQuestionDescription.setHint("公司及其面试经验");
                        REQUEST_TYPE=Question.INTERVIEW;
                        break;
                    case R.id.radio_job_info:
                        mQuestionTitle.setHint("公司名称");
                        mQuestionDescription.setHint("公司招聘详情");
                        REQUEST_TYPE=Question.JOB_INFO;
                        break;
                        default:
                }
            }
        });
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
                finish();
                break;
            case R.id.publish:
                publishQuestion();
                break;
                default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void publishQuestion() {
        String title = mQuestionTitle.getText().toString().trim();
        if (title.length() == 0) {
            Snackbar.make(mScrollView, R.string.title_not_null, Snackbar.LENGTH_SHORT).show();
        } else {
            String des = mQuestionDescription.getText().toString().trim();
            mAddQuestionPresenter.publishQuestion(title, des,REQUEST_TYPE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddQuestionPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddQuestionPresenter.dropView();
    }

    @Override
    public void onPublishSuccess() {
        Snackbar.make(mScrollView, R.string.publish_success, Snackbar.LENGTH_SHORT)
                .addCallback(new Snackbar.Callback(){
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        finish();
                    }
                })
                .show();
        hideSoftKeyboard();
    }

    @Override
    public void onPublishFailed() {
        Snackbar.make(mScrollView, R.string.publish_failed, Snackbar.LENGTH_SHORT).show();
    }
}
