package com.example.sunchan.goodsaying;

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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MY_DEBUG - MainActivity";

    private ListView listView;

    DBHandler db = new DBHandler(this);

    private ArrayList<Item> arItem = new ArrayList<Item>();
    private CustomList listAdapter;

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
        db.alarmDBCreate(db.getWritableDatabase());

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
        //setAlarm();
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
            //Toast.makeText(this, "click click", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
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

}
