package se.spaedtke.math;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;

public class LunarArithmetic
{
    public static LunarInteger multiplicativeIdentity(int base)
    {
        return new LunarInteger(base - 1, base);
    }

    static int[] add(final int[] first, final int[] second)
    {
        final int maxLength = first.length > second.length ? first.length : second.length;
        final int[] p_first = pad(maxLength, first);
        final int[] p_second = pad(maxLength, second);
        final int[] res = new int[maxLength];
        for (int i = 0; i < p_first.length; i++)
        {
            res[i] = Math.max(p_first[i], p_second[i]);
        }
        return res;
    }

    static int[] mult(final int[] first, final int[] second)
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
        return Arrays.stream(multRes).reduce(new int[resLength], LunarArithmetic::add);
    }

    static int[] pad(final int length, final int[] value)
    {
        final int[] res = new int[length];
        final int offset = length - value.length;
        for (int i = 0; i < (length - offset); i++)
        {
            res[i + offset] = value[i];
        }
        return res;
    }
}
