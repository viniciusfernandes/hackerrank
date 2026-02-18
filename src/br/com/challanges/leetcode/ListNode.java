package br.com.challanges.leetcode;

import java.util.Objects;

class ListNode {
    int val;
    br.com.challanges.leetcode.ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode next(int value) {
        next = new ListNode(value);
        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ListNode listNode = (ListNode) o;
        return val == listNode.val;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }

    public boolean allEquals(ListNode h2) {
        ListNode h1 = this;
        while (h1 != null) {
            if (h2 == null || h1.val != h2.val) {
                return false;
            }
            h1 = h1.next;
            h2 = h2.next;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + (next != null ? next.val : null) +
                '}';
    }
}