class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def mergeTwoLists(self, list1: ListNode, list2: ListNode) -> ListNode:
        if list1 is None:
            return list2
        elif list2 is None:
            return list1
        elif list1 is None and list2 is None:
            return None

        if list1.val > list2.val:
            temp = list1
            list1 = list2
            list2 = temp

        if list1.next is None:
            list1.next = list2
            return list1

        p1 = list1
        n1 = list1.next
        n2 = list2
        while True:
            if n1 is None or n2 is None:
                break
            if p1.val <= n2.val <= n1.val:
                temp = n2.next
                p1.next = n2
                n2.next = n1
                n1 = n2
                n2 = temp
            else:
                p1 = n1
                n1 = n1.next

            if n1 is None:
                p1.next = n2
                break
        return list1


def build_list(arr):
    if not arr:
        return None

    head = ListNode(arr[0])
    current = head

    for val in arr[1:]:
        current.next = ListNode(val)
        current = current.next

    return head


sol = Solution()
sol.mergeTwoLists(build_list([1, 2, 4]), build_list([1, 3, 4]))
