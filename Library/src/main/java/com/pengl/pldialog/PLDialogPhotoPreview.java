package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * 单图片浏览
 */
public class PLDialogPhotoPreview extends Dialog {

    private final Object imgUrl;// 图像的url
    private final boolean isUseCache;// 是否使用缓存，默认使用

    private View.OnLongClickListener mOnLongClick;

    public PLDialogPhotoPreview(@NonNull Context context, Object imgUrl) {
        super(context, R.style.PLAppDialog_TransBg_FadeInOut);
        this.imgUrl = imgUrl;
        this.isUseCache = true;
    }

    public PLDialogPhotoPreview(@NonNull Context context, Object imgUrl, boolean isUseCache) {
        super(context, R.style.PLAppDialog_TransBg_FadeInOut);
        this.imgUrl = imgUrl;
        this.isUseCache = isUseCache;
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
        if (null != window) {
            View decorView = window.getDecorView();
            // 设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }

            // 内容扩展到导航栏
            window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);

            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }

        PhotoView mPhotoView = findViewById(R.id.mPhotoView);
        if (isUseCache) {
            Glide.with(mPhotoView.getContext()).load(imgUrl).dontAnimate().into(mPhotoView);
        } else {
            Glide.with(mPhotoView.getContext()).load(imgUrl)
                    .skipMemoryCache(true) // 不使用内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                    .dontAnimate().into(mPhotoView);
        }

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
