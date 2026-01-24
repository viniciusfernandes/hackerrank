package br.com.challanges;


import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.Stack;

public class FishDownstream {
    public static int solution(int[] A, int[] B) {
        if (A == null || B == null || A.length == 0 || B.length == 0) {
            return 0;
        }
        Stack<Integer> fishesQueue = new Stack<>();
        fishesQueue.add(0);
        int currfish = 1;
        int prevfish;
        while (currfish < A.length) {
            prevfish = fishesQueue.peek();
            int prevDir = B[prevfish];
            int currDir = B[currfish];
            if (prevDir == currDir || prevDir == 0 && currDir == 1) {
                fishesQueue.push(currfish);
                currfish++;
                continue;
            }
            if (A[prevfish] < A[currfish]) {
                fishesQueue.pop();

            } else {
                currfish++;
            }
            if(fishesQueue.empty()){
                fishesQueue.push(currfish);
                break;
            }
        }
        return fishesQueue.size();
    }

    public static void main(String[] args) {
        Assertions.assertEquals(2, solution(new int[]{4, 3, 2, 1, 5}, new int[]{0, 1, 0, 0, 0}));
        Assertions.assertEquals(2, solution(new int[]{4, 3}, new int[]{0, 0}));
        Assertions.assertEquals(2, solution(new int[]{4, 3}, new int[]{1, 1}));
        Assertions.assertEquals(2, solution(new int[]{4, 3}, new int[]{0, 1}));
        Assertions.assertEquals(0, solution(new int[]{}, new int[]{}));
        Assertions.assertEquals(1, solution(new int[]{4, 3, 2, 1}, new int[]{1, 0, 0, 0}));
        Assertions.assertEquals(1, solution(new int[]{1, 2, 3, 4}, new int[]{1, 1, 1, 0}));
    }
}
