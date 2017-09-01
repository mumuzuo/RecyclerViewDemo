package com.cnbs.recyclerviewdemo.indexRV;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.cnbs.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class IndexBar extends View {
    private Paint whitePaint;

    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";

    private Paint grayPaint;

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        whitePaint = createPaint(getResources().getColor(R.color.base_color));
        whitePaint.setTypeface(Typeface.DEFAULT);   //设置字体
        grayPaint = createPaint(Color.GRAY);
        grayPaint.setTypeface(Typeface.DEFAULT);
    }


    Rect textBounds = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundResource(android.R.color.transparent);
        computeValues();
        // 绘制背景
//        canvas.drawColor(Color.WHITE);
        // 绘制文字,坐标x 和y 指的是 文字区域的左下角的位置
//        canvas.drawText("A",5,100,whitePaint);
        for (int i = 0; i < LETTERS.length(); i++) {
            Paint textPaint = null;
            if (i == touchIndex) {
                textPaint = grayPaint;
            } else {
                textPaint = whitePaint;
            }
            textPaint.getTextBounds(LETTERS, i, i + 1, textBounds);
            float x = (cellWidth - textBounds.width()) / 2;
            float y = (cellHeight + textBounds.height()) / 2 + i * cellHeight;
            canvas.drawText(LETTERS, i, i + 1, x, y, textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int i = (int) (event.getY() / cellHeight);
                updateTouchIndex(i);
                //回调监听器
                if (null != mOnIndexPressedListener) {
                    mOnIndexPressedListener.onIndexPressed(i,LETTERS.substring(i,i+1));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                updateTouchIndex(-1);
                //回调监听器
                if (null != mOnIndexPressedListener) {
                    mOnIndexPressedListener.onMotionEventEnd();
                }
                break;
        }
//        touchIndex = (int) (event.getY() / cellHeight);
        return true;
    }

    private void updateTouchIndex(int newIndex) {
        if(touchIndex ==newIndex){
            return ;
        }
        touchIndex = newIndex;
        invalidate();
    }

    float cellWidth;
    float cellHeight;

    private void computeValues() { // 10.0 /3  =3.33333  ; 3.33333 * 3 = 9.99999;
        cellWidth = getWidth();
        cellHeight = 1.0f * getHeight() / LETTERS.length();
    }

    private int touchIndex = -1;

    private Paint createPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        return paint;
    }


    //-----------------------
    /**
     * 当前被按下的index的监听器
     */
    public interface onIndexPressedListener {
        void onIndexPressed(int index, String text);//当某个Index被按下

        void onMotionEventEnd();//当触摸事件结束（UP CANCEL）
    }

    private onIndexPressedListener mOnIndexPressedListener;

    public onIndexPressedListener getmOnIndexPressedListener() {
        return mOnIndexPressedListener;
    }

    public void setmOnIndexPressedListener(onIndexPressedListener mOnIndexPressedListener) {
        this.mOnIndexPressedListener = mOnIndexPressedListener;
    }

    /**
     * 显示当前被按下的index的TextView
     *
     * @return
     */
    private TextView mPressedShowTextView;//用于特写显示正在被触摸的index值
    private LinearLayoutManager mLayoutManager;
    public IndexBar setmPressedShowTextView(TextView mPressedShowTextView) {
        this.mPressedShowTextView = mPressedShowTextView;
        return this;
    }

    public  IndexBar setmLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        return this;
    }
}
