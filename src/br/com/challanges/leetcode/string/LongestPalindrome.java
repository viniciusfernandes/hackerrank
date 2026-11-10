package br.com.challanges.leetcode.string;

class LongestPalindrome {
    public String longestPalindrome(String s) {
        if (s.length() == 1) {
            return s;
        }
        int start, end;
        String max = "";
        for (int i = 1; i < s.length(); i++) {
            //odd palindrome
            start = i - 1;
            end = i + 1;
            max = palindrome(s, max, start, end);

            //even palindrome
            start = i - 1;
            end = i;
            max = palindrome(s, max, start, end);
        }

        return max;
    }

    private String palindrome(String s, String max, int start, int end) {
        int i0 = 0, i1 = 0;
        while (start >= 0 && end <= s.length() - 1) {
            if (s.charAt(start) != s.charAt(end)) {
                break;
            } else {
                i0 = start;
                i1 = end;
            }
            start--;
            end++;
        }

        if (i1 - i0 + 1 > max.length()) {
            if (i1 >= s.length()) {
                max = s.substring(i0);
            } else {
                max = s.substring(i0, i1 + 1);
            }
        }
        return max;
    }
}
