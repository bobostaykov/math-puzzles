//
package com.example.boris.mathpuzzles.main;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.activity.Game;
import com.example.boris.mathpuzzles.activity.Settings;
import com.example.boris.mathpuzzles.datatype.Inversions;
import com.example.boris.mathpuzzles.datatype.PuzzleItem;
import com.example.boris.mathpuzzles.help.Global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Puzzle {

    private int blankPosition, boardColumns, movesCount;
    private ArrayList<PuzzleItem> items = new ArrayList<>();
    private Game gameObj = new Game();
    private Game game;
    private Global global = new Global();
    private boolean timerStarted = false;
    private static int minutesPassed;
    private static int secondsPassed;


    public Puzzle() {
        boardColumns = Global.getBoardColumns();
        //importItemsToList();

        ImageView image = new ImageView(Global.getContext());
        image.setImageResource(R.drawable.sheet1);
        splitImage(image);

        shuffleItems();
        movesCount = 0;
    }


    private void splitImage(ImageView image) {
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        int pieceWidth = bitmap.getWidth() / boardColumns;
        int pieceHeight = bitmap.getHeight() / boardColumns;
        int xCoord, yCoord = 0, index = 0;

        for (int x = 0; x < boardColumns; x++){
            xCoord = 0;
            for (int y = 0; y < boardColumns; y++){
                Bitmap b = Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, pieceWidth, pieceHeight);
                if (index != boardColumns*boardColumns - 1) items.add(new PuzzleItem(b, index));
                else items.add(new PuzzleItem(null, index));
                xCoord += pieceWidth;
                index++;
            }
            yCoord += pieceHeight;
        }
    }


