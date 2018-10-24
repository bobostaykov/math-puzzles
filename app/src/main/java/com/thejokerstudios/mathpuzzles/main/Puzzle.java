package com.thejokerstudios.mathpuzzles.main;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thejokerstudios.mathpuzzles.R;
import com.thejokerstudios.mathpuzzles.activity.Game;
import com.thejokerstudios.mathpuzzles.activity.MainMenu;
import com.thejokerstudios.mathpuzzles.activity.Settings;
import com.thejokerstudios.mathpuzzles.datatype.AllLevels;
import com.thejokerstudios.mathpuzzles.datatype.Inversions;
import com.thejokerstudios.mathpuzzles.datatype.Level;
import com.thejokerstudios.mathpuzzles.datatype.PuzzleItem;
import com.thejokerstudios.mathpuzzles.help.Global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class Puzzle {

    private int blankPosition, boardColumns, movesCount, soundIdSlide, soundIdWin, soundIdButton;
    private ArrayList<PuzzleItem> items = new ArrayList<>();
    private ArrayList<Integer> easyImages = new ArrayList<>(),
                               mediumImages = new ArrayList<>(),
                               hardImages = new ArrayList<>();
    private Game gameObj = new Game();
    private Global global = new Global();
    private boolean timerStarted = false;
    private static int minutesPassed, secondsPassed;
    private SoundPool soundPool;


    Puzzle(SoundPool soundPool) {
        boardColumns = Global.getBoardColumns();

        populateImageLists();
        ImageView image;

        //in order to load the same image when user chooses "restart"
        if (Global.getCurrentImageRealTag() == 0)
            image = chooseImage();
        else {
            image = new ImageView(Global.getContext());
            image.setImageResource(Global.getCurrentImageRealTag());
            image.setTag(Global.getCurrentImageRealTag());
            Global.setCurrentImageRealTag(0);
        }

        Global.setCurrentImageTag((int)image.getTag());
        splitImage(image);

        shuffleItems();
        movesCount = 0;

        this.soundPool = soundPool;
        soundIdSlide = this.soundPool.load(Global.getContext(), R.raw.slide_sound_2, 2);
        soundIdWin = this.soundPool.load(Global.getContext(), R.raw.win_sound, 1);
        soundIdButton = this.soundPool.load(Global.getContext(), R.raw.button_click_1, 1);
    }


    //filling the easy-, medium- and hard-Images lists with imagesIds from drawable folder
    private void populateImageLists() {
        int resourceIdEasy;
        int resourceIdMedium;
        int resourceIdHard;

        for (int i = 1; i <= 15; i++) {
            resourceIdEasy = Global.getGlobalResources().getIdentifier("easy" + i, "drawable", Global.getGlobalPackageName());
            //ImageView easyImage = new ImageView(Global.getContext());
            easyImages.add(resourceIdEasy);

            resourceIdMedium = Global.getGlobalResources().getIdentifier("medium" + i, "drawable", Global.getGlobalPackageName());
            //ImageView mediumImage = new ImageView(Global.getContext());
            mediumImages.add(resourceIdMedium);

            resourceIdHard = Global.getGlobalResources().getIdentifier("hard" + i, "drawable", Global.getGlobalPackageName());
            //ImageView hardImage = new ImageView(Global.getContext());
            hardImages.add(resourceIdHard);
        }
    }


    //randomly choosing an image according to the chosen math problems difficulty
    private ImageView chooseImage() {
        ArrayList<Integer> listToChooseFrom;

        if (AllLevels.getMath() == Level.EASY)
            listToChooseFrom = easyImages;
        else if (AllLevels.getMath() == Level.MEDIUM)
            listToChooseFrom = mediumImages;
        else //if (AllLevels.getMath() == Level.HARD)
            listToChooseFrom = hardImages;

        Random random = new Random();
        int randomNumber = random.nextInt(listToChooseFrom.size());

        ImageView image = new ImageView(Global.getContext());
        image.setImageResource(listToChooseFrom.get(randomNumber));
        image.setTag(listToChooseFrom.get(randomNumber));

        return image;
    }


    //splitting image to 9, 16 or 25 pieces depending on the chosen board size
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


    //shuffling the board pieces
    private void shuffleItems() {
        //avoiding impossible to solve permutations
        do {
            Collections.shuffle(items);
            int index = 0;
            for (PuzzleItem curr : items) {
                curr.setCurrentIndex(index);
                index++;
            }
            blankPosition = getBlankItemPos();
        } while (isImpossible(items));
    }


    //checking if the current permutation of the board pieces is impossible to solve,
    //depending on the number of inversions in the pieces presented in the form of an array
    private boolean isImpossible(ArrayList<PuzzleItem> list) {
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


    private boolean blankIsOnEvenRow() {
        //counting from bottom
        if (boardColumns == 4) return (blankPosition >= 0 && blankPosition <= 3)  ||  (blankPosition >= 8 && blankPosition <= 11);
        if (boardColumns == 5) return (blankPosition >= 5 && blankPosition <= 9)  ||  (blankPosition >= 15 && blankPosition <= 19);
        //if (boardColumns == 3)
        return blankPosition >= 3 && blankPosition <= 5;
    }


    private static Inversions countInversions(PuzzleItem[] array) {
        if (array.length == 1) return new Inversions(0, array);
        Inversions a = countInversions(Arrays.copyOfRange(array, 0, (array.length - 1) / 2 + 1));
        Inversions b = countInversions(Arrays.copyOfRange(array, (array.length - 1) / 2 + 1, array.length));
        Inversions c = mergeAndCount(a.getArray(), b.getArray());
        return new Inversions(a.getInversions() + b.getInversions() + c.getInversions(), c.getArray());
    }


    private static Inversions mergeAndCount(PuzzleItem[] array1, PuzzleItem[] array2) {
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


    //converting an ArrayList to a simple array, so it can be passed to countInversions()
    private static PuzzleItem[] toArray(ArrayList<PuzzleItem> list) {
        PuzzleItem[] array = new PuzzleItem[list.size()];
        int i = 0;
        for (PuzzleItem curr : list) {
            array[i] = curr;
            i++;
        }
        return array;
    }


    //returns the same list without the blank item, so it stays at the end
    private ArrayList<PuzzleItem> withoutBlank(ArrayList<PuzzleItem> list) {
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


    public PuzzleItem getItem(int position) {
        for (PuzzleItem curr : items) {
            if (curr.getCurrentIndex() == position) {
                return curr;
            }
        }
        return null;
    }


    //moving board piece(s) after the user touches one
    public void moveItemGroup(PuzzleItem item, final TextView movesNumber, CountDownTimer timer, final Game game) {

        //clicked item
        int index = item.getCurrentIndex();
        int newMoves = 0;
        final PuzzleItem itemCopy = item;

        if (Settings.getSoundOn() && rowColumn(itemCopy) != 0)
            global.playSound(soundPool, soundIdSlide);

        //releasing SoundPool resources and creating a new SoundPool object every 500 button presses to avoid running out of memory
        if (Global.getTotalSounds() % 500 == 0) {
            //reloading sound with delay, otherwise the playing sound (two lines of code above) gets interrupted (slide sound length = 400 milliseconds)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    soundPool = MainMenu.reloadSound(soundPool);
                    soundIdSlide = soundPool.load(Global.getContext(), R.raw.slide_sound_2, 2);
                    soundIdWin = soundPool.load(Global.getContext(), R.raw.win_sound, 1);
                    soundIdButton = soundPool.load(Global.getContext(), R.raw.button_click_1, 1);
                }
            }, 400);
        }

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
        movesNumber.setText(String.valueOf(movesCount));


        if (isSolved()) {

            if (Settings.getSoundOn())
                global.playSound(soundPool, soundIdWin);

            timer.cancel();

            View.OnClickListener mainMenu = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Settings.getSoundOn())
                        global.playSound(soundPool, soundIdButton);
                    gameObj.backToMain(game);
                }
            };

            View.OnClickListener changeLevel = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Settings.getSoundOn())
                        global.playSound(soundPool, soundIdButton);
                    gameObj.back(game);
                }
            };

            View.OnClickListener tryAnother = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Settings.getSoundOn())
                        global.playSound(soundPool, soundIdButton);
                    gameObj.restartActivity(game);
                }
            };

            String youWon_time, youWon_noTime, textToShow;

            // otherwise the textToShow is always in english no matter the current locale, a bug I suppose...
            if (Global.getLocale().equalsIgnoreCase("de")) {
                youWon_time = "Gut gemacht! Züge: %1d,\nZeit %02d:%02d";
                youWon_noTime = "Gut gemacht! Züge: %1d";
            } else if (Global.getLocale().equalsIgnoreCase("bg")) {
                youWon_time = "Поздравления! Ходове: %1d,\nВреме %02d:%02d";
                youWon_noTime = "Поздравления! Ходове: %1d";
            } else {
                youWon_time = "Congrats! Moves: %1d,\nTime %02d:%02d";
                youWon_noTime = "Congrats! Moves: %1d";
            }

            if (Settings.getForTimeOn())
                textToShow = String.format(new Locale(Global.getLocale()), youWon_time, movesCount, minutesPassed, secondsPassed);
            else
                textToShow = String.format(new Locale(Global.getLocale()), youWon_noTime, movesCount);

            //creating the "you won" dialog
            global.createDialog(false, R.string.you_won,
                                textToShow,
                                R.string.main_menu, mainMenu,
                                R.string.change_level, changeLevel,
                                R.string.try_another, tryAnother);


        }

    }


    //returns if the input item is in the same row or column as the blank item and which row/column
    private int rowColumn(PuzzleItem item) {
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


    private void slideItem(PuzzleItem itemToSlide, PuzzleItem itemToRemove) {
        itemToSlide.setImageView(itemToRemove.getImageView());
        if (itemToSlide.getCurrentIndex() == blankPosition) blankPosition = itemToRemove.getCurrentIndex();
        itemToSlide.setCurrentIndex(itemToRemove.getCurrentIndex());
        itemToSlide.setImageBitmap(itemToSlide.getBitmap());
    }


    private int getBlankItemPos() {
        for (PuzzleItem curr : items) {
            if (curr.getBitmap() == null)
                return curr.getCurrentIndex();
        }
        return -1;
    }


    //checks if the puzzle is solved (if every board piece is in its correct place)
    private boolean isSolved() {
        for (PuzzleItem curr : items)
            if (curr.getSolvedIndex() != curr.getCurrentIndex()) return false;
        return true;
    }


    public static void setMinutesPassed(int minutes) {
        minutesPassed = minutes;
    }


    public static void setSecondsPassed(int seconds) {
        secondsPassed = seconds;
    }

}













