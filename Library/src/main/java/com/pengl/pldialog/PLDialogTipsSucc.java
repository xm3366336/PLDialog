package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

public class PLDialogTipsSucc extends Dialog {

    private AppCompatImageView ic_tips_succ;
    private AppCompatTextView tv_title, tv_content;

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
}
