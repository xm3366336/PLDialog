package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 一个简单的提示
 */
public class PLDialogTips extends Dialog {

    TextView tv_content, tv_ok;
    View btn_close;
    View.OnClickListener OnClickOK, OnClickCancel;

    public void setOnClickOK(View.OnClickListener onClickOK) {
        OnClickOK = onClickOK;
    }

    public void setOnClickCancel(View.OnClickListener onClickCancel) {
        OnClickCancel = onClickCancel;
    }

    public PLDialogTips(Context context, String content) {
        super(context, R.style.AppDialog_TransBg);
        setContentView(R.layout.pl_dialog_tips);
        setCanceledOnTouchOutside(false);
        tv_content = findViewById(R.id.tv_content);
        btn_close = findViewById(R.id.btn_close);
        tv_ok = findViewById(R.id.tv_ok);

        tv_content.setText(content);
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

    /**
     * 修改确定按钮的文字
     *
     * @param okStr 文字
     */
    public void setBtnOkText(String okStr) {
        tv_ok.setText(okStr);
    }

    /**
     * 设置内容的对齐方式
     * 默认居中对齐，如果文字较多，建议修改为左对齐
     *
     * @param gravity android.view.Gravity
     */
    public void setContent(int gravity) {
        tv_content.setGravity(gravity);
    }

    /**
     * 显示关闭按钮，默认是不显示的
     */
    public void setShowBtnClose() {
        btn_close.setVisibility(View.VISIBLE);
    }
}
