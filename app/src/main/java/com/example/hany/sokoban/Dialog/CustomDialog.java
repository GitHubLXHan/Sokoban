package com.example.hany.sokoban.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hany.sokoban.R;
import com.example.hany.sokoban.util.ScreenSizeUtil;

/**
 * @author 6小h
 * @e-mail 1026310040@qq.com
 * @date 2018/11/21 21:36
 * @filName Dialog
 * @describe ...
 */
public class CustomDialog {

    private Context mContext;
    private TextView mLastClickTxt;
    private TextView mBackClickTxt;
    private TextView mNextClickTxt;
    private TextView mHeadTxt;
    private TextView mMiddleTxt;
    private View view;
    private CustomDialogInt customDialogInt;

    public CustomDialog(Context context) {
        mContext = context;
        initVeiw();
    }


    private void initVeiw() {
        // 加载布局
        view = View.inflate(mContext, R.layout.dialog_pass, null);
        // 获取控件
        mHeadTxt = view.findViewById(R.id.txt_dialog_head);
        mMiddleTxt = view.findViewById(R.id.txt_dialog_middle);
        mLastClickTxt = view.findViewById(R.id.txt_click_last_level);
        mBackClickTxt = view.findViewById(R.id.txt_click_back);
        mNextClickTxt = view.findViewById(R.id.txt_click_next_level);
    }
    /**
     * 获取过关时的对话框
     */
    public Dialog getDialog() {
        // 创建一个对话框
        final android.app.Dialog dialog = new android.app.Dialog(mContext, R.style.PassDialog);
        // 将布局文件添加到对话框中
        dialog.setContentView(view);
        // 设置点击对话框外部时，对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        // 设置对话框大小
//        view.setMinimumHeight((int) (ScreenSizeUtil.getInstance(this).getScreenHeight() * 0.23f));
//        view.setMinimumWidth((int) (ScreenSizeUtil.getInstance(this).getScreenWidth() * 0.75f));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = (int) (ScreenSizeUtil.getInstance(mContext).getScreenHeight() * 0.23f);
        layoutParams.width = (int) (ScreenSizeUtil.getInstance(mContext).getScreenWidth() * 0.75f);
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        // 调用回调函数，以下分别为左侧、中间、右侧按钮
        mLastClickTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogInt.lastButton();
            }
        });
        mBackClickTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogInt.backButton();
            }
        });
        mNextClickTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogInt.nextButton();
            }
        });
        return dialog;
    }

    /**
     * 设置对话框头部信息
     * @param mHead
     */
    public void setHeadTxt(String mHead) {
        mHeadTxt.setText(mHead);
    }

    public void setMiddleTxt(String mMiddle) {
        mMiddleTxt.setText(mMiddle);
    }

    public void setCustomDialogInt(CustomDialogInt customDialogInt) {
        this.customDialogInt = customDialogInt;
    }

    public interface CustomDialogInt {
        void lastButton();
        void backButton();
        void nextButton();
    }

}
