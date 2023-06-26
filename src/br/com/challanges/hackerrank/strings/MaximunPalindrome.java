package br.com.challanges.hackerrank.strings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class MaximunPalindrome {
  private class Freq {
    char value;
    int total;
    int pairs;

    public Freq(char value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "Freq{" +
          "value=" + value +
          ", total=" + total +
          ", pairs=" + pairs +
          '}';
    }
  }

  private long calculateTotalPalindromes(Collection<Freq> frequencies) {
    int pairsLength = 0;
    long nFac;
    long mFact = 1;
    long noPairs = 0;
    for (var freq : frequencies) {
      if (freq.pairs != 0) {
        pairsLength += freq.pairs;
        if (freq.total - 2 * freq.pairs != 0) {
          noPairs++;
        }
      } else {
        noPairs++;
      }
      mFact *= factorial(freq.pairs);
    }
    nFac = pairsLength == 0 ? 0 : factorial(pairsLength);
    // is a non-trivial pair length palindrome
    boolean hasNoDuplicatedChars = pairsLength == 0 && noPairs >= 1;
    if (hasNoDuplicatedChars) {
      return noPairs;
    } else if (pairsLength >= 1 && noPairs == 0) {
      return ((nFac / mFact)) % 1000000007;
    } else {
      return ((nFac / mFact) * noPairs) % 1000000007;
    }
  }

  private void initialize(String s, Map<Character, Freq> table) {
    var chars = s.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      var freq = table.get(chars[i]);
      if (freq == null) {
        freq = new Freq(chars[i]);
        table.put(chars[i], freq);
      }
      freq.total++;
      if (freq.total % 2 == 0) {
        freq.pairs++;
      }
    }
  }

  private long factorial(int n) {
    if (n == 0 || n == 1) {
      return 1;
    }
    long fact = 1;
    for (int i = 1; i <= n; i++) {
      fact *= i;
      fact %= 1000000007;
    }
    return fact;
  }

  public long answerQuery(String s, int l, int r) {
    if (s.length() == 0 || s.length() > 100000 ||
        l == 0 || l > s.length() || r == 0 || r > s.length()) {
      throw new IllegalArgumentException();
    }
    s = s.substring(l - 1, r);
    var table = new HashMap<Character, Freq>();
    initialize(s, table);
    return calculateTotalPalindromes(table.values());
  }

  public static void main(String[] args) {
    scenario1();
    scenario2();
    scenario3();
    scenario4();
    scenario5();
    scenario6();
  }

  public static void bbmain(String[] args) throws IOException {
    BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(
                new FileInputStream("/home/vinicius/Documents/maximum-palindromes.txt")));
    String s = bufferedReader.readLine();

    int q = Integer.parseInt(bufferedReader.readLine().trim());
    var calculator = new MaximunPalindrome();
    if (q == 0 || q > 100000) {
      return;
    }
    IntStream.range(0, q).forEach(qItr -> {
      try {
        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int l = Integer.parseInt(firstMultipleInput[0]);

        int r = Integer.parseInt(firstMultipleInput[1]);

        long result = calculator.answerQuery(s, l, r);
        System.out.println(result);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      } catch (IllegalArgumentException ex) {
        return;
      }
    });
    bufferedReader.close();
  }

  public static void scenario1() {
    var s = "week";
    var total = new MaximunPalindrome().answerQuery(s, 1, 4);
    validate("scenario1", total, 2);

  }

  public static void scenario2() {
    var s = "weekxs";
    var total = new MaximunPalindrome().answerQuery(s, 4, 6);
    validate("scenario2", total, 3);
  }

  public static void scenario3() {
    var s = "weeek";
    var total = new MaximunPalindrome().answerQuery(s, 1, 5);
    validate("scenario3", total, 3);
  }

  public static void scenario4() {
    var s = "weeeek";
    var total = new MaximunPalindrome().answerQuery(s, 1, 6);
    validate("scenario4", total, 2);
  }

  public static void scenario5() {
    var s = "weee";
    var total = new MaximunPalindrome().answerQuery(s, 1, 4);
    validate("scenario5", total, 2);
  }

  public static void scenario6() {
    var s = "daadabbadcabacbcccbdcccdbcccbbaadcbabbdaaaabbbdabdbbdcadaaacaadadacddabbbbbdcccbaabbbacacddbbbcbbdbd";
    var total = new MaximunPalindrome().answerQuery(s, 14, 17);
    validate("scenario6", total, 2);
  }

  private static void validate(String name, long value, long expected) {
    if (value != expected) {
      System.err.println(String.format(
          "The test \"%s\" has got value %s, but the the expected value is %s", name, value, expected));
    }
  }
}
