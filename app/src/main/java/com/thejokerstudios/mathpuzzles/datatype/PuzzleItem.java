package com.thejokerstudios.mathpuzzles.datatype;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class PuzzleItem {

    private ImageView imageView;
    private Bitmap bitmap;
    private int currentIndex;
    private final int solvedIndex;


    public PuzzleItem(Bitmap bitmap, int solvedIndex) {
        this.bitmap = bitmap;
        this.solvedIndex = solvedIndex;
        this.currentIndex = solvedIndex;
    }


    //to create a copy
    public PuzzleItem(PuzzleItem toCopy) {
        this.imageView = toCopy.getImageView();
        this.currentIndex = toCopy.getCurrentIndex();
        this.solvedIndex = toCopy.getSolvedIndex();
    }


    public Bitmap getBitmap() {
        return bitmap;
    }


    public ImageView getImageView() {
        return imageView;
    }


    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public void setImageBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
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
        return "[" + imageView + ", " + bitmap + ", " + currentIndex + ", " + solvedIndex + "]";
    }

}
