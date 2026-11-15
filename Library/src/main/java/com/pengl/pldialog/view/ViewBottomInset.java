package com.pengl.pldialog.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 底部占位视图。
 * <p>
 * 高度自动等于「导航条（系统栏）与刘海屏底部」的 inset，
 * 用于在 Edge-to-Edge 下把导航条区域让给弹窗自身的背景，
 * 从而让导航条与弹窗背景融为一体，消除色差与割裂感。
 * <p>
 * 当输入法弹出时（{@link WindowInsetsCompat.Type#ime()}），
 * 高度会自动变为输入法高度，保证输入框等交互内容不会被键盘遮挡。
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
            setInsetBottom(resolveInsetBottom(windowInsets));
            return windowInsets;
        });
    }

    private int resolveInsetBottom(WindowInsetsCompat windowInsets) {
        int systemBottom = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout()).bottom;

        int imeBottom = 0;
        if (windowInsets.isVisible(WindowInsetsCompat.Type.ime())) {
            imeBottom = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
        }

        // 取较大值：键盘弹出时以键盘高度为准，否则以导航条高度为准
        return Math.max(systemBottom, imeBottom);
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
