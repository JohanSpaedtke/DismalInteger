package se.spaedtke.math;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import static org.valid4j.Assertive.require;

public class LunarInteger
{
    private final int base;
    private final int[] magnitude;

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

    public LunarInteger(final int val)
    {
        this(toArray(val));
    }

    public LunarInteger(final int val, final int base)
    {
        this(toArray(val), base);
    }

    public LunarInteger(final String val)
    {
        this(toArray(val));
    }

    public LunarInteger(final String val, final int base)
    {
        this(toArray(val), base);
    }

    public LunarInteger(final int[] val)
    {
        this(val, 10);
    }

    public LunarInteger(final int[] val, final int base)
    {
        this.magnitude = val;
        this.base = base;
    }

    public LunarInteger add(final LunarInteger other)
    {
        require(base == other.base,
                "Can't add two " + LunarInteger.class.getSimpleName() + " with different base");
        return new LunarInteger(LunarArithmetic.add(magnitude, other.magnitude), base);
    }

    public LunarInteger mult(final LunarInteger other)
    {
        require(base == other.base,
                "Can't multiply two " + LunarInteger.class.getSimpleName() + " with different base");
        return new LunarInteger(LunarArithmetic.mult(magnitude, other.magnitude), base);
    }

    public LunarInteger pow(final int exponent)
    {
        require(exponent >= 0, "Exponents less than 0 are not defined");
        if (exponent == 0)
        {
            return LunarArithmetic.multiplicativeIdentity(base);
        }
        return new LunarInteger(IntStream.range(1, exponent)
                .boxed()
                .map(i -> magnitude)
                .reduce(magnitude, LunarArithmetic::mult), base);
    }

    public int intValue()
    {
        return Integer.valueOf(stringValue());
    }


    public String stringValue()
    {
        StringBuilder res = new StringBuilder();
        for (int i : magnitude)
        {
            res.append(i);
        }
        return res.toString();
    }
    
    @Override
    public int hashCode()
    {
        int result = Objects.hash(base);
        result = 31 * result + Arrays.hashCode(magnitude);
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
                Arrays.equals(magnitude, that.magnitude);
    }

    @Override
    public String toString()
    {
        return new StringJoiner(", ", LunarInteger.class.getSimpleName() + "[", "]")
                .add("magnitude=" + Arrays.toString(magnitude))
                .add("base=" + base)
                .toString();
    }
}
