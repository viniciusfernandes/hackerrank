package br.com.challanges.leetcode;

import java.math.BigInteger;

class ReversedIntegers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        BigInteger i1 = toInt(l1);
        BigInteger i2 = toInt(l2);
        if (i1.compareTo(new BigInteger("-1")) == 0 || i2.compareTo(new BigInteger("-1")) == 0) {
            return new ListNode();
        }
        BigInteger s = i1.add(i2);

        ListNode dummy = new ListNode();
        BigInteger r;
        ListNode n = dummy;
        while (true) {
            r = s.remainder(BigInteger.TEN);
            s = s.subtract(r).divide(BigInteger.TEN);
            n.val = r.intValue();
            n.next = new ListNode();
            n = n.next;
            if (s.compareTo(BigInteger.ZERO) <= 0) {
                break;
            }

        }
        return dummy;
    }

    private BigInteger toInt(ListNode l1) {
        BigInteger i1 = BigInteger.ZERO;
        BigInteger c = BigInteger.ONE;
        int count = 0;
        while (l1 != null) {
            count++;
            if (l1.val < 0 || l1.val > 9) {
                return new BigInteger("-1");
            }
            i1 = new BigInteger(l1.val + "").multiply(c).add(i1);
            c = c.multiply(BigInteger.TEN);
            l1 = l1.next;
        }
        if (count <= 0 || count > 100) {
            return new BigInteger("-1");
        }
        return i1;
    }

    public static void main(String[] args) {
        ListNode l = new ListNode(1);
        l.next = new ListNode(2);
        l.next.next = new ListNode(3);

        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);

        System.out.println(new ReversedIntegers().addTwoNumbers(l, l1));
    }
}