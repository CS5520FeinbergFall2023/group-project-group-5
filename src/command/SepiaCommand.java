package command;

import model.ImageModel;

/**
 * This is the class to represent the command that to sepia the specific image horizontally.
 */
public class SepiaCommand extends CommandImpl{

  private String oldImageName;
  private String newImageName;

  /**
   * The constructor of the command that sepia the image with oldImageName horizontally, and
   * name the corresponding new image as newImageName.
   *
   * @param oldImageName the original name of the image that need to do the sepia operation.
   * @param newImageName he name of the resulting new image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  protected SepiaCommand(String oldImageName, String newImageName) {
    super(oldImageName, newImageName);
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.sepia(this.oldImageName, this.newImageName);
  }
}
