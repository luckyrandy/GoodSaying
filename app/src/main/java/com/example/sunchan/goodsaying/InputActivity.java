package com.example.sunchan.goodsaying;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class InputActivity extends Activity {
    private static final String TAG = "MY_DEBUG - InputActivity";

    private EditText edit_text;
    private String mUserInput = "";

    DBHandler db = new DBHandler(this);

    private AlarmManager mAlarmMgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_layout);

        edit_text = (EditText) findViewById(R.id.edit_text);
    }

    public void onButtonClick(View view) {
        // 버튼 ID를 가져온다.
        switch (view.getId()) {
            case R.id.btn_save:         // 저장 버튼 설정
                insertItem();
                break;
            case R.id.btn_alarm:
                setAlarm();
                break;

        }
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

    public void setAlarm() {
        // 수행할 동작을 생성
        Intent intent = new Intent(this, AlarmReceive.class);   //AlarmReceive.class이클레스는 따로 만들꺼임 알람이 발동될때 동작하는 클레이스임
        //PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        // FLAG_UPDATE_CURRENT : 이미 생성된 PendingIntent 가 존재하면 해당 Intent 의 내용을 변경한다
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mAlarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);

        // 우선 알람 해제  ... 먼저 설정되엇던 것이 잇을 지도 모르니...
        mAlarmMgr.cancel(pIntent);

        // 알람이 발생할 정확한 시간을 지정
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 11, 00);//시간을 17시 45분으로 일단 set했음
        calendar.set(Calendar.SECOND, 0);


        //알람 예약
        // 한번 알람
        //mAlarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        //이건 여러번 알람 24*60*60*1000 이건 하루에한번 계속 알람한다는 뜻.
        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1 * 60 * 60 * 1000, pIntent);

        Toast.makeText(this, "Alarm setting", Toast.LENGTH_SHORT).show();

    }
}





