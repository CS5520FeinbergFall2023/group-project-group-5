package command;

import model.ImageModel;

/**
 * This is the class to represent the command that to get the intensity component image of the
 * original image.
 */
public class IntensityComponentCommand extends CommandImpl {
  private String oldImageName;
  private String newImageName;

  /**
   * The constructor of the command that getting the image with oldImageName's intensity component,
   * and name the corresponding new image as newImageName.
   *
   * @param oldImageName the original name of the image that need to do the intensity-component
   *                     operation.
   * @param newImageName he name of the resulting new image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  protected IntensityComponentCommand(String oldImageName, String newImageName) {
    super(oldImageName, newImageName);
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.intensityComponentGrey(this.oldImageName, this.newImageName);
  }
}
