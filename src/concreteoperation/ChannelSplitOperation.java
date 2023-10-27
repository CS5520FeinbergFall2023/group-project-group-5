package concreteoperation;

import abstractoperation.LinearTransformOperation;
import model.Channel;
import model.Image;

/**
 * A class that represents channel splitting operations on images.
 */
public class ChannelSplitOperation extends LinearTransformOperation {
  private Image image;
  private Channel channel;

  /** Construct a channel splitting operation on a given image.
   * @param image the image to operate
   * @param channel the channel to split
   */
  public ChannelSplitOperation(Image image, Channel channel) {
    this.image = image;
    this.channel = channel;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
