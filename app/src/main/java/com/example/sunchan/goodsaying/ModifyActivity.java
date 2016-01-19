package com.example.sunchan.goodsaying;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {
    private static final String TAG = "MYD - ModifyActivity";

    private EditText mEditText;
    private Toolbar mToolBar;

    private int mId;
    private String mText = "";
    private int mCount;

    private String mUserInput = "";

    private AlertDialog mDialog = null;

    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_layout);

        setupViews();

        Intent intent = getIntent();

        mId = intent.getIntExtra("id", -1);
        mText = intent.getStringExtra("text");
        mCount = intent.getIntExtra("count", -1);

        //Toast.makeText(this, Integer.toString(mId), Toast.LENGTH_SHORT).show();

        mEditText.setText(mText);
    }


    private void setupViews() {
        mEditText = (EditText) findViewById(R.id.edit_text_update);

        mToolBar = (Toolbar) findViewById(R.id.sub_toolbar);
        setSupportActionBar(mToolBar);
        //mToolBar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);

        getSupportActionBar().setTitle(R.string.update_title);
    }

    public void onButtonClick(View view) {
        // 버튼 ID를 가져온다.
        switch (view.getId()) {
            case R.id.btn_update:         // 수정 버튼 설정
                updateItem();
                break;
            case R.id.btn_delete:         // 삭제 버튼 설정
                mDialog = createDialog();
                mDialog.show();
                break;
        }
    }


    public void updateItem() {
        mUserInput = mEditText.getText().toString();

        if (mUserInput == null || mUserInput.length() == 0) {
            Toast.makeText(this, com.example.sunchan.goodsaying.R.string.warn_text, Toast.LENGTH_SHORT).show();
            mEditText.requestFocus();       // focus on
            return;
        }

        db.updateItem(mId, mUserInput, mCount);

        Toast.makeText(ModifyActivity.this, com.example.sunchan.goodsaying.R.string.txt_update_done, Toast.LENGTH_SHORT).show();
        finish();           // Activity 종료
    }


    private AlertDialog createDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        ab.setTitle(com.example.sunchan.goodsaying.R.string.btn_delete);
        ab.setMessage(com.example.sunchan.goodsaying.R.string.dial_del_msg);
        ab.setCancelable(false);
        //ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

        ab.setPositiveButton(com.example.sunchan.goodsaying.R.string.btn_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                db.deleteItem(mId);
                setDismiss(mDialog);
                Toast.makeText(ModifyActivity.this, com.example.sunchan.goodsaying.R.string.txt_delete_done, Toast.LENGTH_SHORT).show();
                finish();           // Activity 종료
            }
        });

        ab.setNegativeButton(com.example.sunchan.goodsaying.R.string.txt_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mDialog);
            }
        });

        return ab.create();
    }


    /**
     * 다이얼로그 종료
     * @param dialog
     */
    private void setDismiss(Dialog dialog){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

}
