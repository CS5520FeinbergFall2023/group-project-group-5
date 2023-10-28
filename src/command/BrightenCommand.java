package command;

/**
 * This is the class to represent the command that to brighten the specific image.
 */
public class BrightenCommand extends NewBrightnessCommand {
  private double value;
  private String oldImageName;
  private String newImageName;

  /**
   * The constructor of the command that increase the brightness of the image with oldImageName, and
   * name the corresponding new image as newImageName.
   *
   * @param value the value need to increase on the original image.
   * @param oldImageName the original name of the image that need to do the brighten operation.
   * @param newImageName he name of the resulting new image.
   * @throws IllegalArgumentException if the given filename or filepath is null, or the value is
   *                                  negative.
   */
  public BrightenCommand(double value, String oldImageName, String newImageName) {
    super(oldImageName, newImageName);
    if(value < 0) {
      throw new IllegalArgumentException("The value to brighten the image must be positive");
    }
    this.value = value;
  }
}
