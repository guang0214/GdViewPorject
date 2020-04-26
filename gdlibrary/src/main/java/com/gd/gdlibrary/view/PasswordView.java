package com.gd.gdlibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.gd.gdlibrary.R;

import java.util.ArrayList;

/**
 * 基本信息
 *
 * @author HuangXiangGuang
 * @description 请填写类功能描述
 * @date Created in 2020/1/16 9:13
 * @modified By HuangXiangGuang
 */
public class PasswordView extends View {

    private float mWidth;
    private float mHeight;

    private float childWidth;
    private float childHeight;
    private float leftMargin;
    private float topMargin;

    private float mButtonWidth;
    private float mButtonHeight;

    private float mButtonMargin;

    private float mPasswordRadius;
    private Bitmap deleteIcon;

    private Paint mBgPaint;
    private Paint mFramePaint;
    private Paint mPanelBg;
    private Paint mButtonPaint;
    private Paint mTextPaint;
    private Paint circlePaint;

    private ArrayList<Pair<Float,Float>> totlaPassword = new ArrayList<>();
    private ArrayList<Pair<Float,Float>> showPassword = new ArrayList<>();
    private ArrayList<RectF> rectFDatas = new ArrayList<>();

    /**文字大小*/
    private int mTextSize = 24;

    private float downX;
    private float downY;
    private float upX;
    private float upY;

    private ArrayList<String> numDatas = new ArrayList<String>(){{
        add("1");
        add("2");
        add("3");
        add("4");
        add("5");
        add("6");
        add("7");
        add("8");
        add("9");
        add("");
        add("0");
        add("");
    }};
    private ArrayList<String> passwordData = new ArrayList<>();
    private InputCompleteCallBack inputCompleteCallBack;

    public void setInputCompleteCallBack(InputCompleteCallBack inputCompleteCallBack) {
        this.inputCompleteCallBack = inputCompleteCallBack;
    }

    public PasswordView(Context context) {
        super(context);
        init();
    }

