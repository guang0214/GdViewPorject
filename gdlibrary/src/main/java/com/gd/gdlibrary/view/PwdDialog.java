package com.gd.gdlibrary.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gd.gdlibrary.R;

/**
 * 基本信息
 *
 * @author HuangXiangGuang
 * @description 请填写类功能描述
 * @date Created in 2020/1/16 16:12
 * @modified By HuangXiangGuang
 */
public class PwdDialog {

    private Context mContext;
    private String label;
    private PopupWindow popupWindow;
    private ImageView ivClose;
    private TextView tvLabel;
    private PasswordView passwordView;
    private InputCallBack mInputCallBack;

    private PwdDialog(Builder builder) {
        this.mContext = builder.context;
        this.label = builder.label;
        this.mInputCallBack = builder.inputCallBack;
        init();
    }

    public static class Builder{
        private Context context;
        private String label;
        private InputCallBack inputCallBack;

        public Builder(Context context){
            this.context = context;
        }

        public Builder label(String label){
            this.label = label;
            return this;
        }

        public Builder setInputCallBack(InputCallBack inputCallBack){
            this.inputCallBack = inputCallBack;
            return this;
        }

        public PwdDialog build(){
            return new PwdDialog(this);
        }
    }

    private void init(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.gd_pwd_dialog,null);
        ivClose = view.findViewById(R.id.gd_iv_close_pup);
        tvLabel = view.findViewById(R.id.gd_tv_label);
        tvLabel.setText(TextUtils.isEmpty(label)?"":label);
        passwordView = view.findViewById(R.id.gd_password_view);
        passwordView.setInputCompleteCallBack(new PasswordView.InputCompleteCallBack() {
            @Override
            public void complete(String password) {
                if (mInputCallBack != null){
                    mInputCallBack.inputComplete(password);
                }
            }
        });
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.gd_popup);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mContext instanceof Activity){
                    setBackgroundAlpha((Activity)mContext,1f);
                }else if(mContext instanceof AppCompatActivity){
                    setBackgroundAlpha((AppCompatActivity)mContext,1f);
                }
            }
        });
    }

    public void dismiss(){
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(lp);
    }

    public void show(){
        try {
            View parent = null;
            if (mContext instanceof Activity){
                parent = ((Activity)mContext).getWindow().getDecorView();
                setBackgroundAlpha((Activity)mContext,0.5f);
            }else if(mContext instanceof AppCompatActivity){
                parent = ((AppCompatActivity)mContext).getWindow().getDecorView();
                setBackgroundAlpha((AppCompatActivity)mContext,0.5f);
            }else{
                throw new Exception("parent view should not be null");
            }
            popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
        } catch (Exception e) {
        }
    }

    public void setLabel(String label){
        if (TextUtils.isEmpty(label) || tvLabel == null ){
            return;
        }
        tvLabel.setText(label);
    }

    public interface InputCallBack{
        /**
         * 获取输入密码
         * @param password
         */
        void inputComplete(String password);
    }
}
