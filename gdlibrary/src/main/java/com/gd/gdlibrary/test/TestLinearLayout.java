package com.gd.gdlibrary.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 基本信息
 *
 * @author HuangXiangGuang
 * @description 请填写类功能描述
 * @date Created in 2020/4/22 10:07
 * @modified By HuangXiangGuang
 */
public class TestLinearLayout extends ViewGroup {


    public TestLinearLayout(Context context) {
        super(context);
    }

    public TestLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i=0,count = getChildCount();i<count;i++){
            View child = getChildAt(i);
            ViewGroup.LayoutParams lp =  child.getLayoutParams();
            int childWidth = lp.width;
            int childHeight = lp.height;
            if (childWidth == -1){
                childWidth = getMeasuredWidth();
            }
            int childwidtMaesureSpec = MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY);
            int childheightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY);
            Log.e("子","i:"+childWidth+"--"+childHeight+"--"+childwidtMaesureSpec+"--"+childheightMeasureSpec);
            child.measure(childwidtMaesureSpec,childheightMeasureSpec);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = 0;
        for (int i=0,count = getChildCount();i<count;i++){
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            Log.e("onLayout",childWidth+"--"+childHeight);
            child.layout(0,childTop,childWidth,childTop+childHeight);
            childTop = childTop + childHeight;
            if (child instanceof TextView){
                ((TextView) child).setGravity(Gravity.CENTER);
            }
        }
    }
}
