package br.com.challanges.leetcode;

import java.math.BigInteger;

public class TwoNumbers {
    private static class ListNode {
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return new ListNode();
        }
        int s1 = size(l1), s2 = size(l2);
        if (s1 < 1 || s1 > 100 || s2 < 1 || s2 > 100) {
            return new ListNode();
        }
        BigInteger num1 = toInt(l1);
        BigInteger num2 = toInt(l2);
        BigInteger sum = num1.add(num2);
        return toListNode(sum);
    }

    private int size(ListNode node) {
        if (node == null) return 0;
        int count = 0;
        do {
            if (node.val < 0 || node.val > 9) {
                return -1;
            }
            count++;
            node = node.next;
        }
        while (node != null);
        return count;
    }

    private BigInteger toInt(ListNode node) {
        if (node.next == null) {
            return BigInteger.valueOf(node.val);
        }
        BigInteger tenMultiple = BigInteger.ONE;
        BigInteger baseTenNum = BigInteger.ZERO;
        while (node != null) {
            baseTenNum = BigInteger.valueOf(node.val).multiply(tenMultiple).add(baseTenNum);
            tenMultiple = tenMultiple.multiply(BigInteger.TEN);
            node = node.next;
        }
        return baseTenNum;
    }

    private ListNode toListNode(BigInteger val) {
        if (val.equals(BigInteger.ZERO)) {
            return new ListNode(0);
        }
        BigInteger digit;
        ListNode node = new ListNode();
        ListNode u = node;
        while (true) {
            digit = val.mod(BigInteger.TEN);
            ;
            val = val.subtract(digit);
            val = val.divide(BigInteger.TEN);
            u.val = digit.intValue();
            if (!val.equals(BigInteger.ZERO)) {
                u.next = new ListNode();
                u = u.next;
            } else {
                return node;
            }
        }
    }
}



