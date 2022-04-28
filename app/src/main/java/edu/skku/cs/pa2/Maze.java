package edu.skku.cs.pa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Integer> maze_int;
    private ArrayList<Cell> maze_cell;
    private ArrayList<Integer> maze_size;
    private ArrayList<Integer> now_state;
    int hint = -1;
    int state = 0;
    int turn = 0;
    int image = R.drawable.user_up;
    boolean check_hint = false;

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

                maze_int = new ArrayList<>();
                maze_cell = new ArrayList<>();
                maze_size = new ArrayList<>();
                now_state = new ArrayList<>();

                for(int i=0; i<size_num; i++){
                    String line = inform.split("\n")[i+1];
                    String[] line_num = line.split("\\s", size_num);
                    for(int j=0; j<line_num.length; j++){
                        int arr = Integer.parseInt(line_num[j].trim());
                        maze_int.add(arr);
                    }
                }

                for(int i=0; i<maze_int.size(); i++){
                    Cell cell = new Cell(maze_int.get(i));
                    maze_cell.add(cell);
                    maze_size.add(size_num);
                    now_state.add(0);
                }

                Maze.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GridView gridView = findViewById(R.id.maze_gridview);
                        Button up_btn = findViewById(R.id.up_button);
                        Button left_btn = findViewById(R.id.left_button);
                        Button down_btn = findViewById(R.id.down_button);
                        Button right_btn = findViewById(R.id.right_button);
                        Button hint_btn = findViewById(R.id.hint_button);
                        
                        //초기 시작 화면
                        gridView.setNumColumns(size_num);
                        gridMazeAdapter = new GridMazeAdapter(Maze.this, R.drawable.user_up, maze_cell, maze_size, now_state, 0, check_hint);
                        gridView.setAdapter(gridMazeAdapter);

                        //위쪽 버튼 눌렀을 경우
                        up_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int check = state - size_num;
                                if (check < 0 | check >= maze_int.size()){}
                                else {
                                    if (maze_cell.get(state - size_num).bottom) {
                                    } else {
                                        state -= size_num;
                                        if(state == hint){
                                            check_hint = false;
                                        }
                                        turn++;
                                        now_state = new ArrayList<>();
                                        for (int i = 0; i < maze_int.size(); i++) {
                                            now_state.add(state);
                                        }
                                        image = R.drawable.user_up;
                                        gridMazeAdapter = new GridMazeAdapter(Maze.this, R.drawable.user_up, maze_cell, maze_size, now_state, 0, check_hint);
                                        gridView.setAdapter(gridMazeAdapter);

                                        TextView tv = findViewById(R.id.turn_textView);
                                        tv.setText("Turn : " + String.valueOf(turn));

                                        if(state == maze_int.size() - 1){
                                            Toast.makeText(getApplicationContext(), "Finish!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });

                        //왼쪽 버튼 눌렀을 경우
                        left_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int check = state - 1;
                                if (check < 0 | check >= maze_int.size()){}
                                else {
                                    if (maze_cell.get(state - 1).right) {
                                    } else {
                                        state -= 1;
                                        if(state == hint){
                                            check_hint = false;
                                        }
                                        turn++;
                                        now_state = new ArrayList<>();
                                        for (int i = 0; i < maze_int.size(); i++) {
                                            now_state.add(state);
                                        }
                                        image = R.drawable.user_left;
                                        gridMazeAdapter = new GridMazeAdapter(Maze.this, R.drawable.user_left, maze_cell, maze_size, now_state, 0, check_hint);
                                        gridView.setAdapter(gridMazeAdapter);

                                        TextView tv = findViewById(R.id.turn_textView);
                                        tv.setText("Turn : " + String.valueOf(turn));

                                        if(state == maze_int.size() - 1){
                                            Toast.makeText(getApplicationContext(), "Finish!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });

                        //아래쪽 버튼 눌렀을 경우
                        down_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int check = state + size_num;
                                if (check < 0 | check >= maze_int.size()){}
                                else {
                                    if (maze_cell.get(state + size_num).top) {
                                    } else {
                                        state += size_num;
                                        if(state == hint){
                                            check_hint = false;
                                        }
                                        turn++;
                                        now_state = new ArrayList<>();
                                        for (int i = 0; i < maze_int.size(); i++) {
                                            now_state.add(state);
                                        }
                                        image = R.drawable.user_down;
                                        gridMazeAdapter = new GridMazeAdapter(Maze.this, R.drawable.user_down, maze_cell, maze_size, now_state, 0 ,check_hint);
                                        gridView.setAdapter(gridMazeAdapter);

                                        TextView tv = findViewById(R.id.turn_textView);
                                        tv.setText("Turn : " + String.valueOf(turn));

                                        if(state == maze_int.size() - 1){
                                            Toast.makeText(getApplicationContext(), "Finish!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                        
                        //오른쪽 버튼 눌렀을 경우
                        right_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int check = state + 1;
                                if (check < 0 | check >= maze_int.size()){}
                                else {
                                    if (maze_cell.get(state + 1).left) {
                                    } else {
                                        state += 1;
                                        if(state == hint){
                                            check_hint = false;
                                        }
                                        turn++;
                                        now_state = new ArrayList<>();
                                        for (int i = 0; i < maze_int.size(); i++) {
                                            now_state.add(state);
                                        }
                                        image = R.drawable.user_right;
                                        gridMazeAdapter = new GridMazeAdapter(Maze.this, R.drawable.user_right, maze_cell, maze_size, now_state, 0, check_hint);
                                        gridView.setAdapter(gridMazeAdapter);

                                        TextView tv = findViewById(R.id.turn_textView);
                                        tv.setText("Turn : " + String.valueOf(turn));

                                        if(state == maze_int.size() - 1){
                                            Toast.makeText(getApplicationContext(), "Finish!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });

                        //힌트 버튼 눌렀을 경우
                        hint_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                check_hint = true;
                                MazeDfsCheck check = new MazeDfsCheck(maze_cell, state, size_num);
                                hint = check.bfs();


                                now_state = new ArrayList<>();
                                for (int i = 0; i < maze_int.size(); i++) {
                                    now_state.add(state);
                                }
                                gridMazeAdapter = new GridMazeAdapter(Maze.this, image, maze_cell, maze_size, now_state, hint, check_hint);
                                gridView.setAdapter(gridMazeAdapter);
                            }
                        });

                    }
                });
            }
        });
    }
}