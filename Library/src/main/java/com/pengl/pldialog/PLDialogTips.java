package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

/**
 * 一个简单的提示
 */
public class PLDialogTips extends Dialog {

    AppCompatTextView tv_title, tv_content, tv_ok;
    View btn_close;
    View.OnClickListener OnClickOK, OnClickCancel;
    ShapeableImageView bg;

    public PLDialogTips setOnClickOK(View.OnClickListener onClickOK) {
        OnClickOK = onClickOK;
        return this;
    }

    public PLDialogTips setOnClickCancel(View.OnClickListener onClickCancel) {
        OnClickCancel = onClickCancel;
        return this;
    }

    public PLDialogTips(Context context) {
        super(context, R.style.PLAppDialog_TransBg);
        init("");
    }

    public PLDialogTips(Context context, String content) {
        super(context, R.style.PLAppDialog_TransBg);
        init(content);
    }

    private void init(String content) {
        setContentView(R.layout.pl_dialog_tips);
        setCanceledOnTouchOutside(false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N && null != getWindow()) {
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setGravity(Gravity.CENTER);
        }

        bg = findViewById(R.id.bg);
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

        setBgRoundedDip(8);
    }

    public AppCompatTextView getTitleView() {
        return tv_title;
    }

    public AppCompatTextView getContentView() {
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
     * 显示关闭按钮，默认是不显示的
     */
    public PLDialogTips setShowBtnClose() {
        btn_close.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置圆角的大小
     *
     * @param cornerSizePx 单位px
     */
    public PLDialogTips setBgRounded(float cornerSizePx) {
        bg.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.ROUNDED, Math.max(cornerSizePx, 0)).build());
        return this;
    }

    public PLDialogTips setBgRoundedDip(int cornerSizeDip) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerSizeDip, getContext().getResources().getDisplayMetrics());
        return setBgRounded(px);
    }

    public ShapeableImageView getBg() {
        return bg;
    }
}
