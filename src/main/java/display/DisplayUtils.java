package display;

import java.util.function.DoubleFunction;

/**
 * Created by douwe on 12-10-16.
 */
public final class DisplayUtils {

    /**
     * private constructor since this is a utility class.
     */
    private DisplayUtils() { }

    /**
     *  transform the given target array in the form (x,y,x,y,...) according to the given x function and y function.
     *  the x function is applied to every x value (every uneven index) and the y function is applied to every
     *  y value (every even index)
     * @param xFunction the function you want to apply to every x coord
     * @param yFunction the function you want to apply to every y coord
     * @param target the target array (this remains unchanged)
     * @return a new array with both function applied
     */
    public static double[] translate(final DoubleFunction<Double> xFunction, final DoubleFunction<Double> yFunction,
                                     final double[] target) {
        if (xFunction == null || yFunction == null || target == null) {
            throw new IllegalArgumentException("parameters can't be null");
        }
        final double[] temp = new double[target.length];

        for (int i = 0; i < temp.length; i++) {
            final double value = target[i];
            if (i % 2 == 0) {
                temp[i] = xFunction.apply(value);
            } else {
                temp[i] = yFunction.apply(value);
            }
        }

        return temp;
    }
}
