package command;

import model.ImageModel;

/**
 * This is the class to represent the command that brighten or darken the image.
 */
public abstract class NewBrightnessCommand extends CommandImpl {
  protected double value;
  protected String oldImageName;
  protected String newImageName;

  /**
   * The constructor of the command that changing the brightness of the image with oldImageName, and
   * name the corresponding new image as newImageName.
   *
   * @param oldImageName the original name of the image that need to do the brighten operation.
   * @param newImageName he name of the resulting new image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  protected NewBrightnessCommand(String oldImageName, String newImageName ) {
    super(oldImageName,newImageName);
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.brighten(this.value,this.oldImageName, this.newImageName);
  }

}
