package edu.skku.cs.pa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String DES="username";
    EditText editText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edittext);
        btn = findViewById(R.id.button);
    }

    public void startNewActivity(View v){
        String name = editText.getText().toString();
        OkHttpClient client = new OkHttpClient();
        DataModel data = new DataModel();
        data.setUsername(name);
        Gson gson = new Gson();
        String json = gson.toJson(data, DataModel.class);

        HttpUrl.Builder urlbuilder = HttpUrl.parse("http://115.145.175.57:10099/users").newBuilder();
        String url = urlbuilder.build().toString();

        Request req = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson1 = new GsonBuilder().create();
                final Data accept = gson1.fromJson(myResponse, Data.class);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(accept.isSuccess()){
                            Intent intent = new Intent(MainActivity.this, MapSelection.class);
                            intent.putExtra(DES, name);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Wrong User Name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}