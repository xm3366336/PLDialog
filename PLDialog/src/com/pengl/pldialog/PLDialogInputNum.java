package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.pengl.pldialog.util.ICallBack;
import com.pengl.pldialog.view.ViewKeyboard;

/**
 * 输入数字
 */
public class PLDialogInputNum extends Dialog {

    private TYPE showType;// 显示方式

    private ICallBack callback;
    private int maxLength;// 最大长度

    private final TextView tvNum;
    private final ViewKeyboard mViewKeyboard;
    private final AppCompatImageButton btn_del;

    public enum TYPE {
        NORMAL, // 正常输入数字
        IDCARD, // 输入身份证号码
    }

    public PLDialogInputNum(Context context) {
        super(context, R.style.AppDialog_TransBg);
        setContentView(R.layout.pl_dialog_input_num);
        tvNum = findViewById(R.id.tv_num);
        mViewKeyboard = findViewById(R.id.mViewKeyboard);
        btn_del = findViewById(R.id.btn_del);
    }

    /**
     * 设置显示方式
     *
     * @param showType 显示方式 @see PLDialogInputNum.TYPE
     */
    public void setShowType(TYPE showType) {
        this.showType = showType;
    }

    /**
     * 设置回调
     *
     * @param callback 回调
     */
    public void setCallback(ICallBack callback) {
        this.callback = callback;
    }

    /**
     * 设置最大可输入的长度
     *
     * @param maxLength 长度
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);

        findViewById(R.id.btn_close).setOnClickListener(view -> dismiss());
        btn_del.setOnClickListener(view -> {
            if (tvNum.getText().toString().length() <= 0) {
                return;
            }

            tvNum.setText(tvNum.getText().toString().substring(0, tvNum.getText().length() - 1));
            if (tvNum.getText().length() <= 0) {
                btn_del.setVisibility(View.GONE);
            }

            if (showType == TYPE.IDCARD) {
                mViewKeyboard.setKeyboardBLShow(tvNum.getText().toString().length() == maxLength - 1);
            }
        });

        mViewKeyboard.setOnKeyboardClickListener(new ViewKeyboard.OnKeyboardClickListener() {
            @Override
            public void onKeyDown(String s) {
                if (tvNum.getText().toString().length() >= maxLength) {
                    return;
                }

                btn_del.setVisibility(View.VISIBLE);
                tvNum.setText(tvNum.getText().toString() + s);
                if (showType == TYPE.IDCARD) {
                    mViewKeyboard.setKeyboardBLShow(tvNum.getText().toString().length() == maxLength - 1);
                }
            }

            @Override
            public void onKeyDownBottomRight() {
                if (null != callback) {
                    callback.onCallBack(tvNum.getText().toString());
                }

                dismiss();
            }

            @Override
            public void onKeyDownLongBottomRight() {
            }
        });
    }

    @Override
    public void show() {
        if (showType == TYPE.IDCARD) {
            setMaxLength(18);
            tvNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.text_size_20));
            mViewKeyboard.setKeyboardBLText("X");
        }
        super.show();
    }

}
