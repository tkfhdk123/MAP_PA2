
package edu.skku.cs.pa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapSelection extends AppCompatActivity {
    public static final String NAME="maze_name";
    private ListView maplistview;
    private MapList mapList;
    private ArrayList<String> mapname;
    private ArrayList<String> mapsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.DES);

        TextView textView = findViewById(R.id.us_textView);
        textView.setText(name);
        mapname = new ArrayList<>();
        mapsize = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlbuilder = HttpUrl.parse("http://115.145.175.57:10099/maps").newBuilder();
        String url = urlbuilder.build().toString();

        Request req = new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson = new GsonBuilder().create();
                final DataMapList[] mapLists = gson.fromJson(myResponse, DataMapList[].class);

                for(DataMapList list : mapLists){
                    mapname.add(list.getName());
                    mapsize.add(list.getSize());
                }

                MapSelection.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        maplistview = findViewById(R.id.maze_listview);
                        mapList = new MapList(MapSelection.this, mapname, mapsize);
                        maplistview.setAdapter(mapList);
                    }
                });
            }
        });
    }
}