package command;

import model.ImageModel;

/**
 * This is the class to represent the command that to flip the specific image horizontally.
 */
public class HorizontalFlipCommand extends CommandImpl{

  private String oldImageName;
  private String newImageName;

  /**
   * The constructor of the command that flip the image with oldImageName horizontally, and
   * name the corresponding new image as newImageName.
   *
   * @param oldImageName the original name of the image that need to do the flip-horizontally
   *                     operation.
   * @param newImageName he name of the resulting new image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  protected HorizontalFlipCommand(String oldImageName, String newImageName) {
    super(oldImageName, newImageName);
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.horizontalFlip(this.oldImageName, this.newImageName);
  }
}
