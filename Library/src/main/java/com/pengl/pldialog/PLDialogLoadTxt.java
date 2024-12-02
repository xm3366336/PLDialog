package com.pengl.pldialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class PLDialogLoadTxt extends Dialog {

    private static PLDialogLoadTxt loadDialog;
    private final boolean canNotCancel;// 不能取消 true不能取消，false能取消

    /**
     * 构造
     *
     * @param ctx          上下文
     * @param canNotCancel 不能取消 true不能取消，false能取消
     */
    public PLDialogLoadTxt(final Context ctx, String tvMsg, boolean canNotCancel) {
        super(ctx);

        this.canNotCancel = canNotCancel;
        this.getContext().setTheme(R.style.PLAppDialog_TransBg);
        setContentView(R.layout.pl_dialog_loading_txt);

        Window window = getWindow();
        if (null != window) {
            WindowManager.LayoutParams attributesParams = window.getAttributes();
            attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            attributesParams.dimAmount = 0.5f;
            window.setAttributes(attributesParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
            } else {
                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }

        ((TextView) findViewById(R.id.tv_msg)).setText(tvMsg);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canNotCancel) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 显示
     *
     * @param context      Context 上下文
     * @param tvMsg        提示内容
     * @param canNotCancel 不能取消 true不能取消，false能取消
     */
    public static void show(Context context, String tvMsg, boolean canNotCancel) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new PLDialogLoadTxt(context, tvMsg, canNotCancel);
        loadDialog.setCanceledOnTouchOutside(!canNotCancel);
        loadDialog.show();
    }

    public static void show(Context context) {
        show(context, "加载中", false);
    }

    public static void show(Context context, String tvMsg) {
        show(context, tvMsg, false);
    }

    /**
     * 关闭
     */
    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }
                }
                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            loadDialog = null;
        }
    }

}
