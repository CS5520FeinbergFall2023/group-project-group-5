package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class ImageView {
//
//  public ImageView() {
//    this.scanner = new Scanner(System.in);
//  }
//
//  public String getUserCommand(){
//    System.out.print("Please enter command: ");
//    return scanner.nextLine();
//  }
//
//  public void displayMessage(String message) {
//    System.out.println(message);
//  }

  private final Scanner scanner;
  private final Appendable output;
  private BufferedReader bufferedReader;

  public ImageView(Readable readable, Appendable output) {
    this.scanner = new Scanner(readable);
    this.output = output;
    this.bufferedReader = new BufferedReader((Reader) readable);
  }

  public String getUserCommand() {
    try {
      output.append("Please enter command: ");

    } catch (IOException e) {
      throw new RuntimeException("Error appending message.", e);
    }
    if (scanner.hasNextLine()) {
      return scanner.nextLine();
    } else {
      return null;
    }
  }

  public void displayMessage(String message) {
    try {
      output.append(message).append("\n");
    } catch (IOException e) {
      throw new RuntimeException("Error appending message.", e);
    }
  }

  public int getModeChoice() {
    displayMessage("Please select which mode you want to input the commands: 1. Console " +
          "2. From file");

    try {
      int choice = Integer.parseInt(bufferedReader.readLine());
      displayMessage("Please enter the command.");
      return choice;
    } catch (NumberFormatException | IOException e) {
      displayMessage("Invalid choice.");
      return 0;
    }

  }

  public String getFilePath() {
    displayMessage("Please enter the file path.");
    try {
      return bufferedReader.readLine();
    } catch (IOException e) {
      displayMessage("Error to read the file path.");
      return "";
    }

  }
}



