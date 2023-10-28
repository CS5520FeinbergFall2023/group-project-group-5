package command;

import model.ImageModel;

/**
 * This is the class to represent the command that gather three images from R,G,B channels
 * respectively to one new image.
 */
public class RGBCombineCommand implements Command {
  private String redImageName;
  private String greenImageName;
  private String blueImageName;
  private String oneNewImageName;

  /**
   * The constructor of the command that combining three images from three channels respectively
   * with their names and create a combined image named oneNewImageName.
   *
   * @param redImageName the red channel image's name.
   * @param greenImageName the green channel image's name.
   * @param blueImageName the blue channel image's name.
   * @param oneNewImageName the name of the combined image.
   * @throws IllegalArgumentException if the given image names are null.
   */
  public RGBCombineCommand(String redImageName, String greenImageName, String blueImageName,
                           String oneNewImageName) {
    if (redImageName == null || greenImageName == null || blueImageName == null
          || oneNewImageName == null) {
      throw new IllegalArgumentException("The image name could not be null");
    }
    this.redImageName = redImageName;
    this.greenImageName = greenImageName;
    this.blueImageName = blueImageName;
    this.oneNewImageName = oneNewImageName;
  }

  @Override
  public void execute(ImageModel model) {

    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.rgbCombine(redImageName, greenImageName, blueImageName,oneNewImageName);

  }
}
