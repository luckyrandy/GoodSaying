package com.example.sunchan.goodsaying;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "MYD - SettingActivity";

    private Switch mSwitch;
    private Button mTimeButton;
    private TextView mTextView;
    private Toolbar mToolBar;

    private int iCount;
    private int iHour;
    private int iMin;

    private String mEnabledColor = "#4169E1";
    private String mDisabledColor = "#A8A8A8";

    DBHandler db = new DBHandler(this);

    private AlarmManager mAlarmMgr;

    private ArrayList<AlarmItem> arItem = new ArrayList<AlarmItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        setupViews();


        // for Alarm
        mAlarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);


        arItem = db.getAllAlarmItem();
        iCount = arItem.size();

        /*
        Log.d(TAG, "SIZE : " + String.valueOf(iCount));
        for (int i = 0; i < arItem.size(); i++) {
            Log.d(TAG, "flag : " + arItem.get(i).getFlag());
            Log.d(TAG, "hour : " + arItem.get(i).getHour());
            Log.d(TAG, "min : " + arItem.get(i).getMin());
        }
        */

        if (iCount == 0) {
            mSwitch.setChecked(false);

            //초기 시간 설정
            final Calendar c = Calendar.getInstance();
            iHour = c.get(Calendar.HOUR_OF_DAY);
            iMin = c.get(Calendar.MINUTE);

            mTimeButton.setEnabled(false);
            mTimeButton.setTextColor(Color.parseColor(mDisabledColor));
            mTextView.setText(R.string.set_alarm_msg);
        } else if (arItem.get(0).getFlag() == 0) {
            mSwitch.setChecked(false);
            // 이전 설정 시간으로 초기화
            iHour = arItem.get(0).getHour();
            iMin = arItem.get(0).getMin();

            mTimeButton.setEnabled(false);
            mTimeButton.setTextColor(Color.parseColor(mDisabledColor));
            mTextView.setText(R.string.set_alarm_off_msg);
        } else if (arItem.get(0).getFlag() == 1) {
            mSwitch.setChecked(true);

            iHour = arItem.get(0).getHour();
            iMin = arItem.get(0).getMin();

            String time = String.valueOf(iHour) + "시 " +
                    String.valueOf(iMin) + "분에 알람이 설정 되었습니다";
            mTextView.setText(time);
        } else {
            mSwitch.setChecked(false);

            //초기 시간 설정
            final Calendar c = Calendar.getInstance();
            iHour = c.get(Calendar.HOUR_OF_DAY);
            iMin = c.get(Calendar.MINUTE);

            mTimeButton.setEnabled(false);
            mTimeButton.setTextColor(Color.parseColor(mDisabledColor));
            mTextView.setText(R.string.set_alarm_msg);
        }

        //set the switch to ON


        //attach a listener to check for changes in state
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Toast.makeText(SettingActivity.this, "Switch is currently ON", Toast.LENGTH_SHORT).show();
                    mTimeButton.setEnabled(true);
                    mTimeButton.setTextColor(Color.parseColor(mEnabledColor));
                    mTextView.setText(R.string.set_alarm_msg);
                } else {
                    //Toast.makeText(SettingActivity.this, "Switch is currently OFF", Toast.LENGTH_SHORT).show();
                    mTimeButton.setEnabled(false);
                    mTimeButton.setTextColor(Color.parseColor(mDisabledColor));
                    cancelAlarm();
                    mTextView.setText(R.string.set_alarm_off_msg);

                    Toast.makeText(SettingActivity.this, R.string.set_alarm_off_msg, Toast.LENGTH_SHORT).show();
                }

            }
        });

        //check the current state before we display the screen
        /*
        if (mSwitch.isChecked()) {
            //switchStatus.setText("Switch is currently ON");
        } else {
            //switchStatus.setText("Switch is currently OFF");
        }
        */
    }


    private void setupViews() {
        mSwitch = (Switch) findViewById(R.id.alarm_set);
        mTimeButton = (Button) findViewById(R.id.btn_time);
        mTextView = (TextView) findViewById(R.id.text_view);

        mToolBar = (Toolbar) findViewById(R.id.sub_toolbar);
        setSupportActionBar(mToolBar);
        //mToolBar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        /* It doesn't work
        mToolBar.setTitle(R.string.set_alarm_title);
        */
        getSupportActionBar().setTitle(R.string.set_alarm_title);   /* It works */
    }


    public void onButtonClick(View view) {
        // 버튼 ID를 가져온다.
        switch (view.getId()) {
            case R.id.btn_time:                // 시간 설정 버튼
                /*
                final Calendar c = Calendar.getInstance();
                iHour = c.get(Calendar.HOUR_OF_DAY);
                iMin = c.get(Calendar.MINUTE);
                */

                Dialog dlgTime = new TimePickerDialog(this, myTimeSetListener, iHour,
                        iMin, false);
                dlgTime.show();

                break;

        }
    }


    private TimePickerDialog.OnTimeSetListener myTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            iHour = hourOfDay;
            iMin = minute;

            setAlarm();

            String time = String.valueOf(iHour) + "시 " +
                          String.valueOf(iMin) + "분에 알람이 설정 되었습니다";
            mTextView.setText(time);

            Toast.makeText(SettingActivity.this, time, Toast.LENGTH_SHORT).show();

        }
    };


    public void setAlarm() {
        long triggerTime = 0;
        long intervalTime = 24 * 60 * 60 * 1000;    // 24시간

        // 우선 알람 해제  ... 먼저 설정되엇던 것이 잇을 지도 모르니...
        cancelAlarm();

        // Alarm 등록
        Intent intent = new Intent(this, AlarmReceive.class);   //AlarmReceive.class이클레스는 따로 만들꺼임 알람이 발동될때 동작하는 클레이스임
        //PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        // FLAG_UPDATE_CURRENT : 이미 생성된 PendingIntent 가 존재하면 해당 Intent 의 내용을 변경한다
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        triggerTime = setTriggerTime();

        //알람 예약
        //mAlarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);          // 한번 알람
        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, intervalTime, pIntent);

        //Toast.makeText(this, "Alarm setting", Toast.LENGTH_SHORT).show();

        db.insertAlarmItem(new AlarmItem(1, iHour, iMin));

    }

    private void cancelAlarm()
    {
        Intent intent = new Intent(this, AlarmReceive.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmMgr.cancel(pIntent);

        db.deleteAlarmItem();

    }


    private long setTriggerTime()
    {
        // current Time
        long atime = System.currentTimeMillis();

        // timepicker
        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR_OF_DAY, iHour);
        curTime.set(Calendar.MINUTE, iMin);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);

        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }


}
