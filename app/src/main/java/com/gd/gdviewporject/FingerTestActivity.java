package com.gd.gdviewporject;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class FingerTestActivity extends AppCompatActivity {

    private TextView textView;

    private FingerprintManagerCompat fingerprintManagerCompat;
    private KeyguardManager keyguardManager;
    private CancellationSignal mCancellationSignal = new CancellationSignal();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_test);
        init();
        textView = findViewById(R.id.tv_note);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFingerPrintDialog();
            }
        });
    }

    private void init(){
        keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        fingerprintManagerCompat = FingerprintManagerCompat.from(this);
        boolean isHardware = fingerprintManagerCompat.isHardwareDetected();
        Log.e("硬件支持","isHardware = "+isHardware);
        boolean isKey = keyguardManager.isKeyguardSecure();
        Log.e("是否设置锁屏","isKey = "+isKey);
        boolean hasEnrolled = fingerprintManagerCompat.hasEnrolledFingerprints();
        Log.e("是否添加指纹","hasEnrolled = "+hasEnrolled);
    }

    private void showFingerPrintDialog(){
        fingerprintManagerCompat.authenticate(null, 0, mCancellationSignal, new FingerprintManagerCompat.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                Log.e("onAuthenticationError",errMsgId+"--"+errString);
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                Log.e("onAuthenticationHelp",helpMsgId+"--"+helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                Log.e("Succeeded","--"+result.toString());
            }

            @Override
            public void onAuthenticationFailed() {
                Log.e("onAuthenticationFailed","--");
            }
        },null);
    }


}
