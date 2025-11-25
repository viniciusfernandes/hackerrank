class Solution:
    def removeDuplicates(self, nums) -> int:
        if len(nums) == 0:
            return 0
        elif len(nums) == 1:
            return 1
        last = len(nums) - 1
        i = 0
        k = 0
        while i <= last:
            if nums[k] != nums[i]:
                k += 1
                nums[k] = nums[i]
            i += 1
        return k


sol = Solution()
print(sol.removeDuplicates([1, 1, 2,2, 3,3,4]))
