package com.pengl.PLDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

    public void OnClickTips1(View v) {
        new PLDialogTips(this, "这是提示内容").show();
    }

    public void OnClickTips2(View v) {
        PLDialogTips dialog = new PLDialogTips(this, "你确定要做这个操作吗？\n\n如果操作了会导致你的手机死机，或者是原地爆炸，如果你确认的话，请点击确定，否则请关闭");
        dialog.setOnClickOK(v1 -> Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show());
        dialog.setOnClickCancel(v2 -> Toast.makeText(MainActivity.this, "点击了关闭", Toast.LENGTH_SHORT).show());
        dialog.setGravity(Gravity.START);
        dialog.setBtnOkText("确定");
        dialog.setShowBtnClose();
        dialog.show();
    }

    public void OnClickTipsSucc(View v) {
        new PLDialogTipsSucc(this, "干活成功", "你可以继续ooxx了").show();
    }

    public void OnClickDialog(View v) {
        PLDialog dialog = new PLDialog(this, "这是标题", "这里是内容这里是内容这里是内容这里是内容");
        dialog.setOnClickOK(v1 -> Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show());
        dialog.show();
    }

    public void OnClickInputText(View v) {
        PLDialogInput dialog = new PLDialogInput(this, "请输入姓名", "为确保信息安全，请输入你的姓名，不超过5个字");
        dialog.setInputHint("最长5个字");
        dialog.setOriginContent("张三");
        dialog.setCallback(params -> {
            String input = (String) params[0];
            Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void OnClickInputNum(View v) {
        PLDialogInputNum dialog = new PLDialogInputNum(this);
        dialog.setMaxLength(6);
        dialog.setCallback(params -> {
            String input = (String) params[0];
            Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void OnClickInputIdCard(View v) {
        PLDialogInputNum dialog = new PLDialogInputNum(this);
        dialog.setShowType(PLDialogInputNum.TYPE.IDCARD);
        dialog.setCallback(params -> {
            String input = (String) params[0];
            Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();
        });
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
        PLDialogPhotoPreview dialog = new PLDialogPhotoPreview(this, "http://oss.pengl.com/github/other/pay.jpg");
        dialog.setOnLongClickListener(view -> {
            OnClickChoose(view);
            return true;
        });
        dialog.show();
    }
}