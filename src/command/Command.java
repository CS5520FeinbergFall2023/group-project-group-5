package command;

import model.ImageModel;

/**
 * This interface represents the commands that the user may enter, and these commands will be passed
 * to the model so the model would know what operations should perform.
 *
 */
public interface Command {



  /**
   * Execute commands by using the ImageModel model.
   *
   * @param model the model of this program.
   * @throws IllegalArgumentException if the given model is null or if parameters produce invalid
   *                                  results, like invalid file path.
   */
  void execute(ImageModel model);
}
