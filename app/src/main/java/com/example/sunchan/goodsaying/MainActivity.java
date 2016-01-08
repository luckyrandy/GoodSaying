package com.example.sunchan.goodsaying;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MY_DEBUG - MainActivity";

    private ListView listView;

    DBHandler db = new DBHandler(this);

    private ArrayList<Item> arItem = new ArrayList<Item>();
    private CustomList listAdapter;

    private AlarmManager mAlarmMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Add a new good saying", Toast.LENGTH_SHORT).show();;
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, InputActivity.class);

                startActivityForResult(intent, 1);

            }
        });

        // table이 존재하지 않으면 table 생성
        db.onCreate(db.getWritableDatabase());

        getAllItem();

        // ListView와 Adapter 연결
        listView = (ListView)this.findViewById(R.id.list_view);
        listAdapter = new CustomList(getApplicationContext(), R.layout.list_layout, arItem);
        listView.setAdapter(listAdapter);

         // 항목 [클릭]시의 이벤트 리스너를 등록
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ModifyActivity.class);

                intent.putExtra("id", arItem.get(position).getId());
                intent.putExtra("text", arItem.get(position).getText());

                startActivityForResult(intent, 1);

            }
        });


        // 알람 setting
        setAlarm();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // get all items
    public void getAllItem() {
        arItem = db.getAllItems();

        for (int i = 0; i < arItem.size(); i++) {
            Log.d(TAG, "ID : " + arItem.get(i).getId());
            Log.d(TAG, "TEXT : " + arItem.get(i).getText());

            arItem.get(i).setText(arItem.get(i).getText());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        arItem.clear();
        getAllItem();

        listAdapter.setArrayList(arItem);
        listAdapter.notifyDataSetChanged();
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
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 17, 37);//시간을 17시 45분으로 일단 set했음
        calendar.set(Calendar.SECOND, 0);


        //알람 예약
        // 한번 알람
        //mAlarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        //이건 여러번 알람 24*60*60*1000 이건 하루에한번 계속 알람한다는 뜻.
        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1 * 3 * 60 * 1000, pIntent);



    }
}
