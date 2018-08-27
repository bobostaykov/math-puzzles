//everything ok
package com.thejokerstudios.mathpuzzles.datatype;

//a class to save all three chosen game levels

public class AllLevels {
    private static Level board;
    private static Level time;
    private static Level math;

    public AllLevels(Level b, Level t, Level m) {
        board = b;
        time = t;
        math = m;
    }

    public static Level getBoard() {
        return board;
    }

    public static Level getTime() {
        return time;
    }

    public static Level getMath() {
        return math;
    }

    public static void setBoard(Level b) {
        board = b;
    }

    public static void setTime(Level t) {
        time = t;
    }

    public static void setMath(Level m) {
        math = m;
    }
}
