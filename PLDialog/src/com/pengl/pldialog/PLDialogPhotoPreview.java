package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * 单图片浏览
 */
public class PLDialogPhotoPreview extends Dialog {

    private final String imgUrl;// 图像的url
    private View.OnLongClickListener mOnLongClick;

    public PLDialogPhotoPreview(@NonNull Context context, String imgUrl) {
        super(context, R.style.AppDialog_TransBg_FadeInOut);
        this.imgUrl = imgUrl;
    }

    public void setOnLongClickListener(View.OnLongClickListener l) {
        this.mOnLongClick = l;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pl_dialog_photo_preview);

        Window window = getWindow();
        View decorView = window.getDecorView();
        // 设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        // 设置导航栏颜色
        window.setNavigationBarColor(Color.TRANSPARENT);
        // 内容扩展到导航栏
        window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);

        PhotoView mPhotoView = findViewById(R.id.mPhotoView);
        Glide.with(mPhotoView.getContext()).load(imgUrl).dontAnimate().into(mPhotoView);
        mPhotoView.setOnPhotoTapListener((view, x, y) -> dismiss());
        mPhotoView.setOnOutsidePhotoTapListener(imageView -> dismiss());
        mPhotoView.setOnLongClickListener(view -> {
            if (null != mOnLongClick) {
                mOnLongClick.onLongClick(view);
                return true;
            }
            return true;
        });
    }

}
