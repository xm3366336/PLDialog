package com.pengl.pldialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.pengl.pldialog.util.ICallBack;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 通用的输入框
 */
public class PLDialogInput extends Dialog {

    private ShapeableImageView bg;
    private AppCompatTextView tv_title, tv_title_sub;
    private AppCompatEditText etInput;
    private AppCompatButton btn_confirm, btn_cancel;
    private ICallBack callback;

    private boolean isMustInput;// 是否必须输入，默认是的
    private boolean isShowKeyboard;// 是否弹出键盘

    private int showType = 0;// 显示样式

    public PLDialogInput(Context context) {
        super(context, R.style.PLAppDialog_TransBg);
        init(R.layout.pl_dialog_input);
    }

    public PLDialogInput(Context context, int showType) {
        super(context, R.style.PLAppDialog_TransBg);
        this.showType = showType;
        init(showType == 2 ? R.layout.pl_dialog_input_2 : R.layout.pl_dialog_input);
    }

    private void init(@LayoutRes int layoutResID) {
        setContentView(layoutResID);
        bg = findViewById(R.id.bg);
        etInput = findViewById(R.id.et_input);
        tv_title = findViewById(R.id.tv_title);
        tv_title_sub = findViewById(R.id.tv_title_sub);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_cancel = findViewById(R.id.btn_cancel);
        this.isMustInput = true;
        this.isShowKeyboard = true;
    }

    public PLDialogInput setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public PLDialogInput setTitleSub(String titleSub) {
        tv_title_sub.setText(titleSub);
        return this;
    }

    /**
     * 设置回调
     *
     * @param callback 回调
     */
    public PLDialogInput setCallback(ICallBack callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 显示原内容，并且全部选中
     *
     * @param content 内容
     */
    public PLDialogInput setOriginContent(String content) {
        etInput.setText(content);
        Selection.selectAll(etInput.getText());
        return this;
    }

    /**
     * 设置提示内容
     *
     * @param hint 提示内容
     */
    public PLDialogInput setInputHint(String hint) {
        etInput.setHint(hint);
        return this;
    }

    /**
     * 是否必须输入
     *
     * @param mustInput true是的（默认），false无须
     */
    public PLDialogInput setMustInput(boolean mustInput) {
        isMustInput = mustInput;
        return this;
    }

    /**
     * 是否弹出键盘
     *
     * @param showKeyboard true是的
     */
    public PLDialogInput setShowKeyboard(boolean showKeyboard) {
        isShowKeyboard = showKeyboard;
        return this;
    }

    public EditText getEditText() {
        return etInput;
    }

    public ShapeableImageView getBg() {
        return bg;
    }

    public AppCompatTextView getTextViewTitle() {
        return tv_title;
    }

    public AppCompatTextView getTextViewTitleSub() {
        return tv_title_sub;
    }

    public AppCompatButton getBtnConfirm() {
        return btn_confirm;
    }

    public AppCompatButton getBtnCancel() {
        return btn_cancel;
    }

    /**
     * 设置圆角的大小
     *
     * @param cornerSizeDip 默认是4dip，单位dip
     */
    public PLDialogInput setBgRounded(int cornerSizeDip) {
        if (showType == 2) {
            bg.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                    .setAllCorners(CornerFamily.ROUNDED, Math.max(cornerSizeDip, 0)).build());
        } else {
            bg.setShapeAppearanceModel(ShapeAppearanceModel.builder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, Math.max(cornerSizeDip, 0))
                    .setTopRightCorner(CornerFamily.ROUNDED, Math.max(cornerSizeDip, 0))
                    .build());
        }
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (showType == 2) {

        } else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.BOTTOM;
            getWindow().setAttributes(lp);
            getWindow().setWindowAnimations(R.style.PLKeyboardStyle);
        }

        btn_cancel.setOnClickListener(v -> dismiss());
        btn_confirm.setOnClickListener(view -> {
            String input = etInput.getText().toString().trim();
            if (isMustInput && TextUtils.isEmpty(input)) {
                Toast.makeText(getContext(), "还没有输入", Toast.LENGTH_SHORT).show();
                return;
            }

            callback.onCallBack(input);
            dismiss();
        });

        etInput.setFocusable(true);
        etInput.requestFocus();
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isMustInput) {
                    String input = etInput.getText().toString().trim();
                    btn_confirm.setEnabled(!TextUtils.isEmpty(input));
                }
            }
        });
    }

    @Override
    public void show() {
        // 强制弹出键盘
        if (isShowKeyboard) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    InputMethodManager m = (InputMethodManager) etInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (m != null)
                        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }, 300);
        }
        super.show();
    }

}
