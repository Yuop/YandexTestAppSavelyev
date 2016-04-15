package com.oupsec.savelyevyura.yandextestapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by savelyevyura on 04/04/16.
 */
public class CustomMusicAdapter extends BaseAdapter {

    private Activity activity;
    private List<Artist> artists;
    private LayoutInflater inflater;

    public CustomMusicAdapter(Activity activity,List<Artist> artists) {
        this.activity = activity;
        this.artists = artists;

    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public Object getItem(int position) {
        return artists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return artists.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_artist_row, parent, false);
            holder = new ViewHolder();
            holder.artistName = (TextView) convertView.findViewById(R.id.artistText);
            holder.artistGenre = (TextView) convertView.findViewById(R.id.genreText);
            holder.artistSongs = (TextView) convertView.findViewById(R.id.songsText);
            holder.artistPortret = (ImageView) convertView.findViewById(R.id.portretImage);
            convertView.setTag(holder);
            Log.d("ADAPTER", "updated");
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Artist item = artists.get(position);

        holder.artistName.setText(item.getName());
        holder.artistGenre.setText(item.getGenres());
        holder.artistSongs.setText(String.format("%d песен, %d альбомов", item.getTracks(), item.getAlbums()));
        Picasso.with(convertView.getContext()).load(item.getSmallPic()).into(holder.artistPortret);

        final View finalConvertView = convertView;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalConvertView.getContext(), DescriptionActivity.class);

                String transitionName = finalConvertView.getContext().getString(R.string.transition_artist);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(finalConvertView, 0 , 0, finalConvertView.getWidth(), finalConvertView.getHeight());


                intent.putExtra("title", item.getName());
                intent.putExtra("bigPic", item.getBigPic());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("genres", item.getGenres());
                intent.putExtra("tracks", item.getTracks());
                intent.putExtra("albums", item.getAlbums());

                ActivityCompat.startActivity(activity, intent, options.toBundle());
            }
        });



        return convertView;
    }
    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }

    class ViewHolder {
        TextView artistGenre, artistName, artistSongs;
        ImageView artistPortret;
    }

}

