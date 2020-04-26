package com.gd.gdviewporject;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gd.gdlibrary.view.GdTitleBar;
import com.gd.gdlibrary.view.PwdDialog;
import com.gd.gdlibrary.view.TestView;

public class MainActivity extends AppCompatActivity {

    private GdTitleBar gdTitleBar;
    private int i = 0;
    private int notiId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TestView testView = findViewById(R.id.test_view);
        gdTitleBar = findViewById(R.id.titlebar);
        gdTitleBar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gdTitleBar.removeRight01();
//                gdTitleBar.setRightImage01(R.drawable.common_chat_icon_active);
//                gdTitleBar.setRightBtn02("测试");
                Intent intent = new Intent(MainActivity.this,BActivity.class);
                startActivity(intent);
//                testView.requestLayout();
//                showNoti();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PwdDialog.Builder(MainActivity.this).label("支付金额1000元")
                        .setInputCallBack(new PwdDialog.InputCallBack() {
                            @Override
                            public void inputComplete(String password) {
                                Toast.makeText(MainActivity.this,password,Toast.LENGTH_LONG).show();
                            }
                        }).build().show();
            }
        });

        findViewById(R.id.btn_finger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FingerTestActivity.class));
            }
        });


    }



    private void showNoti(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("掌上运维");
        builder.setContentText("【" +"】产生一条新的【"+"】"+
                "【"+"】告警，请及时处理！");
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        // 需要VIBRATE权限
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setPriority(Notification.PRIORITY_DEFAULT);

        // Creates an explicit intent for an Activity in your app
        //自定义打开的界面
        Intent resultIntent = new Intent(this, MainActivity.class);
//		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, i++,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        notiId++;
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notiId, builder.build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("方法","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("方法","onResume");
    }
}
