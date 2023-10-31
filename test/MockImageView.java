import java.io.BufferedReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import view.ImageView;

/**
 * This is a "mock" ImageView class which extends the ImageView class. In this class,
 * overwriting functions to help to test the controller, to examine whether the controller component
 * correctly receives the command that user inputted or not.
 */
public class MockImageView extends ImageView {

  private List<String> messagesDisplayed = new ArrayList<>();
  private final Appendable output;
  private final BufferedReader bufferedReader;

  /**
   * The constructor of the ImageView.
   *
   * @param reader the reader to read user input from.
   * @param output the output where messages would be displayed.
   */
  public MockImageView(Reader reader, Appendable output) {
    super(reader, output);
    this.bufferedReader = new BufferedReader(reader);
    this.output = output;
  }

  /**
   * Add the given message to the list of messages displayed.
   *
   * @param message The message to be displayed.
   */
  @Override
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
   * Return a list of messages that have been displayed.
   *
   * @return A list containing all the messages that have been displayed.
   */
  public List<String> getMessagesDisplayed() {
    return messagesDisplayed;
  }

  @Override
  public String getUserCommand() {
    try {
      return bufferedReader.readLine();
    } catch (IOException e) {
      return "exit";
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
