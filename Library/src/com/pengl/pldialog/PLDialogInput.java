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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.pengl.pldialog.util.ICallBack;

/**
 * 通用的输入框
 */
public class PLDialogInput extends Dialog {

    private final AppCompatEditText etInput;
    private AppCompatButton btn_confirm;
    private ICallBack callback;

    private boolean isMustInput;// 是否必须输入，默认是的

    public PLDialogInput(Context context, String title, String subTitle) {
        super(context, R.style.AppDialog_TransBg);
        this.isMustInput = true;
        setContentView(R.layout.pl_dialog_input);
        etInput = findViewById(R.id.et_input);
        ((TextView) findViewById(R.id.tv_title)).setText(title);
        ((TextView) findViewById(R.id.tv_title_sub)).setText(subTitle);
        findViewById(R.id.btn_cancel).setOnClickListener(v -> dismiss());
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
     * 显示原内容，并且全部选中
     *
     * @param content 内容
     */
    public void setOriginContent(String content) {
        etInput.setText(content);
        Selection.selectAll(etInput.getText());
    }

    /**
     * 设置提示内容
     *
     * @param hint 提示内容
     */
    public void setInputHint(String hint) {
        etInput.setHint(hint);
    }

    /**
     * 是否必须输入
     *
     * @param mustInput true是的（默认），false无须
     */
    public void setMustInput(boolean mustInput) {
        isMustInput = mustInput;
    }

    public EditText getEditText() {
        return etInput;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);

        btn_confirm = findViewById(R.id.btn_confirm);
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
        if (isMustInput) {
            // 强制弹出键盘
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
