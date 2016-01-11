package com.example.sunchan.goodsaying;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends Activity {
    private static final String TAG = "MY_DEBUG - ModifyActivity";

    private EditText edit_text;
    private int mId;
    private String mText = "";

    private String mUserInput = "";

    private AlertDialog mDialog = null;

    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_layout);

        edit_text = (EditText) findViewById(R.id.edit_text_update);

        Intent intent = getIntent();

        mId = intent.getIntExtra("id", -1);
        mText = intent.getStringExtra("text");

        //Toast.makeText(this, Integer.toString(mId), Toast.LENGTH_SHORT).show();
        
        edit_text.setText(mText);
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
        mUserInput = edit_text.getText().toString();

        if (mUserInput == null || mUserInput.length() == 0) {
            Toast.makeText(this, R.string.warn_text, Toast.LENGTH_SHORT).show();
            edit_text.requestFocus();       // focus on
            return;
        }

        db.updateItem(mId, mUserInput);

        Toast.makeText(ModifyActivity.this, R.string.txt_update_done, Toast.LENGTH_SHORT).show();
        finish();           // Activity 종료
    }


    private AlertDialog createDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        ab.setTitle(R.string.btn_delete);
        ab.setMessage(R.string.dial_del_msg);
        ab.setCancelable(false);
        //ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

        ab.setPositiveButton(R.string.btn_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                db.deleteItem(mId);
                setDismiss(mDialog);
                Toast.makeText(ModifyActivity.this, R.string.txt_delete_done, Toast.LENGTH_SHORT).show();
                finish();           // Activity 종료
            }
        });

        ab.setNegativeButton(R.string.txt_cancel, new DialogInterface.OnClickListener() {
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
