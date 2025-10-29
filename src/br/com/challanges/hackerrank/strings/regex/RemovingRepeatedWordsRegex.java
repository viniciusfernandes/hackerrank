package br.com.challanges.hackerrank.strings.regex;

class RemovingRepeatedWordsRegex {
    public String pattern = "\\b(\\w+)\\s+\\1\\b";

    public static void main(String[] args) {
        String pattern = new RemovingRepeatedWordsRegex().pattern;
        String phrase = "hello hello world world world test";
        System.out.println(phrase.replaceAll(pattern, "$1"));
    }
}
