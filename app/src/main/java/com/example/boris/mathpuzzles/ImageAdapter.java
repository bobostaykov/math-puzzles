package com.example.boris.mathpuzzles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ImageAdapter extends BaseAdapter {

    private Puzzle puzzle = new Puzzle();
    private GridView game_board;
    private Context mContext;
    private boolean zeroOnce = false;

    public ImageAdapter(Context context, GridView game_board) {
        mContext = context;
        this.game_board = game_board;
    }

    public int getCount() {
        return puzzle.getItems().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return puzzle.getItemId(position);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels/3, Resources.getSystem().getDisplayMetrics().widthPixels/3));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        //otherwise several elements are being imported to position 0
        if (position == 0 && zeroOnce) return imageView;
        if (position == 0) zeroOnce = true;

        imageView.setImageResource(puzzle.getItemId(position));
        puzzle.setImageViewToItem(imageView, position);

        game_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                puzzle.moveItem(puzzle.getItem(position));
            }
        });

        return imageView;
    }


}
