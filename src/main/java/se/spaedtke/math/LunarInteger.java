package se.spaedtke.math;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.valid4j.Assertive;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.valid4j.Assertive.require;

public class LunarInteger
{

    public final int[] value;
    private final int base;

    private static int[] add(final int[] first, final int[] second)
    {
        final int[] res = new int[first.length];
        for (int i = 0; i < first.length; i++)
        {
            res[i] = Math.max(first[i], second[i]);
        }
        return res;
    }

    private static int[] mult(final int[] first, final int[] second)
    {
        final Pair<int[], int[]> sortedNumbers = first.length > second.length ? Pair.of(first, second) : Pair.of(second, first);
        final int[] longest = sortedNumbers.getKey();
        final int[] shortest = sortedNumbers.getValue();
        int resLength = longest.length + shortest.length - 1;
        final int[][] multRes = new int[shortest.length][resLength];
        for (int i = 0; i < shortest.length; i++)
        {
            for (int j = 0; j < longest.length; j++)
            {
                int number = shortest.length - 1 - i;
                int position = i + j;
                multRes[number][position] = Math.min(shortest[i], longest[j]);
            }
        }
        return Arrays.stream(multRes).reduce(new int[resLength], LunarInteger::add);
    }

    private static int[] pad(final int length, final int[] value)
    {
        final int[] res = new int[length];
        final int offset = length - value.length;
        for (int i = 0; i < (length - offset); i++)
        {
            res[i + offset] = value[i];
        }
        return res;
    }

    private static int[] toArray(@NotNull final String val)
    {
        int[] res = new int[val.length()];
        for (int i = 0; i < val.length(); i++)
        {
            res[i] = Character.digit(val.charAt(i), 10);
        }
        return res;
    }

    private static int[] toArray(final int val)
    {
        return toArray(String.valueOf(val));
    }

    public LunarInteger(final String val)
    {
        this(toArray(val));
    }

    public LunarInteger(final int val)
    {
        this(toArray(val));
    }

    public LunarInteger(final int val, final int base)
    {
        this(toArray(val), base);
    }

    public LunarInteger(final int[] val)
    {
        this(val, 10);
    }

    public LunarInteger(final int[] i, final int base)
    {
        this.value = i;
        this.base = base;
    }

    public LunarInteger(final String val, final int base)
    {
        this(toArray(val), base);
    }

    public LunarInteger add(final LunarInteger other)
    {
        require(this.base == other.base,
                "Can't add two " + LunarInteger.class.getSimpleName() + " with different base");

        if (this.value.length == other.value.length)
        {
            return new LunarInteger(add(this.value, other.value), this.base);
        }
        else if (this.value.length > other.value.length)
        {
            final int[] padded = pad(this.value.length, other.value);
            return new LunarInteger(add(this.value, padded), this.base);
        }
        else
        {
            final int[] padded = pad(other.value.length, this.value);
            return new LunarInteger(add(padded, other.value), this.base);
        }
    }

    public LunarInteger mult(final LunarInteger other)
    {
        require(this.base == other.base,
                "Can't multiply two " + LunarInteger.class.getSimpleName() + " with different base");
        return new LunarInteger(mult(this.value, other.value), base);
    }

    public LunarInteger pow(final int exponent)
    {
        Assertive.require(exponent >= 0, "Exponents less than 0 are not defined");
        if (exponent == 0)
        {
            return new LunarInteger(this.base - 1, this.base);
        }
        return new LunarInteger(IntStream.range(1, exponent)
                .boxed()
                .map(i -> this.value)
                .reduce(this.value, LunarInteger::mult), this.base);
    }

    public int toInt()
    {
        StringBuilder res = new StringBuilder();
        for (int i : value)
        {
            res.append(i);
        }
        return Integer.valueOf(res.toString());
    }
}
