package ru.mikhail.lab2;

public class ResultDto {
    private final float x;
    private final int y;
    private final int r;
    private final boolean result;

    public ResultDto(float x, int y, int r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public float getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public boolean getResult() {
        return result;
    }
}
