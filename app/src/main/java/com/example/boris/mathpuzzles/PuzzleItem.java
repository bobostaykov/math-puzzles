//
package com.example.boris.mathpuzzles;

import android.widget.ImageView;

public class PuzzleItem {

    private ImageView imageView;
    private int drawableId, currentIndex;
    private final int solvedIndex;


    public PuzzleItem(int drawableId, int solvedIndex) {
        this.drawableId = drawableId;
        this.solvedIndex = solvedIndex;
        this.currentIndex = solvedIndex;
    }


    //to create a copy
    public PuzzleItem(PuzzleItem toCopy) {
        this.imageView = toCopy.getImageView();
        this.drawableId = toCopy.getDrawableId();
        this.currentIndex = toCopy.getCurrentIndex();
        this.solvedIndex = toCopy.getSolvedIndex();
    }


    public ImageView getImageView() {
        return imageView;
    }


    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public void setImageResource(int id) {
        imageView.setImageResource(id);
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

    @Override
    public String toString() {
        return "[" + imageView + ", " + drawableId + ", " + currentIndex + ", " + solvedIndex + "]";
    }

}
