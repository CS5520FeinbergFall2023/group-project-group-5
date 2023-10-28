package command;

import model.ImageModel;

/**
 * This is the class to represent the command that to sharpen the specific image horizontally.
 */
public class SharpenCommand extends CommandImpl{

  private String oldImageName;
  private String newImageName;

  /**
   * The constructor of the command that sharpen the image with oldImageName horizontally, and
   * name the corresponding new image as newImageName.
   *
   * @param oldImageName the original name of the image that need to do the sharpen operation.
   * @param newImageName he name of the resulting new image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  protected SharpenCommand(String oldImageName, String newImageName) {
    super(oldImageName, newImageName);
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.sharpen(this.oldImageName, this.newImageName);
  }
}
