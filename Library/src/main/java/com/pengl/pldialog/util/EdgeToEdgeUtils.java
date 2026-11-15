package com.pengl.pldialog.util;

import android.graphics.Color;
import android.os.Build;
import android.view.Window;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * Edge-to-Edge 适配工具
 * <p>
 * 按照 Android 官方规范（https://developer.android.com/develop/ui/views/layout/edge-to-edge）：
 * 弹窗内容延伸至系统栏（状态栏、导航条）下方，导航条区域由弹窗自身的背景色填充，
 * 从而与应用界面自然融为一体，消除色差与割裂感。
 */
public final class EdgeToEdgeUtils {

    private EdgeToEdgeUtils() {
    }

    /**
     * 让窗口延伸到导航条下方，并让导航条透明
     * <p>
     * 悬浮窗口（windowIsFloating=true）的框架会被系统裁剪在导航条之上，
     * 因此需要使用全屏窗口才能真正绘制到导航条区域。
     *
     * @param window 弹窗的窗口
     */
    public static void applyEdgeToEdge(Window window) {
        if (null == window) {
            return;
        }

        WindowCompat.setDecorFitsSystemWindows(window, false);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 关闭导航条的对比度强制，否则系统会在导航条区域叠加一层半透明遮罩，
            // 与弹窗背景产生色差（官方 Edge-to-Edge 规范要求关闭）
            window.setNavigationBarContrastEnforced(false);
            window.setStatusBarContrastEnforced(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.setNavigationBarDividerColor(Color.TRANSPARENT);
        }

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());
        if (null != controller) {
            // 弹窗背景为浅色，导航条使用深色图标
            controller.setAppearanceLightNavigationBars(true);
        }
    }
}
