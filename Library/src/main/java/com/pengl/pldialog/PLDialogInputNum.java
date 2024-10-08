package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.pengl.pldialog.util.ICallBack;
import com.pengl.pldialog.view.ViewKeyboard;
import com.pengl.pldialog.view.ViewKeyboardHex;

/**
 * 输入数字
 */
public class PLDialogInputNum extends Dialog {

    private TYPE showType;// 显示方式

    private ICallBack callback;
    private int maxLength;// 最大长度

    private final ShapeableImageView bg;
    private final AppCompatTextView tvNum;
    private ViewKeyboard mViewKeyboard;
    private ViewKeyboardHex mViewKeyboardHex;
    private final AppCompatImageButton btn_del;

    public enum TYPE {
        NORMAL, // 正常输入数字
        IDCARD, // 输入身份证号码
        PHONE,  // 11位的手机号码
        HEX16,  // 16进制的数字
    }

    public PLDialogInputNum(Context context) {
        this(context, TYPE.NORMAL);
    }

    public PLDialogInputNum(Context context, TYPE showType) {
        super(context, R.style.PLAppDialog_TransBg);
        setContentView(showType == TYPE.HEX16 ? R.layout.pl_dialog_input_hex : R.layout.pl_dialog_input_num);
        bg = findViewById(R.id.bg);
        tvNum = findViewById(R.id.tv_num);
        btn_del = findViewById(R.id.btn_del);
        if (showType == TYPE.HEX16) {
            mViewKeyboardHex = findViewById(R.id.mViewKeyboard);
        } else {
            mViewKeyboard = findViewById(R.id.mViewKeyboard);
        }
        this.showType = showType;
        setBgRoundedDip(8);
    }

    /**
     * 设置显示方式
     *
     * @param showType 显示方式 @see PLDialogInputNum.TYPE
     */
    public PLDialogInputNum setShowType(TYPE showType) {
        this.showType = showType;
        return this;
    }

    /**
     * 设置回调
     *
     * @param callback 回调
     */
    public PLDialogInputNum setCallback(ICallBack callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 设置最大可输入的长度
     *
     * @param maxLength 长度
     */
    public PLDialogInputNum setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public AppCompatTextView getTvNum() {
        return tvNum;
    }

    public AppCompatImageButton getBtnDel() {
        return btn_del;
    }

    public ViewKeyboard getViewKeyboard() {
        return mViewKeyboard;
    }

    public ViewKeyboardHex getViewKeyboardHex() {
        return mViewKeyboardHex;
    }

    /**
     * 设置圆角的大小
     *
     * @param cornerSizePx 单位px
     */
    public PLDialogInputNum setBgRounded(float cornerSizePx) {
        bg.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                .setTopLeftCorner(CornerFamily.ROUNDED, Math.max(cornerSizePx, 0))
                .setTopRightCorner(CornerFamily.ROUNDED, Math.max(cornerSizePx, 0))
                .build());
        return this;
    }

    public PLDialogInputNum setBgRoundedDip(int cornerSizeDip) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerSizeDip, getContext().getResources().getDisplayMetrics());
        return setBgRounded(px);
    }

    public ShapeableImageView getBg() {
        return bg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);
        getWindow().setWindowAnimations(R.style.PLKeyboardStyle);

        findViewById(R.id.btn_close).setOnClickListener(view -> dismiss());
        btn_del.setOnClickListener(view -> btnDel());
        btn_del.setOnLongClickListener(view -> {
            tvNum.setText("");
            btn_del.setVisibility(View.GONE);
            return false;
        });

        if (null != mViewKeyboard) {
            mViewKeyboard.setOnKeyboardClickListener(new ViewKeyboard.OnKeyboardClickListener() {
                @Override
                public void onKeyDown(String s) {
                    if (tvNum.getText().toString().length() >= maxLength) {
                        return;
                    }

                    btn_del.setVisibility(View.VISIBLE);
                    tvNum.setText(tvNum.getText().toString() + s);
                }

                @Override
                public void onKeyDownBottomLeft(boolean isLong) {
                    if (!isLong) {
                        if (showType == TYPE.IDCARD) {
                            if (tvNum.length() == 17) {
                                tvNum.setText(tvNum.getText() + mViewKeyboard.getBtnBottomLeft().getText().toString());
                            }
                        } else {
                            tvNum.setText("");
                        }
                    }
                }

                @Override
                public void onKeyDownBottomRight(boolean isLong) {
                    if (!isLong) {
                        btnOK();
                    }
                }
            });
        }

        if (null != mViewKeyboardHex) {
            mViewKeyboardHex.setOnKeyboardClickListener(new ViewKeyboardHex.OnKeyboardClickListener() {
                @Override
                public void onKeyDown(String num) {
                    if (maxLength > 0 && tvNum.getText().toString().length() >= maxLength) {
                        return;
                    }

                    btn_del.setVisibility(View.VISIBLE);
                    tvNum.setText(tvNum.getText().toString() + num);
                }

                @Override
                public void onKeyDownClean() {
                    tvNum.setText("");
                    btn_del.setVisibility(View.GONE);
                }

                @Override
                public void onKeyDownFinish() {
                    btnOK();
                }
            });
        }
    }

    private void btnDel() {
        if (tvNum.getText().toString().length() <= 0) {
            return;
        }

        tvNum.setText(tvNum.getText().toString().substring(0, tvNum.getText().length() - 1));
        if (tvNum.getText().length() <= 0) {
            btn_del.setVisibility(View.GONE);
        }
    }

    private void btnOK() {
        if (null != callback) {
            callback.onCallBack(tvNum.getText().toString());
        }

        dismiss();
    }

    @Override
    public void show() {
        if (showType == TYPE.IDCARD) {
            setMaxLength(18);
            tvNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.pld_px_24));
            mViewKeyboard.setKeyboardBLText("X");
        } else if (showType == TYPE.PHONE) {
            tvNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.pld_px_40));
            setMaxLength(11);
        }
        super.show();
    }

}
