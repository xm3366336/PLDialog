package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

public class PLDialogTipsSucc extends Dialog {

    private final ImageView ic_tips_succ;

    /**
     * 显示成功的提示
     *
     * @param context   上下文
     * @param tipsTitle 提示的标题
     * @param tipsInfo  提示的内容
     */
    public PLDialogTipsSucc(Context context, String tipsTitle, String tipsInfo) {
        super(context, R.style.AppDialog_TransBg);
        setContentView(R.layout.pl_dialog_tips_succ);
        ic_tips_succ = findViewById(R.id.ic_tips_succ);
        ((TextView) findViewById(R.id.succ_title)).setText(tipsTitle);
        ((TextView) findViewById(R.id.succ_content)).setText(tipsInfo);
        findViewById(R.id.btn_close).setOnClickListener(view -> dismiss());
    }

    /**
     * 设置中间的图片
     *
     * @param resId 图片id
     */
    public void setImageResource(@DrawableRes int resId) {
        ic_tips_succ.setImageResource(resId);
    }

}
