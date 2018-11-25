package com.example.hany.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.hany.sokoban.collector.ActivityCollector;

import java.util.ArrayList;
import java.util.HashMap;


public class ChooseLevelActivity extends BaseActivity {

    GridView mGridView;
    // 配合SimpleAdapter使用
    ArrayList<HashMap<String, Object>> dataList = new ArrayList<>();
    // 关卡选项列表
    String[] mLevel = {"第一关", "第二关", "第三关", "第四关", "第五关"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_choose_level);
        ActivityCollector.addActivity(this);
        initData(); // 初始化dataList
        // 设置适配器
        SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.item_grid_choose_level,
                new String[]{"level"}, new int[]{R.id.txt_level});
        mGridView.setAdapter(adapter);
    }

    /**
     * 初始化关卡选项按钮数据
     */
    private void initData() {
        mGridView = findViewById(R.id.gridview);
        mGridView.setOnItemClickListener(this);

        for (int i = 0; i < mLevel.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("level", mLevel[i]);
            dataList.add(map);
        }
    }

    /**
     * 选择关卡按钮点击事件
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                Intent intent0 = new Intent(ChooseLevelActivity.this, GameActivity.class);
                intent0.putExtra("level", (i + 1));
                startActivity(intent0);
                Toast.makeText(view.getContext(), "点击了第" + (i + 1) + "关", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Intent intent1 = new Intent(ChooseLevelActivity.this, GameActivity.class);
                intent1.putExtra("level", (i + 1));
                startActivity(intent1);
                Toast.makeText(view.getContext(), "点击了第" + (i + 1) + "关", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Intent intent2 = new Intent(ChooseLevelActivity.this, GameActivity.class);
                intent2.putExtra("level", (i + 1));
                startActivity(intent2);
                Toast.makeText(view.getContext(), "点击了第" + (i + 1) + "关", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Intent intent3 = new Intent(ChooseLevelActivity.this, GameActivity.class);
                intent3.putExtra("level", (i + 1));
                startActivity(intent3);
                Toast.makeText(view.getContext(), "点击了第" + (i + 1) + "关", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Intent intent4 = new Intent(ChooseLevelActivity.this, GameActivity.class);
                intent4.putExtra("level", (i + 1));
                startActivity(intent4);
                Toast.makeText(view.getContext(), "点击了第" + (i + 1) + "关", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
