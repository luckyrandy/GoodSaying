package com.example.sunchan.goodsaying;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends Activity {
    private static final String TAG = "MY_DEBUG - ModifyActivity";

    private EditText edit_text;
    private int mId;
    private String mText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_layout);

        edit_text = (EditText) findViewById(R.id.edit_text_update);

        Intent intent = getIntent();

        mId = intent.getIntExtra("id", -1);
        mText = intent.getStringExtra("text");

        Toast.makeText(this, Integer.toString(mId), Toast.LENGTH_SHORT).show();
        
        edit_text.setText(mText);
    }
}
