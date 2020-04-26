package com.gd.gdviewporject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

public class BActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isEmpty = savedInstanceState == null;
        if (savedInstanceState !=null){
            Log.e("onCreate",""+savedInstanceState.getString("ed"));
        }

        setContentView(R.layout.activity_b);
        TextView textView = findViewById(R.id.tv_test);
        textView.setGravity(Gravity.CENTER);
        editText = findViewById(R.id.ed_username);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("onRestore","is running"+savedInstanceState.getString("ed"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("onSaveInstanceState","is running");
        if (!TextUtils.isEmpty(editText.getText().toString())){
            outState.putString("ed",editText.getText().toString());
        }
    }
}
