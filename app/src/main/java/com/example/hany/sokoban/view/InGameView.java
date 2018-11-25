package com.example.hany.sokoban.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.hany.sokoban.data.MapData;
import com.example.hany.sokoban.R;
import com.example.hany.sokoban.util.ScreenSizeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 6小h
 * @e-mail 1026310040@qq.com
 * @date 2018/11/2 16:06
 * @filName InGameView
 * @describe ...
 */
public class InGameView extends View {

    private final int DOWN = 0, RIGHT = 1, UP = 2, LEFT = 3;                                // 人物状态分别对应向下、向右、向上、向左
    private Bitmap mBlockBit = BitmapFactory.decodeResource(getResources(), R.mipmap.block);   // 加载地板的图片，矩阵中编号为0
    private Bitmap mBallBit = BitmapFactory.decodeResource(getResources(), R.mipmap.ball);     // 加载球的图片，矩阵中编号为1
    private Bitmap mBoxBit = BitmapFactory.decodeResource(getResources(), R.mipmap.box);       // 加载箱子的图片，矩阵中编号为2
    private Bitmap mDownBit = BitmapFactory.decodeResource(getResources(), R.mipmap.down);     // 加载人物向下的图片，矩阵中编号为3
    private Bitmap mLeftBit = BitmapFactory.decodeResource(getResources(), R.mipmap.left);     // 加载人物向左的图片，矩阵中编号为3
    private Bitmap mRightBit = BitmapFactory.decodeResource(getResources(), R.mipmap.right);   // 加载人物向右的图片，矩阵中编号为3
    private Bitmap mUpBit = BitmapFactory.decodeResource(getResources(), R.mipmap.up);         // 加载人物向上的图片，矩阵中编号为3
    private Bitmap mWallBit = BitmapFactory.decodeResource(getResources(), R.mipmap.wall);     // 加载墙的图片，矩阵中编号为4
    private Bitmap mMoveDownBtnBit = BitmapFactory.decodeResource(getResources(), R.mipmap.move_down); // 加载向下按钮图片
    private Bitmap mMoveRightBtnBit = BitmapFactory.decodeResource(getResources(), R.mipmap.move_right); // 加载向下按钮图片
    private Bitmap mMoveUpBtnBit = BitmapFactory.decodeResource(getResources(), R.mipmap.move_up); // 加载向下按钮图片
    private Bitmap mMoveLeftBtnBit = BitmapFactory.decodeResource(getResources(), R.mipmap.move_left); // 加载向下按钮图片
    private int mWidth; // 画板宽度
    private int mHeight; // 画板高度
    private int mMoveBtnWidth; // 移动按钮的宽度
    private int[][] mCurMap; // 当前地图矩阵
    private int[][] mCurLevelMap; // 当前关卡的地图，用于checkAndMove()函数中
    private int mLevel; // 关卡
    private Player player; // 玩家
    private List<Ball> ballList = new ArrayList<>(); // 用于记录球的位置
    private Context mContext; // 本布局的上下文context
    private onWinListener onWinListener; // 设置监听事件

    public InGameView(Context context) {
        super(context, null);
        mMoveBtnWidth = ScreenSizeUtil.getInstance(context).getScreenWidth() / 6;
        this.mContext = context;
    }

