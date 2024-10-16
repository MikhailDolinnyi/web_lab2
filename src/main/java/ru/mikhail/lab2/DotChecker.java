package ru.mikhail.lab2;

public class DotChecker {



    static boolean checkDot(float x, int y, int r) {
        if (x < 0 && y > 0) {
            return false;
        }
        if (x < 0 && y < 0) {
            if (x < (float) -r / 2 || y > r / 2) {
                return false;
            }
        }
        if (x > 0 && y > 0) {
            return !(x > (float) r / 2) && !(y > r / 2);
        }
        if (x > 0 && y < 0) {
            return !((x * x + y * y) > r * r);

        }
        return true;
    }
}
