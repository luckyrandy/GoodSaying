package com.example.sunchan.goodsaying;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SettingActivity extends Activity {
    private static final String TAG = "DEBUG - SettingActivity";

    private Switch mSwitch;

    //private TimePicker mTimePicker;
    private int iHour;
    private int iMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        mSwitch = (Switch) findViewById(R.id.alarm_set);
        //mTimePicker = (TimePicker) findViewById(R.id.time_picker);

        //set the switch to ON
        mSwitch.setChecked(true);

        //attach a listener to check for changes in state
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingActivity.this, "Switch is currently ON", Toast.LENGTH_SHORT).show();
                    //mTimePicker.setEnabled(true);
                } else {
                    Toast.makeText(SettingActivity.this, "Switch is currently OFF", Toast.LENGTH_SHORT).show();
                    //mTimePicker.setEnabled(false);
                }

            }
        });

        //check the current state before we display the screen
        if (mSwitch.isChecked()) {
            //switchStatus.setText("Switch is currently ON");
        } else {
            //switchStatus.setText("Switch is currently OFF");
        }


        /*
        final Calendar c = Calendar.getInstance();
        iHour = c.get(Calendar.HOUR_OF_DAY);
        iMin = c.get(Calendar.MINUTE);


        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                iHour = hourOfDay;
                iMin = minute;
            }
        });
        */

    }


    public void onButtonClick(View view) {
        // 버튼 ID를 가져온다.
        switch (view.getId()) {
            case R.id.btn_alarm_cancel:         // 취소 버튼 설정
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.btn_alarm_save:         // 저장 버튼 설정
                String time = "Hour: " + String.valueOf(iHour) + "\n"
                        + "Minute: " + String.valueOf(iMin);
                Toast.makeText(SettingActivity.this, time, Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_time:                // 시간 설정 버튼
                final Calendar c = Calendar.getInstance();
                iHour = c.get(Calendar.HOUR_OF_DAY);
                iMin = c.get(Calendar.MINUTE);

                Dialog dlgTime = new TimePickerDialog(this, myTimeSetListener, iHour,
                        iMin, false);
                dlgTime.show();


                break;

        }
    }


    private TimePickerDialog.OnTimeSetListener myTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = "Hour: " + String.valueOf(hourOfDay) + "\n"
                    + "Minute: " + String.valueOf(minute);
            Toast.makeText(SettingActivity.this, time, Toast.LENGTH_LONG).show();
        }
    };

}
