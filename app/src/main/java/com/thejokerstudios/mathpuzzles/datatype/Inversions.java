package com.thejokerstudios.mathpuzzles.datatype;

//a class to help count the inversions in the board pieces presented as an array

public class Inversions {

    private int inversions;
    private PuzzleItem[] array;

    public Inversions(int inversions, PuzzleItem[] array) {
        this.inversions = inversions;
        this.array = array;
    }

    public int getInversions() {
        return inversions;
    }

    public PuzzleItem[] getArray() {
        return array;
    }

    public void setArray(PuzzleItem[] array) {
        this.array = array;
    }
}
