package com.gd.gdlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 基本信息
 *
 * @author HuangXiangGuang
 * @description 请填写类功能描述
 * @date Created in 2020/3/24 14:05
 * @modified By HuangXiangGuang
 */
public class TestView extends View {
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //MeasureSpec.EXACTLY 子view的size确定,layout_width设置具体数值的dp或者设置为match_parent
        //MeasureSpec.AT_MOST 子view可以为任意大，但不可以超过父view，layout_width设置为wrap_content
        //MeasureSpec.UNSPECIFIED 子view可以为任意大，可以超过父view,可滚动的容器中子view的测量模式大多为MeasureSpec.UNSPECIFIED
        Log.e("onMeasure","mode="+mode+",width="+width);
        Log.e("onMeasure","widthMeasureSpec="+widthMeasureSpec+",heightMeasureSpec="+heightMeasureSpec);
        setMeasuredDimension(1000,100);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("onLayout",getWidth()+"--"+getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("onDraw",getWidth()+"--"+getHeight());
    }
}