    public PasswordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
//        childWidth = dp2px(55);
        childHeight = dp2px(45);
        mButtonHeight = dp2px(55);
        mButtonMargin = dp2px(1);
        mPasswordRadius = dp2px(5);
        Log.e("childview",childWidth+"--"+childHeight);
        deleteIcon = BitmapFactory.decodeResource(getResources(), R.drawable.gd_icon_delete_onebyone);
        setPaint();
    }

    private void setPaint(){
        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);

        mFramePaint = new Paint();
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setColor(Color.parseColor("#adadad"));

        mPanelBg = new Paint();
        mPanelBg.setStyle(Paint.Style.FILL);
        mPanelBg.setColor(Color.parseColor("#e0e0e0"));

        mButtonPaint = new Paint();
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setColor(Color.parseColor("#FFFFFF"));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.parseColor("#000000"));
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setTextSize(dp2px(mTextSize));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#000000"));
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        Log.e("onMeasure","sizeWidth="+sizeWidth);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // wrap_content
        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        height = (int) (topMargin+childHeight+dp2px(60)+4*mButtonHeight+3*mButtonMargin);

        setMeasuredDimension(sizeWidth,height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("onLayout",getWidth()+"--"+getHeight());
        mWidth = getWidth();
        mHeight = getHeight();
        childWidth = (mWidth/8)-5;
        float sumWith = childWidth*6;
        leftMargin = (mWidth - sumWith)/2;
        topMargin = dp2px(30);
        mButtonWidth = (mWidth - 3*mButtonMargin)/3;
        if (totlaPassword.isEmpty()){
            float y = topMargin + childHeight/2;
            float x = leftMargin + childWidth/2;
            totlaPassword.add(new Pair<>(x,y));
            totlaPassword.add(new Pair<>(x+childWidth,y));
            totlaPassword.add(new Pair<>(x+2*childWidth,y));
            totlaPassword.add(new Pair<>(x+3*childWidth,y));
            totlaPassword.add(new Pair<>(x+4*childWidth,y));
            totlaPassword.add(new Pair<>(x+5*childWidth,y));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(leftMargin,topMargin,mWidth-leftMargin,topMargin+childHeight);
        canvas.drawRoundRect(rectF,5,5,mFramePaint);
        canvas.drawLine(leftMargin+childWidth,topMargin,leftMargin+childWidth,topMargin+childHeight,mFramePaint);
        canvas.drawLines(getLines(),mFramePaint);

        if (!showPassword.isEmpty()){
            for (int i=0,size = showPassword.size();i<size;i++){
                Pair<Float,Float> p = showPassword.get(i);
                canvas.drawCircle(p.first,p.second,mPasswordRadius,circlePaint);
            }
        }

        float panelTop = topMargin + childHeight + dp2px(30);
        float panelBottom = panelTop + mButtonHeight*4 + mButtonMargin*4;
        RectF panelF = new RectF(0,panelTop,mWidth,panelBottom);
        canvas.drawRect(panelF,mPanelBg);

        float x1 = 0;
        float x2 = mButtonWidth+mButtonMargin;
        float x3 = 2*mButtonWidth+2*mButtonMargin;
        float y1 = panelTop + dp2px(1);
        float y2 = y1 + mButtonHeight+mButtonMargin;
        float y3 = y1 + 2*(mButtonHeight+mButtonMargin);
        float y4 = y1 + 3*(mButtonHeight + mButtonMargin);

        RectF rectF1  = new RectF(x1,y1,x1+mButtonWidth,y1+mButtonHeight);
        canvas.drawRect(rectF1,mButtonPaint);
        canvas.drawText("1",getTextX(rectF1),getTextY(rectF1),mTextPaint);
        RectF rectF2  = new RectF(x2,y1,x2+mButtonWidth,y1+mButtonHeight);
        canvas.drawRect(rectF2,mButtonPaint);
        canvas.drawText("2",getTextX(rectF2),getTextY(rectF2),mTextPaint);
        RectF rectF3  = new RectF(x3,y1,x3+mButtonWidth,y1+mButtonHeight);
        canvas.drawRect(rectF3,mButtonPaint);
        canvas.drawText("3",getTextX(rectF3),getTextY(rectF3),mTextPaint);

        RectF rectF4  = new RectF(x1,y2,x1+mButtonWidth,y2+mButtonHeight);
        canvas.drawRect(rectF4,mButtonPaint);
        canvas.drawText("4",getTextX(rectF4),getTextY(rectF4),mTextPaint);
        RectF rectF5  = new RectF(x2,y2,x2+mButtonWidth,y2+mButtonHeight);
        canvas.drawRect(rectF5,mButtonPaint);
        canvas.drawText("5",getTextX(rectF5),getTextY(rectF5),mTextPaint);
        RectF rectF6  = new RectF(x3,y2,x3+mButtonWidth,y2+mButtonHeight);
        canvas.drawRect(rectF6,mButtonPaint);
        canvas.drawText("6",getTextX(rectF6),getTextY(rectF6),mTextPaint);

        RectF rectF7  = new RectF(x1,y3,x1+mButtonWidth,y3+mButtonHeight);
        canvas.drawRect(rectF7,mButtonPaint);
        canvas.drawText("7",getTextX(rectF7),getTextY(rectF7),mTextPaint);
        RectF rectF8  = new RectF(x2,y3,x2+mButtonWidth,y3+mButtonHeight);
        canvas.drawRect(rectF8,mButtonPaint);
        canvas.drawText("8",getTextX(rectF8),getTextY(rectF8),mTextPaint);
        RectF rectF9  = new RectF(x3,y3,x3+mButtonWidth,y3+mButtonHeight);
        canvas.drawRect(rectF9,mButtonPaint);
        canvas.drawText("9",getTextX(rectF9),getTextY(rectF9),mTextPaint);

        RectF rectF10  = new RectF(x1,y4,x1+mButtonWidth,y4+mButtonHeight);
        canvas.drawRect(rectF10,mButtonPaint);
        RectF rectF11  = new RectF(x2,y4,x2+mButtonWidth,y4+mButtonHeight);
        canvas.drawRect(rectF11,mButtonPaint);
        canvas.drawText("0",getTextX(rectF11),getTextY(rectF11),mTextPaint);
        RectF rectF12  = new RectF(x3,y4,x3+mButtonWidth,y4+mButtonHeight);
        canvas.drawRect(rectF12,mButtonPaint);

        float bitmapW = deleteIcon.getWidth();
        float bitmapH = deleteIcon.getHeight();
        float bX = rectF12.centerX() - bitmapW/2;
        float bY = rectF12.centerY() - bitmapH/2;
        canvas.drawBitmap(deleteIcon,bX,bY,mTextPaint);

        if (rectFDatas.isEmpty()){
            rectFDatas.add(rectF1);
            rectFDatas.add(rectF2);
            rectFDatas.add(rectF3);
            rectFDatas.add(rectF4);
            rectFDatas.add(rectF5);
            rectFDatas.add(rectF6);
            rectFDatas.add(rectF7);
            rectFDatas.add(rectF8);
            rectFDatas.add(rectF9);
            rectFDatas.add(rectF10);
            rectFDatas.add(rectF11);
            rectFDatas.add(rectF12);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                handleClick();
                break;
                default:
        }
        return true;
    }

    private void handleClick(){
        int pwSize = showPassword.size();
        int downPos = -1;
        int upPos = -2;
        for (int i=0,size = rectFDatas.size();i<size;i++){
            RectF rectF = rectFDatas.get(i);
            if (rectF.contains(downX,downY)){
                downPos = i;
                break;
            }
        }
        for (int i=0,size = rectFDatas.size();i<size;i++){
            RectF rectF = rectFDatas.get(i);
            if (rectF.contains(upX,upY)){
                upPos = i;
                break;
            }
        }
        if (downPos == upPos){
            if (downPos == 11){
                //删除
                if (!showPassword.isEmpty() && !passwordData.isEmpty()){
                    showPassword.remove(showPassword.size()-1);
                    passwordData.remove(passwordData.size()-1);
                    invalidate();
                }
            }else if(downPos != 9){
                if (showPassword.isEmpty()){
                    showPassword.add(totlaPassword.get(0));
                    passwordData.add(numDatas.get(downPos));
                    invalidate();
                }else if(showPassword.size()<6){
                    showPassword.add(totlaPassword.get(pwSize));
                    passwordData.add(numDatas.get(downPos));
                    invalidate();
                    listenerInput();
                }
            }else{
            }
        }
    }

    private void listenerInput(){
        if (passwordData.size() == 6){
            if (inputCompleteCallBack != null){
                StringBuilder builder = new StringBuilder();
                for (String s:passwordData){
                    builder.append(s);
                }
                inputCompleteCallBack.complete(builder.toString());
            }
        }
    }

    private float getTextX(RectF srcRectF){
        return srcRectF.centerX();
    }

    private float getTextY(RectF srcRectF){
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        float baseline = srcRectF.centerY()+distance;
        return baseline;
    }

    private float[] getLines(){
        float startY = topMargin;
        float stopY = topMargin+childHeight;
        float[] lines = {
                leftMargin+childWidth,startY,leftMargin+childWidth,stopY,
                leftMargin+2*childWidth,startY,leftMargin+2*childWidth,stopY,
                leftMargin+3*childWidth,startY,leftMargin+3*childWidth,stopY,
                leftMargin+4*childWidth,startY,leftMargin+4*childWidth,stopY,
                leftMargin+5*childWidth,startY,leftMargin+5*childWidth,stopY,
        };
        return lines;
    }

    private int dp2px(int dp){
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) (dp*metrics.density);
    }


    public interface InputCompleteCallBack{
        void complete(String password);
    }

}
