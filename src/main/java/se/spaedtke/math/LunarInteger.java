package se.spaedtke.math;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.valid4j.Assertive.require;

public class LunarInteger
{
    public final int[] value;
    private final int base;

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
        return new LunarInteger(LunarArithmetic.add(this.value, other.value), this.base);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(base);
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        LunarInteger that = (LunarInteger) o;
        return base == that.base &&
                Arrays.equals(value, that.value);
    }

    public LunarInteger mult(final LunarInteger other)
    {
        require(this.base == other.base,
                "Can't multiply two " + LunarInteger.class.getSimpleName() + " with different base");
        return new LunarInteger(LunarArithmetic.mult(this.value, other.value), base);
    }

    public LunarInteger pow(final int exponent)
    {
        require(exponent >= 0, "Exponents less than 0 are not defined");
        if (exponent == 0)
        {
            return LunarArithmetic.multiplicativeIdentity(this.base);
        }
        return new LunarInteger(IntStream.range(1, exponent)
                .boxed()
                .map(i -> this.value)
                .reduce(this.value, LunarArithmetic::mult), this.base);
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
