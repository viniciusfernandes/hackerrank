from typing import List


class Solution:
    def removeElement(self, nums: List[int], val: int) -> int:

        last = len(nums) - 1
        i = 0
        k = 0
        while i <= last:
            if val != nums[i]:
                nums[k] = nums[i]
                k += 1
            i += 1
        return k


sol = Solution()
print(sol.removeElement([1], 1))
