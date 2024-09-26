package com.pengl.pldialog.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.pengl.pldialog.R;

public class ViewKeyboard extends androidx.constraintlayout.widget.ConstraintLayout {

    private final int[] keyboard_btn = new int[]{//
            R.id.keyboard_btn_0, R.id.keyboard_btn_1, //
            R.id.keyboard_btn_2, R.id.keyboard_btn_3, //
            R.id.keyboard_btn_4, R.id.keyboard_btn_5, //
            R.id.keyboard_btn_6, R.id.keyboard_btn_7, //
            R.id.keyboard_btn_8, R.id.keyboard_btn_9
    };
    private final View keyboard_btn_bottom_right; // 右下角的按钮
    private final ImageView keyboard_btn_bottom_right_img;
    private final TextView keyboard_btn_bottom_right_txt;
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
        keyboard_btn_bottom_right_img = findViewById(R.id.keyboard_btn_bottom_right_img);
        keyboard_btn_bottom_right_txt = findViewById(R.id.keyboard_btn_bottom_right_txt);

        if (null != attrs) {
            @SuppressLint("CustomViewStyleable") TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyBoard);
            int theme = a.getInt(R.styleable.KeyBoard_KB_Theme, 0);
            int textColor = a.getColor(R.styleable.KeyBoard_KB_textColor, getResources().getColor(theme == 1 ? R.color.colorWhite : R.color.text_666));

            setKeyboardTextColor(textColor);
            setKeyboardTextSize(a.getDimension(R.styleable.KeyBoard_KB_textSize, getResources().getDimension(R.dimen.pld_px_24)));

            String leftText = a.getString(R.styleable.KeyBoard_KB_BtnBottomLeft_text);
            if (TextUtils.isEmpty(leftText)) {
                keyboard_btn_bottom_left.setVisibility(View.INVISIBLE);
            } else {
                keyboard_btn_bottom_left.setVisibility(View.VISIBLE);
                setKeyboardBLText(leftText);
            }
            setKeyboardBLTextColor(a.getColor(R.styleable.KeyBoard_KB_BtnBottomLeft_textColor, textColor));
            setKeyboardBLTextSize(a.getDimension(R.styleable.KeyBoard_KB_BtnBottomLeft_textSize, getResources().getDimension(R.dimen.pld_px_24)));

            String rightText = a.getString(R.styleable.KeyBoard_KB_BtnBottomRight_text);
            if (TextUtils.isEmpty(rightText)) {
                setKeyboardBRImageResource(a.getResourceId(R.styleable.KeyBoard_KB_BtnBottomRight_img, //
                        theme == 1 ? R.mipmap.pld_keyboard_del_light : R.mipmap.pld_keyboard_del_dark));
            } else {
                setKeyboardBRText(rightText);
            }
            setKeyboardBRTextColor(a.getColor(R.styleable.KeyBoard_KB_BtnBottomRight_textColor, textColor));
            setKeyboardBRTextSize(a.getDimension(R.styleable.KeyBoard_KB_BtnBottomRight_textSize, getResources().getDimension(R.dimen.pld_px_24)));

            setKeyboardBtnBg(a.getResourceId(R.styleable.KeyBoard_KB_BtnBg, R.drawable.list_selector)); //
            a.recycle();
        }

        keyboard_btn_bottom_left.setOnClickListener(v -> {

            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDownBottomLeft(false);
            }
        });
        keyboard_btn_bottom_left.setOnLongClickListener(v -> {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDownBottomLeft(true);
            }
            return false;
        });
        keyboard_btn_bottom_right.setOnClickListener(v -> {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDownBottomRight(false);
            }
        });
        keyboard_btn_bottom_right.setOnLongClickListener(v -> {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyDownBottomRight(true);
            }
            return false;
        });

        for (int btnId : keyboard_btn) {
            findViewById(btnId).setOnClickListener(v -> {
                String num = ((Button) v).getText().toString();
                if (null != onKeyboardListener) {
                    onKeyboardListener.onKeyDown(num);
                }
            });
        }
    }

    /**
     * 设置按钮背景色
     *
     * @param resId 按钮的背景色
     */
    public void setKeyboardBtnBg(@DrawableRes int resId) {
        for (int btnId : keyboard_btn) {
            findViewById(btnId).setBackgroundResource(resId);
        }
        keyboard_btn_bottom_left.setBackgroundResource(resId);
        keyboard_btn_bottom_right.setBackgroundResource(resId);
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

    /**
     * 右下角显示的按钮图标
     *
     * @param resId 资源id
     */
    public void setKeyboardBRImageResource(@DrawableRes int resId) {
        keyboard_btn_bottom_right_img.setImageResource(resId);
    }

    // ------------------ 左下方，显示文字的内容、大小及颜色 ----------------------------------------------------------------
    public void setKeyboardBLText(String text) {
        if (TextUtils.isEmpty(text)) {
            keyboard_btn_bottom_left.setText("");
            keyboard_btn_bottom_left.setVisibility(View.INVISIBLE);
        } else {
            keyboard_btn_bottom_left.setText(text);
            keyboard_btn_bottom_left.setVisibility(View.VISIBLE);
        }
    }

    public void setKeyboardBLTextColor(@ColorInt int color) {
        keyboard_btn_bottom_left.setTextColor(color);
    }

    public void setKeyboardBLTextSize(float textSize) {
        keyboard_btn_bottom_left.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    // ------------------ 右下方，若为文字，显示文字内容、大小及颜色 ----------------------------------------------------------
    public void setKeyboardBRText(String text) {
        keyboard_btn_bottom_right_txt.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setKeyboardBRTextSize(float textSize) {
        keyboard_btn_bottom_right_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void setKeyboardBRTextColor(@ColorInt int color) {
        keyboard_btn_bottom_right_txt.setTextColor(color);
    }

    public Button getBtnBottomLeft() {
        return keyboard_btn_bottom_left;
    }

    public ImageView getBtnBottomRightImg() {
        return keyboard_btn_bottom_right_img;
    }

    public TextView getBtnBottomRightTxt() {
        return keyboard_btn_bottom_right_txt;
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

        void onKeyDownBottomLeft(boolean isLong);

        void onKeyDownBottomRight(boolean isLong);
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
