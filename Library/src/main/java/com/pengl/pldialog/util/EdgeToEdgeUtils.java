package com.pengl.pldialog.util;

import android.graphics.Color;
import android.os.Build;
import android.view.Window;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * Edge-to-Edge 适配工具
 * <p>
 * 按照 Android 官方规范（<a href="https://developer.android.com/develop/ui/views/layout/edge-to-edge">...</a>）：
 * 弹窗内容延伸至系统栏（状态栏、导航条）下方，导航条区域由弹窗自身的背景色填充，
 * 从而与应用界面自然融为一体，消除色差与割裂感。
 * <p>
 * 兼容性说明：
 * <ul>
 *     <li>minSdk 24 ~ 34：系统栏颜色可编程控制，设置为透明。</li>
 *     <li>API 35（Android 15）起：系统强制透明系统栏，{@code setStatusBarColor}/{@code setNavigationBarColor} 已废弃且不再生效，因此跳过调用，避免使用已废弃 API。</li>
 *     <li>API 37（compileSdk 37）：{@code setStatusBarColor}/{@code setNavigationBarColor} 以及 {@code android:statusBarColor}/{@code android:navigationBarColor} 属性已标记废弃，这里统一改为代码内按 SDK 版本判断调用，并屏蔽废弃告警。</li>
 * </ul>
 */
public final class EdgeToEdgeUtils {

    /**
     * API 35（Android 15）起系统强制透明系统栏，
     * {@code setStatusBarColor}/{@code setNavigationBarColor} 不再生效。
     * 这里使用数值常量而非 {@code Build.VERSION_CODES.VANILLA_ICE_CREAM}，
     * 避免因 compileSdk 版本变化导致符号缺失而编译失败。
     */
    private static final int API_VANILLA_ICE_CREAM = 35;

    private EdgeToEdgeUtils() {
    }

    /**
     * 让窗口延伸到导航条下方，并让导航条透明（导航条使用深色图标）。
     *
     * @param window 弹窗的窗口
     */
    public static void applyEdgeToEdge(Window window) {
        applyEdgeToEdge(window, true);
    }

    /**
     * 让窗口延伸到导航条下方，并让导航条透明。
     *
     * @param window             弹窗的窗口
     * @param lightNavigationBar true 表示导航条使用深色图标（浅色背景），
     *                           false 表示导航条使用浅色图标（深色背景）
     */
    @SuppressWarnings("deprecation")
    public static void applyEdgeToEdge(Window window, boolean lightNavigationBar) {
        if (null == window) {
            return;
        }

        // 内容延伸到系统栏下方（API 30+ 走 Window.setDecorFitsSystemWindows，低版本走兼容实现）
        WindowCompat.setDecorFitsSystemWindows(window, false);

        if (Build.VERSION.SDK_INT < API_VANILLA_ICE_CREAM) {
            // API 35+ 系统强制透明系统栏，调用已废弃 API 无效，故跳过
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 关闭系统栏对比度强制，否则系统会在导航条区域叠加一层半透明遮罩，
            // 与弹窗背景产生色差（官方 Edge-to-Edge 规范要求关闭）
            window.setNavigationBarContrastEnforced(false);
            window.setStatusBarContrastEnforced(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.setNavigationBarDividerColor(Color.TRANSPARENT);
        }

        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightNavigationBars(lightNavigationBar);
    }
}
