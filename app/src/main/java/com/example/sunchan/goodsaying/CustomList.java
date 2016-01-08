package com.example.sunchan.goodsaying;

// 어댑터 클래스

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomList extends BaseAdapter {
    private ArrayList<Item> arrayItem;
    private Context context;

    public CustomList(Context context, int resource, ArrayList<Item> objects) {
        //super(context, resource, objects);
        this.arrayItem = objects;
        this.context = context;
    }

    public int getCount() {
        return arrayItem.size();
    }

    public Object getItem(int position) {
        return arrayItem.get(position).getId();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        View v = convertView;

        if (v == null) {
            LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_layout, null);
        }

        final Item item = arrayItem.get(pos);

        if (item != null) {
            TextView txt = (TextView) v.findViewById(R.id.text_view);
            txt.setText(arrayItem.get(pos).getText());
        }

        return v;

    }

    // Adapter가 관리하는 Data List를 교체 한다.
    // 교체 후 Adapter.notifyDataSetChanged() 메서드로 변경 사실을
    // Adapter에 알려 주어 ListView에 적용 되도록 한다.
    public void setArrayList(ArrayList<Item> objects){
        this.arrayItem = objects;
    }
}
