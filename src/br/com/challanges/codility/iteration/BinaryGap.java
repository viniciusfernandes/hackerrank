package br.com.challanges.codility.iteration;

public class BinaryGap {
  public int solution(int n) {
    int bit;
    int count = 0, max = 0;
    boolean init = false;
    while (n > 0) {
      bit = n % 2;
      if (init && bit == 1) {
        if (count > max) {
          max = count;
        }
        count = 0;
      } else if (init && bit == 0) {
        count++;
      } else if (bit == 1) {
        count = 0;
        init = true;
      }
      n /= 2;
    }
    return max;
  }

  public static void main(String[] args) {
    System.out.println(new BinaryGap().solution(0));
    System.out.println(new BinaryGap().solution(1));
    System.out.println(new BinaryGap().solution(9));
    System.out.println(new BinaryGap().solution(15));
    System.out.println(new BinaryGap().solution(20));
    System.out.println(new BinaryGap().solution(32));
    System.out.println(new BinaryGap().solution(529));
    System.out.println(new BinaryGap().solution(1041));
  }
}
