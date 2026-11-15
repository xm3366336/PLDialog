package com.pengl.pldialog.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 底部导航条占位 View
 * <p>
 * 高度等于系统导航条的高度，用于把弹窗底部的内容向上顶，
 * 而导航条所在的区域则由弹窗自身的背景色填充，
 * 从而让导航条与弹窗内容自然融为一体（Edge-to-Edge）。
 */
public class ViewBottomInset extends View {

    private int insetBottom;

    public ViewBottomInset(Context context) {
        this(context, null);
    }

    public ViewBottomInset(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewBottomInset(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ViewCompat.setOnApplyWindowInsetsListener(this, (v, windowInsets) -> {
            setInsetBottom(windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout()).bottom);
            return windowInsets;
        });
    }

    private void setInsetBottom(int insetBottom) {
        if (this.insetBottom == insetBottom) {
            return;
        }
        this.insetBottom = insetBottom;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), insetBottom);
    }
}
