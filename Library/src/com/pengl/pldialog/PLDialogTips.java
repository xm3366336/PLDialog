package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 一个简单的提示
 */
public class PLDialogTips extends Dialog {

    TextView tv_title, tv_content, tv_ok;
    View btn_close;
    View.OnClickListener OnClickOK, OnClickCancel;

    public PLDialogTips setOnClickOK(View.OnClickListener onClickOK) {
        OnClickOK = onClickOK;
        return this;
    }

    public PLDialogTips setOnClickCancel(View.OnClickListener onClickCancel) {
        OnClickCancel = onClickCancel;
        return this;
    }

    public PLDialogTips(Context context) {
        super(context, R.style.AppDialog_TransBg);
        init("");
    }

    public PLDialogTips(Context context, String content) {
        super(context, R.style.AppDialog_TransBg);
        init(content);
    }

    private void init(String content) {
        setContentView(R.layout.pl_dialog_tips);
        setCanceledOnTouchOutside(false);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        btn_close = findViewById(R.id.btn_close);
        tv_ok = findViewById(R.id.tv_ok);

        setContent(content);
        tv_ok.setOnClickListener(view -> {
            if (null != OnClickOK) {
                OnClickOK.onClick(null);
            }
            dismiss();
        });
        btn_close.setOnClickListener(view -> {
            if (null != OnClickCancel) {
                OnClickCancel.onClick(null);
            }
            dismiss();
        });
    }

    public TextView getTitleView() {
        return tv_title;
    }

    public TextView getContentView() {
        return tv_content;
    }

    public PLDialogTips setTitle(String title) {
        tv_title.setText(title);
        tv_title.setVisibility(View.VISIBLE);
        return this;
    }

    public PLDialogTips setContent(String content) {
        tv_content.setText(content);
        return this;
    }

    public PLDialogTips setContent(Spanned content) {
        tv_content.setText(content);
        return this;
    }

    /**
     * 修改确定按钮的文字
     *
     * @param okStr 文字
     */
    public PLDialogTips setBtnOkText(String okStr) {
        tv_ok.setText(okStr);
        return this;
    }

    /**
     * 设置内容的对齐方式
     * 默认居中对齐，如果文字较多，建议修改为左对齐
     *
     * @param gravity android.view.Gravity
     * @deprecated use getContentView().setGravity(int gravity)
     */
    @Deprecated
    public void setGravity(int gravity) {
        tv_content.setGravity(gravity);
    }

    /**
     * 显示关闭按钮，默认是不显示的
     */
    public PLDialogTips setShowBtnClose() {
        btn_close.setVisibility(View.VISIBLE);
        return this;
    }
}
