package command;

import model.ImageModel;

/**
 * This is the class to represent the command that split the specific image to three images, each
 * images belong to R,G,B channels respectively.
 */
public class RGBSplitCommand implements Command{
  private String oldImageName;
  private String redNewImageName;
  private String greenNewImageName;
  private String blueNewImageName;


  /**
   * Executing the split operation to obtain the three corresponding channels after the specific
   * image is divided into R, G, and B channels, and name them redNewImageName, greenNewImageName,
   * and blueNewImageName respectively.
   *
   * @param oldImageName the original name of the image that need to do the split operation.
   * @param redNewImageName the name of the resulting red channel image.
   * @param greenNewImageName the name of the resulting green channel image.
   * @param blueNewImageName the name of the resulting blue channel image.
   * @throws IllegalArgumentException if the given names is null.
   */
  public RGBSplitCommand(String oldImageName, String redNewImageName, String greenNewImageName,
                         String blueNewImageName) {
    if (oldImageName == null || redNewImageName == null || greenNewImageName == null
          || blueNewImageName == null) {
      throw new IllegalArgumentException("The image name could not be null");
    }
    this.oldImageName = oldImageName;
    this.redNewImageName = redNewImageName;
    this.greenNewImageName = greenNewImageName;
    this.blueNewImageName = blueNewImageName;
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.rgbSplit(oldImageName, redNewImageName,greenNewImageName,blueNewImageName);
  }
}
