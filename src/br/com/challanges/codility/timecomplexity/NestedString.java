package br.com.challanges.codility.timecomplexity;

import java.util.Stack;

public class NestedString {
  public int solution(String s) {
    if (s == null || s.isBlank()) {
      return 1;
    }
    var parenthesis = new Stack<Character>();

    for (char ch : s.toCharArray()) {
      if (ch == '(' || ch == '{' || ch == '[') {
        parenthesis.push(ch);
      } else if (ch == ')') {
        if (parenthesis.isEmpty() || parenthesis.peek() != '(') {
          return 0;
        }
        parenthesis.pop();
      } else if (ch == '}') {
        if (parenthesis.isEmpty() || parenthesis.peek() != '{') {
          return 0;
        }
        parenthesis.pop();
      } else if (ch == ']') {
        if (parenthesis.isEmpty() || parenthesis.peek() != '[') {
          return 0;
        }
        parenthesis.pop();
      }
    }
    return parenthesis.isEmpty() ? 1 : 0;
  }

  public static void main(String[] args) {
    System.out.println(new NestedString().solution(null));
    System.out.println(new NestedString().solution(""));
    System.out.println(new NestedString().solution("()"));
    System.out.println(new NestedString().solution("ab"));
    System.out.println(new NestedString().solution("a()"));
    System.out.println(new NestedString().solution("a()b"));
    System.out.println(new NestedString().solution("(ab)"));
    System.out.println(new NestedString().solution("a(ab)b"));
    System.out.println(new NestedString().solution("(   )b"));
    System.out.println(new NestedString().solution("((()))a"));
    System.out.println(new NestedString().solution("(() ())a"));
    System.out.println(new NestedString().solution("())"));
    System.out.println(new NestedString().solution("(()"));
    System.out.println(new NestedString().solution("{[()()]}"));
    System.out.println(new NestedString().solution("([)()]"));
  }
}
