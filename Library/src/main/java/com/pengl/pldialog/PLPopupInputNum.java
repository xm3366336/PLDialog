package com.pengl.pldialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.pengl.pldialog.util.ICallBack;
import com.pengl.pldialog.view.ViewKeyboard;

import org.jetbrains.annotations.NotNull;

public class PLPopupInputNum extends PopupWindow {

    private final ViewKeyboard mViewKeyboard;

    public PLPopupInputNum(Context context, @NotNull final ICallBack callBack) {
        View v = View.inflate(context, R.layout.pl_popup_input_num, null);

        this.setContentView(v);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);// 设置SelectPicPopupWindow弹出窗体可点击
        this.setOutsideTouchable(true);// 点back键和view之外的地方可以使其消失
        this.update();// 刷新状态
        this.setBackgroundDrawable(null);
        this.setAnimationStyle(R.style.PLAnimationFadeInOut);// 设置SelectPicPopupWindow弹出窗体动画效果

        mViewKeyboard = v.findViewById(R.id.mViewKeyboard);
        mViewKeyboard.setOnKeyboardClickListener(new ViewKeyboard.OnKeyboardClickListener() {
            @Override
            public void onKeyDown(String num) {
                if (TextUtils.equals(num, "C")) {
                    callBack.onCallBack(1);
                } else {
                    callBack.onCallBack(0, num);
                }
            }

            @Override
            public void onKeyDownBottomRight() {
                callBack.onCallBack(2);
            }

            @Override
            public void onKeyDownLongBottomRight() {

            }
        });
    }

    public ViewKeyboard getViewKeyboard() {
        return mViewKeyboard;
    }

    /**
     * 显示popupWindow
     *
     * @param parent 他爹
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, -5, 0);
        } else {
            this.dismiss();
        }
    }
}
