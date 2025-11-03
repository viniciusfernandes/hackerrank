package br.com.challanges.aws;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

public class MinDaysRefactor {
    public static int minReleaseDays(List<Integer> schedule, List<Integer> refactorDay) {
        Map<Integer, Integer> moduleDays = new HashMap<>();
        int l = schedule.size();
        for (int i = 0; i < l; i++) {
            int module = schedule.get(i);
            if (module >= refactorDay.size()) {
                continue;
            }
            int days = refactorDay.get(module);
            int totDays = moduleDays.getOrDefault(module, 0);
            if (i == l - 1 || (i + 1 < l && module != schedule.get(i + 1))) {
                totDays++;
            }
            moduleDays.put(module, totDays + days);
        }
        int max = -1;
        for (Map.Entry<Integer, Integer> entry : moduleDays.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        new HashSet<>(new ArrayList<>());
        return max;
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }

    private static void test1() {
        var schedule = List.of(0, 0);
        var days = List.of(2);
        Assertions.assertEquals(5, minReleaseDays(schedule, days));
    }

    private static void test2() {
        var schedule = List.of(0, 0, 1, 0, 2, 2, 2);
        var days = List.of(2, 2);
        Assertions.assertEquals(8, minReleaseDays(schedule, days));
    }

    private static void test3() {
        var schedule = List.of(0, 0, 1);
        var days = List.of(2, 10);
        Assertions.assertEquals(11, minReleaseDays(schedule, days));
    }

    private static void test4() {
        var schedule = List.of(1, 0, 0);
        var days = List.of(2, 10);
        Assertions.assertEquals(11, minReleaseDays(schedule, days));
    }

    private static void test5() {
        var schedule = List.of(10);
        var days = List.of(2, 10);
        Assertions.assertEquals(-1, minReleaseDays(schedule, days));
    }

    private static void test6() {
        var schedule = List.of(10, 0);
        var days = List.of(2, 10);
        Assertions.assertEquals(3, minReleaseDays(schedule, days));
    }
}
