package controller;


import java.io.IOException;

/**
 * This controller allows user to input commands or files,then this controller would pass commands
 * to the model and model would execute corresponding operations.
 */
public interface TextScriptController {

  /**
   * Acquire the command and execute these commands.
   *
   * @throws IllegalArgumentException if the command that user inputting is null.
   */
  void executeCommand(String command) throws IOException;

}
