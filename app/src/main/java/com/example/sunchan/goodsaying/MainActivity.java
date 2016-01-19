package com.example.sunchan.goodsaying;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MYD - MainActivity";

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

        if (arItem.size() == 0) {
            insertDefaultItems();
            getAllItem();
        }

        // ListView와 Adapter 연결
        listView = (ListView)this.findViewById(R.id.list_view);
        listAdapter = new CustomList(getApplicationContext(), R.layout.list_layout, arItem);
        listView.setAdapter(listAdapter);

        // 항목 클릭 시의 이벤트 리스너를 등록 -> NotiActivity 화면으로
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, NotiActivity.class);

                intent.putExtra("id", arItem.get(position).getId());
                intent.putExtra("text", arItem.get(position).getText());
                intent.putExtra("count", arItem.get(position).getCount());

                startActivity(intent);

            }
        });


        // 항목 롱 클릭 시의 이벤트 리스너를 등록 -> 수정/삭제 화면으로
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ModifyActivity.class);

                intent.putExtra("id", arItem.get(position).getId());
                intent.putExtra("text", arItem.get(position).getText());
                intent.putExtra("count", arItem.get(position).getCount());

                startActivityForResult(intent, 1);

                return true;
            }
        });


        // Notification 받고 알림 클릭시 해당 메시지 받는 부분
        Intent mainIntent = getIntent();
        String txt = mainIntent.getStringExtra("text");
        if (txt != null) {
            //Log.d(TAG, "Received MSG : " + txt);

            Intent notiIntent = new Intent(MainActivity.this, NotiActivity.class);
            notiIntent.putExtra("text", txt);
            startActivity(notiIntent);
        } else {
            //Log.d(TAG, "Received MSG is null");
        }

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

        //Log.d(TAG, "============================================================");
        for (int i = 0; i < arItem.size(); i++) {
            //Log.d(TAG, "ID : " + arItem.get(i).getId() + ", TEXT : " + arItem.get(i).getText() + ", COUNT : " + arItem.get(i).getCount());
            arItem.get(i).setText(arItem.get(i).getText());
        }
        //Log.d(TAG, "============================================================");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        arItem.clear();
        getAllItem();

        listAdapter.setArrayList(arItem);
        listAdapter.notifyDataSetChanged();
    }


    public void insertDefaultItems() {
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_1), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_2), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_3), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_4), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_5), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_6), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_7), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_8), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_9), 1));
        db.insertItem(new Item(getString(com.example.sunchan.goodsaying.R.string.default_10), 1));
    }

}
