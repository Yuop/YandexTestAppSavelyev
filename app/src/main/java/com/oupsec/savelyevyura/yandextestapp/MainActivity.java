package com.oupsec.savelyevyura.yandextestapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Artist> artists = new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ParseTask().execute();
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Если интернет во время запуска был выключен, а после того как свернули - развернули приложение включили
        if (listView.getAdapter() == null)
          new ParseTask().execute();
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String resultJSON = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                //Создаем соединение
                URL url = new URL("http://cache-default06g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream stream = connection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                //Разбираем объект
                reader = new BufferedReader(new InputStreamReader(stream));

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJSON = buffer.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJSON;
        }

        @Override
        protected void onPostExecute(String stringJSON) {
            super.onPostExecute(stringJSON);
            Log.d("LOG", stringJSON);

            JSONArray dataJSONArray = null;
            //Наполняем массив
            try {
                dataJSONArray = new JSONArray(stringJSON);


                artists = Artist.arrayFromJson(dataJSONArray);
                //Устанавливаем адаптер для listView
                listView.setAdapter(new CustomMusicAdapter(MainActivity.this, artists));
                Log.d("SIZE", String.format("%d",artists.size()));

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Данных не найдено.\nВы подключены к интернету?", Toast.LENGTH_LONG).show();

            }

            }
    }
}
