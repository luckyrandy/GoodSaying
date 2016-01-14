package com.example.sunchan.goodsaying;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {
    private static final String TAG = "MY_DEBUG - InputActivity";

    private EditText edit_text;
    private String mUserInput = "";
    private Toolbar mToolBar;

    DBHandler db = new DBHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_layout);

        setupViews();

        edit_text.requestFocus();

        //키보드 보이게 하는 부분
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


    }

    public void onButtonClick(View view) {
        // 버튼 ID를 가져온다.
        switch (view.getId()) {
            case R.id.btn_save:         // 저장 버튼 설정
                insertItem();
                break;
        }
    }


    private void setupViews() {
        edit_text = (EditText) findViewById(R.id.edit_text);

        mToolBar = (Toolbar) findViewById(R.id.sub_toolbar);
        setSupportActionBar(mToolBar);
        //mToolBar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);

        getSupportActionBar().setTitle(R.string.insert_title);
    }

    public void insertItem() {
        // check user input
        mUserInput = edit_text.getText().toString();

        if (mUserInput == null || mUserInput.length() == 0) {
            Toast.makeText(this, R.string.warn_text, Toast.LENGTH_SHORT).show();
            edit_text.requestFocus();       // focus on
            return;
        }

        // insert to DB
        if (db.insertItem(new Item(mUserInput)) > 0) {
            Toast.makeText(this, R.string.insert_ok, Toast.LENGTH_SHORT).show();
            finish();       // 저장 후 엑티비티 종료
        } else {
            Toast.makeText(this, R.string.insert_fail, Toast.LENGTH_SHORT).show();
        }

        return;
    }

}





