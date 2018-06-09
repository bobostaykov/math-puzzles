package com.example.boris.mathpuzzles;

import android.widget.ImageView;

public class PuzzleItem {

    private ImageView imageView;
    private int drawableId;
    private final int solvedIndex;
    private int currentIndex;


    public PuzzleItem(int drawableId, int solvedIndex) {
        this.drawableId = drawableId;
        this.solvedIndex = solvedIndex;
        this.currentIndex = solvedIndex;
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


    public void setDrawableId(int newID) {
        drawableId = newID;
    }


    public int getSolvedIndex() {
        return solvedIndex;
    }


    public int getCurrentIndex() {
        return currentIndex;
    }


    public void setCurrentIndex(int newIndex) {
        currentIndex = newIndex;
    }

}
