package com.pengl.pldialog.vehicle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.pengl.pldialog.R;

import java.util.Arrays;
import java.util.List;

/**
 * 为KeyboardView编辑自定义样式
 */
class VehicleKeyboardView extends KeyboardView {

    /**
     * 含省份的简称的输入键盘
     */
    private Keyboard mProvincesKeyBoard;

    private Paint paint;
    private Rect rectBg, rectBound, rectText, rectIcon;

    private int _3dpToPx, _6dpToPx, _32dpToPx;

    public static VehicleKeyboardView newInstance(Context context) {
        return new VehicleKeyboardView(context, null);
    }

    public VehicleKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VehicleKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VehicleKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        paint.setAntiAlias(true);

        rectBg = new Rect();
        rectBound = new Rect();
        rectText = new Rect();
        rectIcon = new Rect();

        _3dpToPx = dip2px(3);
        _6dpToPx = dip2px(6);
        _32dpToPx = dip2px(32);

        mProvincesKeyBoard = new Keyboard(getContext(), R.xml.keyboard_car_number_provinces);
        setKeyboard(mProvincesKeyBoard);
        setPreviewEnabled(false);// 不支持popup显示按钮
    }

    void switchToProvinces() {
        if (getKeyboard() == mProvincesKeyBoard)
            return;
        setKeyboard(mProvincesKeyBoard);
    }

    void switchToLetters() {
        Keyboard mLettersKeyBoard = new Keyboard(getContext(), R.xml.keyboard_car_number_letters, 0,
                mProvincesKeyBoard.getMinWidth(), mProvincesKeyBoard.getHeight());
        setKeyboard(mLettersKeyBoard);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Keyboard keyboard = getKeyboard();
        if (keyboard == null)
            return;

        paint.setColor(ContextCompat.getColor(getContext(), R.color.keyboard_bg_color));
        getDrawingRect(rectBg);
        canvas.drawRect(rectBg, paint);

        List<Keyboard.Key> keys = keyboard.getKeys();
        if (null == keys || keys.size() <= 0)
            return;

        for (Keyboard.Key key : keys) {

            // 1 绘制按钮的背景（左右-3dp 上下-6dp）
            Drawable dr;
            if (key.codes[0] == 10086) {
                if (key.pressed)
                    dr = ContextCompat.getDrawable(getContext(), R.drawable.bg_keyboard_gray);
                else
                    dr = null;
            } else if (Arrays.asList(
                    Keyboard.KEYCODE_CANCEL,
                    "ABC".hashCode(),
                    "中文".hashCode(),
                    Keyboard.KEYCODE_DELETE).contains(key.codes[0])) {
                dr = ContextCompat.getDrawable(getContext(), key.pressed ? R.drawable.bg_keyboard_white : R.drawable.bg_keyboard_gray);
            } else {
                dr = ContextCompat.getDrawable(getContext(), key.pressed ? R.drawable.bg_keyboard_gray : R.drawable.bg_keyboard_white);
            }

            rectBound.left = key.x + _3dpToPx;
            rectBound.top = key.y + _6dpToPx;
            rectBound.right = key.x + key.width - _3dpToPx;
            rectBound.bottom = key.y + key.height - _6dpToPx;

            if (null != dr) {
                dr.setBounds(rectBound);
                dr.draw(canvas);
            }

            // 2 绘制文字
            if (key.label != null) {
                if (Arrays.asList("ABC".hashCode(), "中文".hashCode()).contains(key.codes[0])) {
                    paint.setTextSize(rectBound.height() - 5 * _6dpToPx);
                } else {
                    paint.setTextSize(rectBound.height() - 4 * _6dpToPx);
                }
                paint.setColor(getContext().getResources().getColor(R.color.text_333));

                rectText.left = key.x;
                rectText.top = key.y;
                rectText.right = key.x + key.width;
                rectText.bottom = key.y + key.height;

                Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                int baseline = (rectText.bottom + rectText.top - fontMetrics.bottom - fontMetrics.top) / 2;
                // 实现水平居中，drawText对应改为传入targetRect.centerX()
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(key.label.toString(), rectText.centerX(), baseline, paint);
            }

            // 3 绘制图标
            if (key.icon != null) {
                rectIcon.left = rectBound.centerX() - _32dpToPx / 2;
                rectIcon.right = rectBound.centerX() + _32dpToPx / 2;
                rectIcon.top = rectBound.centerY() - _32dpToPx / 2;
                rectIcon.bottom = rectBound.centerY() + _32dpToPx / 2;

                key.icon.setBounds(rectIcon);
                key.icon.draw(canvas);
            }
        }
    }

    /**
     * dip转px
     *
     * @param dpValue dip
     * @return px
     */
    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
