package com.pengl.pldialog;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.Objects;

/**
 * Created by pengl on 2017/3/21.
 */
public class PLDialogChoice extends BottomSheetDialog {

    private final int margins;

    private final AppCompatButton btn_cancel;
    private final AppCompatTextView tv_title, tv_content;
    private final LinearLayout layout_btns;
    private final ShapeableImageView bg;

    private OnClickListener onConfirmListener;

    public PLDialogChoice(Context context) {
        super(context, R.style.PLAppDialog_TransBg);
        setContentView(R.layout.pl_dialog_choice);
        this.bg = findViewById(R.id.bg);
        this.btn_cancel = findViewById(R.id.btn_cancel);
        this.tv_title = findViewById(R.id.tv_title);
        this.tv_content = findViewById(R.id.tv_content);
        this.layout_btns = findViewById(R.id.layout_btns);
        this.margins = (int) -TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onStart() {
        super.onStart();

        BottomSheetBehavior<FrameLayout> behavior = getBehavior();
        if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public PLDialogChoice setOnClickListener(OnClickListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    public PLDialogChoice setTitle(String title) {
        tv_title.setText(title);
        tv_title.setVisibility(View.VISIBLE);
        return this;
    }

    public PLDialogChoice setContent(String content) {
        tv_content.setText(content);
        tv_content.setVisibility(View.VISIBLE);
        return this;
    }

    public PLDialogChoice setCancelText(String text) {
        btn_cancel.setText(text);
        return this;
    }

    public PLDialogChoice setItems(String[] items) {
        if (null == items || items.length == 0) {
            return this;
        }

        if (items.length == 1) {
            layout_btns.addView(createButton(items[0], 0, 0));
        } else if (items.length == 2) {
            layout_btns.addView(createButton(items[0], 1, 0));
            layout_btns.addView(createButton(items[1], 3, 1));
        } else {
            layout_btns.addView(createButton(items[0], 1, 0));
            for (int i = 1, size = items.length - 1; i < size; i++) {
                layout_btns.addView(createButton(items[i], 2, i));
            }
            layout_btns.addView(createButton(items[items.length - 1], 3, items.length - 1));
        }
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow()).setWindowAnimations(R.style.PLKeyboardStyle);

        btn_cancel.setOnClickListener(view -> {
            if (null != onConfirmListener) {
                onConfirmListener.onClick(PLDialogChoice.this, -1);
            }
            dismiss();
        });
    }

    /**
     * @param str      文字
     * @param style    样式：0 单个、1上面的、2中间的、3下面的
     * @param position 位置点
     * @return btn
     */
    private Button createButton(String str, int style, final int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatButton btn = new AppCompatButton(getContext());
        btn.setText(str);
        btn.setAllCaps(false);
        btn.setTextColor(getContext().getResources().getColor(R.color.text_666_to_999));
        btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

        if (style == 0) {
            btn.setBackgroundResource(R.drawable.btn_r4_stroke_white);
        } else if (style == 1) {
            btn.setBackgroundResource(R.drawable.btn_r4_stroke_white_top);
        } else if (style == 2) {
            btn.setBackgroundResource(R.drawable.btn_r4_stroke_white_middle);
            params.setMargins(0, margins, 0, 0);
        } else if (style == 3) {
            btn.setBackgroundResource(R.drawable.btn_r4_stroke_white_bottom);
            params.setMargins(0, margins, 0, 0);
        }
        btn.setLayoutParams(params);
        btn.setOnClickListener(view -> {
            if (null != onConfirmListener) {
                onConfirmListener.onClick(PLDialogChoice.this, position);
            }
            dismiss();
        });
        return btn;
    }

    /**
     * 设置圆角的大小
     *
     * @param cornerSizeDip 默认是4dip，单位dip
     */
    public PLDialogChoice setBgRounded(int cornerSizeDip) {
        bg.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                .setTopLeftCorner(CornerFamily.ROUNDED, Math.max(cornerSizeDip, 0))
                .setTopRightCorner(CornerFamily.ROUNDED, Math.max(cornerSizeDip, 0))
                .build());
        return this;
    }

    public ShapeableImageView getBg() {
        return bg;
    }

    public AppCompatTextView getTvTitle() {
        return tv_title;
    }

    public AppCompatTextView getTvContent() {
        return tv_content;
    }

    public AppCompatButton getBtnCancel() {
        return btn_cancel;
    }
}
