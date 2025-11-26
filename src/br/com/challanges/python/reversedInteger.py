class Solution:
    def reverse(self, x: int) -> int:
        rev = 0
        pos = x > 0
        while x != 0:
            r = abs(x) % 10
            rev *= 10
            if (r != 0):
                rev += r if pos else -r
                x = x - r if pos else x + r
            x //= 10
        maxVal = 2147483647
        minVal = -2147483648
        if rev > maxVal or rev < minVal:
            return 0
        return rev


s = Solution()
print(s.reverse(21474836))
