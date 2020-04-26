package com.gd.gdlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gd.gdlibrary.R;

/**
 * titlbar类封装
 *
 * @author HuangXiangGuang
 * @description 请填写类功能描述
 * @date Created in 2020/1/17 10:23
 * @modified By HuangXiangGuang
 */
public class GdTitleBar extends FrameLayout {

    private View leftBtn;
    private TextView tvTitle;
    private View rightBtn01,rightBtn02;
    private TextView tvRight01,tvRight02;
    private ImageView imageRight01,imageRight02;

    public GdTitleBar(@NonNull Context context) {
        this(context,null);
    }

    public GdTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public GdTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.gd_titlebar_layout,this,true);
        initView();
        setAttrs(context,attrs);
    }

    private void initView(){
        tvTitle = findViewById(R.id.gd_title);
        leftBtn = findViewById(R.id.gd_left_layout);
        rightBtn01 = findViewById(R.id.gd_right_layout01);
        rightBtn02 = findViewById(R.id.gd_right_layout02);
        tvRight01 = findViewById(R.id.gd_right_btn01);
        tvRight02 = findViewById(R.id.gd_right_btn02);
        imageRight01 = findViewById(R.id.gd_right_img01);
        imageRight02 = findViewById(R.id.gd_right_img02);
    }

    private void setAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.GdTitleBar);
        if (typedArray != null){
            int defaulteColor = context.getResources().getColor(R.color.gd_white);

            boolean showLeftIcon = typedArray.getBoolean(R.styleable.GdTitleBar_showLeftIcon,true);
            String title = typedArray.getString(R.styleable.GdTitleBar_title);
            int titleColor = typedArray.getColor(R.styleable.GdTitleBar_titleTextColor,defaulteColor);
            int bgColor = typedArray.getColor(R.styleable.GdTitleBar_titlebarBg, context.getResources().getColor(R.color.gd_titlebar_bg));
            int image01 = typedArray.getResourceId(R.styleable.GdTitleBar_rightImage01,0);
            String textRight01 = typedArray.getString(R.styleable.GdTitleBar_rightBtn01);
            int btnRightColor01 = typedArray.getColor(R.styleable.GdTitleBar_rightBtnColor01,defaulteColor);
            String textRight02 = typedArray.getString(R.styleable.GdTitleBar_rightBtn02);
            int btnRightColor02 = typedArray.getColor(R.styleable.GdTitleBar_rightBtnColor02,defaulteColor);
            int image02 = typedArray.getResourceId(R.styleable.GdTitleBar_rightImage02,0);
            typedArray.recycle();

            if (!showLeftIcon){
                leftBtn.setVisibility(INVISIBLE);
            }

            if (!TextUtils.isEmpty(title) && tvTitle!=null){
                tvTitle.setText(title);
            }
            tvTitle.setTextColor(titleColor);
            setBackgroundColor(bgColor);

            //同时设置文字和图标时，优先显示文字
            if (!TextUtils.isEmpty(textRight01)){
                tvRight01.setText(textRight01);
                tvRight01.setTextColor(btnRightColor01);
            }else{
                if (image01 !=0){
                    imageRight01.setImageResource(image01);
                }
            }
            if (!TextUtils.isEmpty(textRight02)){
                tvRight02.setText(textRight02);
                tvRight02.setTextColor(btnRightColor02);
            }else{
                if (image02 !=0){
                    imageRight02.setImageResource(image02);
                }
            }
        }
    }

    public GdTitleBar setLeftClick(OnClickListener onClickListener){
        if (onClickListener == null){
            return  this;
        }
        if (leftBtn!=null){
            leftBtn.setOnClickListener(onClickListener);
        }
        return this;
    }

    public GdTitleBar setRightClick01(OnClickListener onClickListener){
        if (onClickListener == null){
            return  this;
        }
        if (rightBtn01 != null ){
            rightBtn01.setOnClickListener(onClickListener);
        }
        return this;
    }

    public GdTitleBar setRightClick02(OnClickListener onClickListener){
        if (onClickListener == null){
            return  this;
        }
        if (rightBtn02 != null ){
            rightBtn02.setOnClickListener(onClickListener);
        }
        return this;
    }

    public GdTitleBar removeRight01(){
        if (rightBtn01 != null && imageRight01 !=null && tvRight01!=null){
            rightBtn01.setVisibility(View.INVISIBLE);
            imageRight01.setImageResource(0);
            tvRight01.setText("");
        }
        return this;
    }

    public GdTitleBar removeRight02(){
        if (rightBtn02 != null && imageRight02 !=null && tvRight02!=null){
            rightBtn02.setVisibility(View.INVISIBLE);
            imageRight02.setImageResource(0);
            tvRight02.setText("");
        }
        return this;
    }

    public GdTitleBar setRightImage01(int resourcesId){
        if (imageRight01 != null){
            imageRight01.setImageResource(resourcesId);
        }
        if (tvRight01 != null){
            tvRight01.setText("");
            tvRight01.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public GdTitleBar setRightImage02(int resourcesId){
        if (imageRight02 != null){
            imageRight02.setImageResource(resourcesId);
        }
        if (tvRight02 != null){
            tvRight02.setText("");
            tvRight02.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public GdTitleBar setRightBtn01(String text){
        if (tvRight01!=null && !TextUtils.isEmpty(text)){
            tvRight01.setText(text);
        }
        if (imageRight01!=null){
            imageRight01.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public GdTitleBar setRightBtn02(String text){
        if (tvRight02!=null && !TextUtils.isEmpty(text)){
            tvRight02.setText(text);
        }
        if (imageRight02!=null){
            imageRight02.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public GdTitleBar setRightBtnColor01(int color){
        if (tvRight01 != null){
            tvRight01.setTextColor(color);
        }
        return this;
    }

    public GdTitleBar setRightBtnColor02(int color){
        if (tvRight02 != null){
            tvRight02.setTextColor(color);
        }
        return this;
    }

}
