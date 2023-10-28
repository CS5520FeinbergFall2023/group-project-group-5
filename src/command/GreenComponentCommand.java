package command;

import model.ImageModel;

/**
 * This is the class to represent the command that acquire the green-component of a specific image.
 */
public class GreenComponentCommand extends CommandImpl {
  private String oldImageName;
  private String newImageName;

  /**
   * The constructor of the command that getting the green-component image with newImageName based
   * on the specific image called oldImageName.
   *
   * @param oldImageName The name of the specific image uses for creating a new green-component
   *                     image.
   * @param newImageName The name of the corresponding new green-component image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  public GreenComponentCommand(String oldImageName, String newImageName) {
    super(oldImageName,newImageName);
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.greenComponent(this.oldImageName, this.newImageName);
  }

}
