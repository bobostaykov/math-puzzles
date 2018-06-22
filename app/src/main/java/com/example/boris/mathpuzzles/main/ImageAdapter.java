//
package com.example.boris.mathpuzzles.main;

import android.content.Context;
import android.content.res.Resources;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boris.mathpuzzles.activity.Game;
import com.example.boris.mathpuzzles.help.Global;

public class ImageAdapter extends BaseAdapter {

    private Puzzle puzzle;
    private GridView game_board;
    private TextView movesNumber;
    private CountDownTimer timer;
    private Context mContext;
    private Game game;
    private SoundPool soundPool;
    private boolean zeroOnce = false;

    public ImageAdapter(Context context, GridView game_board, int columns, TextView movesNumber, CountDownTimer timer, Game game, SoundPool soundPool) {
        mContext = context;
        this.game_board = game_board;
        this.movesNumber = movesNumber;
        this.timer = timer;
        this.game = game;
        //this.soundPool = soundPool;
        Global.setBoardColumns(columns);
        puzzle = new Puzzle(soundPool);
    }

    public int getCount() {
        return puzzle.getItems().size();
    }

    public Object getItem(int position) {
        return puzzle.getItem(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels / game_board.getNumColumns(), Resources.getSystem().getDisplayMetrics().widthPixels / game_board.getNumColumns()));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        //otherwise several elements are being imported to position 0
        if (position == 0 && zeroOnce) return imageView;
        if (position == 0) zeroOnce = true;

        //imageView.setImageResource(puzzle.getItemBitmap(position));
        imageView.setImageBitmap(puzzle.getItem(position).getBitmap());
        puzzle.setImageViewToItem(imageView, position);

        game_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                puzzle.moveItemGroup(puzzle.getItem(position), movesNumber, timer, game);
            }
        });

        return imageView;
    }


}
