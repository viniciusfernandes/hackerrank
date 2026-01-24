package br.com.challanges;

import java.math.BigInteger;
import java.util.*;

class AnagramsList {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> anags = new HashMap<>();
        for (String s : strs) {
            var chars = s.toCharArray();
            Arrays.sort(chars);
            String k = Arrays.toString(chars);
            if (!anags.containsKey(k)) {
                var h = new ArrayList<String>();
                h.add(s);
                anags.put(k, h);
                continue;
            }
            anags.get(k).add(s);
        }
        List<List<String>> l = new ArrayList<>();
        for (var list : anags.values()) {
            l.add(list);
        }
        return l;
    }

    public int myAtoi(String s) {
        List<Integer> l = new ArrayList<>();
        boolean negative = false;
        for (char c : s.toCharArray()) {
            if (' ' == c) {
                continue;
            }
            if ('-' == c && l.isEmpty()) {
                negative = true;
            } else if ('0' == c && l.isEmpty()) {
                continue;
            } else if ('1' <= c && '9' >= c) {
                l.add(Integer.valueOf(String.valueOf(c)));
            } else {
                break;
            }
        }
        BigInteger value = BigInteger.ZERO;
        BigInteger pow = pow(l.size() - 1);
        for (int i : l) {
            value = BigInteger.valueOf(i).multiply(pow).add(value);
            pow = pow.divide(BigInteger.TEN);
        }

        if (!negative) {
            return value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0 ? Integer.MAX_VALUE : value.intValue();
        }
        value = value.multiply(BigInteger.valueOf(-1));
        return value.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0 ? Integer.MIN_VALUE : value.intValue();
    }

    private BigInteger pow(int n) {
        BigInteger pow = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            pow = BigInteger.TEN.multiply(pow);
        }
        return pow;
    }

    public static void main(String[] args) {
        new AnagramsList().groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
    }
}