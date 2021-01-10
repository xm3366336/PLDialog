package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 简单的dialog窗口
 */
public class PLDialog extends Dialog {

    TextView tv_title, tv_content;
    AppCompatButton btn_confirm, btn_cancel;

    View.OnClickListener OnClickOK, OnClickCancel;

    public void setOnClickOK(View.OnClickListener onClickOK) {
        OnClickOK = onClickOK;
    }

    public void setOnClickCancel(View.OnClickListener onClickCancel) {
        OnClickCancel = onClickCancel;
    }

    public PLDialog(Context context, String title, String content) {
        super(context, R.style.AppDialog_TransBg);
        setContentView(R.layout.pl_dialog);
        setCanceledOnTouchOutside(false);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_cancel = findViewById(R.id.btn_cancel);

        tv_title.setText(title);
        tv_content.setText(content);
        btn_confirm.setOnClickListener(view -> {
            if (null != OnClickOK) {
                OnClickOK.onClick(null);
            }
            dismiss();
        });
        btn_cancel.setOnClickListener(view -> {
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
        btn_confirm.setText(okStr);
    }

    /**
     * 修改取消按钮的文字
     *
     * @param cancelStr 文字
     */
    public void setBtnCancelText(String cancelStr) {
        btn_cancel.setText(cancelStr);
    }

}
