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
 * 弹窗内容延伸至系统栏（状态栏、导航条）下方，导航条区域由弹窗自身的背景色填充，从而与应用界面自然融为一体，消除色差与割裂感。
 * <p>
 * 实现上复用 Material 内部的 {@code EdgeToEdgeUtils} 完成系统栏色彩决策，并补齐它未覆盖的两项：系统栏对比度强制关闭与导航条分隔线透明。
 * <p>
 * 兼容性说明：
 * <ul>
 *     <li>minSdk 24 ~ 34：系统栏颜色可编程控制，设置为透明。</li>
 *     <li>API 35（Android 15）起：系统强制透明系统栏，{@code setStatusBarColor}/{@code setNavigationBarColor} 已废弃且不再生效，Material 内部实现同样做了版本判断（{@code SDK_INT < 35} 才调用）。</li>
 *     <li>API 37（compileSdk 37）：{@code setStatusBarColor}/{@code setNavigationBarColor} 以及 {@code android:statusBarColor}/{@code android:navigationBarColor} 属性已标记废弃，主题中不再声明，统一由代码按 SDK 版本处理。</li>
 * </ul>
 */
public final class EdgeToEdgeUtils {

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
    public static void applyEdgeToEdge(Window window, boolean lightNavigationBar) {
        if (null == window) {
            return;
        }

        // 复用 Material 内部实现：处理 setDecorFitsSystemWindows、系统栏颜色与状态栏图标明暗
        com.google.android.material.internal.EdgeToEdgeUtils.applyEdgeToEdge(window, true);

        // 关闭系统栏对比度强制，否则系统会在导航条区域叠加一层半透明遮罩，
        // 与弹窗背景产生色差（官方 Edge-to-Edge 规范要求关闭）。
        // Material 内部实现未覆盖此项，需在此补齐。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.setNavigationBarContrastEnforced(false);
            window.setStatusBarContrastEnforced(false);
        }

        // 导航条分隔线透明，避免底部出现一条突兀的分隔线
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.setNavigationBarDividerColor(Color.TRANSPARENT);
        }

        // Material 内部实现固定使用深色导航条图标，
        // 此处按弹窗背景明暗覆盖，保证深色背景（如图片预览）使用浅色图标
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightNavigationBars(lightNavigationBar);
    }
}
