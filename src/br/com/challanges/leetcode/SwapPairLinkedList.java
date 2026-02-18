package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

class SwapPairLinkedList {
    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode curr = head;
        ListNode prev = null;
        head = head.next;
        swap(prev, curr);
        return head;
    }

    private static void swap(ListNode prev, ListNode curr) {
        if (curr == null || curr.next == null) {
            return;
        }
        ListNode nnext = curr.next.next;
        ListNode next = curr;
        curr = curr.next;
        curr.next = next;
        next.next = nnext;
        if (prev != null) {
            prev.next = curr;
        }
        prev = curr.next;
        curr = curr.next.next;
        swap(prev, curr);
    }

    public static void main(String[] args) {
        assertEvenSizedLinkedList();
        assertOddSizedLinkedList();
        assertSingleSizedLinkedList();
    }

    private static void assertEvenSizedLinkedList() {
        ListNode expected = new ListNode(2);
        expected.next(1).next(4).next(3);

        ListNode head = new ListNode(1);
        head.next(2).next(3).next(4);
        head = swapPairs(head);
        Assertions.assertTrue(expected.allEquals(head));
    }

    private static void assertOddSizedLinkedList() {
        ListNode expected = new ListNode(2);
        expected.next(1).next(4).next(3).next(5);

        ListNode head = new ListNode(1);
        head.next(2).next(3).next(4).next(5);
        head = swapPairs(head);
        Assertions.assertTrue(expected.allEquals(head));
    }

    private static void assertSingleSizedLinkedList() {
        ListNode expected = new ListNode(1);
        ListNode head = new ListNode(1);
        head = swapPairs(head);
        Assertions.assertTrue(expected.allEquals(head));
    }
}