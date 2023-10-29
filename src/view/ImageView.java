package view;
import java.util.Scanner;
public class ImageView {
  private final Scanner scanner;

  public ImageView() {
    this.scanner = new Scanner(System.in);
  }

  public String getUserCommand(){
    System.out.print("Please enter command: ");
    return scanner.nextLine();
  }

  public void displayMessage(String message) {
    System.out.println(message);
  }
}
