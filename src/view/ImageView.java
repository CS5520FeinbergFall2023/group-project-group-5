package view;

import java.io.BufferedReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.Reader;

/**
 * This view is a component that responsible for displaying program's messages and getting user's
 * input from the console or the command file.
 */
public class ImageView {

  private final Appendable output;
  private final BufferedReader bufferedReader;

  /**
   * The constructor of the ImageView.
   *
   * @param reader the reader to read user input from.
   * @param output the output where messages would be displayed.
   */
  public ImageView(Reader reader, Appendable output) {
    this.bufferedReader = new BufferedReader(reader);
    this.output = output;
  }

  /**
   * Read commands that user input from the console.
   *
   * @return the command entered by the user.
   * @throws RuntimeException if there's an error reading the command.
   */
  public String getUserCommand() {
    try {
      return bufferedReader.readLine();
    } catch (IOException e) {
      throw new RuntimeException("Error appending message.", e);
    }
  }

  /**
   * Display a given message to the user through the console.
   *
   * @param message the message need to be displayed.
   * @throws RuntimeException if there's an error when displaying the message.
   */
  public void displayMessage(String message) {
    try {
      output.append(message).append("\n");

      // Flush the output if possible
      if (output instanceof Flushable) {
        ((Flushable) output).flush();
      }

    } catch (IOException e) {
      throw new RuntimeException("Error appending message.", e);
    }
  }

  /**
   * Gets and returns the mode choice made by the user from the console.
   *
   * @return the mode choice as an integer.
   */
  public int getModeChoice() {
    try {
      // Get the input of the user and transform it into Integer.
      return Integer.parseInt(bufferedReader.readLine());
    } catch (NumberFormatException | IOException e) {
      // Return -1 when there is a error.
      return -1;
    }
  }

  /**
   * Get the file path entered by user from console, read the file and return each lines in it.
   *
   * @return the file path entered by the user.
   */
  public String getFilePath() {
    try {
      displayMessage("Please enter the file path:");
      // Get and return the file path that user inputted.
      return bufferedReader.readLine();
    } catch (IOException e) {
      displayMessage("Error reading the file path.");
      return null;
    }
  }

}