//    public void importItemsToList() {
//        for (int i = 0; i < boardColumns*boardColumns - 1; i++) {
//            int resourceID = Global.getGlobalResources().getIdentifier("s" + i, "drawable", Global.getGlobalPackageName());
//            String name = Global.getGlobalResources().getResourceEntryName(resourceID);
//            if (i < 10) {
//                if (name.charAt(name.length() - 2) == 's') {
//                    items.add(new PuzzleItem(resourceID, i));
//                }
//            }
//            else {
//                if (name.charAt(name.length() - 3) == 's') {
//                    items.add(new PuzzleItem(resourceID, i));
//                }
//            }
//        }
//        items.add(new PuzzleItem(0, boardColumns*boardColumns - 1));
//    }


    public void shuffleItems() {
        //avoiding impossible to solve permutations
        do {
            Collections.shuffle(items);
            int index = 0;
            for (PuzzleItem curr : items) {
                curr.setCurrentIndex(index);
                index++;
            }
            blankPosition = getItemPos(null);
        } while (isImpossible(items));
    }


    public void outputItems() {
        System.err.println();
        for (PuzzleItem curr : items) System.err.print(curr.getCurrentIndex() + " ");
        System.err.println();
    }


    public boolean isImpossible(ArrayList<PuzzleItem> list) {
        boolean inversionsEven;
        if (boardColumns % 2 == 0) {
            inversionsEven = countInversions(toArray(withoutBlank(list))).getInversions() % 2 == 0;
            if (blankIsOnEvenRow()) return inversionsEven;
            else return !inversionsEven;
        }
        else {
            inversionsEven = countInversions(toArray(withoutBlank(list))).getInversions() % 2 == 0;
            return !inversionsEven;
        }
    }


    public boolean blankIsOnEvenRow() {
        //counting from bottom
        if (boardColumns == 4) return (blankPosition >= 0 && blankPosition <= 3)  ||  (blankPosition >= 8 && blankPosition <= 11);
        if (boardColumns == 5) return (blankPosition >= 5 && blankPosition <= 9)  ||  (blankPosition >= 15 && blankPosition <= 19);
        //if (boardColumns == 3)
        return blankPosition >= 3 && blankPosition <= 5;
    }


    public static Inversions countInversions(PuzzleItem[] array) {
        if (array.length == 1) return new Inversions(0, array);
        Inversions a = countInversions(Arrays.copyOfRange(array, 0, (array.length - 1) / 2 + 1));
        Inversions b = countInversions(Arrays.copyOfRange(array, (array.length - 1) / 2 + 1, array.length));
        Inversions c = mergeAndCount(a.getArray(), b.getArray());
        return new Inversions(a.getInversions() + b.getInversions() + c.getInversions(), c.getArray());
    }


    public static Inversions mergeAndCount(PuzzleItem[] array1, PuzzleItem[] array2) {
        int i = 0, j = 0, p = 0, count = 0;
        PuzzleItem[] resultArray = new PuzzleItem[array1.length + array2.length];
        while (array1.length != 0 && array2.length != 0) {
            if (array1[i].getSolvedIndex() <= array2[j].getSolvedIndex()) {
                resultArray[p] = array1[i];
                p++;
                if (array1.length == 1) array1 = new PuzzleItem[0];
                else array1 = Arrays.copyOfRange(array1, i + 1, array1.length);
            } else {
                resultArray[p] = array2[j];
                p++;
                if (array2.length == 1) array2 = new PuzzleItem[0];
                else array2 = Arrays.copyOfRange(array2, j + 1, array2.length);
                //number of elements left in list1
                count += array1.length;
            }
        }
        if (array1.length == 0) {
            for (int k = j; k < array2.length; k++) {
                resultArray[p] = array2[k];
                p++;
            }
        }
        if (array2.length == 0) {
            for (int k = i; k < array1.length; k++) {
                resultArray[p] = array1[k];
                p++;
            }
        }
        return new Inversions(count, resultArray);
    }


    private static PuzzleItem[] toArray(ArrayList<PuzzleItem> list) {
        PuzzleItem[] array = new PuzzleItem[list.size()];
        int i = 0;
        for (PuzzleItem curr : list) {
            array[i] = curr;
            i++;
        }
        return array;
    }


    //returns the same list without the blank item
    public ArrayList<PuzzleItem> withoutBlank(ArrayList<PuzzleItem> list) {
        ArrayList<PuzzleItem> result = new ArrayList<>(list);
        for (PuzzleItem curr : result) {
            if (curr.getBitmap() == null) {
                result.remove(curr);
                break;
            }
        }
        return result;
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


    public Bitmap getItemBitmap(int index) {
        for (PuzzleItem curr : items) {
            if (curr.getCurrentIndex() == index) return curr.getBitmap();
        }
        return null;
    }


    public Bitmap getItemBitmap(ImageView imageView) {
        for (PuzzleItem curr : items) {
            if (curr.getImageView().equals(imageView)) return curr.getBitmap();
        }
        return null;
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


    public void moveItemGroup(PuzzleItem item, TextView movesNumber, CountDownTimer timer, final Game game, SoundPool soundPool) {
        this.game = game;

        //clicked item
        int index = item.getCurrentIndex();
        int newMoves = 0;
        final int soundIdSlide = soundPool.load(game, R.raw.slide_sound_2, 2);
        final int soundIdWin = soundPool.load(game, R.raw.win_sound, 1);
        final PuzzleItem itemCopy = item;

        game.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (Settings.getSoundOn() && rowColumn(itemCopy) != 0) global.playSound(soundPool, soundIdSlide);
            }
        });

        switch (rowColumn(item)) {
            case 1: {
                //item is in the same row before the blank
                PuzzleItem toMove = new PuzzleItem(item);
                newMoves = blankPosition - index;
                for (int i = 0; i < blankPosition - index; i++) {
                    PuzzleItem toRemove = getItem(item.getCurrentIndex() + 1);
                    slideItem(item, toRemove);
                    item = toRemove;
                }
                //finally "slide" the blank to the place of the initial (clicked) item
                slideItem(item, toMove);
                break;
            }

            case 2: {
                //item is in the same row after the blank
                PuzzleItem toMove = new PuzzleItem(item);
                newMoves = index - blankPosition;
                for (int i = 0; i < index - blankPosition; i++) {
                    PuzzleItem toRemove = getItem(item.getCurrentIndex() - 1);
                    slideItem(item, toRemove);
                    item = toRemove;
                }
                //finally "slide" the blank to the place of the initial (clicked) item
                slideItem(item, toMove);
                break;
            }

            case 3: {
                //item is in the same column before the blank
                PuzzleItem toMove = new PuzzleItem(item);
                newMoves = (blankPosition - index) / boardColumns;
                for (int i = 0; i < (blankPosition - index) / boardColumns; i++) {
                    PuzzleItem toRemove = getItem(item.getCurrentIndex() + boardColumns);
                    slideItem(item, toRemove);
                    item = toRemove;
                }
                //finally "slide" the blank to the place of the initial (clicked) item
                slideItem(item, toMove);
                break;
            }

            case 4: {
                //item is in the same column after the blank
                PuzzleItem toMove = new PuzzleItem(item);
                newMoves = (index - blankPosition) / boardColumns;
                for (int i = 0; i < (index - blankPosition) / boardColumns; i++) {
                    PuzzleItem toRemove = getItem(item.getCurrentIndex() - boardColumns);
                    slideItem(item, toRemove);
                    item = toRemove;
                }
                //finally "slide" the blank to the place of the initial (clicked) item
                slideItem(item, toMove);
                break;
            }
        }

        movesCount += newMoves;
        if (movesCount > 0 && !timerStarted && Settings.getForTimeOn()) {
            timer.start();
            timerStarted = true;
        }
        movesNumber.setText("" + movesCount);


        if (isSolved()) {

            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (Settings.getSoundOn() && rowColumn(itemCopy) != 0) global.playSound(soundPool, soundIdWin);
                }
            });

            timer.cancel();

            DialogInterface.OnClickListener mainMenu = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameObj.backToMain(game);
                }
            };

            DialogInterface.OnClickListener changeLevel = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameObj.back(game);
                }
            };

            DialogInterface.OnClickListener tryAnother = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameObj.restartActivity(game);
                }
            };

            Global.createDialog(R.string.you_won,
                                Global.getContext().getString(R.string.youWon_dialog_text, movesCount, minutesPassed, secondsPassed),
                                R.string.main_menu, mainMenu,
                                R.string.change_level, changeLevel,
                                R.string.try_another, tryAnother);


        }

    }


    public static void setMinutesPassed(int minutes) {
        minutesPassed = minutes;
    }


    public static void setSecondsPassed(int seconds) {
        secondsPassed = seconds;
    }


    //returns if the input item is in the same row or column as the blank item and which row/column
    public int rowColumn(PuzzleItem item) {
        int index = item.getCurrentIndex();
        if (index < blankPosition  &&  (blankPosition - index <= blankPosition % boardColumns)) {
            //item is in the same row as blank, before blank
            return 1;
        }
        if (index > blankPosition  &&  (index - blankPosition <= boardColumns - blankPosition % boardColumns - 1)) {
            //item is in the same row as blank, after blank
            return 2;
        }
        if ((index - blankPosition) % boardColumns == 0) {
            //item is in the same column as blank
            if (index < blankPosition) {
                //item is before blank in this column
                return 3;
            }
            if (index > blankPosition) {
                //item is after blank in this column
                return 4;
            }
        }
        //item ist neither in the same row nor in the same column, or it is == blank
        return 0;
    }


    public void slideItem(PuzzleItem itemToSlide, PuzzleItem itemToRemove) {
        itemToSlide.setImageView(itemToRemove.getImageView());
        if (itemToSlide.getCurrentIndex() == blankPosition) blankPosition = itemToRemove.getCurrentIndex();
        itemToSlide.setCurrentIndex(itemToRemove.getCurrentIndex());
        itemToSlide.setImageBitmap(itemToSlide.getBitmap());
    }


