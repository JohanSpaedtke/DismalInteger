package se.spaedtke.math;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Addition")
class AdditionTest
{
    @ParameterizedTest
    @DisplayName("Different Length Digits")
    @MethodSource("differentLengthDigits")
    public void addDifferentLengthDigits(int base, int first, int second, int expected)
    {
        Assertions.assertEquals(expected,
                new LunarInteger(first, base).add(new LunarInteger(second, base)).intValue());
    }

    @ParameterizedTest
    @DisplayName("Double Digits")
    @MethodSource("doubleDigits")
    public void addDoubleDigits(int base, String first, String second, int expected)
    {
        Assertions.assertEquals(expected,
                new LunarInteger(first, base).add(new LunarInteger(second, base)).intValue());
    }

    @ParameterizedTest
    @DisplayName("Single Digits")
    @MethodSource("singleDigits")
    public void addSingleDigits(int base, int first, int second, int expected)
    {
        Assertions.assertEquals(expected,
                new LunarInteger(first, base).add(new LunarInteger(second, base)).intValue());
    }

    static Stream<Arguments> differentLengthDigits()
    {
        return Stream.of(arguments(10, 0, 11, 11)
                , arguments(8, 270, 31, 271)
                , arguments(10, 270, 31, 271)
                , arguments(10, 31, 270, 271)
                , arguments(10, 1523, 28, 1528));
    }

    static Stream<Arguments> doubleDigits()
    {
        return Stream.of(arguments(10, "20", "11", 21)
                , arguments(8, "27", "71", 77));
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
                                .map(second -> arguments(bf.getLeft(), bf.getRight(), second, Math.max(bf.getRight(), second))));
    }
}