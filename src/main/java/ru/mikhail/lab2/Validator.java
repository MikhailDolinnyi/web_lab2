package ru.mikhail.lab2;

import jakarta.servlet.http.HttpServletRequest;


public class Validator {
    public void validate(float x, int y, int r) throws ValidateException {
        validateX(x);
        validateY(y);
        validateR(r);
    }

    private void validateX(float x) throws ValidateException {
        try {
            if (x < -3 || x > 3) {
                throw new ValidateException("Неверное значение X");
            }
        } catch (NumberFormatException e) {
            throw new ValidateException("X не является номером");
        }
    }

    private void validateY(int y) throws ValidateException {
        try {
            if (y < -4 || y > 4) {
                throw new ValidateException("Неверное значения Y");
            }
        } catch (NumberFormatException e) {
            throw new ValidateException("Y не является номером");
        }
    }

    private void validateR(int r) throws ValidateException {
        try {
            if (r < 1 || r > 5) {
                throw new ValidateException("Неверное значение R");
            }
        } catch (NumberFormatException e) {
            throw new ValidateException("R не является номером");
        }
    }
}
