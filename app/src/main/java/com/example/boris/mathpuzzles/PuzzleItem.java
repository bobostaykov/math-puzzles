package com.example.boris.mathpuzzles;

import android.widget.ImageView;

public class PuzzleItem {

    private ImageView imageView;
    private int drawableId;
    private int index;


    public PuzzleItem(int drawableId, int index) {
        this.drawableId = drawableId;
        this.index = index;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public int getDrawableId() {
        return drawableId;
    }


    public int getIndex() {
        return index;
    }

}