//    public void swapItems(PuzzleItem item1, PuzzleItem item2) {
//        //swapping ImageViews
//        ImageView tempView = item1.getImageView();
//        item1.setImageView(item2.getImageView());
//        item2.setImageView(tempView);
//
//        //swapping indices
//        int tempIndex = item1.getCurrentIndex();
//        item1.setCurrentIndex(item2.getCurrentIndex());
//        if (blankPosition == tempIndex) blankPosition = item1.getCurrentIndex();
//        if (blankPosition == item2.getCurrentIndex()) blankPosition = tempIndex;
//        item2.setCurrentIndex(tempIndex);
//
//        int tempId = item1.getDrawableId();
//        item1.getImageView().setImageResource(item2.getDrawableId());
//        item2.getImageView().setImageResource(tempId);
//    }


//    public void drawPuzzle(GridView gridView) {
//        for (int i = 0; i < gridView.getChildCount(); i++) {
//            ImageView curr = (ImageView) gridView.getChildAt(i);
//            curr.setImageResource(getItem(i).getDrawableId());
//        }
//    }


    public int getItemPos(Bitmap bitmap) {
        for (PuzzleItem curr : items) {
            if (curr.getBitmap() == bitmap) return curr.getCurrentIndex();
        }
        return -1;
    }


    public boolean isSolved() {
        for (PuzzleItem curr : items)
            if (curr.getSolvedIndex() != curr.getCurrentIndex()) return false;
        return true;
    }

}













