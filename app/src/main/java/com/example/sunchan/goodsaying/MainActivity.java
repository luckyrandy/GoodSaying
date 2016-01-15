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
    private static final String TAG = "MYD - MainActivity";

    private static final String DEFAULT_1 = "나는 매일 모든 면에서 점점 더 좋아지고 있다";
    private static final String DEFAULT_2 = "매일 새로운 마음으로 임하자";
    private static final String DEFAULT_3 = "하면 할 수 있다";
    private static final String DEFAULT_4 = "웃자 ^^";
    private static final String DEFAULT_5 = "차분하게 준비하면서 기다리면 반드시 좋은 기회가 온다";

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

         // 항목 [클릭]시의 이벤트 리스너를 등록
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ModifyActivity.class);

                intent.putExtra("id", arItem.get(position).getId());
                intent.putExtra("text", arItem.get(position).getText());
                intent.putExtra("count", arItem.get(position).getCount());

                startActivityForResult(intent, 1);

            }
        });

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

        Log.d(TAG, "============================================================");
        for (int i = 0; i < arItem.size(); i++) {
            Log.d(TAG, "ID : " + arItem.get(i).getId()
                    + ", TEXT : " + arItem.get(i).getText()
                    + ", COUNT : " + arItem.get(i).getCount());

            arItem.get(i).setText(arItem.get(i).getText());
        }
        Log.d(TAG, "============================================================");
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
        db.insertItem(new Item(DEFAULT_1, 1));
        db.insertItem(new Item(DEFAULT_2, 1));
        db.insertItem(new Item(DEFAULT_3, 1));
        db.insertItem(new Item(DEFAULT_4, 1));
        db.insertItem(new Item(DEFAULT_5, 1));
    }

}
