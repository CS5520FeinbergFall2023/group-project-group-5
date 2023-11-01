package service;

import model.Axis;
import model.Channel;
import model.Image;
import model.Pixel;

/**
 * This class performs image operations.
 */
public class ImageService {
  public ImageService() {
  }

  /**
   * Get one certain channel of the image (result in one colored image).
   *
   * @param image   the image to operate on
   * @param channel the given channel
   * @return the split colored image
   * @throws IllegalArgumentException when given argument is null or not legal
   */

  public Image splitComponent(Image image, Channel channel) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    return image.channelSplit(channel);
  }

  /**
   * Blur an image.
   *
   * @param image the image to operate on
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */

  public Image blur(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    float[][] blur = new float[][]{
        {0.0625f, 0.125f, 0.0625f},
        {0.125f, 0.25f, 0.125f},
        {0.0625f, 0.125f, 0.0625f}
    };
    return image.filtering(blur);
  }

  /**
   * Get value of the image.
   *
   * @param image the image to operate on
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */

  public Image getValue(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    return image.mapElement(Pixel::max);
  }

  /**
   * Get intensity of the image.
   *
   * @param image the image to operate on
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image getIntensity(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    return image.mapElement(Pixel::avg);
  }

  /**
   * Get luma of an image.
   *
   * @param image the image to operate on
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image getLuma(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    float[][] luma = new float[][]{
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    };
    return image.matrixMultiplication(luma);
  }

  /**
   * Flip an image.
   *
   * @param image the image to operate on
   * @param axis  the axis to flip on
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image flip(Image image, Axis axis) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    if (axis == null) {
      throw new IllegalArgumentException("The axis is null");
    }
    int[][] matrix;
    if (axis == Axis.Y) {
      //horizontal flip
      matrix = new int[][]{
          {-1, 0, image.getWidth() - 1},
          {0, 1, 0}
      };
    } else {
      //vertical flip
      matrix = new int[][]{
          {1, 0, 0},
          {0, -1, image.getHeight() - 1}
      };
    }
    return image.projectCoordinate(matrix);
  }

  /**
   * Brighten/darken an image with given delta. If delta is negative, then it's darken; if delta is
   * positive, it's brighten.
   *
   * @param image the image to operate on
   * @param delta the amount to brighten/darken
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image brighten(Image image, float delta) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    float[] matrix = new float[]{delta, delta, delta};
    return image.imgArrayAddition(matrix);
  }


  /**
   * Greyscale an image. In this specific case, greyscale uses the same matrix as luma. However,
   * greyscale can be a general term that only guarantees same value in every channel. Although it's
   * currently not required for user to call greyscale, so it's only a private function, it's still
   * better idea to list this function as individual function than luma, so even when we need apply
   * different interpretation for greyscale, we can easily do so without dependency on luma.
   *
   * @param image the image to operate on
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  private Image greyscale(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    float[][] greyscale = new float[][]{
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    };
    return image.matrixMultiplication(greyscale);
  }

  /**
   * Result in channelCount greyscale images.
   *
   * @param image the image to operate on
   * @return the result images
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image[] splitChannel(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    Image[] result = new Image[image.getChannels().length];
    for (int i = 0; i < result.length; i++) {
      result[i] = greyscale(image.channelSplit(image.getChannels()[i]));
    }
    return result;
  }

  /**
   * Combine images each representing one monochrome channel to one multicolor image.
   *
   * @param channels the channels to combine
   * @param images   the images to combine, corresponding to channels
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */

  public Image combineChannels(Channel[] channels, Image[] images) throws IllegalArgumentException {
    if (channels.length == 0) {
      throw new IllegalArgumentException("There has to at least one channel.");
    }
    if (images.length == 0) {
      throw new IllegalArgumentException("There has to at least one image.");
    }
    if (images[0].isMonochromeOfChannel(channels[0])) {
      Image result = images[0];

      for (int i = 1; i < images.length; i++) {
        if (images[i].isMonochromeOfChannel(channels[i])) {
          result = result.addition(images[i]);

        } else {
          throw new IllegalArgumentException("Input image should be monochrome of corresponding "
                                             + "channel.");
        }
      }

      return result;
    }
    throw new IllegalArgumentException("Input image should be monochrome of corresponding channel"
                                       + ".");
  }

  /**
   * Sharpen an image.
   *
   * @param image the image to operate on
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image sharpen(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    float[][] sharpen = new float[][]{
        {-0.125f, -0.125f, -0.125f, -0.125f, -0.125f},
        {-0.125f, 0.25f, 0.25f, 0.25f, -0.125f},
        {-0.125f, 0.25f, 1f, 0.25f, -0.125f},
        {-0.125f, 0.25f, 0.25f, 0.25f, -0.125f},
        {-0.125f, -0.125f, -0.125f, -0.125f, -0.125f},
    };
    return image.filtering(sharpen);
  }


  /**
   * Get sepia version of an image.
   *
   * @param image the image to operate on
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image getSepia(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    float[][] sepia = new float[][]
        {
            {0.393f, 0.769f, 0.189f},
            {0.349f, 0.686f, 0.168f},
            {0.272f, 0.534f, 0.131f}
        };
    return image.matrixMultiplication(sepia);
  }

}
