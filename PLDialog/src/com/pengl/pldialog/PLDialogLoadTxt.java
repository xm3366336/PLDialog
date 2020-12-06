package com.pengl.pldialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PLDialogLoadTxt extends Dialog {

    private static PLDialogLoadTxt loadDialog;
    private boolean canNotCancel;// 不能取消 true不能取消，false能取消

    /**
     * 构造
     *
     * @param ctx          上下文
     * @param canNotCancel 不能取消 true不能取消，false能取消
     */
    public PLDialogLoadTxt(final Context ctx, String tvMsg, boolean canNotCancel) {
        super(ctx);

        this.canNotCancel = canNotCancel;
        this.getContext().setTheme(R.style.AppDialog_TransBg);
        setContentView(R.layout.pl_dialog_loading_txt);

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        window.setAttributes(attributesParams);

        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ((TextView) findViewById(R.id.tv_msg)).setText(tvMsg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
            e.printStackTrace();
            loadDialog = null;
        }
    }

}
