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
   */
  public Image splitComponent(Image image, Channel channel) {
    return image.channelSplit(channel);
  }

  /**
   * Blur an image.
   *
   * @param image the image to operate on
   * @return the result image
   */
  public Image blur(Image image) {
    float[][] blurFilter = new float[][]{
        {0.0625f, 0.125f, 0.0625f},
        {0.125f, 0.25f, 0.125f},
        {0.0625f, 0.125f, 0.0625f}
    };
    return image.filtering(blurFilter);
  }

  /**
   * @param image the image to operate on
   * @return the result image
   */
  public Image getValue(Image image) {
    return image.mapElement(Pixel::avg);
  }

  /**
   * @param image the image to operate on
   * @return the result image
   */
  public Image getIntensity(Image image) {
    return image.mapElement(Pixel::max);
  }

  /**
   * @param image the image to operate on
   * @return the result image
   */
  public Image getLuma(Image image) {
    float[][] luma = new float[][]{
        {0.2126f, 0.7152f, 0.722f},
        {0.2126f, 0.7152f, 0.722f},
        {0.2126f, 0.7152f, 0.722f}
    };
    return image.arrayMultiplication(luma);
  }

  /**
   * @param image the image to operate on
   * @param axis  the axis to flip on
   * @return the result image
   */
  public Image flip(Image image, Axis axis) {
    int[][] matrix;
    if (axis == Axis.X) {
      matrix = new int[][]{
          {-1, 0, image.getWidth() - 1},
          {0, 1, 0}
      };
    } else {
      matrix = new int[][]{
          {1, 0, 0},
          {0, -1, image.getHeight() - 1}
      };
    }
    return image.projectCoordinate(matrix);
  }

  /**
   * @param image the image to operate on
   * @param delta the amount to brighten
   * @return the result image
   */
  public Image brighten(Image image, float delta) {
    if (delta < 0) {
      throw new IllegalArgumentException("delta should not be negative for brightening");
    }
    float[] matrix = new float[]{delta, delta, delta};
    return image.imgArrayAddition(matrix);
  }

  /**
   * @param image the image to operate on
   * @param delta the amount to darken
   * @return the result image
   */
  public Image darken(Image image, float delta) {
    if (delta > 0) {
      throw new IllegalArgumentException("delta should not be positive for darkening");
    }
    float[] matrix = new float[]{delta, delta, delta};
    return image.imgArrayAddition(matrix);
  }

  /**
   * @param image the image to operate on
   * @return the result image
   */
  private Image greyscale(Image image) {
    //todo:same as luma?
    float[][] greyscale = new float[][]{
        {0.2126f, 0.7152f, 0.722f},
        {0.2126f, 0.7152f, 0.722f},
        {0.2126f, 0.7152f, 0.722f}
    };
    return image.arrayMultiplication(greyscale);
  }

  /**
   * Result in $channelCount greyscale images.
   *
   * @param image the image to operate on
   * @return the result images
   */
  public Image[] splitChannel(Image image) {
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
   */
  public Image combineChannels(Channel[] channels, Image[] images) {
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
   */
  public Image sharpen(Image image) {
    float[][] sharpen = new float[][]{
        {-0.125f, -0.125f, -0.125f, -0.125f, -0.125f},
        {-0.125f, 0.25f, 0.25f, 0.25f, 0.25f},
        {-0.125f, 0.25f, 1f, 0.25f, -0.125f},
        {-0.125f, -0.25f, 0.25f, 0.25f, -0.125f},
        {-0.125f, -0.125f, -0.125f, -0.125f, -0.125f},
    };
    return image.filtering(sharpen);
  }


  /**
   * Get sepia version of an image
   *
   * @param image the image to operate on
   * @return the result image
   */
  public Image getSepia(Image image) {
    float[][] sepia = new float[][]
        {
            {0.393f, 0.769f, 0.189f},
            {0.349f, 0.686f, 0.168f},
            {0.272f, 0.534f, 0.131f}
        };
    return image.arrayMultiplication(sepia);
  }

}
