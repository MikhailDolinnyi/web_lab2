package ru.mikhail.lab2;

import java.util.logging.Logger;

public class DotChecker {


    private static final Logger logger = Logger.getLogger(DotChecker.class.getName());

    static boolean checkDot(float x, int y, int r) {
        // Первая четверть (треугольник, масштаб R/2)
        if (x > 0 && y > 0) {
            // Треугольник: y <= -x + R/2
            return (y <= ((float) r / 2) - x / 2);


        }

        // Вторая четверть (нет области)
        if (x < 0 && y > 0) {
            return false;
        }

        // Третья четверть (прямоугольник)
        if (x <= 0 && y <= 0) {
            // Прямоугольник: x >= -R/2 и y >= -R
            return (x >= ((float) -r / 2) && y >= -r);
        }

        // Четвёртая четверть (четверть окружности радиуса R)
        if (x >= 0 && y <= 0) {
            // Условие: x^2 + y^2 <= R^2
            return (x * x + y * y <= r * r);
        }


        // Если точка не попала ни в одну из областей
        return false;

    }


}
