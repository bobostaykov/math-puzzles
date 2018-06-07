package com.example.boris.mathpuzzles;

import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class Puzzle {

    private int blankPosition = 8;

    private ArrayList<PuzzleItem> items = new ArrayList<PuzzleItem>() {{
        add(new PuzzleItem(R.drawable.s1, 0));
        add(new PuzzleItem(R.drawable.s2, 1));
        add(new PuzzleItem(R.drawable.s3, 2));
        add(new PuzzleItem(R.drawable.s4, 3));
        add(new PuzzleItem(R.drawable.s5, 4));
        add(new PuzzleItem(R.drawable.s6, 5));
        add(new PuzzleItem(R.drawable.s7, 6));
        add(new PuzzleItem(R.drawable.s8, 7));
        add(new PuzzleItem(0, 8));
    }};


    public Puzzle() {
        Collections.shuffle(items);
    }


    public ArrayList<PuzzleItem> getItems() {
        return items;
    }


    public void setImageViewToItem(ImageView imageView, int index) {
        for (PuzzleItem curr : items) {
            if (curr.getIndex() == index) {
                curr.setImageView(imageView);
                break;
            }
        }
    }


    public int getItemId(int index) {
        for (PuzzleItem curr : items) {
            if (curr.getIndex() == index) return curr.getDrawableId();
        }
        return -1;
    }


    public int getItemId(ImageView imageView) {
        for (PuzzleItem curr : items) {
            if (curr.getImageView().equals(imageView)) return curr.getDrawableId();
        }
        return -1;
    }


    public int getImageViewIndex(ImageView imageView) {
        for (PuzzleItem curr : items) {
            if (curr.getImageView().equals(imageView)) return curr.getIndex();
        }
        return -1;
    }


    public PuzzleItem getItem(int position) {
        for (PuzzleItem curr : items) {
            if (curr.getIndex() == position) return curr;
        }
        return null;
    }


    public int getBlankPosition() {
        return blankPosition;
    }


    public void setBlankPosition(int blankPosition) {
        this.blankPosition = blankPosition;
    }


    public void moveItem(PuzzleItem item, GridView gridView) {
        boolean left = item.getIndex() == blankPosition - 1 && blankPosition % 3 != 0;
        boolean right = item.getIndex() == blankPosition + 1 && (blankPosition - 2) % 3 != 0;
        boolean over = item.getIndex() == blankPosition - 3;
        boolean under = item.getIndex() == blankPosition + 3;
        if (left || right || over || under) {
            ImageView blank = (ImageView) gridView.getChildAt(blankPosition);
            blankPosition = item.getIndex();
            blank.setImageResource(item.getDrawableId());
            if (item.getImageView() != null) item.getImageView().setImageDrawable(null);
        }
    }


    public static void test(PuzzleItem item) {
        System.err.println(item.getImageView() == null);
    }

}
