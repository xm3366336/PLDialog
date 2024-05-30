package com.pengl.demo.dialog;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pengl.pldialog.PLDialog;
import com.pengl.pldialog.PLDialogChoice;
import com.pengl.pldialog.PLDialogInput;
import com.pengl.pldialog.PLDialogInputNum;
import com.pengl.pldialog.PLDialogLoad;
import com.pengl.pldialog.PLDialogLoadTxt;
import com.pengl.pldialog.PLDialogPhotoPreview;
import com.pengl.pldialog.PLDialogTips;
import com.pengl.pldialog.PLDialogTipsSucc;
import com.pengl.pldialog.PLToast;
import com.pengl.pldialog.vehicle.VehicleKeyboardHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_url)))));

        ((TextView) findViewById(R.id.tv_copyright)).setText(getString(R.string.version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, getString(R.string.github_url)));

        EditText et_vehicle = findViewById(R.id.et_vehicle);
        VehicleKeyboardHelper.bind(et_vehicle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 简单的提示
     *
     * @param v v
     */
    public void OnClickTips1(View v) {
        new PLDialogTips(this, "这是提示内容").show();
    }

    /**
     * 全部的接口
     *
     * @param v v
     */
    public void OnClickTips2(View v) {
        PLDialogTips dialog = new PLDialogTips(this)
                .setTitle("你确定要做这个操作吗？")
                .setContent("这里显示是的内容，这里显示是的内容，这里显示是的内容，这里显示是的内容。\n如果你确认的话，请点击确定，否则请关闭")
                .setOnClickOK(v1 -> Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show())
                .setOnClickCancel(v2 -> Toast.makeText(MainActivity.this, "点击了关闭", Toast.LENGTH_SHORT).show())
                .setBtnOkText("确定")
                .setShowBtnClose();
        dialog.getTitleView().setGravity(Gravity.START);
        dialog.getContentView().setGravity(Gravity.START);
        dialog.getContentView().setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        dialog.show();
    }

    public void OnClickTipsSucc(View v) {
        new PLDialogTipsSucc(this)
                .setTitle("干活成功")
                .setContent("你可以继续ooxx了")
                .setImageResource(R.mipmap.ic_successful)
                .show();
    }

    public void OnClickDialog1(View v) {
        PLDialog dialog = new PLDialog(this, "这是标题", "这里是内容这里是内容这里是内容这里是内容");
        dialog.setBgRounded(8);
        dialog.setOnClickOK(v1 -> Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show());
        dialog.show();
    }

    public void OnClickDialog2(View v) {
        PLDialog dialog = new PLDialog(this, 2);
        dialog.setContent("这里是内容这里是内容这里是内容这里是内容");
        dialog.getTvTitle().setVisibility(View.GONE);
        dialog.setBgRounded(8);
        dialog.setOnClickOK(v1 -> Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show());
        dialog.show();
    }

    public void OnClickInputText1(View v) {
        PLDialogInput dialog = new PLDialogInput(this);
        dialog.setTitle("请输入姓名");
        dialog.setTitleSub("为确保信息安全，请输入你的姓名，不超过5个字");
        dialog.setInputHint("最长5个字");
        dialog.setOriginContent("张三");
        dialog.setBgRounded(24);
        dialog.setCallback(params -> {
            String input = (String) params[0];
            Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void OnClickInputText2(View v) {
        PLDialogInput dialog = new PLDialogInput(this, 2);
        dialog.setTitle("请输入姓名");
        dialog.setTitleSub("为确保信息安全，请输入你的姓名，不超过5个字");
        dialog.setInputHint("最长5个字");
        dialog.setOriginContent("张三");
        dialog.getEditText().setBackgroundResource(R.drawable.bg_edit_default);
        dialog.setShowKeyboard(false);
        dialog.setBgRounded(24);
        dialog.setCallback(params -> {
            String input = (String) params[0];
            Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void OnClickInputNum(View v) {
        PLDialogInputNum dialog = new PLDialogInputNum(this);
        dialog.setMaxLength(6);
        dialog.setBgRounded(24);
        dialog.setCallback(params -> {
            String input = (String) params[0];
            Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void OnClickInputIdCard(View v) {
        PLDialogInputNum dialog = new PLDialogInputNum(this);
        dialog.setBgRounded(24);
        dialog.setShowType(PLDialogInputNum.TYPE.IDCARD);
        dialog.setCallback(params -> {
            String input = (String) params[0];
            Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void OnClickInputHex16(View v) {
        PLDialogInputNum dialog = new PLDialogInputNum(this, PLDialogInputNum.TYPE.HEX16);
        dialog.setBgRounded(24);
        dialog.setCallback(params -> {
            String input = (String) params[0];
            Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
        });
        dialog.getViewKeyboardHex().setKeyboardAllCap(true);
        dialog.show();
    }

    public void OnClickLoad1(View v) {
        new PLDialogLoad(this, false).show();
    }

    public void OnClickLoad2(View v) {
        new PLDialogLoadTxt(this, "加载中", false).show();
    }

    public void OnClickChoose(View v) {
        String[] items = new String[]{"发给微信好友", "分享到朋友圈", "保存至本地"};
        PLDialogChoice dialog = new PLDialogChoice(this);
        dialog.setTitle("标题").setContent("这是内容，这是内容，这是内容，这是内容！");
        dialog.setBgRounded(24);
        dialog.setItems(items);
        dialog.setOnClickListener((dialog1, which) -> {
            if (which == -1) {
                return;
            }
            Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void OnClickPhotoPreview(View v) {
        PLDialogPhotoPreview dialog = new PLDialogPhotoPreview(this, "https://pengl.com/res/pic/pengl_com_weixin_code.png");
        dialog.setOnLongClickListener(view -> {
            OnClickChoose(view);
            return true;
        });
        dialog.show();
    }

    public void OnClickToast1(View v) {
        PLToast.showErr(this, "出错啦～");
    }

    public void OnClickToast2(View v) {
        PLToast.showSimple(this, "这是一个提示");
    }
}