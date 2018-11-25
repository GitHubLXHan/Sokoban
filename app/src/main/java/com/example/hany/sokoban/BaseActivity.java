package com.example.hany.sokoban;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hany.sokoban.collector.ActivityCollector;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    // Toolbar标题
    private TextView mHeaderTitleTxt;
    // Toolbar
    private Toolbar mToolbar;
    // 内容区域
    private RelativeLayout mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActivityCollector.addActivity(this);
    }

    /**
     * 初始化标题、Toolbar、子布局
     */
    private void initView() {
        mHeaderTitleTxt = (TextView) findViewById(R.id.toolBar_title);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mContentLayout = (RelativeLayout) findViewById(R.id.content_layout);
    }

    public void setContentLayout(int childLayoutId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(childLayoutId, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentLayout.addView(contentView, params);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mHeaderTitleTxt.setText(title);
        }
    }

    /**
     * 按钮监听
     * @param view
     */
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
