package com.oupsec.savelyevyura.yandextestapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by savelyevyura on 05/04/16.
 */
public class Artist {
    //Объявляем переменные
    private int id, tracks, albums;
    private String name;
    private String link;
    private String description;
    private String smallPic;
    private String bigPic;
    private String genres;

    //Инициализация в конструкторе
    public Artist(int id, String name, String genres, int tracks, int albums, String link, String description) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
    }

    //Разбираем JSON
    public static Artist getJSON(final JSONObject object) throws JSONException {
        final int id = object.optInt("id");
        final String name = object.optString("name");
        String genres = "";
        JSONArray jsonGenres = object.getJSONArray("genres");
        for (int index = 0; index < jsonGenres.length(); index++) {
            if( index == jsonGenres.length() - 1)
                genres += jsonGenres.getString(index);
            else genres += jsonGenres.getString(index)+ ", ";
        }
        final int tracks = object.optInt("tracks");
        final int albums = object.optInt("albums");
        final String link = object.optString("link");
        String description = object.optString("description");

        description = Character.toUpperCase(description.charAt(0)) + description.substring(1);



        final JSONObject pics = object.getJSONObject("cover");



        Artist artist = new Artist(id, name, genres, tracks, albums, link, description);
        artist.setSmallPic(pics.getString("small"));
        artist.setBigPic(pics.getString("big"));

        return artist;
    }
    //Инициализируем массив JSON
    public static ArrayList<Artist> arrayFromJson(final JSONArray array) {
        final ArrayList<Artist> artists = new ArrayList<>();
        Log.d("ARRAY_SIZE", String.format("%d",array.length()));
        for (int index = 0; index < array.length(); ++index) {
            try {
                final Artist artist = getJSON(array.getJSONObject(index));
                if (artist != null)
                    artists.add(artist);

            } catch (final JSONException ignored) {

            }
        }

        return artists;

    }




    //Геттеры
    public int getId() {
        return id;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getGenres() {
        return genres;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public String getBigPic() {
        return bigPic;
    }

    //Сеттеры
    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%d,%s,%s,%s", this.id, this.tracks, this.albums, this.name, this.link, this.genres);
    }
}
