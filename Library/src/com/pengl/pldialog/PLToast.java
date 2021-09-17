package com.pengl.pldialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 这是一个通用的Toast提示消息框
 */
public class PLToast {

    public static void show(Context ctx, String message) {
        if (TextUtils.isEmpty(message) || null == ctx) {
            return;
        }
        if (message.length() > 12)
            Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context ctx, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    public static void showSucc(Context ctx, String message) {
        showStyle(ctx, 1, message);
    }

    public static void showInfo(Context ctx, String message) {
        showStyle(ctx, 2, message);
    }

    public static void showAlert(Context ctx, String message) {
        showStyle(ctx, 3, message);
    }

    public static void showErr(Context ctx, String message) {
        showStyle(ctx, 4, message);
    }

    private static void showStyle(Context ctx, int type, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        Toast toast = new Toast(ctx);
        LayoutInflater inflate = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.pl_toast, null);
        TextView tv = v.findViewById(R.id.toast_tv);
        tv.setText(message);
        tv.setTextColor(ctx.getResources().getColor(R.color.colorWhite));
        ImageView img = v.findViewById(R.id.toast_img);

        if (type == 1) {
            img.setImageResource(R.drawable.ic_toast_right);
        } else if (type == 2) {
            img.setImageResource(R.drawable.ic_toast_info);
        } else if (type == 3) {
            img.setImageResource(R.drawable.ic_toast_alert);
        } else if (type == 4) {
            img.setImageResource(R.drawable.ic_toast_err);
        }

        toast.setView(v);
        toast.setGravity(Gravity.CENTER, 0, 0);

        if (message.length() > 12)
            toast.setDuration(Toast.LENGTH_LONG);
        else
            toast.setDuration(Toast.LENGTH_SHORT);

        toast.show();
    }

    /**
     * 居中显示一个简单的toast
     *
     * @param ctx     上下文
     * @param message 内容
     */
    public static void showSimple(Context ctx, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        int dip8 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                ctx.getResources().getDisplayMetrics());
        TextView tv = new TextView(ctx);
        tv.setBackgroundResource(R.drawable.bg_r8_black_t99);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        tv.setTextColor(ctx.getResources().getColor(R.color.colorWhite));
        tv.setPadding(3 * dip8, dip8, 3 * dip8, dip8);
        tv.setText(message);
        tv.setGravity(Gravity.CENTER);

        Toast toast = new Toast(ctx);
        toast.setView(tv);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
