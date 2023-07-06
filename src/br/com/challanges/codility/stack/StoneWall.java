package br.com.challanges.codility.stack;

import java.util.Stack;

class StoneWall {

  public int solution(int h[]) {
    int count = 0;
    var stack = new Stack<Integer>();
    for (int i = 1; i < h.length; i++) {
      if (stack.isEmpty() || stack.peek() != h[i]) {
        stack.push(h[i]);
      } else if (stack.peek() > h[i]) {
        count++;
        stack.pop();
      }
    }
    return count + stack.size();
  }

  public static void main(String[] args) {
    System.out.println(new StoneWall().solution(new int[]{8, 8, 10, 10}));
  }
}
