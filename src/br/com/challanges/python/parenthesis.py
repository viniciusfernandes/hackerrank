from collections import deque


class Solution:
    def isValid(self, s: str) -> bool:
        if len(s) <= 0:
            return True
        queue = deque()
        closeMap = {
            "{": "}", "(": ")", "[": "]"
        }
        openings = {'(', '[', '{'}
        for ch in s:
            if ch in openings:
                queue.append(ch)
            else:
                if len(queue) <= 0 or closeMap[queue.pop()] != ch:
                    return False
        return len(queue) <= 0


sol = Solution()

print(sol.isValid("()"))          # True
print(sol.isValid("()[]{}"))      # True
print(sol.isValid("(]"))          # False
print(sol.isValid("([)]"))        # False
print(sol.isValid("{[]}"))