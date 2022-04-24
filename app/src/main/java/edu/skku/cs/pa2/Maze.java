package edu.skku.cs.pa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Maze extends AppCompatActivity {

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

                Maze.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(myResponse);
                    }
                });
            }
        });
    }
}