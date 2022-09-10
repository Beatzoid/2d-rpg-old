package main;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Utils {
    public static int clamp(int val, int min, int max) {
        return min(max, max(min, val));
    }
}