    public InGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 获取手机屏幕原来的宽高
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    /**
     * 绘制游戏界面
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        // 如果地图数组为空或没有数据，则不进行下面的绘制操作
        if (mCurMap == null || mCurMap.length == 0) {
            return;
        }
        Paint p = new Paint(); // 新建画笔

        int rangeLength = mWidth;   // 地图范围。以屏幕宽度为边长的正方形
        int gridLength = rangeLength / mCurMap[0].length;   // 每个正方形小格的边长
        // 绘画地图
        for (int i = 0, len1 = mCurMap[0].length; i < len1; i++) {
            for (int j = 0, len2 = mCurMap[0].length; j < len2; j++) {
                Rect rect = new Rect(gridLength * j, gridLength * i, (gridLength * (j + 1)), (gridLength * (i + 1)));
                int x = mCurMap[i][j];
                switch (x) {
                    case 0: // 地板
                        canvas.drawBitmap(mBlockBit, null, rect, p);
                        break;
                    case 1: // 球
                        canvas.drawBitmap(mBlockBit, null, rect, p);
                        canvas.drawBitmap(mBallBit, null, rect, p);
                        break;
                    case 2: // 箱子
                        canvas.drawBitmap(mBoxBit, null, rect, p);
                        break;
                    case 3: // 人物。绘制人物不同运动方向的状态。
                        canvas.drawBitmap(mBlockBit, null, rect, p);
                        switch (player.style) {
                            case DOWN:
                                canvas.drawBitmap(mDownBit, null, rect, p);
                                break;
                            case RIGHT:
                                canvas.drawBitmap(mRightBit, null, rect, p);
                                break;
                            case UP:
                                canvas.drawBitmap(mUpBit, null, rect, p);
                                break;
                            case LEFT:
                                canvas.drawBitmap(mLeftBit, null, rect, p);
                                break;
                        }
                        break;
                    case 4: // 墙
                        canvas.drawBitmap(mBlockBit, null, rect, p);
                        canvas.drawBitmap(mWallBit, null, rect, p);
                        break;
                }
            }
        }

        // 绘制向下按钮
        Rect rectDownBtn = new Rect(
                (mWidth - mMoveBtnWidth) / 2,
                mHeight - mMoveBtnWidth,
                (mWidth + mMoveBtnWidth) / 2,
                mHeight);
        canvas.drawBitmap(mMoveDownBtnBit, null, rectDownBtn, p);
        // 绘制向右按钮
        Rect rectRightBtn = new Rect(
                (mWidth + mMoveBtnWidth) / 2,
                mHeight - 2 * mMoveBtnWidth,
                (mWidth + mMoveBtnWidth) / 2 + mMoveBtnWidth,
                mHeight - mMoveBtnWidth);
        canvas.drawBitmap(mMoveRightBtnBit, null, rectRightBtn, p);
        // 绘制向上按钮
        Rect rectUpBtn = new Rect(
                (mWidth - mMoveBtnWidth) / 2,
                mHeight - 3 * mMoveBtnWidth,
                (mWidth + mMoveBtnWidth) / 2,
                mHeight - 2 * mMoveBtnWidth);
        canvas.drawBitmap(mMoveUpBtnBit, null, rectUpBtn, p);
        // 绘制向左按钮
        Rect rectLeftBtn = new Rect(
                (mWidth - mMoveBtnWidth) / 2 - mMoveBtnWidth,
                mHeight - 2 * mMoveBtnWidth,
                (mWidth - mMoveBtnWidth) / 2,
                mHeight - mMoveBtnWidth);
        canvas.drawBitmap(mMoveLeftBtnBit, null, rectLeftBtn, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 判断点击到哪个按钮
                if ((mWidth - mMoveBtnWidth) / 2 <= x
                        && x <= (mWidth + mMoveBtnWidth) / 2
                        && mHeight - mMoveBtnWidth <= y
                        && y <= mHeight) {
                    player.style = DOWN; // 人物的状态设置为向下
                    tryMove(DOWN);
                } else if ((mWidth + mMoveBtnWidth) / 2 <= x
                        && x <= (mWidth + mMoveBtnWidth) / 2 + mMoveBtnWidth
                        && mHeight - 2 * mMoveBtnWidth <= y
                        && y <= mHeight - mMoveBtnWidth) {
                    player.style = RIGHT; // 人物的状态设置为向右
                    tryMove(RIGHT);
                } else if ((mWidth - mMoveBtnWidth) / 2 <= x
                        && x <= (mWidth + mMoveBtnWidth) / 2
                        && mHeight - 3 * mMoveBtnWidth <= y
                        && y <= mHeight - 2 * mMoveBtnWidth) {
                    player.style = UP; // 人物的状态设置为向上
                    tryMove(UP);
                } else if ((mWidth - mMoveBtnWidth) / 2 - mMoveBtnWidth <= x
                        && x <= (mWidth - mMoveBtnWidth) / 2
                        && mHeight - 2 * mMoveBtnWidth <= y
                        && y <= mHeight - mMoveBtnWidth) {
                    player.style = LEFT; // 人物的状态设置为向左
                    tryMove(LEFT);
                }
                break;
        }
        return true;
    }


    /**
     * 初始化数据
     * @param level
     */
    private void initData(int level) {
        setMapData(level); // 设置地图
        setPlayer(); // 设置玩家状态
        recordBallPos(); // 记录球的位置
    }

