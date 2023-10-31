package service;

import model.Axis;
import model.Channel;
import model.Image;

/**
 * The interface that conclude image operations.
 */
public interface ImageServiceInterface {
  /**
   * Get one certain channel of the image (result in one colored image).
   *
   * @param image   the image to operate on
   * @param channel the given channel
   * @return the split colored image
   */
  Image splitComponent(Image image, Channel channel);

  /**
   * Blur an image.
   *
   * @param image the image to operate on
   * @return the result image
   */
  Image blur(Image image);

  /**
   * Get value of the image.
   *
   * @param image the image to operate on
   * @return the result image
   */
  Image getValue(Image image);

  /**
   * Get intensity of the image.
   *
   * @param image the image to operate on
   * @return the result image
   */
  Image getIntensity(Image image);

  /**
   * Get luma of an image.
   *
   * @param image the image to operate on
   * @return the result image
   */
  Image getLuma(Image image);

  /**
   * Flip an image.
   *
   * @param image the image to operate on
   * @param axis  the axis to flip on
   * @return the result image
   */
  Image flip(Image image, Axis axis);

  /**
   * Brighten/darken an image with given delta. If delta is negative, then it's darken; if delta
   * is positive, it's brighten.
   *
   * @param image the image to operate on
   * @param delta the amount to brighten/darken
   * @return the result image
   */
  Image brighten(Image image, float delta);

  /**
   * Result in channelCount greyscale images.
   *
   * @param image the image to operate on
   * @return the result images
   */
  Image[] splitChannel(Image image);

  /**
   * Combine images each representing one monochrome channel to one multicolor image.
   *
   * @param channels the channels to combine
   * @param images   the images to combine, corresponding to channels
   * @return the result image
   */
  Image combineChannels(Channel[] channels, Image[] images);

  /**
   * Sharpen an image.
   *
   * @param image the image to operate on
   * @return the result image
   */
  Image sharpen(Image image);

  /**
   * Get sepia version of an image.
   *
   * @param image the image to operate on
   * @return the result image
   */
  Image getSepia(Image image);

}
