package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

class DroneMinimumTimeToDelivery {
    public long minimumTime(int[] d, int[] r) {
        long d1 = d[0], d2 = d[1];
        long r1 = r[0], r2 = r[1];

        long lcm = lcm(r1, r2);

        long lo = 0, hi = (d1 + d2) * Math.max(r1, r2); // safe upper bound
        while (lo < hi) {
            long mid = (lo + hi) >>> 1;
            if (canFinish(mid, d1, d2, r1, r2, lcm)) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    private boolean canFinish(long t, long d1, long d2, long r1, long r2, long lcm) {
        long avail1 = t - t / r1;        // hours drone 1 can work
        long avail2 = t - t / r2;        // hours drone 2 can work
        long usable = t - t / lcm;       // hours at least one drone can work

        if (avail1 < d1 || avail2 < d2) return false;
        return d1 + d2 <= usable;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static void main(String[] args) {
        Assertions.assertEquals(2L, new DroneMinimumTimeToDelivery().minimumTime(new int[]{1, 1}, new int[]{2, 3}));
        Assertions.assertEquals(7L, new DroneMinimumTimeToDelivery().minimumTime(new int[]{1, 3}, new int[]{2, 2}));
        Assertions.assertEquals(3L, new DroneMinimumTimeToDelivery().minimumTime(new int[]{1, 2}, new int[]{2, 3}));
        Assertions.assertEquals(3L, new DroneMinimumTimeToDelivery().minimumTime(new int[]{1, 2}, new int[]{5, 3}));
    }
}