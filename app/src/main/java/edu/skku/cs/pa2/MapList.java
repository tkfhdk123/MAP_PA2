package edu.skku.cs.pa2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MapList extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> name;
    private ArrayList<String> size;

    public MapList(Context mContext, ArrayList<String> name, ArrayList<String> size){
        this.mContext=mContext;
        this.name=name;
        this.size=size;
    }

    @Override
    public int getCount() { return name.size(); }

    @Override
    public Object getItem(int i){ return name.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater layoutInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.activity_map_list, viewGroup, false);
        }
        TextView name_tv = view.findViewById(R.id.name_textView);
        TextView size_tv = view.findViewById(R.id.size_textView);
        Button start_btn = view.findViewById(R.id.start_button);

        String mname = name.get(i).toString();
        String msize = size.get(i).toString();

        name_tv.setText(mname);
        size_tv.setText(msize);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Maze.class);
                intent.putExtra(MapSelection.NAME, mname);
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}
