package br.com.challanges.codility.iteration;

public class Frog {

  public int solution(int X, int[] A) {
    int[] positions = new int[X + 1];
    int uncovered = X;

    for (int i = 0; i < A.length; i++) {
      int leafPosition = A[i];

      if (leafPosition <= X && positions[leafPosition] == 0) {
        positions[leafPosition] = 1;
        uncovered--;

        if (uncovered == 0)
          return i;
      }
    }

    return -1;

  }

  public static void main(String[] args) {
    System.out.println(new Frog().solution(5, new int[]{1, 3, 1, 4, 2, 3, 5, 4, 5}));
    System.out.println(new Frog().solution(2, new int[]{2, 2, 2, 2, 2, 2, 2}));
    System.out.println(new Frog().solution(20, new int[]{3, 2, 1}));
  }

}

