package com.abu.abupro.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.abu.abupro.app.GlideApp;
import com.abu.abupro.data.model.User;

import com.abu.abupro.R;
import com.abu.abupro.di.ActivityScoped;
import com.abu.abupro.ui.activity.SettingsActivity;
import com.abu.abupro.ui.activity.UserAnswerActivity;
import com.abu.abupro.ui.activity.UserFavourAnswerActivity;
import com.abu.abupro.ui.activity.UserFavourArticleActivity;
import com.abu.abupro.ui.activity.UserFavourQuestionActivity;
import com.abu.abupro.ui.activity.UserQuestionsActivity;
import com.abu.abupro.ui.activity.UserShareArticleActivity;
import com.abu.abupro.utils.ImageUtils;
import com.abu.abupro.utils.PermissionUtils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.feedback.Comment;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.avos.avoscloud.feedback.FeedbackThread;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;


import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

@ActivityScoped
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_GALLERY_IMAGES = 0;
    private static final int REQUEST_CAMERA_IMAGES = 1;

    private static final String IMAGE_DIR = Environment.getExternalStorageDirectory()
            + "/com.abu.abupro/images/";

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.slogan)
    TextView mSlogan;

    private BottomSheetDialog mBottomSheetDialog;
    private String mImageName;
    private User mUser;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Inject
    public MeFragment() {
    }

    @Override
    protected void init() {
        super.init();
        mUser = AVUser.getCurrentUser(User.class);
        mUserName.setText(mUser.getUsername());
        if (mUser.getSlogan() != null) {
            mSlogan.setText(mUser.getSlogan());
        }
        GlideApp.with(this)
                .load(mUser.getAvatar())
                .transform(new CircleCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(mAvatar);

    }


    @OnClick({R.id.avatar, R.id.user_name, R.id.slogan, R.id.user_share, R.id.user_questions, R.id.user_answers,
            R.id.user_favour_articles, R.id.user_favour_questions, R.id.user_favour_answers, R.id.feedback,
            R.id.settings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                showBottomSheet();
                break;
            case R.id.user_name:
                showEditUserNameDialog();
                break;
            case R.id.slogan:
                showEditSloganDialog();
                break;
            case R.id.user_share:
                navigateTo(UserShareArticleActivity.class);
                break;
            case R.id.user_questions:
                navigateTo(UserQuestionsActivity.class);
                break;
            case R.id.user_answers:
                navigateTo(UserAnswerActivity.class);
                break;
            case R.id.user_favour_articles:
                navigateTo(UserFavourArticleActivity.class);
                break;
            case R.id.user_favour_questions:
                navigateTo(UserFavourQuestionActivity.class);
                break;
            case R.id.user_favour_answers:
                navigateTo(UserFavourAnswerActivity.class);
                break;
            case R.id.feedback:
                //sync feedback 启用用户反馈模块
                FeedbackAgent feedbackAgent = new FeedbackAgent(getActivity());
                //在用户打开 App 时，通知用户新的反馈回复，只需要在你的入口 Activity 的 OnCreate 方法中添加
                feedbackAgent.startDefaultThreadActivity();
                feedbackAgent.sync();
                feedbackAgent.getDefaultThread().sync(new FeedbackThread.SyncCallback() {
                    @Override
                    public void onCommentsSend(List<Comment> list, AVException e) {

                    }

                    @Override
                    public void onCommentsFetch(List<Comment> list, AVException e) {

                    }
                });
                break;
            case R.id.settings:
                navigateTo(SettingsActivity.class);
                break;
                default:
        }
    }

    private void showEditSloganDialog() {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit, null);
        final EditText editText = content.findViewById(R.id.dialog_edit);
        editText.setHint(R.string.input_slogan);
        if (mUser.getSlogan() != null) {
            editText.setText(mUser.getSlogan());
            editText.setSelection(editText.getText().length());
        }
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.edit_slogan)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updateSlogan = editText.getText().toString().trim();
                        if (mUser.getSlogan() == null || !mUser.getSlogan().equals(updateSlogan)) {
                            mSlogan.setText(updateSlogan);
                            mUser.setSlogan(updateSlogan);
                        }
                    }
                })
                .setView(content)
                .show();
    }

    private void showEditUserNameDialog() {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit, null);
        final EditText editText = content.findViewById(R.id.dialog_edit);
        editText.setText(mUser.getUsername());
        editText.setSelection(editText.getText().length());
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.edit_user_name)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updateUserName = editText.getText().toString().trim();
                        if (!mUser.getUsername().equals(updateUserName)) {
                            updateUserName(updateUserName);
                        }
                    }
                })
                .setView(content)
                .show();
    }

    private void updateUserName(final String userName) {
        mUser.setUsername(userName);
        mUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    mUserName.setText(userName);
                }
            }
        });
    }

    private void showBottomSheet() {
        if (mBottomSheetDialog == null) {
            mBottomSheetDialog = new BottomSheetDialog(getContext());
            View root = LayoutInflater.from(getContext()).inflate(R.layout.dialog_bottom_sheet, null);
            root.findViewById(R.id.take_photo).setOnClickListener(this);
            root.findViewById(R.id.gallery).setOnClickListener(this);
            mBottomSheetDialog.setContentView(root);
        }
        mBottomSheetDialog.show();
    }

    @Override
    public void onClick(View v) {
        mBottomSheetDialog.dismiss();
        switch (v.getId()) {
            case R.id.take_photo:
                if (PermissionUtils.checkPermissions((Activity) getContext())) {
                    navigateToCamera();
                }
                break;
            case R.id.gallery:
                navigateToGallery();
                break;
        }
    }

    private void navigateToGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_GALLERY_IMAGES);
    }

    private void navigateToCamera() {
        File file = new File(IMAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        Uri uri = null;
        mImageName = String.valueOf(System.currentTimeMillis()) + ".png";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //7.0适配，通过FileProvider获取图片Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            uri = FileProvider.getUriForFile(getContext(),
                    "com.abu.abupro.fileProvider", file);
        } else {
            //直接通过文件获取Uri
            uri = Uri.fromFile(new File(IMAGE_DIR + mImageName));
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CAMERA_IMAGES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY_IMAGES:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    GlideApp.with(this).load(uri).transform(new CircleCrop()).into(mAvatar);
                    ImageUtils.saveAvatar(getContext(), uri);
                }
                break;
            case REQUEST_CAMERA_IMAGES:
                if (resultCode == RESULT_OK) {
                    GlideApp.with(getContext())
                            .load(IMAGE_DIR + mImageName)
                            .transform(new CircleCrop())
                            .placeholder(R.drawable.avoscloud_feedback_user_reply_background)
                            .transition(new DrawableTransitionOptions().crossFade())
                            .into(mAvatar);
                    ImageUtils.saveAvatar(mImageName, IMAGE_DIR + mImageName);

                }
                break;
            default:
        }
    }

}
