package command;

import model.ImageModel;

/**
 * This is the class to represent the command that acquire the blue-component of a specific image.
 */
public class BlueComponentCommand extends CommandImpl {
  private String oldImageName;
  private String newImageName;

  /**
   * The constructor of the command that getting the blue-component image with newImageName based
   * on the specific image called oldImageName.
   *
   * @param oldImageName The name of the specific image uses for creating a new blue-component
   *                     image.
   * @param newImageName The name of the corresponding new blue-component image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  public BlueComponentCommand(String oldImageName, String newImageName) {
    super(oldImageName,newImageName);
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.blueComponent(this.oldImageName, this.newImageName);
  }

}
