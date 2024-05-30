package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
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
        init(R.layout.pl_dialog);
    }

    public PLDialog(Context context, int style) {
        super(context, R.style.PLAppDialog_TransBg);
        init(style == 2 ? R.layout.pl_dialog_2 : R.layout.pl_dialog);
    }

    public PLDialog(Context context, String title, String content) {
        super(context, R.style.PLAppDialog_TransBg);
        init(R.layout.pl_dialog);
        setTitle(title);
        setContent(content);
    }

    private void init(@LayoutRes int layoutResID) {
        setContentView(layoutResID);
        setCanceledOnTouchOutside(false);
        bg = findViewById(R.id.bg);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_other = findViewById(R.id.btn_other);

        setBgRoundedDip(8);
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
     * @param cornerSizePx 单位px
     */
    public PLDialog setBgRounded(float cornerSizePx) {
        bg.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.ROUNDED, Math.max(cornerSizePx, 0)).build());
        return this;
    }

    public PLDialog setBgRoundedDip(int cornerSizeDip) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerSizeDip, getContext().getResources().getDisplayMetrics());
        return setBgRounded(px);
    }

    public ShapeableImageView getBg() {
        return bg;
    }

    public TextView getTvTitle() {
        return tv_title;
    }

    public TextView getTvContent() {
        return tv_content;
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