    /**
     * 作为数据接口。
     * 设置关卡并初始化所有数据
     * @param level 关卡数
     */
    public void setLevel(int level) {
        this.mLevel = level; // 设置本局等级
        initData(level); // 初始化数据
        invalidate(); // 更新数据
    }

    /**
     * 设置玩家状态信息
     */
    private void setPlayer() {
        String TAG = "newSetData";
        Log.d(TAG, "setPlayer: run");
        int playerX = -1;
        int playerY = -1;
        boolean flag = false;
        // 寻找玩家的起点位置
        for (int i = 0; i < mCurMap[0].length; i++) {
            for (int j = 0; j < mCurMap.length; j++) {
                if (mCurMap[i][j] == 3) {
                    playerX = j;
                    playerY = i;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        // 实例化玩家
        player = new Player(DOWN, playerX, playerY);
    }

    /**
     * 记录球的位置
     */
    private void recordBallPos() {
        String TAG = "newSetData";
        Log.d(TAG, "recordBallPos: run");
        ballList.clear();
        for (int i = 0; i < mCurMap.length; i++) {
            for (int j = 0; j < mCurMap[0].length; j++) {
                if (mCurMap[i][j] == 1) {
                    Ball ball = new Ball(j, i);
                    ballList.add(ball);
                }
            }
        }
    }



    /**
     * 初始化关卡数据
     * @param level 关卡数
     */
    private void setMapData(int level) {
        String TAG = "newSetData";
        Log.d(TAG, "setMapData: run");
        this.mCurMap = MapData.getMapData(level);
        this.mCurLevelMap = copyArr(mCurMap);

    }

    /**
     * 深拷贝数组
     * @param arr
     * @return
     */
    private int[][] copyArr(int[][] arr) {
        String TAG = "newSetData";
        Log.d(TAG, "copyArr: run");
        int[][] temp = new int[arr.length][arr[0].length];
        for (int i = 0, len = arr.length; i < len; i++) {
            temp[i] = arr[i].clone();
        }
        return temp;
    }

    /**
     * 尝试移动并调用移动函数
     * @param style
     */
    private void tryMove(int style) {
        int nextFirstX = 0;
        int nextFirstY = 0;
        int nextSecondX = 0;
        int nextSecondY = 0;
        switch (style) {
            // 向下
            case DOWN:
                nextFirstX = player.curPositionX;
                nextFirstY = player.curPositionY + 1;
                nextSecondX = player.curPositionX;
                nextSecondY = player.curPositionY + 2;
                // 判断是否可以移动并且进行移动操作
                checkAndMove(nextFirstX, nextFirstY, nextSecondX, nextSecondY);
                if(isFinish()){
                    onWinListener.onWind();
                }
                break;

            // 向右
            case RIGHT:
                nextFirstX = player.curPositionX + 1;
                nextFirstY = player.curPositionY;
                nextSecondX = player.curPositionX + 2;
                nextSecondY = player.curPositionY;
                // 判断是否可以移动并且进行移动操作
                checkAndMove(nextFirstX, nextFirstY, nextSecondX, nextSecondY);
                if(isFinish()){
                    onWinListener.onWind();
                }
                break;
            // 向上
            case UP:
                nextFirstX = player.curPositionX;
                nextFirstY = player.curPositionY - 1;
                nextSecondX = player.curPositionX;
                nextSecondY = player.curPositionY - 2;
                // 判断是否可以移动并且进行移动操作
                checkAndMove(nextFirstX, nextFirstY, nextSecondX, nextSecondY);
                if(isFinish()) {
                    onWinListener.onWind();
                }
                break;
            // 向左
            case LEFT:
                nextFirstX = player.curPositionX - 1;
                nextFirstY = player.curPositionY;
                nextSecondX = player.curPositionX - 2;
                nextSecondY = player.curPositionY;
                // 判断是否可以移动并且进行移动操作
                checkAndMove(nextFirstX, nextFirstY, nextSecondX, nextSecondY);
                if(isFinish()){
                    onWinListener.onWind();
                }
                break;
            default:
                return;
        }
        invalidate();
    }

    /**
     * 判断是否结束
     * @return
     */
    private boolean isFinish() {
        boolean finish = true;
        for (int i = 0, len = ballList.size(); i < len; i++) {
            Ball ball = ballList.get(i);
            if (mCurMap[ball.curPositionY][ball.curPositionX] != 2) {
                finish = false;
                break;
            }
        }
        return finish;
    }


    /**
     * 推动箱子移动的情况下
     * @param nextFirstX 玩家的下一个X点
     * @param nextFirstY 玩家的下一个Y点
     * @param nextSecondX 玩家的下两个X点
     * @param nextSecondY 玩家的下两个Y点
     */
    private void checkAndMove(int nextFirstX, int nextFirstY, int nextSecondX, int nextSecondY) {
        if (mCurMap[nextFirstY][nextFirstX] == 4 ) {
            return;   // 若玩家的下一步为墙则直接返回，不做任何操作
        }
        // 玩家的下一步为箱子的情况下
        if (mCurMap[nextFirstY][nextFirstX] == 2){
            if (mCurMap[nextSecondY][nextSecondX] == 2 || mCurMap[nextSecondY][nextSecondX] == 4) {
                return; // 玩家的下两步是箱子或者墙的话则直接返回，不做任何操作
            } else {
                mCurMap[nextSecondY][nextSecondX] = 2; // 符合条件，玩家下两步的箱子的位置向前移动
            }
        }
        // 玩家向前走一步
        mCurMap[nextFirstY][nextFirstX] = 3;
        // 接下来判断玩家原先的位置是否与球重叠
        int p = mCurLevelMap[player.curPositionY][player.curPositionX];
        // 玩家原本位置不是球的话
        if (p != 1) {
            p = 0;
        }
        // 重新设置玩家原来位置的参数
        mCurMap[player.curPositionY][player.curPositionX] = p;
        // 更新玩家坐标
        player.curPositionX = nextFirstX;
        player.curPositionY = nextFirstY;
    }

    public void setOnWinListener(onWinListener onWinListener) {
        this.onWinListener = onWinListener;
    }


    public interface onWinListener{
        void onWind();
    }

}



/**
 * 内部类
 * 玩家类
 */
class Player {
    int style;          // 玩家目前的状态
    int curPositionX;   // 玩家现在的x坐标
    int curPositionY;   // 玩家现在的y坐标
    public Player(int style, int curPositionX, int curPositionY) {
        this.style = style;
        this.curPositionX = curPositionX;
        this.curPositionY = curPositionY;
    }
}

/**
 * 内部类
 * 球类
 */
class Ball {
    int curPositionX;   // 球现在的x坐标
    int curPositionY;   // 球现在的y坐标
    public Ball(int curPositionX, int curPositionY) {
        this.curPositionX = curPositionX;
        this.curPositionY = curPositionY;
    }
}



