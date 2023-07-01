package br.com.challanges.hackerrank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultChecker {
  private ResultChecker() {
  }

  public static List<String> check(String hackerrankPath, String yourPath) throws IOException {
    var hackerReader = new BufferedReader(new FileReader(hackerrankPath));
    var yourReader = new BufferedReader(new FileReader(yourPath));
    String hackerLine, yourline;
    int count = 0;
    var messages = new ArrayList<String>();
    while ((hackerLine = hackerReader.readLine()) != null) {
      count++;
      yourline = yourReader.readLine();
      if (!hackerLine.equals(yourline)) {
        messages.add("LIne " + count + ": Expected=" + hackerLine + " => Evaluated=" + yourline);
      }
    }
    return messages;
  }
}
