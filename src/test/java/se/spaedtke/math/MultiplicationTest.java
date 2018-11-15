package se.spaedtke.math;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Multiplication")
public class MultiplicationTest
{
    @Test
    public void dummy()
    {
        System.out.println(new LunarInteger(19).pow(2));
    }

    @ParameterizedTest
    @DisplayName("Different Length Digits")
    @MethodSource("differentLengthDigits")
    public void multiplyDifferentLengthDigits(int base, int first, int second, int expected)
    {
        Assertions.assertEquals(expected,
                new LunarInteger(first, base).mult(new LunarInteger(second, base)).intValue());
    }

    @ParameterizedTest
    @DisplayName("Single Digits")
    @MethodSource("singleDigits")
    public void multiplySingleDigits(int base, int first, int second, int expected)
    {
        Assertions.assertEquals(expected,
                new LunarInteger(first, base).mult(new LunarInteger(second, base)).intValue());
    }

    @Test
    @DisplayName("Powers")
    public void pow()
    {
        Assertions.assertEquals(new LunarInteger(9), new LunarInteger(19).pow(0));
        Assertions.assertEquals(new LunarInteger(19), new LunarInteger(19).pow(1));
        Assertions.assertEquals(new LunarInteger(19)
                .mult(new LunarInteger(19)), new LunarInteger(19).pow(2));
        Assertions.assertEquals(new LunarInteger(19)
                .mult(new LunarInteger(19))
                .mult(new LunarInteger(19)), new LunarInteger(19).pow(3));
        Assertions.assertEquals(new LunarInteger(19)
                .mult(new LunarInteger(19))
                .mult(new LunarInteger(19))
                .mult(new LunarInteger(19)), new LunarInteger(19).pow(4));
    }

    static Stream<Arguments> differentLengthDigits()
    {
        return Stream.of(arguments(10, 2, 11, 11)
                , arguments(10, 12, 22, 122)
                , arguments(10, 169, 248, 12468)
                , arguments(2, 1101, 101, 111101)
                , arguments(10, 19, 109, 1119));
    }

    static Stream<Arguments> singleDigits()
    {
        return IntStream.range(2, 11)
                .boxed()
                .flatMap(base ->
                        IntStream.range(0, base)
                                .boxed()
                                .map(first -> Pair.of(base, first)))
                .flatMap(bf ->
                        IntStream.range(0, bf.getLeft())
                                .boxed()
                                .map(second -> arguments(bf.getLeft(), bf.getRight(), second, Math.min(bf.getRight(), second))));
    }
}
