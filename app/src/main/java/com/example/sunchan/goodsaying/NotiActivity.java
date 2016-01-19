package com.example.sunchan.goodsaying;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotiActivity extends AppCompatActivity {
    private static final String TAG = "MYD - NotiActivity";

    private TextView mTextView;
    private Toolbar mToolBar;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.sunchan.goodsaying.R.layout.noti_layout);

        setupViews();

        Intent intent = getIntent();
        String txt = intent.getStringExtra("text");

        mTextView.setText(txt);



    }


    private void setupViews() {
        mTextView = (TextView) findViewById(com.example.sunchan.goodsaying.R.id.noti_text_view);
        // TextView Scroll Enable
        mTextView.setMovementMethod(new ScrollingMovementMethod());
        // Set Font
        mTextView.setTypeface(Typeface.createFromAsset(getAssets(), "NanumPen.ttf"));

        mToolBar = (Toolbar) findViewById(com.example.sunchan.goodsaying.R.id.sub_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(com.example.sunchan.goodsaying.R.string.app_name);   /* It works */

        mLayout = (LinearLayout) findViewById(com.example.sunchan.goodsaying.R.id.noti_layout);
        mLayout.getBackground().setAlpha(130);      // 투명도 효과

    }

}
