package com.manveen.samples.content.playlist.model;

import java.util.ArrayList;
import java.util.List;

final class Utils {

    /**
     * Returns all orderings for the given list. For example, for {1, 2}, return {}, {1}, {2},
     * {1, 2}, and {2, 1}
     */
    public static <T> List<List<T>> allSubsets(List<T> original) {
        // Modified From http://stackoverflow.com/questions/1670862/obtaining-a-powerset-of-a-set-in-java
        // We need all subsets
        List<List<T>> sets = new ArrayList<List<T>>();
        if (original.isEmpty()) {
            sets.add(new ArrayList<T>());
            return sets;
        }
        T head = original.get(0);
        List<T> rest = new ArrayList<T>(original.subList(1, original.size()));
        for (List<T> set : allSubsets(rest)) {
            List<T> newSet = new ArrayList<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);

            if (!set.isEmpty()) {
                newSet = new ArrayList<T>();
                newSet.addAll(set);
                newSet.add(head);
                sets.add(newSet);
            }

            sets.add(set);
        }
        return sets;
    }
}
