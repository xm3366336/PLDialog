package com.pengl.pldialog.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageButton;

import com.pengl.pldialog.R;

public class ViewKeyboard extends androidx.constraintlayout.widget.ConstraintLayout {

    private final int[] keyboard_btn = new int[]{//
            R.id.keyboard_btn_0, R.id.keyboard_btn_1, //
            R.id.keyboard_btn_2, R.id.keyboard_btn_3, //
            R.id.keyboard_btn_4, R.id.keyboard_btn_5, //
            R.id.keyboard_btn_6, R.id.keyboard_btn_7, //
            R.id.keyboard_btn_8, R.id.keyboard_btn_9
    };
    private AppCompatImageButton keyboard_btn_bottom_right; // 右下角的按钮
    private Button keyboard_btn_bottom_left; // 左下角按钮

    public ViewKeyboard(Context context) {
        super(context);
        init(context, null);
    }

    public ViewKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ViewKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.view_keyboard, this);
        keyboard_btn_bottom_left = findViewById(R.id.keyboard_btn_bottom_left);
        keyboard_btn_bottom_right = findViewById(R.id.keyboard_btn_bottom_right);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.KeyBoard);
        int theme = a.getInt(R.styleable.KeyBoard_KBTheme, 0);
        setKeyboardBLShow(a.getBoolean(R.styleable.KeyBoard_KB_BtnBottomLeft_show, false));
        setKeyboardBLText(a.getString(R.styleable.KeyBoard_KB_BtnBottomLeft_text));
        setKeyboardBRImageResource(a.getResourceId(R.styleable.KeyBoard_KB_BtnBottomRight_img, //
                theme == 1 ? R.mipmap.pld_keyboard_del_light : R.mipmap.pld_keyboard_del_dark));
        a.recycle();


        keyboard_btn_bottom_left.setOnClickListener(onClickNum);
        setKeyboardTheme(R.id.keyboard_btn_bottom_left, theme == 1 ? R.color.colorWhite : R.color.text_666);

        keyboard_btn_bottom_right.setOnClickListener(onClickBottomRight);
        keyboard_btn_bottom_right.setOnLongClickListener(v -> {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDownLongBottomRight();
            }
            return false;
        });

        for (int btnId : keyboard_btn) {
            findViewById(btnId).setOnClickListener(onClickNum);
            setKeyboardTheme(btnId, theme == 1 ? R.color.colorWhite : R.color.text_666);
        }

    }

    /**
     * 按主题的样式显示
     *
     * @param resId 资源id
     * @param color 颜色id
     */
    private void setKeyboardTheme(int resId, @ColorRes int color) {
        ((Button) findViewById(resId)).setTextColor(getResources().getColor(color));
    }

    /**
     * 右下角显示的按钮图标
     *
     * @param resId 资源id
     */
    public void setKeyboardBRImageResource(@DrawableRes int resId) {
        keyboard_btn_bottom_right.setImageResource(resId);
    }

    /**
     * 是否显示左下角的按钮
     *
     * @param isShow true显示，否则隐藏但占位
     */
    public void setKeyboardBLShow(boolean isShow) {
        keyboard_btn_bottom_left.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置左下角按钮的值
     *
     * @param text 值
     */
    public void setKeyboardBLText(String text) {
        if (TextUtils.isEmpty(text)) {
            keyboard_btn_bottom_left.setText("");
        } else {
            keyboard_btn_bottom_left.setText(text);
        }
    }

    private final OnClickListener onClickNum = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String num = ((Button) v).getText().toString();
            if (null == onKeyboardListener) {
                return;
            }
            onKeyboardListener.onKeyDown(num);
        }
    };

    private final OnClickListener onClickBottomRight = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (null == onKeyboardListener) {
                return;
            }
            onKeyboardListener.onKeyDownBottomRight();
        }
    };

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

        void onKeyDownBottomRight();

        void onKeyDownLongBottomRight();
    }

    /**
     * 获取推荐的高度
     *
     * @return 高度 px
     */
    public int getRecommendHeight() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        double scale = dm.heightPixels * 1.0 / dm.widthPixels;
        // 全面屏一般的比例为：17:9=1.889、19:10=1.9、18.5:9=2.056、18:9=2、2.16
        // 正常屏一般是：16:9=1.778
        if (scale >= 2.1) {
            return getDip(296, dm);// 每行高度74
        } else if (scale >= 2.05) {
            return getDip(272, dm);// 每行高度68
        } else if (scale >= 2) {
            return getDip(256, dm);// 每行高度64
        } else if (scale >= 1.9) {
            return getDip(240, dm);// 每行高度60
        } else if (scale >= 1.8) {
            return getDip(232, dm);// 每行高度58
        } else {
            return getDip(216, dm); // 每行高度54
        }
    }

    private int getDip(int dip, DisplayMetrics dm) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm);
    }

    public void hide() {
        if (getVisibility() == View.GONE)
            return;
        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_up_out));
        setVisibility(View.GONE);
    }

    public void show() {
        if (getVisibility() == View.VISIBLE)
            return;

        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_up_in));
        setVisibility(View.VISIBLE);
    }
}
