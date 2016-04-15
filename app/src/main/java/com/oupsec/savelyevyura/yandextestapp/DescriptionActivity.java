package com.oupsec.savelyevyura.yandextestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        //Получаем и устанавливаем заголовок
        String title = getIntent().getStringExtra("title");

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e) {
            Log.d("ACTION_BAR", "экшн не найден");
        }


        //Получаем, устанавливаем другие элементы
        String coverUrl = getIntent().getStringExtra("bigPic");
        String genres = getIntent().getStringExtra("genres");
        String description = getIntent().getStringExtra("description");
        int tracks = getIntent().getIntExtra("tracks", 0);
        int albums = getIntent().getIntExtra("albums", 0);

        ImageView coverView = (ImageView) findViewById(R.id.bigCoverImage);
        new DownloadImageTask(coverView).execute(coverUrl);

        TextView genresView = (TextView) findViewById(R.id.genresTextView);
        genresView.setText(genres);

        TextView countView = (TextView) findViewById(R.id.countTextView);
        countView.setText(declension("трек", "трека", "треков", tracks) + " • " + declension("альбом", "альбома", "альбомов", albums));

        TextView descriptionView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionView.setText(description);
        descriptionView.setMovementMethod(new ScrollingMovementMethod());



    }


    //Метод для определения окончания в слове
    public String declension(String w1, String w2, String w3, int num) {

        String declension = Integer.valueOf(num).toString();

        int last = num - ((num / 10) * 10);

        if (num <= 14) {
            if (num == 1)
                declension = declension + " " + w1;
            if (num >= 2 && num <= 4)
                declension = declension + " " + w2;
            if (num >= 5 && num <= 14)
                declension = declension + " " + w3;
        } else {
            if (last == 1)
                declension = declension + " " + w1;
            if (last >= 2 && last <= 4)
                declension = declension + " " + w2;
            if ((last >= 5 && last <= 9) || last == 0)
                declension = declension + " " + w3;

        }
        return declension;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
