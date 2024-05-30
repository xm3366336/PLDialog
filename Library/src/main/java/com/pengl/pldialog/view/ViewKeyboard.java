package com.pengl.pldialog.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.ColorInt;
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
    private final AppCompatImageButton keyboard_btn_bottom_right; // 右下角的按钮
    private final Button keyboard_btn_bottom_left; // 左下角按钮

    public ViewKeyboard(Context context) {
        this(context, null);
    }

    public ViewKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.view_keyboard, this);
        keyboard_btn_bottom_left = findViewById(R.id.keyboard_btn_bottom_left);
        keyboard_btn_bottom_right = findViewById(R.id.keyboard_btn_bottom_right);

        int theme = 1;
        int textColor = 0;
        if (null != attrs) {
            @SuppressLint("CustomViewStyleable") TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.KeyBoard);
            theme = a.getInt(R.styleable.KeyBoard_KB_Theme, 0);
            textColor = a.getColor(R.styleable.KeyBoard_KB_textColor, 0);
            setKeyboardTextSize(a.getDimension(R.styleable.KeyBoard_KB_textSize, getResources().getDimension(R.dimen.pld_px_24)));
            setKeyboardBLShow(a.getBoolean(R.styleable.KeyBoard_KB_BtnBottomLeft_show, false));
            setKeyboardBLText(a.getString(R.styleable.KeyBoard_KB_BtnBottomLeft_text));
            setKeyboardBRImageResource(a.getResourceId(R.styleable.KeyBoard_KB_BtnBottomRight_img, //
                    theme == 1 ? R.mipmap.pld_keyboard_del_light : R.mipmap.pld_keyboard_del_dark));
            a.recycle();
        }

        OnClickListener onClickNum = v -> {
            String num = ((Button) v).getText().toString();
            if (null == onKeyboardListener) {
                return;
            }
            onKeyboardListener.onKeyDown(num);
        };
        keyboard_btn_bottom_left.setOnClickListener(onClickNum);

        OnClickListener onClickBottomRight = v -> {
            if (null == onKeyboardListener) {
                return;
            }
            onKeyboardListener.onKeyDownBottomRight();
        };
        keyboard_btn_bottom_right.setOnClickListener(onClickBottomRight);
        keyboard_btn_bottom_right.setOnLongClickListener(v -> {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDownLongBottomRight();
            }
            return false;
        });

        for (int btnId : keyboard_btn) {
            findViewById(btnId).setOnClickListener(onClickNum);
        }

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
        keyboard_btn_bottom_left.setTextColor(color);
        keyboard_btn_bottom_right.setImageTintList(ColorStateList.valueOf(color));
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
        keyboard_btn_bottom_left.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
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
