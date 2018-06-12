package com.example.boris.mathpuzzles;

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

    public void setInversions(int inversions) {
        this.inversions = inversions;
    }

    public PuzzleItem[] getArray() {
        return array;
    }

    public void setArray(PuzzleItem[] array) {
        this.array = array;
    }
}
