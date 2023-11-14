package service;

import java.util.Arrays;

import model.Axis;
import model.Channel;
import model.compressor.HaarWaveletCompressor;
import model.image.Image;
import model.pixel.Pixel;

/**
 * This class performs image operations.
 */
public class ImageService {

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
   * @param image      the image to operate on
   * @param percentage the split operation percentage (the first part will be operated while the
   *                   second part remains unchanged)
   * @param splitAxis  the axis to split (X means split the images as left and right)
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */

  public Image blur(Image image, float percentage, Axis splitAxis) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    if (percentage < 0 || percentage > 1) {
      throw new IllegalArgumentException("The split percentage should be within [0,1]");
    }
    float[][] blur = new float[][]{
        {0.0625f, 0.125f, 0.0625f},
        {0.125f, 0.25f, 0.125f},
        {0.0625f, 0.125f, 0.0625f}
    };
    Image[] splitImages = image.split(percentage, splitAxis);
    if (splitImages[0] == null) {
      return splitImages[1];
    } else if (splitImages[1] == null) {
      return splitImages[0].filtering(blur);
    } else {
      splitImages[0] = splitImages[0].filtering(blur);
      return splitImages[0].combineImages(splitImages[1], splitAxis);
    }
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
   * Greyscale an image.
   *
   * @param image      the image to operate on
   * @param percentage the split operation percentage (the first part will be operated while the
   *                   second part remains unchanged)
   * @param splitAxis  the axis to split (X means split the images as left and right)
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image greyscale(Image image, float percentage, Axis splitAxis)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    if (percentage < 0 || percentage > 1) {
      throw new IllegalArgumentException("The split percentage should be within [0,1]");
    }
    float[][] greyscale = new float[][]{
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    };
    Image[] splitImages = image.split(percentage, splitAxis);
    if (splitImages[0] == null) {
      return splitImages[1];
    } else if (splitImages[1] == null) {
      return splitImages[0].matrixMultiplication(greyscale);
    } else {
      splitImages[0] = splitImages[0].matrixMultiplication(greyscale);
      return splitImages[0].combineImages(splitImages[1], splitAxis);
    }
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
   * Split channels and result in $channelCount greyscale images.
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
      float[][] matrix = new float[image.getChannels().length][image.getChannels().length];
      for (int row = 0; row < matrix.length; row++) {
        matrix[row][i] = 1;
      }
      result[i] = image.channelSplit(image.getChannels()[i]).matrixMultiplication(matrix);
    }
    return result;
  }

  /**
   * Combine greyscale images each representing one channel to one multicolor image.
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
    if (channels.length != images.length) {
      throw new IllegalArgumentException("Image number is not the same as channel number");
    }
    Image[] splits = new Image[images.length];
    for (int i = 0; i < images.length; i++) {
      if (!images[i].isGreyscale()) {
        throw new IllegalArgumentException("Only take greyscale images");
      }
      splits[i] = images[i].channelSplit(channels[i]);
    }
    return Arrays.stream(splits).reduce(Image::addition).orElse(null);
  }

  /**
   * Sharpen an image.
   *
   * @param image      the image to operate on
   * @param percentage the split operation percentage (the first part will be operated while the
   *                   second part remains unchanged)
   * @param splitAxis  the axis to split (X means split the images as left and right)
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image sharpen(Image image, float percentage, Axis splitAxis)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    if (percentage < 0 || percentage > 1) {
      throw new IllegalArgumentException("The split percentage should be within [0,1]");
    }
    float[][] sharpen = new float[][]{
        {-0.125f, -0.125f, -0.125f, -0.125f, -0.125f},
        {-0.125f, 0.25f, 0.25f, 0.25f, -0.125f},
        {-0.125f, 0.25f, 1f, 0.25f, -0.125f},
        {-0.125f, 0.25f, 0.25f, 0.25f, -0.125f},
        {-0.125f, -0.125f, -0.125f, -0.125f, -0.125f},
    };
    Image[] splitImages = image.split(percentage, splitAxis);
    if (splitImages[0] == null) {
      return splitImages[1];
    } else if (splitImages[1] == null) {
      return splitImages[0].filtering(sharpen);
    } else {
      splitImages[0] = splitImages[0].filtering(sharpen);
      return splitImages[0].combineImages(splitImages[1], splitAxis);
    }
  }


  /**
   * Get sepia version of an image.
   *
   * @param image      the image to operate on
   * @param percentage the split operation percentage (the first part will be operated while the
   *                   second part remains unchanged)
   * @param splitAxis  the axis to split (X means split the images as left and right)
   * @return the result image
   * @throws IllegalArgumentException when given argument is null or not legal
   */
  public Image getSepia(Image image, float percentage, Axis splitAxis)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    if (percentage < 0 || percentage > 1) {
      throw new IllegalArgumentException("The split percentage should be within [0,1]");
    }
    float[][] sepia = new float[][]
        {
            {0.393f, 0.769f, 0.189f},
            {0.349f, 0.686f, 0.168f},
            {0.272f, 0.534f, 0.131f}
        };
    Image[] splitImages = image.split(percentage, splitAxis);
    if (splitImages[0] == null) {
      return splitImages[1];
    } else if (splitImages[1] == null) {
      return splitImages[0].matrixMultiplication(sepia);
    } else {
      splitImages[0] = splitImages[0].matrixMultiplication(sepia);
      return splitImages[0].combineImages(splitImages[1], splitAxis);
    }
  }

  /**
   * Compress images with Haar Wavelet compressor.
   *
   * @param image the image to compress
   * @param ratio the compress ratio ([0,1])
   * @return the compressed image
   * @throws IllegalArgumentException if the given argument is null or not legal
   */
  public Image haarWaveletCompress(Image image, float ratio) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    return image.compress(HaarWaveletCompressor.getInstance(), ratio);
  }

  /**
   * Get histogram of the image.
   *
   * @param image the image to get histogram on
   * @return histogram of the image
   * @throws IllegalArgumentException if the given argument is null or not legal
   */
  public Image getHistogram(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    return image.getHistogram();
  }

  /**
   * Color correct the image.
   *
   * @param image      the image to correct
   * @param percentage the split operation percentage (the first part will be operated while the
   *                   second part remains unchanged)
   * @param splitAxis  the axis to split (X means split the images as left and right)
   * @return the corrected image
   * @throws IllegalArgumentException if the given argument is null or not legal
   */
  public Image colorCorrect(Image image, float percentage, Axis splitAxis)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    Image[] splitImages = image.split(percentage, splitAxis);
    if (splitImages[0] == null) {
      return splitImages[1];
    } else if (splitImages[1] == null) {
      return splitImages[0].colorCorrect();
    } else {
      splitImages[0] = splitImages[0].colorCorrect();
      return splitImages[0].combineImages(splitImages[1], splitAxis);
    }
  }

  /**
   * Perform level adjustment on the image.
   *
   * @param image      the image to operate on
   * @param black      the positions of the black (shadow) point on the horizontal axis
   * @param mid        the positions of the middle point on the horizontal axis
   * @param white      the positions of the white (highlight) point on the horizontal axis
   * @param percentage the split operation percentage (the first part will be operated while the
   *                   second part remains unchanged)
   * @param splitAxis  the axis to split (X means split the images as left and right)
   * @return the adjusted image
   * @throws IllegalArgumentException if the given argument is null or not legal
   */
  public Image levelAdjustment(Image image, float black, float mid, float white, float percentage,
                               Axis splitAxis)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image is null");
    }
    Image[] splitImages = image.split(percentage, splitAxis);
    if (splitImages[0] == null) {
      return splitImages[1];
    } else if (splitImages[1] == null) {
      return splitImages[0].levelAdjustment(black, mid, white);
    } else {
      splitImages[0] = splitImages[0].levelAdjustment(black, mid, white);
      return splitImages[0].combineImages(splitImages[1], splitAxis);
    }
  }

}
