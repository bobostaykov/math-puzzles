package com.example.boris.mathpuzzles;

import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class Puzzle {

    private int blankPosition;

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
        blankPosition = getItemPos(0);
        Collections.shuffle(items);
    }


    public ArrayList<PuzzleItem> getItems() {
        return items;
    }


    public void setImageViewToItem(ImageView imageView, int index) {
        for (PuzzleItem curr : items) {
            if (curr.getCurrentIndex() == index) {
                curr.setImageView(imageView);
                break;
            }
        }
    }


    public int getItemId(int index) {
        for (PuzzleItem curr : items) {
            if (curr.getCurrentIndex() == index) return curr.getDrawableId();
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
            if (curr.getImageView().equals(imageView)) return curr.getCurrentIndex();
        }
        return -1;
    }


    public PuzzleItem getItem(int position) {
        for (PuzzleItem curr : items) {
            if (curr.getCurrentIndex() == position) {
                return curr;
            }
        }
        return null;
    }


    public int getBlankPosition() {
        return blankPosition;
    }


    public void setBlankPosition(int blankPosition) {
        this.blankPosition = blankPosition;
    }


    public void moveItem(PuzzleItem item) {
        boolean left = item.getCurrentIndex() == blankPosition - 1 && blankPosition % 3 != 0;
        boolean right = item.getCurrentIndex() == blankPosition + 1 && (blankPosition - 2) % 3 != 0;
        boolean over = item.getCurrentIndex() == blankPosition - 3;
        boolean under = item.getCurrentIndex() == blankPosition + 3;
        if (left || right || over || under) {
            swapItemWithBlank(item);
        }
    }


    public void swapItemWithBlank(PuzzleItem item) {
        //swapping ImageViews
        ImageView tempView = getItem(blankPosition).getImageView();
        getItem(blankPosition).setImageView(item.getImageView());
        item.setImageView(tempView);

        //swapping indices
        int newIndex = blankPosition;
        getItem(blankPosition).setCurrentIndex(item.getCurrentIndex());
        blankPosition = item.getCurrentIndex();
        item.setCurrentIndex(newIndex);

        getItem(blankPosition).getImageView().setImageResource(0);
        getItem(newIndex).getImageView().setImageResource(item.getDrawableId());
    }


    public void drawPuzzle(GridView gridView) {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            ImageView curr = (ImageView) gridView.getChildAt(i);
            curr.setImageResource(getItem(i).getDrawableId());
        }
    }


    public int getItemPos(int drawableId) {
        for (PuzzleItem curr : items) {
            if (curr.getDrawableId() == drawableId) return curr.getCurrentIndex();
        }
        return -1;
    }

}













