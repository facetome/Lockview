package com.basic.lockview.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.basic.lockview.view.LockView;
import com.basic.lockview.view.LockView.CompleteListener;

/**
 * Created by acer on 2015/8/17.
 */
public class LockViewActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LockView view = new LockView(this);
        view.setCompleteListener(new CompleteListener() {
            @Override
            public void onComplete(String password) {
                Toast.makeText(LockViewActivity.this, password, Toast.LENGTH_SHORT).show();
            }
        });
        //测试
        setContentView(view);

    }
}
