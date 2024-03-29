package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

/**
 * 简单的dialog窗口
 */
public class PLDialog extends Dialog {

    TextView tv_title, tv_content;
    AppCompatButton btn_confirm, btn_cancel, btn_other;
    ShapeableImageView bg;

    View.OnClickListener OnClickOK, OnClickCancel;

    public void setOnClickOK(View.OnClickListener onClickOK) {
        OnClickOK = onClickOK;
    }

    public void setOnClickCancel(View.OnClickListener onClickCancel) {
        OnClickCancel = onClickCancel;
    }

    public PLDialog(Context context) {
        super(context, R.style.PLAppDialog_TransBg);
        setContentView(R.layout.pl_dialog);
        setCanceledOnTouchOutside(false);
        init();
    }

    public PLDialog(Context context, String title, String content) {
        super(context, R.style.PLAppDialog_TransBg);
        setContentView(R.layout.pl_dialog);
        setCanceledOnTouchOutside(false);
        init();
        setTitle(title);
        setContent(content);
    }

    private void init() {
        bg = findViewById(R.id.bg);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_other = findViewById(R.id.btn_other);

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

    public PLDialog setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public PLDialog setContent(String content) {
        tv_content.setText(content);
        return this;
    }

    /**
     * 修改确定按钮的文字
     *
     * @param okStr 文字
     */
    public PLDialog setBtnOkText(String okStr) {
        btn_confirm.setText(okStr);
        return this;
    }

    /**
     * 修改取消按钮的文字
     *
     * @param cancelStr 文字
     */
    public PLDialog setBtnCancelText(String cancelStr) {
        btn_cancel.setText(cancelStr);
        return this;
    }

    /**
     * 设置圆角的大小
     *
     * @param cornerSizeDip 默认是4dip，单位dip
     */
    public PLDialog setBgRounded(int cornerSizeDip) {
        bg.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.ROUNDED, Math.max(cornerSizeDip, 0)).build());
        return this;
    }

    public ShapeableImageView getBg() {
        return bg;
    }

    public AppCompatButton getBtnConfirm() {
        return btn_confirm;
    }

    public AppCompatButton getBtnCancel() {
        return btn_cancel;
    }

    public AppCompatButton getBtnOther() {
        return btn_other;
    }
}
