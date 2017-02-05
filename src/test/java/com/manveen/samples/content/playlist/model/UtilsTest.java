package com.manveen.samples.content.playlist.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.Test;

public class UtilsTest {

    @Test
    public void test() {
        List<Integer> values = Arrays.asList(1, 2);
        List<List<Integer>> subsets = Utils.allSubsets(values);
        assertEquals(subsets.toString(), 5, subsets.size());
        assertTrue(subsetContainsValues(subsets));
        assertTrue(subsetContainsValues(subsets, 1));
        assertTrue(subsetContainsValues(subsets, 2));
        assertTrue(subsetContainsValues(subsets, 1, 2));
        assertTrue(subsetContainsValues(subsets, 2, 1));
    }

    private boolean subsetContainsValues(List<List<Integer>> subsets, Integer... values) {
        return subsets.parallelStream().anyMatch(p -> listContainsValues(p, values));
    }

    private boolean listContainsValues(List<Integer> list, Integer... values) {
        return  (values == null && list.isEmpty())
                ||  (list.size() == values.length && IntStream.range(0, list.size()).allMatch(
                        i -> Objects.equals(list.get(i), values[i])));
    }
}
