import java.io.Flushable;
import java.io.IOException;
import java.util.Arrays;

import model.Axis;
import model.Channel;
import model.image.Image;
import model.image.MyImage;
import service.ImageService;

/**
 * This is a "mock" ImageService class which extends the ImageService class. In this class,
 * overwriting functions to help to test the controller, to examine whether the controller component
 * correctly receives the command that user inputted or not.
 */
public class MockImageService extends ImageService {
  private final Appendable output;

  public MockImageService(Appendable output) {
    this.output = output;
  }

  private void displayMessage(String message) {
    try {
      output.append(message).append("\n");

      // Flush the output if possible
      if (output instanceof Flushable) {
        ((Flushable) output).flush();
      }

    } catch (IOException e) {
      throw new RuntimeException("Error appending message.", e);
    }
  }

  /**
   * Get one certain channel of the image (result in one colored image).
   *
   * @param image   the image to operate on.
   * @param channel the given channel.
   * @return the split colored image.
   */
  @Override
  public Image splitComponent(Image image, Channel channel) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to be split and their corresponding channels.");
    return new MyImage("res/city_small_red_channel_greyscale.png");
  }

  /**
   * Blur an image.
   *
   * @param image the image to operate on.
   * @return the result image.
   */
  @Override
  public Image blur(Image image) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to be blurred, now start to blur.");
    return new MyImage("test/img/cupcake_blurOnce.png");
  }

  /**
   * Get value of the image.
   *
   * @param image the image to operate on.
   * @return the result image.
   */
  @Override
  public Image getValue(Image image) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to get the value-component, now start to do that.");
    return new MyImage("res/city_small_value.png");
  }

  /**
   * Get intensity of the image.
   *
   * @param image the image to operate on.
   * @return the result image.
   */
  @Override
  public Image getIntensity(Image image) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to get the intensity-component, now start to do "
                   + "that.");
    return new MyImage("res/city_small_intensity.png");
  }

  /**
   * Get luma of an image.
   *
   * @param image the image to operate on.
   * @return the result image.
   */
  @Override
  public Image getLuma(Image image) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to get the luma-component, now start to do that.");
    return new MyImage("res/city_small_luma.png");
  }

  /**
   * Flip an image.
   *
   * @param image the image to operate on.
   * @param axis  the axis to flip on.
   * @return the result image.
   */
  @Override
  public Image flip(Image image, Axis axis) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to be flipped, now start to flip.");
    return new MyImage("res/car_doubleFlipped.png");
  }


  /**
   * Brighten an image with given delta.
   *
   * @param image the image to operate on.
   * @param delta the amount to brighten.
   * @return the result image.
   */
  @Override
  public Image brighten(Image image, float delta) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to be brightened, now start to brighten.");
    return new MyImage("test/img/trichromatic/simple-4.ppm");
  }

  /**
   * Result in channelCount greyscale images.
   *
   * @param image the image to operate on.
   * @return the result images.
   */
  @Override
  public Image[] splitChannel(Image image) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to be split, now start to split.");
    Image myImage1 = new MyImage("test/img/split/rose_onlyRed.jpg");
    Image myImage2 = new MyImage("test/img/split/rose_onlyGreen.jpg");
    Image myImage3 = new MyImage("test/img/split/rose_onlyBlue.jpg");
    Image[] result = new Image[3];
    result[0] = myImage1;
    result[1] = myImage2;
    result[2] = myImage3;
    return result;


  }

  /**
   * Combine images each representing one monochrome channel to one multicolor image.
   *
   * @param channels the channels to combine.
   * @param images   the images to combine, corresponding to channels.
   * @return the result image.
   */
  @Override
  public Image combineChannels(Channel[] channels, Image[] images) {
    displayMessage(Arrays.toString(channels));
    for (int i = 0; i < images.length; i++) {
      displayMessage(images[i].hashCode() + "");
    }
    displayMessage("Receive three images need to combine together, now start to combine.");
    return new MyImage("test/img/split/rose.ppm");
  }

  /**
   * Sharpen an image.
   *
   * @param image the image to operate on.
   * @return the result image.
   */
  @Override
  public Image sharpen(Image image) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to be sharpened, now start to sharpen.");
    return new MyImage("test/img/cupcake_sharpenOnce.png");
  }

  /**
   * Get sepia version of an image.
   *
   * @param image the image to operate on.
   * @return the result image.
   */
  @Override
  public Image getSepia(Image image) {
    displayMessage(image.hashCode() + "");
    displayMessage("Receive the image need to get its sepia, now start to do that.");
    return new MyImage("test/img/city_small_sepia.png");
  }
}
