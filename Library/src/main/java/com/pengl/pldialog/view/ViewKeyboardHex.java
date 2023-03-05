package com.pengl.pldialog.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatButton;

import com.pengl.pldialog.R;

public class ViewKeyboardHex extends androidx.constraintlayout.widget.ConstraintLayout {

    private final int[] keyboard_btn = new int[]{//
            R.id.keyboard_btn_0, R.id.keyboard_btn_1, //
            R.id.keyboard_btn_2, R.id.keyboard_btn_3, //
            R.id.keyboard_btn_4, R.id.keyboard_btn_5, //
            R.id.keyboard_btn_6, R.id.keyboard_btn_7, //
            R.id.keyboard_btn_8, R.id.keyboard_btn_9, //
            R.id.keyboard_btn_a, R.id.keyboard_btn_b, //
            R.id.keyboard_btn_c, R.id.keyboard_btn_d, //
            R.id.keyboard_btn_e, R.id.keyboard_btn_f, //
    };

    private final AppCompatButton keyboard_btn_clean, keyboard_btn_ok;

    public ViewKeyboardHex(Context context) {
        this(context, null);
    }

    public ViewKeyboardHex(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewKeyboardHex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_keyboard_hex, this);
        keyboard_btn_clean = findViewById(R.id.keyboard_btn_clean);
        keyboard_btn_ok = findViewById(R.id.keyboard_btn_ok);

        int theme = 1;
        int textColor = 0;
        if (null != attrs) {
            @SuppressLint("CustomViewStyleable") TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyBoard);
            theme = a.getInt(R.styleable.KeyBoard_KB_Theme, 0);
            textColor = a.getColor(R.styleable.KeyBoard_KB_textColor, 0);
            setKeyboardTextSize(a.getDimension(R.styleable.KeyBoard_KB_textSize, getResources().getDimension(R.dimen.text_size_24)));
            setKeyboardAllCap(a.getBoolean(R.styleable.KeyBoard_KB_AllCap, false));
            a.recycle();
        }

        OnClickListener onClickNum = v -> {
            String num = ((Button) v).getText().toString();
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDown(num);
            }
        };
        for (int btnId : keyboard_btn) {
            findViewById(btnId).setOnClickListener(onClickNum);
        }

        keyboard_btn_clean.setOnClickListener(v -> {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDownClean();
            }
        });

        keyboard_btn_ok.setOnClickListener(v -> {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDownFinish();
            }
        });

        if (textColor == 0) {
            setKeyboardTextColor(getResources().getColor(theme == 1 ? R.color.colorWhite : R.color.text_666));
        } else {
            setKeyboardTextColor(textColor);
        }
    }

    /**
     * 设置数字字体颜色
     *
     * @param color 颜色id
     */
    public void setKeyboardTextColor(@ColorInt int color) {
        for (int btnId : keyboard_btn) {
            ((Button) findViewById(btnId)).setTextColor(color);
        }
        keyboard_btn_clean.setTextColor(color);
        keyboard_btn_ok.setTextColor(color);
    }

    /**
     * 设置数字字体颜色
     *
     * @param textSize 字体大小 px
     */
    public void setKeyboardTextSize(float textSize) {
        for (int btnId : keyboard_btn) {
            ((Button) findViewById(btnId)).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    public void setKeyboardAllCap(boolean isAllCap) {
        ((Button) findViewById(R.id.keyboard_btn_a)).setText(isAllCap ? "A" : "a");
        ((Button) findViewById(R.id.keyboard_btn_b)).setText(isAllCap ? "B" : "b");
        ((Button) findViewById(R.id.keyboard_btn_c)).setText(isAllCap ? "C" : "c");
        ((Button) findViewById(R.id.keyboard_btn_d)).setText(isAllCap ? "D" : "d");
        ((Button) findViewById(R.id.keyboard_btn_e)).setText(isAllCap ? "E" : "e");
        ((Button) findViewById(R.id.keyboard_btn_f)).setText(isAllCap ? "F" : "f");
    }

    public AppCompatButton getKeyboardClean() {
        return keyboard_btn_clean;
    }

    public AppCompatButton getKeyboardOK() {
        return keyboard_btn_ok;
    }

    private OnKeyboardClickListener onKeyboardListener;

    /**
     * 按钮的事件监听
     *
     * @param onKeyboardListener 事件
     */
    public void setOnKeyboardClickListener(OnKeyboardClickListener onKeyboardListener) {
        this.onKeyboardListener = onKeyboardListener;
    }

    public interface OnKeyboardClickListener {
        void onKeyDown(String num);

        void onKeyDownClean();

        void onKeyDownFinish();
    }

    private int getDip(int dip, DisplayMetrics dm) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm);
    }

    public void hide() {
        if (getVisibility() == View.GONE)
            return;
        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_exit));
        setVisibility(View.GONE);
    }

    public void show() {
        if (getVisibility() == View.VISIBLE)
            return;

        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_enter));
        setVisibility(View.VISIBLE);
    }
}
