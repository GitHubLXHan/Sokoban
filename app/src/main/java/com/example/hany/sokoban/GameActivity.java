package com.example.hany.sokoban;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hany.sokoban.Dialog.CustomDialog;
import com.example.hany.sokoban.collector.ActivityCollector;
import com.example.hany.sokoban.view.InGameView;

public class GameActivity extends BaseActivity {

    private int mLevel;
    private InGameView inGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_game);
        ActivityCollector.addActivity(this);

        // 获取上一个活动传递过来的关卡等级level
        Intent intent = getIntent();
        mLevel = intent.getIntExtra("level", 1);
        setTitle("第" + mLevel + "关"); // 调用父类方法，设置标题内容
        // 将自定义view添加到基类的内容布局中
        inGameView = new InGameView(this);
        inGameView.setLevel(mLevel);
        RelativeLayout layout = findViewById(R.id.content_layout);
        layout.addView(inGameView);
        callBack(); // 回调函数
    }

    public void callBack() {
        inGameView.setOnWinListener(new InGameView.onWinListener() {
            @Override
            public void onWind() {
                final CustomDialog customDialog = new CustomDialog(GameActivity.this);
                customDialog.setHeadTxt("恭喜你");
                customDialog.setMiddleTxt("通过第" + mLevel + "关");
                final Dialog dialog = customDialog.getDialog(); // 设置参数后，获取Dialog显示框
                customDialog.setCustomDialogInt(new CustomDialog.CustomDialogInt() {
                    @Override
                    public void lastButton() {
                        if (mLevel > 1) {
                            mLevel--;
                            inGameView.setLevel(mLevel);
                            setTitle("第" + mLevel + "关"); // 调用父类方法，设置标题内容
                            Toast.makeText(GameActivity.this, "你点击了上一关", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            customDialog.setHeadTxt("抱歉");
                            customDialog.setMiddleTxt("没有上一关了");
                        }
                        invalidateOptionsMenu(); // 更新标题栏按钮
                    }

                    @Override
                    public void backButton() {
                        Toast.makeText(GameActivity.this, "你点击了返回", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void nextButton() {
                        if (mLevel < 5) {
                            mLevel++;
                            inGameView.setLevel(mLevel);
                            setTitle("第" + mLevel + "关"); // 调用父类方法，设置标题内容
                            Toast.makeText(GameActivity.this, "你点击了下一关", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            customDialog.setHeadTxt("抱歉");
                            customDialog.setMiddleTxt("没有下一关了");

                        }
                        invalidateOptionsMenu(); // 更新标题栏按钮
                    }
                });
                dialog.show();
            }
        });
    }


    /**
     * 创建右上角菜单栏
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_game, menu);
        if (1 == mLevel) {
            menu.getItem(0).setVisible(false);
        } else if (5 == mLevel) {
            menu.getItem(3).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.game_menu_item_last_level:
                inGameView.setLevel(mLevel - 1);
                mLevel--;
                setTitle("第" + mLevel + "关"); // 调用父类方法，设置标题内容
                Toast.makeText(this, "你点击了上一关", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu(); // 更新标题栏按钮
                break;
            case R.id.game_menu_item_game_over:
                ActivityCollector.finishAll(); // 销毁所有活动
                break;
            case R.id.game_menu_item_reelect:
                Intent intent = new Intent(GameActivity.this, ChooseLevelActivity.class);
                startActivity(intent);
                String TAG = "reelect";
                Log.d(TAG, "onOptionsItemSelected: 跳到重选");
                break;
            case R.id.game_menu_item_next_level:
                inGameView.setLevel(mLevel + 1);
                mLevel++;
                setTitle("第" + mLevel + "关"); // 调用父类方法，设置标题内容
                Toast.makeText(this, "你点击了下一关", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu(); // 更新标题栏按钮
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 退出此关游戏并且杀死此活动线程
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("返回","yes");
    }



}
