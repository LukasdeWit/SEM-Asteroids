package display;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.DoubleFunction;

import static org.junit.Assert.assertTrue;

/**
 * Created by douwe on 13-10-16.
 */
@RunWith(Parameterized.class)
public class DisplayUtilsTest {

    private final double[] target;
    private final double[] expected;
    private final DoubleFunction<Double> xFunction;
    private final DoubleFunction<Double> yFunction;

    public DisplayUtilsTest(final double[] target, final double[] expected, final DoubleFunction<Double> xFunction,
                            final DoubleFunction<Double> yFunction) {
        this.target = target;
        this.expected = expected;
        this.xFunction = xFunction;
        this.yFunction = yFunction;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[]{0, 1, 2, 3}, new double[]{1, 2, 3, 4}, (DoubleFunction<Double>) v -> v + 1,
                        (DoubleFunction<Double>) v -> v + 1},
                {new double[]{0, 1, 2, 3}, new double[]{0, 2, 4, 6}, (DoubleFunction<Double>) v -> v * 2,
                        (DoubleFunction<Double>) v -> v * 2},
                {new double[]{0, 1, 2, 3}, new double[]{0, 1.5, 3, 4.5}, (DoubleFunction<Double>) v -> v / 2 * 3,
                        (DoubleFunction<Double>) v -> v / 2 * 3},
                {new double[]{0, 1, 2, 3}, new double[]{0, 1, 4, 9}, (DoubleFunction<Double>) v -> Math.pow(v, 2),
                        (DoubleFunction<Double>) v -> Math.pow(v, 2)},
                {new double[]{0, 1, 2, 3}, new double[]{0, 1, 4, 1}, (DoubleFunction<Double>) v -> Math.pow(v, 2),
                        (DoubleFunction<Double>) v -> Math.pow(v, 0)},
                {new double[]{0, 1, 2, 3}, new double[]{1, 2, 3, 6}, (DoubleFunction<Double>) v -> v + 1,
                        (DoubleFunction<Double>) value -> value * 2},
                {new double[0], new double[0], (DoubleFunction<Double>) v -> v + 1,
                        (DoubleFunction<Double>) v -> v + 1}
        });
    }

    @Test
    public void translate() throws Exception {
        assertTrue(Arrays.equals(expected, DisplayUtils.translate(xFunction, yFunction, target)));
    }

    @Test(expected = NullPointerException.class)
    public void translateNull() throws Exception {
        DisplayUtils.translate(null, value -> value + 1, new double[]{1});
        DisplayUtils.translate(value -> value + 1, null, new double[]{1});
        DisplayUtils.translate(value -> value + 1, value -> value + 1, null);
    }
}