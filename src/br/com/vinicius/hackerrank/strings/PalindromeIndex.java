package br.com.vinicius.hackerrank.strings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public class PalindromeIndex {

  private static boolean isPalindrome(String palindrome, int first, int last) {
    int max = palindrome.length() / 2;
    if (first == last) {
      return false;
    }
    for (int i = first; i < max; i++) {
      if (palindrome.charAt(i) != palindrome.charAt(last)) {
        return false;
      }
      last--;
    }
    return true;
  }

  public static int palindromeIndex(String palindrome) {
    int n = palindrome.length();
    for (int i = 0; i < n / 2; i++) {
      if (palindrome.charAt(i) != palindrome.charAt(n - i - 1)) {
        if (isPalindrome(palindrome, i, n - i - 2)) {
          return n - i - 1;
        } else {
          return i;
        }
      }
    }
    return -1;
  }

  public static void main(String[] args) throws FileNotFoundException {
    var init = new Date();
    // var scanner = new Scanner(System.in);
    var scanner = new Scanner(new FileInputStream("/home/vinicius/Documents/palindrome.txt"));

    var q = scanner.nextInt();
    if (q < 1 || q > 200) {
      return;
    }
    StringBuilder indexes = new StringBuilder();
    String palindrome;
    boolean isAscii;
    for (int i = 0; i < q; i++) {
      palindrome = scanner.next();
      isAscii = palindrome.matches("[a-z]+");
      if (palindrome.length() < 1 || palindrome.length() > 100005 || !isAscii) {
        return;
      }
      indexes.append(palindromeIndex(palindrome)).append("\n");
    }
    System.out.println(indexes.toString());
    System.out.println("t=" + (new Date().getTime() - init.getTime()) / 1000d);
  }
}
