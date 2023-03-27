package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

public class PLDialogTipsSucc extends Dialog {

    private AppCompatImageView ic_tips_succ;
    private AppCompatTextView tv_title, tv_content;
    private ShapeableImageView bg;

    /**
     * 显示成功的提示
     *
     * @param context 上下文
     */
    public PLDialogTipsSucc(Context context) {
        super(context, R.style.PLAppDialog_TransBg);
        init();
    }

    /**
     * 显示成功的提示
     *
     * @param context   上下文
     * @param tipsTitle 提示的标题
     * @param tipsInfo  提示的内容
     */
    public PLDialogTipsSucc(Context context, String tipsTitle, String tipsInfo) {
        super(context, R.style.PLAppDialog_TransBg);
        init();
        setTitle(tipsTitle);
        setContent(tipsInfo);
    }

    private void init() {
        setContentView(R.layout.pl_dialog_tips_succ);
        bg = findViewById(R.id.bg);
        ic_tips_succ = findViewById(R.id.ic_tips_succ);
        tv_title = findViewById(R.id.succ_title);
        tv_content = findViewById(R.id.succ_content);
        findViewById(R.id.btn_close).setOnClickListener(view -> dismiss());
    }

    /**
     * 设置中间的图片
     *
     * @param resId 图片id
     */
    public PLDialogTipsSucc setImageResource(@DrawableRes int resId) {
        ic_tips_succ.setImageResource(resId);
        return this;
    }

    public PLDialogTipsSucc setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public PLDialogTipsSucc setContent(String content) {
        tv_content.setText(content);
        return this;
    }

    /**
     * 设置圆角的大小
     *
     * @param cornerSizeDip 默认是4dip，单位dip
     */
    public PLDialogTipsSucc setBgRounded(int cornerSizeDip) {
        bg.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.ROUNDED, Math.max(cornerSizeDip, 0)).build());
        return this;
    }

    public ShapeableImageView getBg() {
        return bg;
    }

    public AppCompatTextView getTvTitle() {
        return tv_title;
    }

    public AppCompatTextView getTvContent() {
        return tv_content;
    }

}
