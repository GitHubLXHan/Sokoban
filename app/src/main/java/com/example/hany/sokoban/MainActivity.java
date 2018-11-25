package com.example.hany.sokoban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.hany.sokoban.collector.ActivityCollector;
import com.example.hany.sokoban.view.InGameView;

public class MainActivity extends BaseActivity{

    Button mGameStartBtn;

    Button mGameDescribeBtn;

    Button mGameOverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main); // 调用父类方法，设置子项布局内容
        ActivityCollector.addActivity(this); // 将该活动添加到活动管理列表中
        setTitle("推箱子游戏"); // 调用父类方法，设置标题内容
        initView(); // 初始化控件
    }

    /**
     * 初始化按钮并添加点击事件
     */
    private void initView() {
        mGameStartBtn = findViewById(R.id.btn_main_gameStart);
        mGameStartBtn.setOnClickListener(this);
        mGameDescribeBtn = findViewById(R.id.btn_main_gameDescribe);
        mGameDescribeBtn.setOnClickListener(this);
        mGameOverBtn = findViewById(R.id.btn_main_gameOver);
        mGameOverBtn.setOnClickListener(this);
    }

        @Override
    public void onClick(View view) {
        switch (view.getId()){ // 获取组件ID
            case R.id.btn_main_gameStart: // 跳转到关卡选择界面
                Intent intent = new Intent(MainActivity.this, ChooseLevelActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_gameDescribe: // 跳转到游戏说明界面
                startActivity(new Intent(MainActivity.this, GameIntroActivity.class));
                break;
            case R.id.btn_main_gameOver: // 销毁所有活动
                ActivityCollector.finishAll();
                break;
        }
    }



}
