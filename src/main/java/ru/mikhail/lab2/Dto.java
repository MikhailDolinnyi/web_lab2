package ru.mikhail.lab2;

public class Dto {
    private float x;
    private int y;
    private int r;


    public void setX(float x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setR(int r) {
        this.r = r;
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

    public void setAll(float x, int y, int r) {
        setX(x);
        setY(y);
        setR(r);
    }

    @Override
    public String toString() {
        return "Dto{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                '}';
    }
}


