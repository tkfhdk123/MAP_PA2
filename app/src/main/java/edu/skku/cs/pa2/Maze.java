package edu.skku.cs.pa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
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

public class Maze extends AppCompatActivity {
    private GridView gridView;
    private GridMazeAdapter gridMazeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        Intent intent = getIntent();
        String m_name = intent.getStringExtra(MapSelection.NAME);

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlbuilder = HttpUrl.parse("http://115.145.175.57:10099/maze/map").newBuilder();
        urlbuilder.addQueryParameter("name", m_name);
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
                TextView textView = findViewById(R.id.turn_textView);
                Gson gson = new GsonBuilder().create();
                DataMaze dm = gson.fromJson(myResponse, DataMaze.class);

                String inform = dm.getMaze();
                String size = inform.split("\n")[0];
                Integer size_num = Integer.parseInt(size);

                ArrayList<Integer> maze_int = new ArrayList<>();
                for(int i=0; i<size_num; i++){
                    String line = inform.split("\n")[i+1];
                    String[] line_num = line.split("\\s", size_num);
                    for(int j=0; j<line_num.length; j++){
                        int arr = Integer.parseInt(line_num[j].trim());
                        maze_int.add(arr);
                    }
                }

                ArrayList<Cell> maze_cell = new ArrayList<>();
                ArrayList<Integer> maze_size = new ArrayList<>();
                for(int i=0; i<maze_int.size(); i++){
                    Cell cell = new Cell(maze_int.get(i));
                    maze_cell.add(cell);
                    maze_size.add(size_num);
                }

                Maze.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GridView gridView = findViewById(R.id.maze_gridview);
                        gridView.setNumColumns(size_num);
                        gridMazeAdapter = new GridMazeAdapter(Maze.this, maze_int, maze_cell, maze_size);
                        gridView.setAdapter(gridMazeAdapter);
                    }
                });
            }
        });
    }
}