package br.com.challanges.hackerrank.strings.regex;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

class IpRegex {
    public String pattern = "((0?\\d{0,2}|1\\d{0,2}|25[0-5]|2[0-4]\\d)\\.){3}(0?\\d{0,2}|1\\d{0,2}|25[0-5]|2[0-4]\\d)";

    public static void main(String[] args) {
        String pattern = new IpRegex().pattern;
        String IP;
        for (int i = 0; i <= 255; i++) {
            IP = i + "." + i + "." + i + "." + i;
            Assertions.assertTrue(IP.matches(pattern));
        }
    }
}
